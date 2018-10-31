package com.xiaoerzuche.biz.payment.web.rpc;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xiaoerzuche.biz.payment.PaymentContext;
import com.xiaoerzuche.biz.payment.alipayment.handle.OpenApiHandle;
import com.xiaoerzuche.biz.payment.common.BackVerification;
import com.xiaoerzuche.biz.payment.common.PaymentConf;
import com.xiaoerzuche.biz.payment.enu.PayType;
import com.xiaoerzuche.biz.payment.enu.TranStatus;
import com.xiaoerzuche.biz.payment.enu.TranType;
import com.xiaoerzuche.biz.payment.model.PayOrder;
import com.xiaoerzuche.biz.payment.paymentEnu.PaymentEnum;
import com.xiaoerzuche.biz.payment.service.CallbackService;
import com.xiaoerzuche.biz.payment.service.PayAuthService;
import com.xiaoerzuche.biz.payment.service.PayOrderService;
import com.xiaoerzuche.biz.payment.util.DESUtil;
import com.xiaoerzuche.biz.payment.util.MD5Util;
import com.xiaoerzuche.biz.payment.util.TableUtil;
import com.xiaoerzuche.biz.payment.vo.RefundRespone;
import com.xiaoerzuche.biz.payment.vo.RefundVo;
import com.xiaoerzuche.biz.payment.wallet.dto.RefundResp;
import com.xiaoerzuche.biz.payment.wallet.service.WalletService;
import com.xiaoerzuche.common.enu.ErrorCode;
import com.xiaoerzuche.common.exception.ErrorCodeException;
import com.xiaoerzuche.common.util.CheckUtil;
import com.xiaoerzuche.common.util.JsonUtil;

/** 
* 第三方支付平台退款申请
* @author: cyz
* @version: payment
* @time: 2016年5月3日
* 
*/
@Controller
@RequestMapping("/rpc/payment/")
public class RefundRpcController {
	
	private static final Logger logger = LoggerFactory.getLogger(RefundRpcController.class);
	@Autowired
	private PaymentContext		paymentContext;
	@Autowired
	private PayAuthService payAuthService;
	@Autowired
	private PayOrderService payOrderService;
	@Autowired
	private CallbackService callbackService;
	@Autowired
	private WalletService walletService;
	@Autowired
	private OpenApiHandle openApiHandle;
	
	@Value("${addresses}")
	private String addresses;
	
	/**
	 * 申请退款
	 * @param appid
	 * @param payParam
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 */
	@RequestMapping("doRefund.do")
	public String refund(String appid, String refundParam, HttpServletRequest request, HttpServletResponse response, ModelMap map){
		CheckUtil.assertNotBlank(appid, ErrorCode.PARAM.getErrorCode(), "appid不能为空");
		String apiKey = payAuthService.get(appid).getApiKey();
		CheckUtil.assertNotBlank(apiKey, ErrorCode.LIMIT.getErrorCode(), "appid未授权，请联系管理员");
		//根据apiKey解密
		RefundVo refundVo = null;
		try {
			refundParam = DESUtil.decrypt(refundParam,apiKey);
			refundVo = JsonUtil.fromJson(refundParam, RefundVo.class);
			logger.info("[RefundRpcController.doRefund][refundVo={}][refundParam={}]", new Object[]{refundVo.toString(),refundParam});
		} catch (Exception e) {
			logger.error("param["+refundParam+"]解析异常", e);
			throw new ErrorCodeException(ErrorCode.UNKOWN.getErrorCode(),"param["+refundParam+"]解析异常");
		}
		
		CheckUtil.assertNotBlank(refundVo.getSign(), ErrorCode.PARAM.getErrorCode(), "sign不能为空");
		CheckUtil.assertNotBlank(refundVo.getTimeStamp(), ErrorCode.PARAM.getErrorCode(), "timeStamp不能为空");
		CheckUtil.assertNotBlank(refundVo.getOrderId(), ErrorCode.PARAM.getErrorCode(), "orderId不能为空!");
		CheckUtil.assertTrue(null != refundVo.getPrice() && refundVo.getPrice() > 0 , ErrorCode.PARAM.getErrorCode(), "price要大于0");
		CheckUtil.assertNotBlank(refundVo.getPayQueryId(),ErrorCode.PARAM.getErrorCode(), "payQueryId不能为空");
		//签名校验
		String authSign = refundVo.getTimeStamp() + appid + refundVo.getPrice() + apiKey + refundVo.getOrderId();
		authSign = MD5Util.MD5Encode(authSign, "UTF-8").toUpperCase();
		CheckUtil.assertTrue( (refundVo.getSign()).equals(authSign), ErrorCode.NOENOUGH.getErrorCode(), "签名不正确:"+refundVo.getSign());
		PayOrder origPayOrder = payOrderService.get(refundVo.getPayQueryId());//查找原先支付消费的订单
		CheckUtil.assertNotNull(origPayOrder, ErrorCode.NOT_FOUND.getErrorCode(), "payQueryId="+refundVo.getPayQueryId()+"找不到对应的支付记录");

		try {
			switch (PayType.getPayType(origPayOrder.getPayType())) {
			case ALIAPPPAY:
				return this.aliRefund(appid, refundVo, origPayOrder, request, response, map);
			case ALIWAPPAY:
				return this.aliWapRefund(appid, refundVo, origPayOrder, request, response, map);
			case ALIOPENAPPPAY:
				return this.aliWapRefund(appid, refundVo, origPayOrder, request, response, map);
			case WEIXINAPPPAY:
				return this.wechatRefund(appid, refundVo, origPayOrder, request, response, map);
			case WEIXINJSPAY:
				return this.wechatJsRefund(appid, refundVo, origPayOrder, request, response, map);
			case XIAOERWALLET://电子钱包消费
				return walletRefund(appid, refundVo, origPayOrder, request, response, map);
			default:
				throw new ErrorCodeException(ErrorCode.NOT_FOUND.getErrorCode(),"该订单的支付类型不支持退款操作");
			}
		} catch (Exception e) {
			logger.error("[RefundRpcController.refund][异常{}]{}",e.getMessage(), e);
			throw new ErrorCodeException(ErrorCode.NOENOUGH.getErrorCode() , e.getMessage());
		}
	}
	
