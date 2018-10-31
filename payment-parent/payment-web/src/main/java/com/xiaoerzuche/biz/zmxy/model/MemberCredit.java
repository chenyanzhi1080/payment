package com.xiaoerzuche.biz.zmxy.model;

import java.lang.Integer;
import java.util.Date;

/**
 * 会员信用风控，数据来源自芝麻信用和风控上报
 * 
 * @author: cyz
 * @version: payment-web
 * @time: 2017年5月8日
 * 
 */
public class MemberCredit {
	private String id;

	private String zmopenId; // 芝麻信用openid

	private String account; // 会员账号

	private String name; // 姓名

	private String cardId; // 身份证件号
	
	private String transactionId;//芝麻信用业务交易凭证
	
	private Integer zmScore;//芝麻分
	
	private String details; //开开出行平台风控信息
	
	private String zmDetails;// 行业关注名单信息详情,对应芝麻信用的ZmWatchListDetail
	
	private String authResult;//授权回调结果,记录便于排查问题
	
	private String authRecord;//授权 变更记录
	
	private Date authChangeTime;//最近一次授权状态变更时间
	
	private Integer authStatus; // 授权状态
	
	private Integer isBlacklist;//是否开开出行平台黑名单 
	
	private Date createTime; //创建时间

	private Date lastModifyTime; // 最近更新时间

	private String operation; // 操作人
	
	private Integer level;//风控级别
	
	public Integer getLevel() {
		return level;
	}

	public MemberCredit setLevel(Integer level) {
		this.level = level;
		return this;
	}

	public Integer getZmScore() {
		return zmScore;
	}

	public MemberCredit setZmScore(Integer zmScore) {
		this.zmScore = zmScore;
		return this;
	}

	public String getAuthRecord() {
		return authRecord;
	}

	public MemberCredit setAuthRecord(String authRecord) {
		this.authRecord = authRecord;
		return this;
	}

	public Date getAuthChangeTime() {
		return authChangeTime;
	}

	public MemberCredit setAuthChangeTime(Date authChangeTime) {
		this.authChangeTime = authChangeTime;
		return this;
	}

	public String getId() {
		return id;
	}

	public MemberCredit setId(String id) {
		this.id = id;
		return this;
	}

	public MemberCredit setZmopenId(String zmopenId) {
		this.zmopenId = zmopenId;
		return this;
	}

	public String getZmopenId() {
		return zmopenId;
	}

	public MemberCredit setAccount(String account) {
		this.account = account;
		return this;
	}

	public String getAccount() {
		return account;
	}

	public MemberCredit setName(String name) {
		this.name = name;
		return this;
	}

	public String getName() {
		return name;
	}

	public MemberCredit setCardId(String cardId) {
		this.cardId = cardId;
		return this;
	}

	public String getCardId() {
		return cardId;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public MemberCredit setTransactionId(String transactionId) {
		this.transactionId = transactionId;
		return this;
	}

	public MemberCredit setDetails(String details) {
		this.details = details;
		return this;
	}

	public String getDetails() {
		return details;
	}
	
	public MemberCredit setAuthResult(String authResult) {
		this.authResult = authResult;
		return this;
	}
	
	public String getAuthResult() {
		return authResult;
	}

	public MemberCredit setAuthStatus(Integer authStatus) {
		this.authStatus = authStatus;
		return this;
	}

	public Integer getAuthStatus() {
		return authStatus;
	}

	public MemberCredit setCreateTime(Date createTime) {
		this.createTime = createTime;
		return this;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public MemberCredit setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
		return this;
	}

	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	public MemberCredit setOperation(String operation) {
		this.operation = operation;
		return this;
	}

	public String getOperation() {
		return operation;
	}

	public Integer getIsBlacklist() {
		return isBlacklist;
	}

	public MemberCredit setIsBlacklist(Integer isBlacklist) {
		this.isBlacklist = isBlacklist;
		return this;
	}

	public String getZmDetails() {
		return zmDetails;
	}

	public MemberCredit setZmDetails(String zmDetails) {
		this.zmDetails = zmDetails;
		return this;
	}

	public MemberCredit() {
		super();
	}

	@Override
	public String toString() {
		return "MemberCredit [id=" + id + ", zmopenId=" + zmopenId + ", account=" + account + ", name=" + name
				+ ", cardId=" + cardId + ", transactionId=" + transactionId + ", zmScore=" + zmScore + ", details="
				+ details + ", zmDetails=" + zmDetails + ", authResult=" + authResult + ", authRecord=" + authRecord
				+ ", authChangeTime=" + authChangeTime + ", authStatus=" + authStatus + ", isBlacklist=" + isBlacklist
				+ ", createTime=" + createTime + ", lastModifyTime=" + lastModifyTime + ", operation=" + operation
				+ ", level=" + level + ", getLevel()=" + getLevel() + ", getZmScore()=" + getZmScore()
				+ ", getAuthRecord()=" + getAuthRecord() + ", getAuthChangeTime()=" + getAuthChangeTime() + ", getId()="
				+ getId() + ", getZmopenId()=" + getZmopenId() + ", getAccount()=" + getAccount() + ", getName()="
				+ getName() + ", getCardId()=" + getCardId() + ", getTransactionId()=" + getTransactionId()
				+ ", getDetails()=" + getDetails() + ", getAuthResult()=" + getAuthResult() + ", getAuthStatus()="
				+ getAuthStatus() + ", getCreateTime()=" + getCreateTime() + ", getLastModifyTime()="
				+ getLastModifyTime() + ", getOperation()=" + getOperation() + ", getIsBlacklist()=" + getIsBlacklist()
				+ ", getZmDetails()=" + getZmDetails() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}

}
