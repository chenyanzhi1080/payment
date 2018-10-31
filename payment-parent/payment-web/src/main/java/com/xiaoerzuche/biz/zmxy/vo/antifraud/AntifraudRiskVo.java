package com.xiaoerzuche.biz.zmxy.vo.antifraud;

import com.xiaoerzuche.biz.zmxy.enu.AntifraudRiskEnu;

public class AntifraudRiskVo {
//	private String riskCode;
	private String riskName;
//	private int level;
	private String levelName;
	public String getRiskName() {
		return riskName;
	}
	public void setRiskName(String riskName) {
		this.riskName = riskName;
	}
	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	@Override
	public String toString() {
		return "AntifraudRiskVo [riskName=" + riskName + ", levelName=" + levelName + "]";
	}
	public AntifraudRiskVo() {
		super();
	}
	
	public AntifraudRiskVo(String riskCode) {
		AntifraudRiskEnu enu = AntifraudRiskEnu.valueOf(riskCode);
		this.riskName=enu.getRiskName();
		this.levelName=enu.getLevelName()+"风险";
	}
}