    /**
     * 阿里无密退款
     *
     * @param orderId  订单编号
     * @param refundNo  
     * @param refundPrice
     * @param queryId
     * @param response
     * @param map
     * @return
     * @throws Exception
     */
	private String aliRefund(String appid, RefundVo refundVo, PayOrder origPayOrder, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		logger.info("执行支付宝退款功能");
		String refundQueryId = TableUtil.genId();
		String front_url = addresses + "/front/paymentNotify/notifyForFrontAliPay.do";
		String aliRefundBackUrl = addresses + "/front/paymentNotify/notifyForBackAliRefund.do";
		Map<String, String> result = paymentContext.doRefund(PaymentConf.ALI_PAYMENT_SERVICE_NAME, origPayOrder.getQueryId(), origPayOrder.getTranNo(),
				origPayOrder.getTranAmount(), refundQueryId, refundVo.getPrice(), front_url, aliRefundBackUrl);
		PayOrder refundOrder = new PayOrder(refundQueryId, refundVo.getOrderId(), refundVo.getGoodsOrderId(), null, origPayOrder.getTranNo(), appid, origPayOrder.getPayType(),
				origPayOrder.getPayName(), refundVo.getPrice(), origPayOrder.getTranAmount(), TranType.REFUND.getCode(), new Date(), TranStatus.PROCESS.getCode(), 0, null,origPayOrder.getAccount());
		refundOrder.setChannel(origPayOrder.getChannel()).setExpenseType(origPayOrder.getExpenseType());
		CheckUtil.assertTrue(payOrderService.add(refundOrder),ErrorCode.UNKOWN.getErrorCode(), "退款订单保存失败,请联系支付中心");
		// 记录订单状态 结束
		RefundRespone refundRespone = new RefundRespone(TranStatus.PROCESS.getCode(), TranStatus.PROCESS.getName(), refundVo.getOrderId(), refundQueryId);
		logger.info("执行支付宝退款功能响应客户端内容:{}",refundRespone.toString());
		map.put("data", JsonUtil.toJson(refundRespone));
		return "/frontJson";
	}

	// 支付宝退款
	private String aliWapRefund(String appid, RefundVo refundVo, PayOrder origPayOrder, HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
		logger.info("执行支付宝退款功能");
		String refundQueryId = TableUtil.genId();
		PayOrder refundOrder = new PayOrder(refundQueryId, refundVo.getOrderId(), refundVo.getGoodsOrderId(), null, origPayOrder.getTranNo(), appid, origPayOrder.getPayType(),
				origPayOrder.getPayName(), refundVo.getPrice(), origPayOrder.getTranAmount(), TranType.REFUND.getCode(), new Date(), TranStatus.PROCESS.getCode(), 0, null,origPayOrder.getAccount());
		refundOrder.setChannel(origPayOrder.getChannel()).setExpenseType(origPayOrder.getExpenseType());

		RefundRespone refundRespone = openApiHandle.refund(origPayOrder.getQueryId(), refundOrder);
		logger.info("执行支付宝退款功能响应客户端内容:{}",refundRespone.toString());
		map.put("data", JsonUtil.toJson(refundRespone));
		return "/frontJson";
	}
	/**
	 * 一句话描述
	 * @param appid
	 * @param refundVo
	 * @param origPayOrder
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 * @throws Exception
	 */
	private String wechatRefund(String appid, RefundVo refundVo, PayOrder origPayOrder, HttpServletRequest request, HttpServletResponse response, ModelMap map)
					throws Exception {
		logger.info("执行微信app支付退款功能");
		String refundQueryId = TableUtil.genId();
		String wechatBackUrl = addresses + "/front/paymentNotify/notifyForBackWechatPay.do";
		Map<String, String> result = paymentContext.doRefund(PaymentConf.WECHAT_PAYMENT_SERVICE_NAME, origPayOrder.getQueryId(), origPayOrder.getTranNo(),
				origPayOrder.getTranAmount(), refundQueryId, refundVo.getPrice(), null, wechatBackUrl);
		String code = result.get(PaymentConf.code_pay);
		CheckUtil.assertEquals(PaymentConf.successcode, code, result.get(PaymentConf.businessCode)+result.get(PaymentConf.businessDes));
		String refundTranNo = result.get(PaymentConf.queryId_pay);
		PayOrder refundOrder = new PayOrder(refundQueryId, refundVo.getOrderId(), refundVo.getGoodsOrderId(), refundTranNo, origPayOrder.getTranNo(), appid, origPayOrder.getPayType(),
				origPayOrder.getPayName(), refundVo.getPrice(), origPayOrder.getTranAmount(), TranType.REFUND.getCode(), new Date(), TranStatus.SUCCESS.getCode(), 0, new Date(),origPayOrder.getAccount());
		refundOrder.setChannel(origPayOrder.getChannel()).setExpenseType(origPayOrder.getExpenseType());
		boolean isFailure = PaymentConf.successcode.equals(code);
		payOrderService.add(refundOrder);
		int tranStatus = isFailure ? TranStatus.SUCCESS.getCode() : TranStatus.FAIL.getCode();
		RefundRespone refundRespone = new RefundRespone(tranStatus, TranStatus.getTranStatus(tranStatus).getName(), refundVo.getOrderId(), refundQueryId);
		logger.info("result=" + result);
		map.put("data", JsonUtil.toJson(refundRespone));
		return "/frontJson";
	}
	
