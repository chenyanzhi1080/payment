package com.xiaoerzuche.biz.zmxy.enu;

public enum AntifraudRiskEnu {
	R_CN_001("身份证号击中网络欺诈风险名单",2,"中"),
	R_CN_002("身份证号曾经被泄露",1,"低"),
	R_CN_003("身份证号曾经被冒用",1,"低"),
	R_CN_004("身份证号出现在风险关联网络",1,"低"),
	R_CN_JJ_01("身份证当天在多个商户申请",2,"中"),
	R_CN_JJ_02("身份证近一周（不含当天）在多个商户申请",2,"中"),
	R_CN_JJ_03("身份证近一月（不含当天）在多个商户申请",2,"中"),
	R_PH_001("手机号击中网络欺诈风险名单",2,"中"),
	R_PH_002("手机号疑似多个用户共享",1,"低"),
	R_PH_003("手机号疑似虚拟运营商小号",2,"中"),
	R_PH_004("手机号疑似二次放号",3,"高"),
	R_PH_005("手机号失联号",3,"高"),
	R_PH_006("手机号稳定性弱",1,"低"),
	R_PH_JJ_01("手机号当天在多个商户申请",2,"中"),
	R_PH_JJ_02("手机号近一周（不含当天）在多个商户申请",2,"中"),
	R_PH_JJ_03("手机号近一月（不含当天）在多个商户申请",2,"中")
	;
	
	private String riskName;
	private int level;
	private String levelName;
	public String getRiskName() {
		return riskName;
	}
	public void setRiskName(String riskName) {
		this.riskName = riskName;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	private AntifraudRiskEnu(String riskName, int level, String levelName) {
		this.riskName = riskName;
		this.level = level;
		this.levelName = levelName;
	}
	
}