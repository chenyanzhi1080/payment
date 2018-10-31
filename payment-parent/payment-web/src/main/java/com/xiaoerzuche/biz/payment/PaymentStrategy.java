package com.xiaoerzuche.biz.payment;

import java.util.Map;

public interface PaymentStrategy {
	/**
	 * 
	 * 
	 * 支付接口
	 * 
	 * @param orderId
	 *            订单号
	 * @param price
	 *            支付金额
	 * @param payType
	 *            支付类型/ wap,app,扫码等支付方式
	 * @param frontNotifyUrl
	 *            前台回调地址
	 * @param backNotifyUrl
	 *            后台回调地址
	 * @return
	 */
	String doPayment(String timeExpire, String account, String openid,String orderId, int price, Integer payType, String frontNotifyUrl, String backNotifyUrl)
					throws Exception;

	/**
	 * 支付服务前台通知
	 * 
	 * @param valideData
	 * @return
	 */
	Map<String, String> getPaymentFrontNotifaction(Map<String, String> valideData) throws Exception;

	/**
	 * 支付服务后台通知
	 * 
	 * @param valideData
	 * @return
	 */
	Map<String, String> getPaymentBackNotifaction(Map<String, String> valideData) throws Exception;

	/**
	 * 执行退款操作
	 * 
	 * @param orderId
	 *            订单编号
	 * @param queryId
	 *            第三方支付成功产生的流水号
	 * @param price
	 *            总金额
	 * @param refundNo
	 *            退款编号
	 * @param refundPrice
	 *            退款金额
	 * @param frontUrl
	 *            前端通知
	 * @param backUrl
	 *            后端通知
	 * @return
	 * @throws Exception
	 */
	Map<String, String> doRefund(String orderId, String queryId, int price, String refundNo, int refundPrice,
					String frontUrl, String backUrl) throws Exception;
	
	/**
	 * 有密退款
	 * @param orderId
	 * @param queryId
	 * @param price
	 * @param refundNo
	 * @param refundPrice
	 * @param frontUrl
	 * @param backUrl
	 * @return
	 */
	Map<String, String> doPwdRefund(String orderId, String queryId, int price, String refundNo, int refundPrice,
			String frontUrl, String backUrl);
	
	/**
	 * 退款服务端通知
	 * 
	 * @param valideData
	 * @return
	 */
	Map<String, String> getRefundBackNotifaction(Map<String, String> valideData) throws Exception;
}
