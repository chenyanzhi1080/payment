package com.xiaoerzuche.biz.zmxy.vo.antifraud;

public class AntifraudScoreRiskVo {
	private String level;
	private String levelName;
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	
	public AntifraudScoreRiskVo() {
		super();
	}
	public AntifraudScoreRiskVo(String level, String levelName) {
		super();
		this.level = level;
		this.levelName = levelName;
	}
	@Override
	public String toString() {
		return "AntifraudScoreRiskVo [level=" + level + ", levelName=" + levelName + "]";
	}
	
}
