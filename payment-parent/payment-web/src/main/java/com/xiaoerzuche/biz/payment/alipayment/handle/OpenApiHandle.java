package com.xiaoerzuche.biz.payment.alipayment.handle;

import java.math.BigDecimal;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.xiaoerzuche.biz.payment.enu.TranStatus;
import com.xiaoerzuche.biz.payment.model.PayOrder;
import com.xiaoerzuche.biz.payment.paymentConfig.DefaultAliOpendApiClient;
import com.xiaoerzuche.biz.payment.service.CallbackService;
import com.xiaoerzuche.biz.payment.service.PayOrderService;
import com.xiaoerzuche.biz.payment.vo.RefundRespone;
import com.xiaoerzuche.common.core.annotation.SingleEntryLimit;
import com.xiaoerzuche.common.enu.ErrorCode;
import com.xiaoerzuche.common.util.CheckUtil;
import com.xiaoerzuche.common.util.JsonUtil;

/**
 * 接入开放平台api协议辅助接口
 * @author Administrator
 *
 */
@Service
public class OpenApiHandle {
	private static final Logger logger = LoggerFactory.getLogger(OpenApiHandle.class);

	@Autowired
	private DefaultAliOpendApiClient defaultAliOpendApiClient;
	@Autowired
	private PayOrderService payOrderService;
	@Autowired
	private CallbackService callbackService;
	/**
	 * 退款
	 * 单入条件（同一般支付单同时只能退一笔）
	 * @param origQueryId
	 * @param refundOrder
	 * @return
	 */
	@SingleEntryLimit(lockKey = "ALI_REFUND_${0}")
	public RefundRespone refund(String origQueryId, PayOrder refundOrder){
		AlipayClient alipayClient = this.defaultAliOpendApiClient.alipayClient;
		AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
		String refundAmount = new BigDecimal(refundOrder.getTranAmount()).movePointLeft(2).toString();// 付款金额
		StringBuffer bizContent = new StringBuffer();
		bizContent.append("{")
		.append("\"out_trade_no\":\"").append(origQueryId).append("\",")
		.append("\"trade_no\":\"").append(refundOrder.getOrigTranNo()).append("\",")
		.append("\"refund_amount\":\"").append(refundAmount).append("\",")
		.append("\"refund_reason\":\"").append("开开出行退款").append("\",")
		.append("\"out_request_no\":\"").append(refundOrder.getQueryId()).append("\"")
		.append("}");
		
		request.setBizContent(bizContent.toString());
		logger.info("支付宝开放平台退款origQueryId={} request", new Object[]{origQueryId,JsonUtil.toJson(request)});
		AlipayTradeRefundResponse response = null;
		RefundRespone refundRespone = new RefundRespone(TranStatus.PROCESS.getCode(), TranStatus.PROCESS.getName(),
				refundOrder.getOrderId(), refundOrder.getQueryId());
		TranStatus tranStatus = null;
		try {
			response = alipayClient.execute(request);
			logger.info("支付宝开放平台退款origQueryId={} response", new Object[]{origQueryId,JsonUtil.toJson(response)});
			if(response.isSuccess()){
				refundOrder.setTranStatus(TranStatus.SUCCESS.getCode());
				refundOrder.setLastModifyTime(new Date());
				refundOrder.setNotifyFlag(1);
				refundOrder.setTranNo(response.getTradeNo());
				tranStatus = TranStatus.PROCESS;
				//异步回调
				callbackService.callback(refundOrder);
				logger.info("支付宝开放平台退款成功origQueryId={}",new Object[]{origQueryId});
			} else {
				refundOrder.setTranStatus(TranStatus.FAIL.getCode());
				refundOrder.setLastModifyTime(new Date());
				refundOrder.setNotifyFlag(1);
				tranStatus = TranStatus.PROCESSFAIL;
//				callbackService.callback(refundOrder);
				logger.info("支付宝开放平台退款失败origQueryId={}",new Object[]{origQueryId});
			}
		} catch (AlipayApiException e) {
			logger.info("支付宝开放平台退款erro:"+e);
			refundOrder.setTranStatus(TranStatus.FAIL.getCode());
			refundOrder.setLastModifyTime(new Date());
			refundOrder.setNotifyFlag(1);
			tranStatus = TranStatus.PROCESSFAIL;
		}
		CheckUtil.assertTrue(payOrderService.add(refundOrder),ErrorCode.UNKOWN.getErrorCode(), "退款订单保存失败,请联系支付中心");
		refundRespone.setCode(tranStatus.getCode());
		refundRespone.setMsg(tranStatus.getName());
		return refundRespone;
	} 
	
}
