package com.xiaoerzuche.biz.payment.web.rpc;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xiaoerzuche.biz.payment.PaymentContext;
import com.xiaoerzuche.biz.payment.common.BackVerification;
import com.xiaoerzuche.biz.payment.common.PaymentConf;
import com.xiaoerzuche.biz.payment.enu.Channel;
import com.xiaoerzuche.biz.payment.enu.ExpenseType;
import com.xiaoerzuche.biz.payment.enu.PayType;
import com.xiaoerzuche.biz.payment.enu.TranStatus;
import com.xiaoerzuche.biz.payment.enu.TranType;
import com.xiaoerzuche.biz.payment.model.PayAuth;
import com.xiaoerzuche.biz.payment.model.PayOrder;
import com.xiaoerzuche.biz.payment.service.PayAuthService;
import com.xiaoerzuche.biz.payment.service.PayOrderService;
import com.xiaoerzuche.biz.payment.util.DESUtil;
import com.xiaoerzuche.biz.payment.util.MD5Util;
import com.xiaoerzuche.biz.payment.util.TableUtil;
import com.xiaoerzuche.biz.payment.vo.PayResponeVo;
import com.xiaoerzuche.biz.payment.vo.PayVo;
import com.xiaoerzuche.biz.payment.vo.QueryVo;
import com.xiaoerzuche.biz.payment.wallet.service.WalletService;
import com.xiaoerzuche.common.enu.ErrorCode;
import com.xiaoerzuche.common.exception.ErrorCodeException;
import com.xiaoerzuche.common.util.CheckUtil;
import com.xiaoerzuche.common.util.JsonUtil;

/** 
* 第三方支付服务对外接口，实现银联、阿里wap版支付以及微信扫码支付和app支付及退款.建议内网访问。
* @author: cyz
* @version: payment
* @time: 2016-3-30
* 
*/
@Controller
@RequestMapping("/rpc/payment/")
public class PaymentRpcController {

	private static final Logger logger = LoggerFactory.getLogger(PaymentRpcController.class);
	@Autowired
	private PaymentContext		paymentContext;
	@Autowired
	private BackVerification	backVerification;
	@Autowired
	private PayAuthService payAuthService;
	@Autowired
	private PayOrderService payOrderService;
	@Autowired
	private WalletService walletService;
	
	@Value("${addresses}")
	private String addresses;
	
	private final String localKey = "xiaoer_payment";

