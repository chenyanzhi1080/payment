package com.xiaoerzuche.biz.payment.wallet.dto;

/** 
* 退款响应
* @author: cyz
* @version: new-energy
* @time: 2016年7月25日
* 
*/
public class RefundResp {
	private String refundTranNo;
	private String paymentQueryId;
	private int code;
	private String msg;
	
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
	public String getRefundTranNo() {
		return refundTranNo;
	}
	public void setRefundTranNo(String refundTranNo) {
		this.refundTranNo = refundTranNo;
	}
	public String getPaymentQueryId() {
		return paymentQueryId;
	}
	public void setPaymentQueryId(String paymentQueryId) {
		this.paymentQueryId = paymentQueryId;
	}
	@Override
	public String toString() {
		return "RefundResp [refundTranNo=" + refundTranNo + ", paymentQueryId=" + paymentQueryId + ", code=" + code
				+ ", msg=" + msg + "]";
	}
	
}
