package com.xiaoerzuche.biz.payment.enu;

/** 
* 微信交易业务码
* @author: cyz
* @version: payment
* @time: 2016年6月8日
* 
*/
public enum WetchatPayBusinessCode {
	SUCCESS(200,"SUCCESS","交易成功"),
	SYSTEMERROR(10000,"SYSTEMERROR","系统超时请重试"),
	NOTENOUGH(10001,"NOTENOUGH","余额不足"),
	ORDERPAID(10002,"ORDERPAID","商户订单已支付"),
	ORDERCLOSED(10003,"ORDERCLOSED","订单已关闭"),
	OUT_TRADE_NO_USED(10004,"OUT_TRADE_NO_USED","商户订单号重复"),
	USER_ACCOUNT_ABNORMAL(10005,"USER_ACCOUNT_ABNORMAL","用户帐号异常或注销,商户可自行处理退款"), 
	INVALID_TRANSACTIONID(10006,"INVALID_TRANSACTIONID","原交易号不存在,商户可自行处理退款"),
	TRADE_STATE_ERROR(10007,"TRADE_STATE_ERROR","订单状态错误,已经退过款"),
	REFUND_FEE_INVALID(10008,"REFUND_FEE_INVALID","累计退款金额大于支付金额")
	;
	
	private int code;
	/**
	 * 业务码
	 */
	private String businessCode;
	/**
	 * 业务描述
	 */
	private String businessDes;
	
	private WetchatPayBusinessCode(int code, String businessCode, String businessDes) {
		this.code = code;
		this.businessCode = businessCode;
		this.businessDes = businessDes;
	}
	public String getBusinessCode() {
		return businessCode;
	}
	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}
	public String getBusinessDes() {
		return businessDes;
	}
	public void setBusinessDes(String businessDes) {
		this.businessDes = businessDes;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	
}
