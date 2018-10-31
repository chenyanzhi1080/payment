package com.xiaoerzuche.biz.payment.vo.rest;

import com.google.gson.reflect.TypeToken;
import com.xiaoerzuche.common.util.JsonUtil;

public class RestResponeVo {
	
	private int code;
	
	private String msg;
	
	private Object data;
	
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

	public <T> T getDateToClass(Class<T> clazz){
		if(null == this.data){
			return null;
		}else{
			return JsonUtil.fromJson(JsonUtil.toJson(this.data), clazz);
		}
	}

	public <T> T getDateToTypeToken(TypeToken<T> token){
		if(null == this.data){
			return null;
		}else{
			return JsonUtil.fromJson(JsonUtil.toJson(this.data), token);
		}
	}
	
	@Override
	public String toString() {
		return "RestResponeVo [code=" + code + ", msg=" + msg + ", data=" + data + "]";
	}
	
}
