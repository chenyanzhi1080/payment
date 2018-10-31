package com.xiaoerzuche.biz.zmxy.vo;

import com.xiaoerzuche.biz.zmxy.dao.sqlbuilder.MemberCreditBuilder;

/** 
* 芝麻信用风控列表查询vo
* @author: cyz
* @version: payment-web
* @time: 2017年7月4日
* 
*/
public class MemberCreditQueryVo {
	private String account;//登录手机号
	private String cardId;//身份证号
	private String authTimeEnd;//授权变更操作时间结束
	private String authTimeStart;//授权变更操作时间开始
	private Integer authStatus;//授权状态
	private String riskCode;//负面记录类型(行业关注名单风险信息行业编码)
	private Integer limit;
	private Integer offset;
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	public String getAuthTimeEnd() {
		return authTimeEnd;
	}
	public void setAuthTimeEnd(String authTimeEnd) {
		this.authTimeEnd = authTimeEnd;
	}
	public String getAuthTimeStart() {
		return authTimeStart;
	}
	public void setAuthTimeStart(String authTimeStart) {
		this.authTimeStart = authTimeStart;
	}
	public Integer getAuthStatus() {
		return authStatus;
	}
	public void setAuthStatus(Integer authStatus) {
		this.authStatus = authStatus;
	}
	public String getRiskCode() {
		return riskCode;
	}
	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}
	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	public Integer getOffset() {
		return offset;
	}
	public void setOffset(Integer offset) {
		this.offset = offset;
	}
	
	@Override
	public String toString() {
		return "MemberCreditQueryVo [account=" + account + ", cardId=" + cardId + ", authTimeEnd=" + authTimeEnd
				+ ", authTimeStart=" + authTimeStart + ", authStatus=" + authStatus + ", riskCode=" + riskCode
				+ ", limit=" + limit + ", offset=" + offset + "]";
	}
	public MemberCreditBuilder builder(){
		MemberCreditBuilder builder = new MemberCreditBuilder();
		builder
		.offset(this.offset)
		.limit(this.limit)
		.setAccount(this.account)
		.setCardId(this.cardId)
		.setAuthTimeStart(this.authTimeStart)
		.setAuthTimeEnd(this.authTimeEnd)
		.setAuthStatus(this.authStatus)
		.setRiskCode(this.riskCode);
		return builder;
	}
}
