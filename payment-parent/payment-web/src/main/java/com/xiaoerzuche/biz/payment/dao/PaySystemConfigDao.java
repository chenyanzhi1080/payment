package com.xiaoerzuche.biz.payment.dao;

import java.util.List;

import com.xiaoerzuche.biz.payment.model.PaySystemConfig;
import com.xiaoerzuche.common.core.cache.RefreshInterface;
import com.xiaoerzuche.common.core.pubsub.ISubscribe;

public interface  PaySystemConfigDao extends RefreshInterface<PaySystemConfig>,ISubscribe{
	public List<PaySystemConfig> list();
	public PaySystemConfig get(String configKey);
}
