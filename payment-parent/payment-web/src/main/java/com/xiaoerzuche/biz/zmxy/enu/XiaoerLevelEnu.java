package com.xiaoerzuche.biz.zmxy.enu;

public enum XiaoerLevelEnu {
	LEVEL_0(0,"行业负面记录检验"),
	LEVEL_1(1,"芝麻分检验"),
	LEVEL_2(2,"欺诈评分检验");
	
	private int code;
	private String detailed;
	
	private XiaoerLevelEnu(int code, String detailed) {
		this.code = code;
		this.detailed = detailed;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getDetailed() {
		return detailed;
	}
	public void setDetailed(String detailed) {
		this.detailed = detailed;
	}
	
}
