package com.xiaoerzuche.biz.payment.enu;

import java.util.HashMap;
import java.util.Map;

public enum TranStatus {
	PROCESSFAIL(0,"受理失败"),
	PROCESS(1,"预支付"),
	PRE(2,"预授权"),
	CFM(3,"预授权完成"),
	SUCCESS(5,"交易成功"),
	FAIL(10,"交易失败"),
	TRADE_FINISHED(20,"交易已关闭")//改交易已关闭，不能进行退款
	;
	public static Map<Integer,TranStatus> mapByCode;//以code为键的map

	static{
		mapByCode = new HashMap<Integer, TranStatus>();
		for(TranStatus enu : TranStatus.values()){
			mapByCode.put(enu.getCode(), enu);
		}
	}
	// code
	private int code;
	// 名称
	private String name;

	private TranStatus(int code, String name) {
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
	
	public static TranStatus getTranStatus(int code){
		TranStatus[] tranTypes = TranStatus.values();
		for(TranStatus tranType : tranTypes){
			if(tranType.getCode() == code){
				return tranType;
			}
		}
		return null;
	}	
}
