package com.xiaoerzuche.biz.zmxy.vo.antifraud;

import java.util.Date;

public class AntifraudRemarkVo {
	private String remark;
	private String operator;
	private Date createTime;
	public String getRemark() {
		return remark;
	}
	public AntifraudRemarkVo setRemark(String remark) {
		this.remark = remark;
		return this;
	}
	public String getOperator() {
		return operator;
	}
	public AntifraudRemarkVo setOperator(String operator) {
		this.operator = operator;
		return this;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public AntifraudRemarkVo setCreateTime(Date createTime) {
		this.createTime = createTime;
		return this;
	}
	@Override
	public String toString() {
		return "AntifraudRemarkVo [remark=" + remark + ", operator=" + operator + ", createTime=" + createTime + "]";
	}
	
}
