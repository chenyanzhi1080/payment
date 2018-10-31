package com.xiaoerzuche.biz.payment.dao;

import java.util.List;

import com.xiaoerzuche.biz.payment.dao.sqlBuilder.PayTypeConfigBuilder;
import com.xiaoerzuche.biz.payment.model.PayTypeConfig;
import com.xiaoerzuche.common.core.cache.RefreshInterface;
import com.xiaoerzuche.common.core.data.spi.IUpdate;
public interface PayTypeConfigDao  extends RefreshInterface<PayTypeConfig>,IUpdate<PayTypeConfig, Integer> {

	List< PayTypeConfig> list(PayTypeConfigBuilder builder);
	List<PayTypeConfig> page(PayTypeConfigBuilder builder);
	int count(PayTypeConfigBuilder builder);
}