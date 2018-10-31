package com.xiaoerzuche.biz.payment.dao;

import java.util.List;

import com.xiaoerzuche.biz.payment.model.PayAuth;
import com.xiaoerzuche.common.core.cache.RefreshInterface;

/** 
* 接口授权DAO
* @author: cyz
* @version: payment
* @time: 2016-3-31
* 
*/
public interface PayAuthDao extends RefreshInterface<PayAuth>{
	public List<PayAuth> list();
	public PayAuth get(String appid);
}
