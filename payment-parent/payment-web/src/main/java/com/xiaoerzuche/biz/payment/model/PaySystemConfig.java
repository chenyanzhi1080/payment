package com.xiaoerzuche.biz.payment.model;

/** 
* 支付系统配置
* @author: cyz
* @version: payment
* @time: 2016-3-29
* 
*/
public class PaySystemConfig {
	String configKey;
	String configValue;
	String remarks;
	
	public PaySystemConfig() {
	}
	
	public PaySystemConfig(String configKey, String configValue, String remarks) {
		super();
		this.configKey = configKey;
		this.configValue = configValue;
		this.remarks = remarks;
	}

	public String getConfigKey() {
		return configKey;
	}
	public void setConfigKey(String configKey) {
		this.configKey = configKey;
	}
	public String getConfigValue() {
		return configValue;
	}
	public void setConfigValue(String configValue) {
		this.configValue = configValue;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}
