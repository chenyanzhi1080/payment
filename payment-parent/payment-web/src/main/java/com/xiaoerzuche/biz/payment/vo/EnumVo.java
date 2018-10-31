package com.xiaoerzuche.biz.payment.vo;

public class EnumVo {
	private Integer code;
	private String name;
	public Integer getCode() {
		return code;
	}
	public EnumVo setCode(Integer code) {
		this.code = code;
		return this;
	}
	public String getName() {
		return name;
	}
	public EnumVo setName(String name) {
		this.name = name;
		return this;
	}
	@Override
	public String toString() {
		return "EnumVo [code=" + code + ", name=" + name + "]";
	}
	
}
