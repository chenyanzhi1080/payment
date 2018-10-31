package com.xiaoerzuche.biz.zmxy.vo;

public class ZmRiskVo {
	/**
	 * 行业关注风险码
	 */
	private String riskCode;
	/**
	 * 行业关注风险名称
	 */
	private String riskName;
	public String getRiskCode() {
		return riskCode;
	}
	public ZmRiskVo setRiskCode(String riskCode) {
		this.riskCode = riskCode;
		return this;
	}
	public String getRiskName() {
		return riskName;
	}
	public ZmRiskVo setRiskName(String riskName) {
		this.riskName = riskName;
		return this;
	}
	@Override
	public String toString() {
		return "ZmRiskVo [riskCode=" + riskCode + ", riskName=" + riskName + "]";
	}
	
}
