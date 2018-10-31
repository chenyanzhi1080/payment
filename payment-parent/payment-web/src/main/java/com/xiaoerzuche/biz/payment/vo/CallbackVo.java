package com.xiaoerzuche.biz.payment.vo;

/** 
* 业务系统回调传参vo
* @author: cyz
* @version: payment
* @time: 2016年5月11日
* 
*/
public class CallbackVo {
	private int code;
	private String msg;
	private String orderId;
	/**
	 * 主订单ID
	 */
	private String goodsOrderId;
	private String queryId;
	/**
	 * 第三方平台流水号
	 */
	private String tranNo;
	private int tranType;
	private int payType;
	private int amount;
	private int result;
	private String timeStamp;
	private String sign;
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
	public int getTranType() {
		return tranType;
	}
	public void setTranType(int tranType) {
		this.tranType = tranType;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getGoodsOrderId() {
		return goodsOrderId;
	}
	public void setGoodsOrderId(String goodsOrderId) {
		this.goodsOrderId = goodsOrderId;
	}
	public String getTranNo() {
		return tranNo;
	}
	public void setTranNo(String tranNo) {
		this.tranNo = tranNo;
	}
	public int getPayType() {
		return payType;
	}
	public void setPayType(int payType) {
		this.payType = payType;
	}
	public CallbackVo() {
	}
	public CallbackVo(int code, String msg, String orderId, String goodsOrderId, String queryId, String tranNo,
			int tranType, int payType, int amount, int result, String timeStamp, String sign) {
		super();
		this.code = code;
		this.msg = msg;
		this.orderId = orderId;
		this.goodsOrderId = goodsOrderId;
		this.queryId = queryId;
		this.tranNo = tranNo;
		this.tranType = tranType;
		this.payType = payType;
		this.amount = amount;
		this.result = result;
		this.timeStamp = timeStamp;
		this.sign = sign;
	}
	@Override
	public String toString() {
		return "CallbackVo [code=" + code + ", msg=" + msg + ", orderId=" + orderId + ", goodsOrderId=" + goodsOrderId
				+ ", queryId=" + queryId + ", tranNo=" + tranNo + ", tranType=" + tranType + ", payType=" + payType
				+ ", amount=" + amount + ", result=" + result + ", timeStamp=" + timeStamp + ", sign=" + sign + "]";
	}
	
}
