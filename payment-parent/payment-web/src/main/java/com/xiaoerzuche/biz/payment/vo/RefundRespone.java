package com.xiaoerzuche.biz.payment.vo;

/** 
* 退款响应对象
* @author: cyz
* @version: payment
* @time: 2016年5月4日
* 
*/
public class RefundRespone {
	
	private int code;
	
	private String msg;
	
	/**
	 * 订单id(业务订单)
	 */
	private String orderId;
	/**
	 * 退款单号
	 */
	private String refundQueryId;
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getRefundQueryId() {
		return refundQueryId;
	}
	public void setRefundQueryId(String refundQueryId) {
		this.refundQueryId = refundQueryId;
	}
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public RefundRespone() {
	}
	public RefundRespone(int code, String msg, String orderId, String refundQueryId) {
		super();
		this.code = code;
		this.msg = msg;
		this.orderId = orderId;
		this.refundQueryId = refundQueryId;
	}
	@Override
	public String toString() {
		return "RefundRespone [code=" + code + ", msg=" + msg + ", orderId=" + orderId + ", refundQueryId="
				+ refundQueryId + "]";
	}
	
}
