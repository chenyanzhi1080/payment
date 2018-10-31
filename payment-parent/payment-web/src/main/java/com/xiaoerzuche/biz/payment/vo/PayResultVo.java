package com.xiaoerzuche.biz.payment.vo;

/** 
* 支付回调结果
* @author: cyz
* @version: payment
* @time: 2016-4-7
* 
*/
public class PayResultVo {
	/**
	 * 业务订单
	 */
	private String orderId;
	/**
	 * 交易流水号
	 */
	private String queryId;
	/**
	 * 支付结果
	 */
	private String code;
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getQueryId() {
		return queryId;
	}
	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
}
