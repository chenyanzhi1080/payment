package com.xiaoerzuche.common.exception;

/**
 * 异常信息公共类
 * @author Nick C
 * 
 */
public class ErrorCodeException extends RuntimeException {
	//错误码
	private int code;
	//错误消息
	private String message;

	private static final long serialVersionUID = 1L;
	
	public ErrorCodeException(int code, String message){
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

}
