package com.xiaoerzuche.biz.payment.enu;

import java.util.HashMap;
import java.util.Map;

public enum Channel {
	UNDEFINED(0,"UNDEFINED","未定义"),
	HOURLYRATE(1,"HOURLYRATE","时租"),
	DAYLYRATE(2,"DAYLYRATE","日租"),
	CHARGING(3,"CHARGING","充电桩"),
	WALLET(4,"WALLET","电子钱包")
	;
	
	private int code;
	private String value;
	private String name;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	private Channel(int code, String value, String name) {
		this.code = code;
		this.value = value;
		this.name = name;
	}
	@Override
	public String toString() {
		return "Channel [code=" + code + ", value=" + value + ", name=" + name + "]";
	}
	
	private static Map<Integer, Channel> codeMap = new HashMap<Integer, Channel>();
	private static Map<Integer, Channel> getCodeMap(){
		if(codeMap!=null || codeMap.isEmpty()){
			for(Channel channel : Channel.values()){
				codeMap.put(channel.getCode(), channel);
			}
		}
		return codeMap;
	}
	
	private static Map<String, Channel> valueMap = new HashMap<String, Channel>();
	private static Map<String, Channel> getValueMap(){
		if(valueMap!=null || valueMap.isEmpty()){
			for(Channel channel : Channel.values()){
				valueMap.put(channel.getValue(), channel);
			}
		}
		return valueMap;
	}
	
	public static Channel getByCode(Integer code){
		return Channel.getCodeMap().get(code);
	}
	
	public static Channel getByValue(String value){
		return Channel.getValueMap().get(value);
	}
	
}
