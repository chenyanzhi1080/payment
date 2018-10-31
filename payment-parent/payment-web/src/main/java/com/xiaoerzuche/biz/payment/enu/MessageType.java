package com.xiaoerzuche.biz.payment.enu;

import com.xiaoerzuche.common.exception.ErrorCodeException;

public enum MessageType {
	WECHAT(1,"微信通知"),
	SMS(2,"短信通知");
	// code
	private int code;
	// 名称
	private String name;
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
	private MessageType(int code, String name) {
		this.code = code;
		this.name = name;
	}
	public static MessageType getPayType(int code) {
		MessageType[] messageTypes = MessageType.values();
		for (MessageType messageType : messageTypes) {
			if (messageType.getCode() == code) {
				return messageType;
			}
		}
		return null;
	}
}
