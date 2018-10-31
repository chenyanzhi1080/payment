package com.xiaoerzuche.biz.zmxy.enu;

import java.util.HashMap;
import java.util.Map;

/** 
* 欺诈评分风险级别
* @author: cyz
* @version: payment-web
* @time: 2017年10月30日
* 
*/
public enum AntifraudScoreRiskEnu {
	UNKNOWN(0 ,"未知身份"),
	HIGH(1,"极高风险"),
	MEDIUM(40,"中等风险"),
	LOW(80,"信用良好");
	
	private int score;
	private String levelName;
	private static Map<String, AntifraudScoreRiskEnu> enuMap = new HashMap<String, AntifraudScoreRiskEnu>();
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	private AntifraudScoreRiskEnu(int score, String levelName) {
		this.score = score;
		this.levelName = levelName;
	}
	public static Map<String, AntifraudScoreRiskEnu> getMap(){
		if(enuMap.isEmpty()){
			for(AntifraudScoreRiskEnu enu : AntifraudScoreRiskEnu.values()){
				enuMap.put(String.valueOf(enu), enu);
			}
		}
		return enuMap;
	}
	
	/**
	 * 根据枚举名称查找枚举
	 * @param enumName
	 * @return
	 */
	public static AntifraudScoreRiskEnu getByEnumName(String enumName) {
		enuMap = getMap();
		AntifraudScoreRiskEnu enu = enuMap.get(enumName);
		if(null != enu){
			return enu;
		}else{
			return null;
		}
	}
	
}
