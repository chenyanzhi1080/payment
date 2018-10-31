package com.xiaoerzuche.biz.zmxy.vo;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;

/** 
* 用户征信风控页面vo
* @author: cyz
* @version: payment-web
* @time: 2017年5月11日
* 
*/
public class MemberCreditMgtVo {
	private String id;//芝麻授权开开出行应用的用户记录主键
	private String name;//姓名
	private String account;//登录手机号
	private String cardId;//证件号
	private String status;//授权状态，参考AuthStatusEnu
	private Integer zmScore;
	private List<MemberCreditFormVo> details;//风控备注
	private String authChangeTime;//最近一次授权状态变更时间
	private List<AuthRecordVo> authRecors;//记录用户授权行为
	private List<ZmDetailVo> zmDetails;//芝麻信用负面记录
	private List<ZmScoreRecorVo> zmScoreRecors;//芝麻信用分变更记录
	private boolean isBlacklist;//是否黑名单
	private String lastModifyTime;
	public String getId() {
		return id;
	}
	public MemberCreditMgtVo setId(String id) {
		this.id = id;
		return this;
	}
	public String getName() {
		return name;
	}
	public MemberCreditMgtVo setName(String name) {
		this.name = name;
		return this;
	}
	public String getAccount() {
		return account;
	}
	public MemberCreditMgtVo setAccount(String account) {
		this.account = account;
		return this;
	}
	public String getCardId() {
		return cardId;
	}
	public MemberCreditMgtVo setCardId(String cardId) {
		this.cardId = cardId;
		return this;
	}
	public String getStatus() {
		return status;
	}
	public MemberCreditMgtVo setStatus(String status) {
		this.status = status;
		return this;
	}
	public List<MemberCreditFormVo> getDetails() {
		return details;
	}
	public MemberCreditMgtVo setDetails(List<MemberCreditFormVo> details) {
		this.details = details;
		return this;
	}
	public String getAuthChangeTime() {
		return authChangeTime;
	}
	public MemberCreditMgtVo setAuthChangeTime(Date authChangeTime) {
		if(null != authChangeTime){
			this.authChangeTime = DateFormatUtils.format(authChangeTime, "yyyy-MM-dd HH:mm:ss");
		}
		return this;
	}
	public List<AuthRecordVo> getAuthRecors() {
		return authRecors;
	}
	public MemberCreditMgtVo setAuthRecors(List<AuthRecordVo> authRecors) {
		this.authRecors = authRecors;
		return this;
	}
	public boolean isBlacklist() {
		return isBlacklist;
	}
	public MemberCreditMgtVo setBlacklist(Integer isBlacklist) {
		this.isBlacklist = isBlacklist>0? true:false;
		return this;
	}
	public String getLastModifyTime() {
		return lastModifyTime;
	}
	public MemberCreditMgtVo setLastModifyTime(Date lastModifyTime) {
		if(null != lastModifyTime){
			this.lastModifyTime = DateFormatUtils.format(lastModifyTime, "yyyy-MM-dd HH:mm:ss");
		}
		return this;
	}
	
	public Integer getZmScore() {
		return zmScore;
	}
	public MemberCreditMgtVo setZmScore(Integer zmScore) {
		this.zmScore = zmScore==null ? 0 : zmScore;
		return this;
	}
	public List<ZmDetailVo> getZmDetails() {
		return zmDetails;
	}
	public MemberCreditMgtVo setZmDetails(List<ZmDetailVo> zmDetails) {
		this.zmDetails = zmDetails;
		return this;
	}
	
	public List<ZmScoreRecorVo> getZmScoreRecors() {
		return zmScoreRecors;
	}
	public MemberCreditMgtVo setZmScoreRecors(List<ZmScoreRecorVo> zmScoreRecors) {
		this.zmScoreRecors = zmScoreRecors;
		return this;
	}
	public MemberCreditMgtVo() {
		super();
	}
	@Override
	public String toString() {
		return "MemberCreditMgtVo [id=" + id + ", name=" + name + ", account=" + account + ", cardId=" + cardId
				+ ", status=" + status + ", zmScore=" + zmScore + ", details=" + details + ", authChangeTime="
				+ authChangeTime + ", authRecors=" + authRecors + ", zmDetails=" + zmDetails + ", zmScoreRecors="
				+ zmScoreRecors + ", isBlacklist=" + isBlacklist + ", lastModifyTime=" + lastModifyTime + "]";
	}
	
}
