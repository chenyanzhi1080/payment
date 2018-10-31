package com.xiaoerzuche.biz.zmxy.enu;

public enum CreditStatus {
	NOT_AUTH(1,"未授权"),
	NO_BAD_RECORD(2,"无不良记录"),
	HAD_BAD_RECORD(3,"存在不良记录"),
	ZMSCORE_NOT_ENOUGH(4,"芝麻分不足"),
	UNBIND(5,"已解绑");
	
	private int code;
	private String name;
	private CreditStatus(int code, String name) {
		this.code = code;
		this.name = name;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
