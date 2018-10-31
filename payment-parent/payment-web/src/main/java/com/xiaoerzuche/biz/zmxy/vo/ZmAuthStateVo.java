package com.xiaoerzuche.biz.zmxy.vo;

public class ZmAuthStateVo {
	private String successUrl;
	private String failUrl;
	private String memberCreditId;
	private String name;//授权时的姓名
	private String cardId;//授权时的身份证
	
	public String getName() {
		return name;
	}
	public ZmAuthStateVo setName(String name) {
		this.name = name;
		return this;
	}
	public String getCardId() {
		return cardId;
	}
	public ZmAuthStateVo setCardId(String cardId) {
		this.cardId = cardId;
		return this;
	}
	public String getSuccessUrl() {
		return successUrl;
	}
	public ZmAuthStateVo setSuccessUrl(String successUrl) {
		this.successUrl = successUrl;
		return this;
	}
	public String getFailUrl() {
		return failUrl;
	}
	public ZmAuthStateVo setFailUrl(String failUrl) {
		this.failUrl = failUrl;
		return this;
	}
	public String getMemberCreditId() {
		return memberCreditId;
	}
	public ZmAuthStateVo setMemberCreditId(String memberCreditId) {
		this.memberCreditId = memberCreditId;
		return this;
	}
	
	public ZmAuthStateVo() {
		super();
	}
	@Override
	public String toString() {
		return "ZmAuthStateVo [successUrl=" + successUrl + ", failUrl=" + failUrl + ", memberCreditId=" + memberCreditId
				+ ", name=" + name + ", cardId=" + cardId + "]";
	}
	
}
