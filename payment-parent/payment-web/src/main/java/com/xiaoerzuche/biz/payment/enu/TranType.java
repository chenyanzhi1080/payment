package com.xiaoerzuche.biz.payment.enu;

/** 
* 交易类型:分为消费、退款
* @author: cyz
* @version: payment
* @time: 2016年5月5日
* 
*/
public enum TranType {
	CONSUME(1,"消费"),
	PREAUTH(2,"预授权"),
	ADJUST(5,"预授权完成"),//信用卡预授权模式下回发生
	REFUND(10, "退款"),
	BINDING(15, "绑定")//目前只有快钱绑定信用卡场景
	;
	
	//业务码
	private int code;
	// 名称
	private String name;

	private TranType(int code, String name) {
		this.code = code;
		this.name = name;
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
	
	public static TranType getTranType(int code){
		TranType[] tranTypes = TranType.values();
		for(TranType tranType : tranTypes){
			if(tranType.getCode() == code){
				return tranType;
			}
		}
		return null;
	}
}
