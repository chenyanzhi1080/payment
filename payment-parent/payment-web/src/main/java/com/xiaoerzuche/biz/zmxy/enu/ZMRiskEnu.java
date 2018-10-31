package com.xiaoerzuche.biz.zmxy.enu;

import com.xiaoerzuche.common.util.JsonUtil;

public enum ZMRiskEnu {

	AA("金融信贷类"),
	AB("公检法"),
	AC("金融支付类"),
	AD("租车行业"),
	AE("酒店行业"),
	AF("电商行业"),
	AG("租房行业"), 
	AH("运营商"),
	AI("保险行业");

	private String name;
	private ZMRiskEnu(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public static void main(String[] args){
		ZMRiskEnu.values();
		System.out.println(JsonUtil.toJson(ZMRiskEnu.values()));
	}
}
