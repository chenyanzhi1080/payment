package com.xiaoerzuche.biz.zmxy.vo.antifraud;

import com.xiaoerzuche.biz.zmxy.dao.sqlbuilder.MemberAntifraudBuilder;

public class MemberAntifraudQueryVo {
	private String account;//用户登录手机号	
	private String idCard;//身份证号
	private Integer hit;//欺诈关注清单是否命中（1命中，2未命中）
	private String name;//会员姓名
	private String scoreRisk;//欺诈分风险级别
	private String registerCity;//注册城市
	private String registerTimeEnd;//注册时间结束
	private String registerTimeStart;//注册时间开始
	private Integer limit;
	private Integer offset;
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public Integer getHit() {
		return hit;
	}
	public void setHit(Integer hit) {
		this.hit = hit;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRegisterCity() {
		return registerCity;
	}
	public void setRegisterCity(String registerCity) {
		this.registerCity = registerCity;
	}
	public String getScoreRisk() {
		return scoreRisk;
	}
	public void setScoreRisk(String scoreRisk) {
		this.scoreRisk = scoreRisk;
	}
	public String getRegisterTimeEnd() {
		return registerTimeEnd;
	}
	public void setRegisterTimeEnd(String registerTimeEnd) {
		this.registerTimeEnd = registerTimeEnd;
	}
	public String getRegisterTimeStart() {
		return registerTimeStart;
	}
	public void setRegisterTimeStart(String registerTimeStart) {
		this.registerTimeStart = registerTimeStart;
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
		return "MemberAntifraudQueryVo [account=" + account + ", idCard=" + idCard + ", hit=" + hit + ", name=" + name
				+ ", scoreRisk=" + scoreRisk + ", registerCity=" + registerCity + ", registerTimeEnd=" + registerTimeEnd
				+ ", registerTimeStart=" + registerTimeStart + ", limit=" + limit + ", offset=" + offset + "]";
	}
	public MemberAntifraudBuilder builder(){
		MemberAntifraudBuilder builder = new MemberAntifraudBuilder();
		builder
		.offset(this.offset)
		.limit(this.limit)
		.setAccount(this.account)
		.setIdCard(this.idCard)
		.setHit(this.hit)
		.setName(this.name)
		.setScoreRisk(this.scoreRisk)
		.setRegisterCity(this.registerCity)
		.setRegisterTimeStart(this.registerTimeStart)
		.setRegisterTimeEnd(this.registerTimeEnd)
		;
		return builder;
	}
}
