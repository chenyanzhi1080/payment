package com.xiaoerzuche.biz.payment.service;

import java.util.List;

import com.xiaoerzuche.biz.payment.dao.sqlBuilder.PayTypeConfigBuilder;
import com.xiaoerzuche.biz.payment.model.PayTypeConfig;
import com.xiaoerzuche.biz.payment.vo.PagerVo;
import com.xiaoerzuche.biz.payment.vo.payType.PayTypeVo;
import com.xiaoerzuche.common.core.data.jdbc.Page;
public interface PayTypeConfigService  {

	boolean insert(PayTypeConfig payTypeConfig);

	boolean update(PayTypeConfig payTypeConfig);

	boolean delete(Integer id);

	PayTypeConfig get(Integer id);

	PagerVo< PayTypeConfig> page(PayTypeConfigBuilder builder);
	List<PayTypeVo> list(PayTypeConfigBuilder builder);
}