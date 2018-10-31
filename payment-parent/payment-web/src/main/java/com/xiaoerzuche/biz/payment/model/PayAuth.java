package com.xiaoerzuche.biz.payment.model;

/** 
* 支付授权
* @author: cyz
* @version: payment
* @time: 2016-3-31
* 
*/
public class PayAuth {
	/**
	 * 接口调用凭据三位数字
	 */
	private String appid;
	/**
	 * 业务系统的授权key
	 */
	private String apiKey;
	/**
	 * 调用支付接口的业务平台
	 */
	private String serviceName;
	
	/**
	 * payment 的业务渠道
	 */
	private int channel; 
	
	/**
	 * wallet 的业务渠道
	 */
	private int walletChannel;
	
	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public int getChannel() {
		return channel;
	}

	public void setChannel(int channel) {
		this.channel = channel;
	}

	public int getWalletChannel() {
		return walletChannel;
	}

	public void setWalletChannel(int walletChannel) {
		this.walletChannel = walletChannel;
	}

	public PayAuth() {
	}

}
