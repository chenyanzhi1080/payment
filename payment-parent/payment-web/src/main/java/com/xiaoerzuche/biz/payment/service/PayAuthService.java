package com.xiaoerzuche.biz.payment.service;

import com.xiaoerzuche.biz.payment.model.PayAuth;

/** 
* 接口授权service
* @author: cyz
* @version: payment
* @time: 2016-3-31
* 
*/
public interface PayAuthService {
	/**
	 * @Description: 根据appid获取授权信息
	 * @author: cyz
	 * @date: 2016-3-31
	 * @param appid
	 * @return
	 */
	public PayAuth get(String appid);
	/**
	 * @Description: 根据channel获取授权信息
	 * @param channel
	 * @return
	 */
	public PayAuth get(int channel);
	
}
