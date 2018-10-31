package com.xiaoerzuche.biz.zmxy.enu;

import com.xiaoerzuche.biz.zmxy.handler.bill.IllegalBillHandler;
import com.xiaoerzuche.biz.zmxy.handler.bill.RentBillHandler;
import com.xiaoerzuche.biz.zmxy.handler.bill.RepairBillHandler;

public enum FeedbackTypeEnu {
	RENT(100,"租金未缴",RentBillHandler.class),//100-租金未缴
//	CAR(200,"车辆未还"),//200-车辆未还
	ILLEGAL(509,"违章费用",IllegalBillHandler.class),//509-违章费用
	REPAIR(510,"维修费用",RepairBillHandler.class);//510-维修费用
	
	private int code;
	private String name;
	private Class<?> handleClazz;
	
	private FeedbackTypeEnu(int code, String name,Class<?> clazz) {
		this.code = code;
		this.name = name;
		this.handleClazz = clazz;
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
	public Class<?> getHandleClazz() {
		return handleClazz;
	}
	public void setHandleClazz(Class<?> handleClazz) {
		this.handleClazz = handleClazz;
	}
	
}
