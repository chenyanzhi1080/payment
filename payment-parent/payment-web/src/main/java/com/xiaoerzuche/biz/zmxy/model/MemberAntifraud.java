package com.xiaoerzuche.biz.zmxy.model;

import java.util.Date;

/**
 * 用户欺诈
 * 
 * @author: cyz
 * @version: payment-web
 * @time: 2017年8月11日
 * 
 */
public class MemberAntifraud {

	private String id; // 主键

	private String account; // 会员账号

	private String name; // 会员姓名

	private String idCard; // 身份证号

	private String registerCity; // 注册城市

	private Date registerTime; // 注册时间

	private Integer antifraudScore; // 欺诈分
	
	private String antifraudVerify; // 欺诈信息验证结果

	private String antifraudRisk; // 欺诈风险码

	private Integer hit; // 是否命中
	
	private String errorMessage;//请求异常信息
	
	private Date createTime; // 创建时间

	private Date lastModifyTime; // 变更时间

	private String operation; // 操作人
	
	private String remark;

	public MemberAntifraud setId(String id) {
		this.id = id;
		return this;
	}

	public String getId() {
		return id;
	}

	public MemberAntifraud setAccount(String account) {
		this.account = account;
		return this;
	}

	public String getAccount() {
		return account;
	}

	public MemberAntifraud setName(String name) {
		this.name = name;
		return this;
	}

	public String getName() {
		return name;
	}

	public String getIdCard() {
		return idCard;
	}

	public MemberAntifraud setIdCard(String idCard) {
		this.idCard = idCard;
		return this;
	}

	public MemberAntifraud setRegisterCity(String registerCity) {
		this.registerCity = registerCity;
		return this;
	}

	public String getRegisterCity() {
		return registerCity;
	}

	public MemberAntifraud setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
		return this;
	}

	public Date getRegisterTime() {
		return registerTime;
	}

	public MemberAntifraud setAntifraudScore(Integer antifraudScore) {
		this.antifraudScore = antifraudScore;
		return this;
	}

	public Integer getAntifraudScore() {
		return antifraudScore;
	}

	public MemberAntifraud setAntifraudVerify(String antifraudVerify) {
		this.antifraudVerify = antifraudVerify;
		return this;
	}

	public String getAntifraudVerify() {
		return antifraudVerify;
	}

	public MemberAntifraud setAntifraudRisk(String antifraudRisk) {
		this.antifraudRisk = antifraudRisk;
		return this;
	}

	public String getAntifraudRisk() {
		return antifraudRisk;
	}

	public MemberAntifraud setHit(Integer hit) {
		this.hit = hit;
		return this;
	}

	public Integer getHit() {
		return hit;
	}

	public MemberAntifraud setCreateTime(Date createTime) {
		this.createTime = createTime;
		return this;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public MemberAntifraud setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
		return this;
	}

	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	public MemberAntifraud setOperation(String operation) {
		this.operation = operation;
		return this;
	}

	public String getOperation() {
		return operation;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}

	public MemberAntifraud setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
		return this;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public MemberAntifraud() {
		super();
	}

}
