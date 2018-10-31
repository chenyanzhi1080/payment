package com.xiaoerzuche.biz.payment.vo.payType;

public class PayTypeVo {
	/**
	 * 支付方式编码
	 */
	private Integer paytypeCode;
	/**
	 * 支付方式名称
	 */
	private String name;
	/**
	 * 支付方式描述说明
	 */
	private String direction;
	
	public Integer getPaytypeCode() {
		return paytypeCode;
	}
	public PayTypeVo setPaytypeCode(Integer paytypeCode) {
		this.paytypeCode = paytypeCode;
		return this;
	}
	public String getName() {
		return name;
	}
	public PayTypeVo setName(String name) {
		this.name = name;
		return this;
	}
	public String getDirection() {
		return direction;
	}
	public PayTypeVo setDirection(String direction) {
		this.direction = direction;
		return this;
	}
	
	@Override
	public String toString() {
		return "PayTypeVo [paytypeCode=" + paytypeCode + ", name=" + name + ", direction=" + direction + "]";
	}
	
}
