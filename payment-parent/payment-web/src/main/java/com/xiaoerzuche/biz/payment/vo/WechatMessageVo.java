package com.xiaoerzuche.biz.payment.vo;

public class WechatMessageVo {
	private String openId; 
	private String orderId;
	private String type;
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public WechatMessageVo() {
		super();
	}
	public WechatMessageVo(String openId, String orderId, String type) {
		super();
		this.openId = openId;
		this.orderId = orderId;
		this.type = type;
	}
	@Override
	public String toString() {
		return "WechatMessageVo [openId=" + openId + ", orderId=" + orderId + ", type=" + type + "]";
	}
	
}
