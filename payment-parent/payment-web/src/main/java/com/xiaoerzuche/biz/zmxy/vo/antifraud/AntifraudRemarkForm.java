package com.xiaoerzuche.biz.zmxy.vo.antifraud;


public class AntifraudRemarkForm {
	private String id;
	private String remark;
	private String operator;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	@Override
	public String toString() {
		return "AntifraudRemarkForm [id=" + id + ", remark=" + remark + ", operator=" + operator + "]";
	}
	
}
