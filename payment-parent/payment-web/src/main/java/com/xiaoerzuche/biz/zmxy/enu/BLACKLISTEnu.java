package com.xiaoerzuche.biz.zmxy.enu;

public enum BLACKLISTEnu {
	
	WHITE_LIST(0,"白名单"),
	BLACK_LIST(1,"黑名单");
	
	private int code;
	private String name;
	private BLACKLISTEnu(int code, String name) {
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
