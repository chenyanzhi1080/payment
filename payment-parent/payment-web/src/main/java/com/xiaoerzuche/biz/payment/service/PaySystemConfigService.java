package com.xiaoerzuche.biz.payment.service;

import java.util.List;

import com.xiaoerzuche.biz.payment.model.PaySystemConfig;

/** 
* 支付配置service
* @author: cyz
* @version: payment
* @time: 2016-3-31
* 
*/
public interface PaySystemConfigService {
	public List<PaySystemConfig> list();
	public PaySystemConfig get(String configKey);
	public String getClientcallbackUrl(String appid);
}
