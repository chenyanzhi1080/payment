package com.xiaoerzuche.biz.zmxy.vo;

import com.alipay.api.internal.mapping.ApiField;

public class ZmAuthCallBackVo {
	@ApiField("open_id")
	private String openId;
	@ApiField("error_message")
	private String errorMessage;
	@ApiField("error_code")
	private String errorCode;
	@ApiField("state")
	private String state;
	@ApiField("app_id")
	private String appId;
	@ApiField("success")
	private String success;
	
	/**
	 * 芝麻信用商户自定义入参
	 */
	private ZmAuthStateVo authStateVo;
	
	private String details;
	
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	
	public ZmAuthStateVo getAuthStateVo() {
		return authStateVo;
	}
	public void setAuthStateVo(ZmAuthStateVo authStateVo) {
		this.authStateVo = authStateVo;
	}
	public ZmAuthCallBackVo() {
		super();
	}
	
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	
	@Override
	public String toString() {
		return "ZmAuthCallBackVo [openId=" + openId + ", errorMessage=" + errorMessage + ", errorCode=" + errorCode
				+ ", state=" + state + ", appId=" + appId + ", success=" + success + ", authStateVo=" + authStateVo
				+ "]";
	}
	
}