	private String wechatJsRefund(String appid, RefundVo refundVo, PayOrder origPayOrder, HttpServletRequest request, HttpServletResponse response, ModelMap map)throws Exception {
		logger.info("执行微信公众号退款功能");
		String refundQueryId = TableUtil.genId();
		String wechatBackUrl = addresses + "/front/paymentNotify/notifyForBackWechatJsPay.do";
		Map<String, String> result = paymentContext.doRefund(PaymentConf.WECHAT_JSPAYMENT_SERVICE_NAME, origPayOrder.getQueryId(), origPayOrder.getTranNo(),
				origPayOrder.getTranAmount(), refundQueryId, refundVo.getPrice(), null, wechatBackUrl);
		String code = result.get(PaymentConf.code_pay);
		CheckUtil.assertEquals(PaymentConf.successcode, code, result.get(PaymentConf.businessCode)+result.get(PaymentConf.businessDes));
		String refundTranNo = result.get(PaymentConf.queryId_pay);
		PayOrder refundOrder = new PayOrder(refundQueryId, refundVo.getOrderId(), refundVo.getGoodsOrderId(), refundTranNo, origPayOrder.getTranNo(), appid, origPayOrder.getPayType(),
				origPayOrder.getPayName(), refundVo.getPrice(), origPayOrder.getTranAmount(), TranType.REFUND.getCode(), new Date(), TranStatus.SUCCESS.getCode(), 0, new Date(),origPayOrder.getAccount());
		refundOrder.setChannel(origPayOrder.getChannel()).setExpenseType(origPayOrder.getExpenseType());
		boolean isFailure = PaymentConf.successcode.equals(code);
		payOrderService.add(refundOrder);
		int tranStatus = isFailure ? TranStatus.SUCCESS.getCode() : TranStatus.FAIL.getCode();
		RefundRespone refundRespone = new RefundRespone(tranStatus, TranStatus.getTranStatus(tranStatus).getName(), refundVo.getOrderId(), refundQueryId);
		logger.info("result=" + result);
		map.put("data", JsonUtil.toJson(refundRespone));
		return "/frontJson";
	}
	
	private String walletRefund(String appid, RefundVo refundVo, PayOrder origPayOrder, HttpServletRequest request, HttpServletResponse response, ModelMap map){
		String refundQueryId = TableUtil.genId();
		RefundResp refundResp = walletService.doRefund(origPayOrder.getTranNo(), refundQueryId, origPayOrder.getGoodsNo(), refundVo.getPrice());
		String refundTranNo = refundResp.getRefundTranNo();
		PayOrder refundOrder = new PayOrder(refundQueryId, refundVo.getOrderId(), refundVo.getGoodsOrderId(), refundTranNo, origPayOrder.getTranNo(), appid, origPayOrder.getPayType(),
				origPayOrder.getPayName(), refundVo.getPrice(), origPayOrder.getTranAmount(), TranType.REFUND.getCode(), new Date(), TranStatus.PROCESS.getCode(), 0, null,origPayOrder.getAccount());
		refundOrder.setChannel(origPayOrder.getChannel()).setExpenseType(origPayOrder.getExpenseType());
		payOrderService.add(refundOrder);
		int tranStatus = refundResp.getCode()==PaymentEnum.REFUND_SUCCESS.getCode() ? TranStatus.SUCCESS.getCode() : TranStatus.FAIL.getCode();
		RefundRespone refundRespone = new RefundRespone(tranStatus, TranStatus.getTranStatus(tranStatus).getName(), refundVo.getOrderId(), refundQueryId);
		logger.info("result=" + refundResp.toString());
		map.put("data", JsonUtil.toJson(refundRespone));
		return "/frontJson";
	}
}
