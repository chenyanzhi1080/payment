package com.xiaoerzuche.biz.payment.web.front;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xiaoerzuche.biz.payment.PaymentContext;
import com.xiaoerzuche.biz.payment.common.BackVerification;
import com.xiaoerzuche.biz.payment.common.PaymentConf;
import com.xiaoerzuche.biz.payment.enu.PayType;
import com.xiaoerzuche.biz.payment.enu.TranType;
import com.xiaoerzuche.biz.payment.model.PayOrder;
import com.xiaoerzuche.biz.payment.service.PamentSessionService;
import com.xiaoerzuche.biz.payment.service.PayAuthService;
import com.xiaoerzuche.biz.payment.service.PayOrderService;
import com.xiaoerzuche.biz.payment.util.DESUtil;
import com.xiaoerzuche.biz.payment.vo.PayVo;
import com.xiaoerzuche.biz.payment.vo.QueryVo;
import com.xiaoerzuche.common.enu.ErrorCode;
import com.xiaoerzuche.common.exception.ErrorCodeException;
import com.xiaoerzuche.common.util.CheckUtil;
import com.xiaoerzuche.common.util.JsonUtil;

import net.sf.json.JSONObject;

/** 
* 跳转到手机网站支付页面
* @author: cyz
* @version: payment
* @time: 2016年5月18日
* 
*/
@Controller
@RequestMapping("/front/paymentRedirect/")
public class RedirectPayPageController {
	private static final Logger logger = LoggerFactory.getLogger(RedirectPayPageController.class);
	@Autowired
	private PaymentContext		paymentContext;
	@Autowired
	private BackVerification	backVerification;
	@Autowired
	private PayAuthService payAuthService;
	@Autowired
	private PayOrderService payOrderService;
	@Autowired 
	private PamentSessionService pamentSessionService;
	@Value("${addresses}")
	private String addresses;
	private static final String RETURN_TERMINAL = "RETURN_TERMINAL:";
	
	private static final int TIME_OUT = 3600;
	private final String localKey = "xiaoer_payment";
	
	/**
	 * 拉起手机网站支付
	 * @param appid
	 * @param payParam
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 */
	@RequestMapping("wapPay.do")
//	@RequestMapping(value="wapPay/{appid}/{payParam}", method = RequestMethod.GET)
	public String wapPay(String payParam, String success, String fail, HttpServletRequest request, HttpServletResponse response, ModelMap map){
		CheckUtil.assertNotBlank(payParam, ErrorCode.LIMIT.getErrorCode(), "非法请求");
		String appid = "";
		PayVo payVo = null;
		//根据localKey解密
		try {
			String payParamJson = DESUtil.decrypt(payParam,localKey);
			JSONObject jsonObject = JSONObject.fromObject(payParamJson);
			appid = jsonObject.getString("appid");
			payParamJson = jsonObject.getString("payParam");
			String apiKey = payAuthService.get(appid).getApiKey();
			CheckUtil.assertNotBlank(apiKey, ErrorCode.LIMIT.getErrorCode(), "appid未授权，请联系管理员");
			payVo = JsonUtil.fromJson(payParamJson, PayVo.class);
		} catch (Exception e) {
			logger.error("param["+payParam+"]解析异常", e);
			throw new ErrorCodeException(ErrorCode.LIMIT.getErrorCode(),"param内容不合法");
		}
		CheckUtil.assertNotBlank(payVo.getSign(), ErrorCode.PARAM.getErrorCode(), "sign不能为空");
		CheckUtil.assertNotBlank(payVo.getTimeStamp(), ErrorCode.PARAM.getErrorCode(), "timeStamp不能为空");
		CheckUtil.assertNotBlank(payVo.getOrderId(), ErrorCode.PARAM.getErrorCode(), "orderId不能为空!");
		CheckUtil.assertTrue(null != payVo.getPrice() && payVo.getPrice() > 0 , ErrorCode.PARAM.getErrorCode(), "price不能为空");
		logger.info("[手机网页支付][PaymentRpcController.doUnionWapPayment][appid={}][payVo={}]", new Object[]{appid,payVo.toString()});
		switch (PayType.getPayType(payVo.getPayType())) {
		case ALIWAPPAY:
			return this.doAliWapPayment(appid, payVo, success, fail, response, map);
		default:
			throw new ErrorCodeException(405,"不支持payType="+payVo.getPayType());
		}
	}
	
	/**
	 * 支付宝，提交支付接口
	 * 
	 * @param orderId
	 *            订单号
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 */
	private String doAliWapPayment(String appid, PayVo payVo, String success, String fail, HttpServletResponse response, ModelMap map) {
		QueryVo queryVo = new QueryVo(appid, payVo.getOrderId(), null, TranType.CONSUME.getCode(), payVo.getPayType());
		PayOrder payOrder = payOrderService.getPayOrderBy(queryVo);
		try {
			//支付终端
			String terminalServer = "";
			if(StringUtils.isNotBlank(success) && StringUtils.isNotBlank(fail)){
				terminalServer = success+"|split|"+fail;
			}
			logger.info("TerminalServer={}",terminalServer);
			if(StringUtils.isNotBlank(terminalServer) && terminalServer.indexOf("SUCCESS|split|FAIL") <0 ){
				pamentSessionService.setSession(RETURN_TERMINAL + payOrder.getQueryId(), TIME_OUT, terminalServer);
			}
			String front_url = addresses + "/front/paymentNotify/notifyFrontAliPay.do";
			String aliBackUrl = addresses + "/front/paymentNotify/notifyForBackAliPay.do";
			String data = paymentContext.doPayment(payVo.getTimeExpire(),null,null, PaymentConf.ALI_PAYMENT_SERVICE_NAME, payVo.getPrice(), payOrder.getQueryId(),
							PayType.ALIWAPPAY.getCode(), front_url, aliBackUrl);
			response.setContentType("text/html;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(data);
			response.getWriter().close();
			return null;
		} catch (Exception e) {
			logger.error("申请阿里支付时报错{}", e.getMessage(), e);
			throw new ErrorCodeException(ErrorCode.UNKOWN.getErrorCode(), ErrorCode.UNKOWN.getMessage());
		}

	}
}
