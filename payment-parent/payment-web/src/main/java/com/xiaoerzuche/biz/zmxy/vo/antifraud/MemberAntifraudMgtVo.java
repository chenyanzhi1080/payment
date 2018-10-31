package com.xiaoerzuche.biz.zmxy.vo.antifraud;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.google.gson.reflect.TypeToken;
import com.xiaoerzuche.biz.zmxy.enu.AntifraudVerifyEnu;
import com.xiaoerzuche.biz.zmxy.model.MemberAntifraud;
import com.xiaoerzuche.common.util.JsonUtil;

public class MemberAntifraudMgtVo {
	private String id; // 主键

	private String account; // 会员账号

	private String name; // 会员姓名

	private String idCard; // 身份证号

	private String registerCity; // 注册城市

	private Date registerTime; // 注册时间

	private Integer antifraudScore; // 欺诈分
	
	private String antifraudScoDescribe;//欺诈评分风险描述

	private List<String> antifraudVerify; // 欺诈信息验证结果

	private Integer hit; // 是否命中
	
	private List<AntifraudRiskVo> antifraudRiskList;
	
	private List<AntifraudRemarkVo> remarks;
	
	private String errorMessage;//请求异常信息
	
	private Date createTime; // 创建时间

	private Date lastModifyTime; // 变更时间

	private String operation; // 操作人

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
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

	public Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	public Integer getAntifraudScore() {
		return antifraudScore;
	}

	public void setAntifraudScore(Integer antifraudScore) {
		this.antifraudScore = antifraudScore;
	}

	public String getAntifraudScoDescribe() {
		return antifraudScoDescribe;
	}

	public void setAntifraudScoDescribe(String antifraudScoDescribe) {
		this.antifraudScoDescribe = antifraudScoDescribe;
	}

	public List<String> getAntifraudVerify() {
		return antifraudVerify;
	}

	public void setAntifraudVerify(List<String> antifraudVerify) {
		this.antifraudVerify = antifraudVerify;
	}

	public List<AntifraudRiskVo> getAntifraudRiskList() {
		return antifraudRiskList;
	}

	public void setAntifraudRiskList(List<AntifraudRiskVo> antifraudRiskList) {
		this.antifraudRiskList = antifraudRiskList;
	}

	public Integer getHit() {
		return hit;
	}

	public void setHit(Integer hit) {
		this.hit = hit;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public List<AntifraudRemarkVo> getRemarks() {
		return remarks;
	}

	public void setRemarks(List<AntifraudRemarkVo> remarks) {
		this.remarks = remarks;
	}


	@Override
	public String toString() {
		return "MemberAntifraudMgtVo [id=" + id + ", account=" + account + ", name=" + name + ", idCard=" + idCard
				+ ", registerCity=" + registerCity + ", registerTime=" + registerTime + ", antifraudScore="
				+ antifraudScore + ", antifraudScoDescribe=" + antifraudScoDescribe + ", antifraudVerify="
				+ antifraudVerify + ", hit=" + hit + ", antifraudRiskList=" + antifraudRiskList + ", remarks=" + remarks
				+ ", errorMessage=" + errorMessage + ", createTime=" + createTime + ", lastModifyTime=" + lastModifyTime
				+ ", operation=" + operation + "]";
	}

	public MemberAntifraudMgtVo() {
		super();
	}

	public MemberAntifraudMgtVo(MemberAntifraud memberAntifraud) {
		this.id=memberAntifraud.getId(); // 主键
		this.account=memberAntifraud.getAccount(); // 会员账号
		this.name=memberAntifraud.getName(); // 会员姓名
		this.idCard=memberAntifraud.getIdCard(); // 身份证号
		this.registerCity=memberAntifraud.getRegisterCity(); // 注册城市
		this.registerTime=memberAntifraud.getRegisterTime(); // 注册时间
		this.antifraudScore=memberAntifraud.getAntifraudScore(); // 欺诈分
		if(this.antifraudScore!=null){
			if(0==this.antifraudScore){
				this.antifraudScoDescribe = this.antifraudScore+"分；无法识别";
			}
			if(0<this.antifraudScore && this.antifraudScore<=40){
				this.antifraudScoDescribe = this.antifraudScore+"分；极高风险|人工核查";
			}
			if(40<this.antifraudScore && this.antifraudScore<80){
				this.antifraudScoDescribe = this.antifraudScore+"分；中等风险|持续关注";
			}
			if(80<=this.antifraudScore && this.antifraudScore<=100){
				this.antifraudScoDescribe = this.antifraudScore+"分；信用良好";
			}
		}

		this.antifraudVerify = new ArrayList<String>(); // 欺诈信息验证结果
		if(StringUtils.isNotBlank(memberAntifraud.getAntifraudVerify())){
			List<String> verifyCodeList = JsonUtil.fromJson(memberAntifraud.getAntifraudVerify(), new TypeToken<List<String>>(){});
			for(String code : verifyCodeList){
				String verifyResult = AntifraudVerifyEnu.getVerifyResult(code);
				this.antifraudVerify.add(verifyResult);
			}
		}
		
		this.antifraudRiskList = new ArrayList<AntifraudRiskVo>();
		if(StringUtils.isNotBlank(memberAntifraud.getAntifraudRisk())){
			List<String> riskCodeList = JsonUtil.fromJson(memberAntifraud.getAntifraudRisk(), new TypeToken<List<String>>(){});
			for(String riskCode : riskCodeList){
				AntifraudRiskVo antifraudRiskVo = new AntifraudRiskVo(riskCode);
				this.antifraudRiskList.add(antifraudRiskVo);
			}
		}
		this.remarks = new ArrayList<AntifraudRemarkVo>();
		if(StringUtils.isNotBlank(memberAntifraud.getRemark())){
			remarks = JsonUtil.fromJson(memberAntifraud.getRemark(), new TypeToken<List<AntifraudRemarkVo>>(){});
		}
		this.hit= memberAntifraud.getHit()==null ? 2:memberAntifraud.getHit(); // 是否命中
		this.errorMessage=memberAntifraud.getErrorMessage();//请求异常信息
		this.createTime=memberAntifraud.getCreateTime(); // 创建时间
		this.lastModifyTime=memberAntifraud.getLastModifyTime(); // 变更时间
		this.operation=memberAntifraud.getOperation(); // 操作人
	}
}
