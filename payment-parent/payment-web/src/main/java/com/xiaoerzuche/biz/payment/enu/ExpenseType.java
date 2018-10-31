package com.xiaoerzuche.biz.payment.enu;

import java.util.HashMap;
import java.util.Map;

/** 
* 费用类型
* @author: cyz
* @version: payment-web
* @time: 2018年1月11日
* 
*/
public enum ExpenseType {
	//订单费用、押金费用、费用追缴、违章代缴
	ORDER_FEED(1,"订单费用","ORDER_FEED"),
	DEPOSIT_FEED(2,"押金费用","DEPOSIT_FEED"),
	FEED_DEBT(3,"费用追缴","FEED_DEBT"),
	ILLEGAL_FEED(4,"违章代缴","ILLEGAL_FEED")
	;
	private int code;
	private String name;
	private String value;
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
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	private ExpenseType(int code, String name, String value) {
		this.code = code;
		this.name = name;
		this.value = value;
	}
	private static Map<Integer, ExpenseType> codeMap = new HashMap<Integer, ExpenseType>();
	private static Map<Integer, ExpenseType> getCodeMap(){
		if(codeMap!=null || codeMap.isEmpty()){
			for(ExpenseType enu : ExpenseType.values()){
				codeMap.put(enu.getCode(), enu);
			}
		}
		return codeMap;
	}
	
	private static Map<String, ExpenseType> valueMap = new HashMap<String, ExpenseType>();
	private static Map<String, ExpenseType> getValueMap(){
		if(valueMap!=null || valueMap.isEmpty()){
			for(ExpenseType enu : ExpenseType.values()){
				valueMap.put(enu.getValue(), enu);
			}
		}
		return valueMap;
	}
	
	public static ExpenseType getByCode(Integer code){
		return ExpenseType.getCodeMap().get(code);
	}
	
	public static ExpenseType getByValue(String value){
		return ExpenseType.getValueMap().get(value);
	}
}
