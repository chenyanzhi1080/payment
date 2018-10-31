package com.xiaoerzuche.biz.zmxy.vo;

public class MemberBlacklistFromVo {
	private String id;//开开出行平台芝麻授权用户ID
	private String blackReasonCode;//拉黑原因业务代码
	private String remark;//拉黑备注
	private String operator;//操作用户
	private String passwd;//密码   MD5(passwd+token)之后的密码，32位小写

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getBlackReasonCode() {
		return blackReasonCode;
	}
	public void setBlackReasonCode(String blackReasonCode) {
		this.blackReasonCode = blackReasonCode;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	public String toString() {
		return "MemberBlacklistFromVo [id=" + id + ", operator=" + operator + ", passwd=" + passwd
				+ ", blackReasonCode=" + blackReasonCode + ", remark=" + remark + "]";
	}
	
}
