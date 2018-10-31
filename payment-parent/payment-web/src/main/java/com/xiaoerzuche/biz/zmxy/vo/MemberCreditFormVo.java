package com.xiaoerzuche.biz.zmxy.vo;

public class MemberCreditFormVo {
	private String id;//开开出行平台芝麻授权用户ID
	private String behaviourCode;//用户失信行为业务代码
	private String amountCode;//用户逾期金额范围业务代码
	private String overdueCode;//用户逾期时间范围业务代码
	private String currentStateCode;//用户当前状态业务代码
	private String overdueTime;//用户逾期发生时间
	private String operator;//操作用户
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBehaviourCode() {
		return behaviourCode;
	}
	public void setBehaviourCode(String behaviourCode) {
		this.behaviourCode = behaviourCode;
	}
	public String getAmountCode() {
		return amountCode;
	}
	public void setAmountCode(String amountCode) {
		this.amountCode = amountCode;
	}
	public String getOverdueCode() {
		return overdueCode;
	}
	public void setOverdueCode(String overdueCode) {
		this.overdueCode = overdueCode;
	}
	public String getCurrentStateCode() {
		return currentStateCode;
	}
	public void setCurrentStateCode(String currentStateCode) {
		this.currentStateCode = currentStateCode;
	}
	public String getOverdueTime() {
		return overdueTime;
	}
	public void setOverdueTime(String overdueTime) {
		this.overdueTime = overdueTime;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	@Override
	public String toString() {
		return "MemberCreditFormVo [id=" + id + ", behaviourCode=" + behaviourCode + ", amountCode=" + amountCode
				+ ", overdueCode=" + overdueCode + ", currentStateCode=" + currentStateCode + ", overdueTime="
				+ overdueTime + ", operator=" + operator + "]";
	}
	
}
