package com.xiaoerzuche.biz.payment.vo;

public class MessageVo {
	//需要接收到短信的手机号码
	private String phone;
	//需要发送的短信内容
	private String msg;
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public MessageVo() {
		super();
	}
	public MessageVo(String phone, String msg) {
		super();
		this.phone = phone;
		this.msg = msg;
	}
	@Override
	public String toString() {
		return "MessageVo [phone=" + phone + ", msg=" + msg + "]";
	}
	
}
