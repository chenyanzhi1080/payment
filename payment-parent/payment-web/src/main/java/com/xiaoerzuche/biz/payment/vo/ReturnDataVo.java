package com.xiaoerzuche.biz.payment.vo;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;

/** 
* rest 响应Data
* @author: cyz
* @version: payment-web
* @time: 2016年12月13日
* 
*/
public class ReturnDataVo {
	
	private int code=HttpStatus.OK.value();
	
	private String msg;
	
	private Object data;
	
	

	public ReturnDataVo(Object data) {
		super();
		this.code = HttpStatus.OK.value();
		this.data = data;
		this.msg = "请求成功";
	}
	
	public ReturnDataVo(Object data, String msg) {
		super();
		this.code = HttpStatus.OK.value();
		this.data = data;
		this.msg = StringUtils.isNotBlank(msg)? msg : "请求成功";
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "ReturnDataVo [code=" + code + ", msg=" + msg + ", data=" + data + "]";
	}
}