	/**
	 * @param sign 加密签名
	 * @param appid 接口调用凭据
	 * @param param 业务参数
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping("doPay.do")
	private String doPay(String appid, String payParam, HttpServletRequest request, HttpServletResponse response, ModelMap map){
		logger.info("[doPay入参][appid={},payParam={}]",new Object[]{appid, payParam});
		CheckUtil.assertNotBlank(appid, ErrorCode.PARAM.getErrorCode(), "appid不能为空");
		PayAuth auth = payAuthService.get(appid);
		String apiKey = auth.getApiKey();
		CheckUtil.assertNotBlank(apiKey, ErrorCode.LIMIT.getErrorCode(), "appid未授权，请联系管理员");
		//客户端参支付数转成json，根据apiKey加密；服务端根据apiKey解密，获取参数
		PayVo payVo = null;
		String payParamJson = "";
		try {
			payParamJson = DESUtil.decrypt(payParam,apiKey);
			payVo = JsonUtil.fromJson(payParamJson, PayVo.class);
			logger.info("[PaymentRpcController.doPay][payVo={}][payParamJson={}]", new Object[]{payVo.toString(), payParamJson});
		} catch (Exception e) {
			logger.error("param["+payParam+"]解析异常", e);
			throw new ErrorCodeException(ErrorCode.UNKOWN.getErrorCode(),"param["+payParam+"]解析异常");
		}
		
		CheckUtil.assertNotBlank(payVo.getSign(), ErrorCode.PARAM.getErrorCode(), "sign不能为空");
		CheckUtil.assertNotBlank(payVo.getTimeStamp(), ErrorCode.PARAM.getErrorCode(), "timeStamp不能为空");
		CheckUtil.assertNotBlank(payVo.getOrderId(), ErrorCode.PARAM.getErrorCode(), "orderId不能为空!");
		CheckUtil.assertTrue(null != payVo.getPrice() && payVo.getPrice() > 0 , ErrorCode.PARAM.getErrorCode(), "price要大于0");
		
		//签名校验
		String authSign = payVo.getTimeStamp() + appid + payVo.getPrice() + apiKey + payVo.getOrderId();
		authSign = MD5Util.MD5Encode(authSign, "UTF-8").toUpperCase();
		CheckUtil.assertTrue( (payVo.getSign()).equals(authSign), ErrorCode.NOENOUGH.getErrorCode(), "签名不正确:"+payVo.getSign());
		
		QueryVo queryVo = new QueryVo(appid, payVo.getOrderId(), null, TranType.CONSUME.getCode(), payVo.getPayType());
		PayOrder srcPayOrder = payOrderService.getPayOrderBy(queryVo);
		logger.info("[支付请求查找是否存在][queryVo={}][srcPayOrder={}]", new Object[]{queryVo.toString(), srcPayOrder});
		
		String queryId = (null != srcPayOrder && StringUtils.isNotBlank(srcPayOrder.getQueryId())) ? srcPayOrder.getQueryId() : TableUtil.genId();//生成预支付单号

		if(null == payVo.getChannel()){
			payVo.setChannel(Channel.getByCode(auth.getChannel()));
		}
		if(null == payVo.getExpenseType()){
			payVo.setExpenseType(ExpenseType.ORDER_FEED);//默认为订单费用
		}
		switch (PayType.getPayType(payVo.getPayType())) {
		case ALIWAPPAY:
			getWapResources(queryId, appid, payVo, payParamJson, map);
			return "/frontJson";
		case WEIXINAPPPAY:
			return this.doWechatAppPayment(queryId, appid, payVo, request, response, map);
		case WEIXINJSPAY://微信公众号支付
			return this.doWechatJSSDKPayment(queryId, appid, payVo, request, response, map);
		case XIAOERWALLET://电子钱包消费
			return this.doWalletPay(queryId, payVo, auth, request, response, map);
		case ALIAPPPAY:	
			return this.doAliPay(queryId, appid, payVo, request, response, map);
		case ALIOPENAPPPAY:
			return this.doAliOpenAppPay(queryId, appid, payVo, request, response, map);
		case BIGCUSTOMER:
			return this.doBigCustomer(queryId, payVo, auth, request, response, map);
		default:
			throw new ErrorCodeException(405,"不支持payType="+payVo.getPayType());
		}
		
	}
	
	public void getWapResources(String queryId, String appid, PayVo payVo, String payParam, ModelMap map){
		PayOrder payOrder = new PayOrder(queryId, payVo.getOrderId(), payVo.getGoodsOrderId(), null, null, appid, payVo.getPayType(), PayType.getPayType(payVo.getPayType()).getName(),
				payVo.getPrice(), null, TranType.CONSUME.getCode(), new Date(), TranStatus.PROCESS.getCode(), 0, null, payVo.getAccount());
		payOrder.setChannel(payVo.getChannel().getValue()).setExpenseType(payVo.getExpenseType().getValue());
		boolean flag = payOrderService.setOrUpdatePay(payOrder);
		CheckUtil.assertTrue(flag, ErrorCode.UNKOWN.getErrorCode(), "支付订单保存失败,请联系支付中心");
		Map<String, Object> paremMap = new HashMap<String, Object>();
		map.put("appid", appid);
		map.put("payParam", payParam);
		String paramstr = "";
		try {
			paramstr = DESUtil.ENCRYPTMethod(JsonUtil.toJson(map), localKey);
		} catch (Exception e) {
			logger.error("[PaymentRpcController.getWapResources][系统异常:{}]{}", e.getMessage(), e);
			throw new ErrorCodeException(ErrorCode.UNKOWN.getErrorCode(), ErrorCode.UNKOWN.getMessage());
		}
		
		//生成wapUrl
		StringBuffer wapUrl = new StringBuffer();
		wapUrl.append(addresses).append("/front/paymentRedirect/wapPay.do?").append("payParam=").append(paramstr).append("&success=SUCCESS&fail=FAIL");
		logger.info("[wapUrl={}]",wapUrl);
		PayResponeVo responeVo = new PayResponeVo(TranStatus.PROCESS.getCode(), TranStatus.PROCESS.getName(), payVo.getOrderId(), payVo.getGoodsOrderId(), queryId, payVo.getPayType(), wapUrl.toString());
		logger.info(JsonUtil.toJson(responeVo));
		map.put("data", JsonUtil.toJson(responeVo));
	}
	
	
	/**
	 * 微信app支付，提交支付
	 * 
	 * @param orderId
	 *            订单号
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 */
	private String doWechatAppPayment(String queryId, String appid, PayVo payVo, HttpServletRequest request, HttpServletResponse response,
					ModelMap map){
		logger.info("执行微信app支付功能");
		String wechatBackUrl = addresses + "/front/paymentNotify/notifyForBackWechatPay.do";
		String data = paymentContext.doPayment(payVo.getTimeExpire(), null, null, PaymentConf.WECHAT_PAYMENT_SERVICE_NAME, payVo.getPrice(), queryId,
						PayType.WEIXINAPPPAY.getCode(), null, wechatBackUrl);
		CheckUtil.assertNotBlank(data, "请求支付失败");
		// DB交易订单信息
		PayOrder payOrder = new PayOrder(queryId, payVo.getOrderId(), payVo.getGoodsOrderId(), null, null, appid, payVo.getPayType(), PayType.getPayType(payVo.getPayType()).getName(),
				payVo.getPrice(), null, TranType.CONSUME.getCode(), new Date(), TranStatus.PROCESS.getCode(), 0, null, payVo.getAccount());
		payOrder.setChannel(payVo.getChannel().getValue()).setExpenseType(payVo.getExpenseType().getValue());
		CheckUtil.assertTrue(payOrderService.setOrUpdatePay(payOrder), ErrorCode.UNKOWN.getErrorCode(), "支付订单保存失败,请联系支付中心");
		PayResponeVo responeVo = new PayResponeVo(TranStatus.PROCESS.getCode(), TranStatus.PROCESS.getName(), payVo.getOrderId(), payVo.getGoodsOrderId(), queryId, payVo.getPayType(), data);
		
		map.put("data", JsonUtil.toJson(responeVo));
		return "/frontJson";
	}
	
