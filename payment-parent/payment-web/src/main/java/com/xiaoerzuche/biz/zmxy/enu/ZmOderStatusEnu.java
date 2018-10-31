package com.xiaoerzuche.biz.zmxy.enu;

public enum ZmOderStatusEnu {
	START(3,"开始使用"),
	NORMAL(0,"正常"),//（有账单待支付）
	DEFAULT(1,"违约"),//（有账单逾期未结清费用）
	SETTLED(2,"结清");//（使用结束并结清费用）
	private int code;
	private String name;
	
	private ZmOderStatusEnu(int code, String name) {
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
