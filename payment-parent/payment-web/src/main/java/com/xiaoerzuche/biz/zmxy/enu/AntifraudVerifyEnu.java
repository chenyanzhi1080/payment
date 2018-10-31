package com.xiaoerzuche.biz.zmxy.enu;

import java.util.HashMap;
import java.util.Map;

public enum AntifraudVerifyEnu {
	//身份号证验证
	V_CN_NA("查询不到身份证信息"),
	V_CN_NM_UM("身份证与姓名不匹配"),
	V_CN_NM_MA("身份证与姓名匹配"),
	V_CN_NM_UK("身份证与姓名匹配未知"),
	//手机号欺诈验证
	V_PH_NA("查询不到电话号码信息"),
	V_PH_CN_UK("电话号码与本人匹配未知"),
	V_PH_CN_UM("电话号码与本人不匹配"),
	V_PH_CN_MA_UL30D("电话号码与本人匹配，30天内有使用"),
	V_PH_CN_MA_UL90D("电话号码与本人匹配，距今[30,90)天内有使用"),
	V_PH_CN_MA_UL180D("电话号码与本人匹配，距今[90,180)天内有使用"),
	V_PH_CN_MA_UM180D("电话号码与本人匹配，距今180天内没有使用"),
	V_PH_NM_UK("电话号码与姓名匹配未知"),
	V_PH_NM_UM("电话号码与姓名不匹配"),
	V_PH_NM_MA_UL30D("电话号码与姓名匹配，30天内有使用"),
	V_PH_NM_MA_UL90D("电话号码与姓名匹配，距今[30,90)天内有使用"),
	V_PH_NM_MA_UL180D("电话号码与姓名匹配，距今[90,180)天内有使用"),
	V_PH_NM_MA_UM180D("电话号码与姓名匹配，距今180天内没有使用")

	;
	
	
	private String verifyResult;

	private static Map<String, AntifraudVerifyEnu> enuMap = new HashMap<String, AntifraudVerifyEnu>();
	
	public String getVerifyResult() {
		return verifyResult;
	}

	public void setVerifyResult(String verifyResult) {
		this.verifyResult = verifyResult;
	}

	private AntifraudVerifyEnu(String verifyResult) {
		this.verifyResult = verifyResult;
	}
	
	public static Map<String, AntifraudVerifyEnu> getMap(){
		if(enuMap.isEmpty()){
			for(AntifraudVerifyEnu enu : AntifraudVerifyEnu.values()){
				enuMap.put(String.valueOf(enu), enu);
			}
		}
		return enuMap;
	}
	
	/**
	 * 根据枚举字符串值查找枚举的verifyResult，如不存在则直接返回输入内容
	 * @param enumName
	 * @return
	 */
	public static String getVerifyResult(String enumName) {
		enuMap = getMap();
		AntifraudVerifyEnu enu = enuMap.get(enumName);
		if(null != enu){
			return enu.getVerifyResult();
		}else{
			return "未知欺诈验证结果:"+enumName;
		}
	}
}