	private String doWechatJSSDKPayment(String queryId, String appid, PayVo payVo, HttpServletRequest request, HttpServletResponse response,
			ModelMap map){
		logger.info("执行微信app支付功能");
		CheckUtil.assertNotBlank(payVo.getOpenid(), "微信公众号支付时openid不能为空");
		String wechatBackUrl = addresses + "/front/paymentNotify/notifyForBackWechatJsPay.do";

		String data = paymentContext.doPayment(payVo.getTimeExpire(), null,payVo.getOpenid(), PaymentConf.WECHAT_JSPAYMENT_SERVICE_NAME, payVo.getPrice(), queryId,
						PayType.WEIXINJSPAY.getCode(), null, wechatBackUrl);
		CheckUtil.assertNotBlank(data, "请求微信公众号支付失败");
		// DB交易订单信息
		PayOrder payOrder = new PayOrder(queryId, payVo.getOrderId(), payVo.getGoodsOrderId(), null, null, appid, payVo.getPayType(), PayType.getPayType(payVo.getPayType()).getName(),
				payVo.getPrice(), null, TranType.CONSUME.getCode(), new Date(), TranStatus.PROCESS.getCode(), 0, null, payVo.getAccount());
		payOrder.setChannel(payVo.getChannel().getValue()).setExpenseType(payVo.getExpenseType().getValue());
		CheckUtil.assertTrue(payOrderService.setOrUpdatePay(payOrder), ErrorCode.UNKOWN.getErrorCode(), "支付订单保存失败,请联系支付中心");
		
		PayResponeVo responeVo = new PayResponeVo(TranStatus.PROCESS.getCode(), TranStatus.PROCESS.getName(), payVo.getOrderId(), payVo.getGoodsOrderId(), queryId, payVo.getPayType(), data);
		
		map.put("data", JsonUtil.toJson(responeVo));
		return "/frontJson";
	}
	/**
	 * 对接支付宝移动支付接口
	 * @param appid
	 * @param payVo
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 */
	private String doAliPay(String queryId, String appid, PayVo payVo, HttpServletRequest request, HttpServletResponse response,ModelMap map){
		logger.info("执行支付宝app支付功能");
		String aliBackUrl = addresses + "/front/paymentNotify/notifyForBackAliAppPay.do";
		String param = paymentContext.doPayment(payVo.getTimeExpire(), null, null, PaymentConf.ALI_PAYMENT_SERVICE_NAME, payVo.getPrice(), queryId,
						PayType.ALIAPPPAY.getCode(), null, aliBackUrl);
		// DB交易订单信息
		PayOrder payOrder = new PayOrder(queryId, payVo.getOrderId(), payVo.getGoodsOrderId(), null, null, appid, payVo.getPayType(), PayType.getPayType(payVo.getPayType()).getName(),
				payVo.getPrice(), null, TranType.CONSUME.getCode(), new Date(), TranStatus.PROCESS.getCode(), 0, null, payVo.getAccount());
		payOrder.setChannel(payVo.getChannel().getValue()).setExpenseType(payVo.getExpenseType().getValue());
		CheckUtil.assertTrue(payOrderService.setOrUpdatePay(payOrder), ErrorCode.UNKOWN.getErrorCode(), "支付订单保存失败,请联系支付中心");
		
		PayResponeVo responeVo = new PayResponeVo(TranStatus.PROCESS.getCode(), TranStatus.PROCESS.getName(), payVo.getOrderId(), payVo.getGoodsOrderId(), queryId, payVo.getPayType(), param);
		logger.info("支付宝移动支付接口订单数据:"+param);
		map.put("data", JsonUtil.toJson(responeVo));
		return "/frontJson";
	}
	/**
	 * 对接支付宝移动支付接口
	 * @param appid
	 * @param payVo
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 */
	private String doAliOpenAppPay(String queryId, String appid, PayVo payVo, HttpServletRequest request, HttpServletResponse response,ModelMap map){
		logger.info("执行支付宝OpenApp支付功能");
		String aliBackUrl = addresses + "/front/paymentNotify/notifyForBackAliOpenAppPay.do";
		String param = paymentContext.doPayment(payVo.getTimeExpire(), null, null, PaymentConf.ALI_PAYMENT_SERVICE_NAME, payVo.getPrice(), queryId,
						PayType.ALIOPENAPPPAY.getCode(), null, aliBackUrl);
		// DB交易订单信息
		PayOrder payOrder = new PayOrder(queryId, payVo.getOrderId(), payVo.getGoodsOrderId(), null, null, appid, payVo.getPayType(), PayType.getPayType(payVo.getPayType()).getName(),
				payVo.getPrice(), null, TranType.CONSUME.getCode(), new Date(), TranStatus.PROCESS.getCode(), 0, null, payVo.getAccount());
		payOrder.setChannel(payVo.getChannel().getValue()).setExpenseType(payVo.getExpenseType().getValue());
		CheckUtil.assertTrue(payOrderService.setOrUpdatePay(payOrder), ErrorCode.UNKOWN.getErrorCode(), "支付订单保存失败,请联系支付中心");
		
		PayResponeVo responeVo = new PayResponeVo(TranStatus.PROCESS.getCode(), TranStatus.PROCESS.getName(), payVo.getOrderId(), payVo.getGoodsOrderId(), queryId, payVo.getPayType(), param);
		logger.info("支付宝移动支付接口订单数据:"+param);
		map.put("data", JsonUtil.toJson(responeVo));
		return "/frontJson";
	}
	/**
	 * 电子钱包预支付
	 * @param payVo
	 * @param channel 业务渠道号
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 */
	public String doWalletPay(String queryId, PayVo payVo, PayAuth auth, HttpServletRequest request, HttpServletResponse response,ModelMap map){
//		String data = walletService.doPayment(payVo, queryId, auth.getChannel());
		String data = walletService.doPayment(payVo, queryId, auth.getWalletChannel());
		CheckUtil.assertNotBlank(data, "请求支付失败");
		// DB交易订单信息
		PayOrder payOrder = new PayOrder(queryId, payVo.getOrderId(), payVo.getGoodsOrderId(), null, null, auth.getAppid(), payVo.getPayType(), PayType.getPayType(payVo.getPayType()).getName(),
				payVo.getPrice(), null, TranType.CONSUME.getCode(), new Date(), TranStatus.PROCESS.getCode(), 0, null, payVo.getAccount());
		payOrder.setChannel(payVo.getChannel().getValue()).setExpenseType(payVo.getExpenseType().getValue());
		CheckUtil.assertTrue(payOrderService.setOrUpdatePay(payOrder), ErrorCode.UNKOWN.getErrorCode(), "支付订单保存失败,请联系支付网关");
		PayResponeVo responeVo = new PayResponeVo(TranStatus.PROCESS.getCode(), TranStatus.PROCESS.getName(), payVo.getOrderId(), payVo.getGoodsOrderId(), queryId, payVo.getPayType(), data);
		map.put("data", JsonUtil.toJson(responeVo));
		return "/frontJson";
	}
	
