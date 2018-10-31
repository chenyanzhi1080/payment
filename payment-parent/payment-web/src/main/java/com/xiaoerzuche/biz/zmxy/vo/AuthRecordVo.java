package com.xiaoerzuche.biz.zmxy.vo;

public class AuthRecordVo {
	private String authStatus;
	private String recordTime;
	public String getAuthStatus() {
		return authStatus;
	}
	public AuthRecordVo setAuthStatus(String authStatus) {
		this.authStatus = authStatus;
		return this;
	}
	public String getRecordTime() {
		return recordTime;
	}
	public AuthRecordVo setRecordTime(String recordTime) {
		this.recordTime = recordTime;
		return this;
	}
	@Override
	public String toString() {
		return "AuthRecordVo [authStatus=" + authStatus + ", recordTime=" + recordTime + "]";
	}
	
}
