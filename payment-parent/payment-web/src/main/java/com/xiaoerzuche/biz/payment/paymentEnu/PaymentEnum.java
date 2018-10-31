package com.xiaoerzuche.biz.payment.paymentEnu;

public enum PaymentEnum {
	
	SUCCESS(0,"请求成功"),
	//支付类1000起增加
	PAY_SUCCESS(1000, "支付成功"),
	PAY_NOTENOUGH(1001, "余额不足"),
	ORDER_CLOSED(1002,"订单已关闭"),
	ORDER_PAIDED(1003,"订单已支付"),
	PAY_FAILED(1004,"支付失败"),
	ORDER_TIMEOUT(1005,"订单已过期"),
	
	//退款类2000起增加
	REFUND_ACCPET(2000, "退款受理"), 
	REFUND_SUCCESS(2001, "退款成功"),
	TRAN_NOT_EXIST(2002, "原支付不存在"), 
	REFUND_NOT_EQUAL_TOTAL(2003, "退款金额超过原支付金额"), 
	
	//查询类3000起增加
	QUERY_SUCCESS(3000, "查询成功"), 
	ORDER_NOT_EXIST(3001, "交易订单不存在"),
	
	//系统或账户类4000起增加
	SYSTEMERROR(4001, "系统异常"),
	ACCOUNT_NOT_EXIST(4002,"账户不存在"),
	PARAM_ERROR(4003,"参数异常"),
	SIGN_ERROR(4004,"签名异常"),
	BIND_ERROR(4005,"绑定失败"),
	ACCOUNT_BOUND(4006,"当前账户存在绑定的信用卡"),
	BIND_SUCCESS(5000,"绑定成功");
	// code
	private int code;
	// 名称
	private String name;

	private PaymentEnum(int code, String name) {
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

	public static PaymentEnum get(int code) {
		for (PaymentEnum paymentEnum : PaymentEnum.values()) {
			if (paymentEnum.getCode() == code) {
				return paymentEnum;
			}
		}
		return null;
	}
}
