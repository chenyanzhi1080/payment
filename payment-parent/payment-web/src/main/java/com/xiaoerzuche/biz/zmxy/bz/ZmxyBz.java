package com.xiaoerzuche.biz.zmxy.bz;

/** 
* 芝麻信用业务码
* @author: cyz
* @version: payment-web
* @time: 2017年5月12日
* 
*/
public class ZmxyBz {
	private String code;
	private String name;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ZmxyBz(String code, String name) {
		super();
		this.code = code;
		this.name = name;
	}
	public ZmxyBz() {
		super();
	}
	@Override
	public String toString() {
		return "ZmxyBz [code=" + code + ", name=" + name + "]";
	}
	
}
