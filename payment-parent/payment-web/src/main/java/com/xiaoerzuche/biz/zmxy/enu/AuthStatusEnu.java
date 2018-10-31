package com.xiaoerzuche.biz.zmxy.enu;

import java.util.HashMap;
import java.util.Map;

/** 
* 授权状态enu
* @author: cyz
* @version: payment-web
* @time: 2017年7月6日
* 
*/
public enum AuthStatusEnu {
	NOT_AUTH(1,"NOT_AUTH","未授权"),//取消授权或未授权阶段
	AUTH(2,"AUTH","已授权"),
	CANCEL_AUTH(3,"CANCEL_AUTH","取消授权"),
	UNBIND(5,"UNBIND","已解绑");
	private int code;
	private String key;
	private String value;
	
	public static Map<Integer, AuthStatusEnu> mapByCode;//以code为键的map
	public static Map<String, AuthStatusEnu> mapByKey;//以key为键的map
	
	static{
		mapByCode = new HashMap<Integer, AuthStatusEnu>();
		mapByKey = new HashMap<String, AuthStatusEnu>();
		for(AuthStatusEnu enu : AuthStatusEnu.values()){
			mapByCode.put(enu.getCode(), enu);
			mapByKey.put(enu.getKey(), enu);
		}
	}
	
	
	private AuthStatusEnu(int code, String key, String value) {
		this.code = code;
		this.key = key;
		this.value = value;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}