	public String doBigCustomer(String queryId, PayVo payVo, PayAuth auth, HttpServletRequest request, HttpServletResponse response,ModelMap map){
		// DB交易订单信息
		PayOrder payOrder = new PayOrder(queryId, payVo.getOrderId(), payVo.getGoodsOrderId(), null, null, auth.getAppid(), payVo.getPayType(), PayType.getPayType(payVo.getPayType()).getName(),
				payVo.getPrice(), null, TranType.CONSUME.getCode(), new Date(), TranStatus.PROCESS.getCode(), 0, null, payVo.getAccount());
		payOrder.setChannel(payVo.getChannel().getValue()).setExpenseType(payVo.getExpenseType().getValue());
		CheckUtil.assertTrue(payOrderService.setOrUpdatePay(payOrder), ErrorCode.UNKOWN.getErrorCode(), "支付订单保存失败,请联系支付网关");
		
		String data = walletService.doBigCustomerPay(payVo, queryId, auth.getWalletChannel());
		CheckUtil.assertNotBlank(data, "请求支付失败");
		PayResponeVo responeVo = new PayResponeVo(TranStatus.PROCESS.getCode(), TranStatus.PROCESS.getName(), payVo.getOrderId(), payVo.getGoodsOrderId(), queryId, payVo.getPayType(), data);
		map.put("data", JsonUtil.toJson(responeVo));
		return "/frontJson";
	}
	
}