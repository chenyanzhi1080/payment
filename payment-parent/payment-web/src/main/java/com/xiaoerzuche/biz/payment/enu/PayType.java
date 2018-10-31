package com.xiaoerzuche.biz.payment.enu;

import java.util.HashMap;
import java.util.Map;

/** 
* 支付方式
* @author: cyz
* @version: payment
* @time: 2016年5月31日
* 
*/
public enum PayType {

	/**
	 * 阿里wap支付
	 */
	ALIWAPPAY(1, "ALIWAPPAY", "支付宝wap支付"),
	/**
	 * 微信扫码支付
	WEIXINNATIVEPAY(3, "WEIXINNATIVEPAY","微信"),
		 */
	/**
	 * 微信公众号支付
	 */
	WEIXINJSPAY(4, "WEIXINJSPAY","微信公众平台"),
	/**
	 * 微信刷卡支付
	WEIXINMICROPAY(5, "WEIXINMICROPAY","微信"),
	 */
	/**
	 * 微信APP支付 
	 */
	WEIXINAPPPAY(6, "WEIXINAPPPAY","微信开放平台"),
	/**
	 * 电子钱包支付
	 */
	XIAOERWALLET(7, "XIAOERWALLET","电子钱包"),
	/**
	 * 支付宝移动支付(app原生)
	 */
	ALIAPPPAY(8,"ALIAPPPAY","支付宝移动支付"),
	/**
	 * 支付宝app支付
	 */
	ALIOPENAPPPAY(9,"ALIAPPPAY","支付宝APP支付"),
	BIGCUSTOMER(13,"BIGCUSTOMER","企业钱包"),
	;
	// code
	private int		code;
	// 名称
	private String	name;
	
	/**
	 * 备注
	 */
	private String comment;
	
	private PayType(int code, String name, String comment) {
		this.code = code;
		this.name = name;
		this.comment = comment;
	}

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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public static PayType getPayType(int code) {
		PayType[] payTypes = PayType.values();
		for (PayType payType : payTypes) {
			if (payType.getCode() == code) {
				return payType;
			}
		}
		return null;
	}
	
	public static Map<String, String> getCommentMap(){
		Map<String, String> map = new HashMap<String, String>();
		PayType[] payTypes = PayType.values();
		for (PayType payType : payTypes) {
			map.put(String.valueOf(payType.getCode()), payType.getComment());
		}
		return map;
	}

	private static Map<Integer, PayType> codeMap = new HashMap<Integer, PayType>();
	private static Map<Integer, PayType> getCodeMap(){
		if(codeMap!=null || codeMap.isEmpty()){
			for(PayType enu : PayType.values()){
				codeMap.put(enu.getCode(), enu);
			}
		}
		return codeMap;
	}
	
	public static PayType getByCode(Integer code){
		return PayType.getCodeMap().get(code);
	}
	
}
