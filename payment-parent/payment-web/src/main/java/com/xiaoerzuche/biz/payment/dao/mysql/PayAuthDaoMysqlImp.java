package com.xiaoerzuche.biz.payment.dao.mysql;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.xiaoerzuche.biz.payment.dao.PayAuthDao;
import com.xiaoerzuche.biz.payment.model.PayAuth;
import com.xiaoerzuche.common.core.cache.RefreshCacheDefaultImpl;
import com.xiaoerzuche.common.core.data.jdbc.Jdbc;
import com.xiaoerzuche.common.core.data.jdbc.StatementParameter;

@Repository
public class PayAuthDaoMysqlImp extends RefreshCacheDefaultImpl<PayAuth> implements PayAuthDao{
	private static final String TABLE = "pay_auth";
    @Autowired
    private Jdbc jdbc;
    
	@Override
	public void refresh(List<PayAuth> list) {
		throw new UnsupportedOperationException("不支持该操作");
	}

	@Override
	public List<PayAuth> list() {
		String sql = "select * from "+TABLE;
		return this.jdbc.queryForList(sql, PayAuth.class);
	}

	@Override
	public PayAuth get(String appid) {
		String sql = "select * from "+TABLE+" where appid = ? ";
		StatementParameter sp = new StatementParameter();
		sp.setString(appid);
		return this.jdbc.query(sql, PayAuth.class, sp);
	}

}
