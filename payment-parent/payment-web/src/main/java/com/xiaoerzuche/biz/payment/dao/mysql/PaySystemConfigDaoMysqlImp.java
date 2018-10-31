package com.xiaoerzuche.biz.payment.dao.mysql;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.xiaoerzuche.biz.payment.dao.PaySystemConfigDao;
import com.xiaoerzuche.biz.payment.model.PaySystemConfig;
import com.xiaoerzuche.common.core.cache.RefreshCacheDefaultImpl;
import com.xiaoerzuche.common.core.data.jdbc.Jdbc;
import com.xiaoerzuche.common.core.data.jdbc.StatementParameter;
@Repository
public class PaySystemConfigDaoMysqlImp extends RefreshCacheDefaultImpl<PaySystemConfig> implements PaySystemConfigDao{
	
	
    private static final String TABLE = "pay_system_config";
    @Autowired
    private Jdbc jdbc;
	
	@Override
	public void refresh(List<PaySystemConfig> list) {
		throw new UnsupportedOperationException("不支持该操作");
	}

	@Override
	public void subscribe(String message, boolean isMySelf) {
		throw new UnsupportedOperationException("不支持该操作");
	}

	@Override
	public List<PaySystemConfig> list() {
		String sql = "select * from "+TABLE;
		return this.jdbc.queryForList(sql, PaySystemConfig.class);
	}

	@Override
	public PaySystemConfig get(String configKey) {
		String sql = "select * from "+TABLE+" where config_key = ? ";
		StatementParameter sp = new StatementParameter();
		sp.setString(configKey);
		return this.jdbc.query(sql, PaySystemConfig.class, sp);
	}

}
