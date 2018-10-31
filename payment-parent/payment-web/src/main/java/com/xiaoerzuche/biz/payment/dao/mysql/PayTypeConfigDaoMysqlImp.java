package com.xiaoerzuche.biz.payment.dao.mysql;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.xiaoerzuche.biz.payment.dao.PayTypeConfigDao;
import com.xiaoerzuche.biz.payment.dao.sqlBuilder.PayTypeConfigBuilder;
import com.xiaoerzuche.biz.payment.model.PayTypeConfig;
import com.xiaoerzuche.common.core.cache.RefreshCacheDefaultImpl;
import com.xiaoerzuche.common.core.data.jdbc.Jdbc;
import com.xiaoerzuche.common.core.data.jdbc.StatementParameter;
import com.xiaoerzuche.common.core.data.jdbc.builder.InsertBuilder;
import com.xiaoerzuche.common.core.data.jdbc.builder.UpdateBuilder;

@Repository("payTypeConfigDaoMysqlImp")
public class PayTypeConfigDaoMysqlImp extends RefreshCacheDefaultImpl<PayTypeConfig> implements PayTypeConfigDao {

	private static final String	TABLE	= "pay_type_config";
	@Autowired
	private Jdbc				jdbc;

	public boolean add(PayTypeConfig payTypeConfig){
		InsertBuilder ib = new InsertBuilder(TABLE);
//		ib.setInt("id",payTypeConfig.getId());
		ib.setInt("paytype_code",payTypeConfig.getPaytypeCode());
		ib.setString("name",payTypeConfig.getName());
		ib.setString("direction",payTypeConfig.getDirection());
		ib.setString("bz_appid",payTypeConfig.getBzAppid());
		ib.setString("terminal_appid",payTypeConfig.getTerminalAppid());
		ib.setInt("status",payTypeConfig.getStatus());
		ib.setInt("order_no",payTypeConfig.getOrderNo());
		return this.jdbc.updateForBoolean(ib);
	}

	public boolean update(PayTypeConfig payTypeConfig){
		UpdateBuilder ub = new UpdateBuilder(TABLE);
		ub.setInt("paytype_code",payTypeConfig.getPaytypeCode());
		ub.setString("name",payTypeConfig.getName());
		ub.setString("direction",payTypeConfig.getDirection());
		ub.setString("bz_appid",payTypeConfig.getBzAppid());
		ub.setString("terminal_appid",payTypeConfig.getTerminalAppid());
		ub.setInt("status",payTypeConfig.getStatus());
		ub.setInt("order_no",payTypeConfig.getOrderNo());
		ub.where.setInt("id",payTypeConfig.getId());
		return this.jdbc.updateForBoolean(ub);
	}

	public boolean delete(Integer id){
		String sql = "delete  from " + TABLE + " where id = ?";
		StatementParameter sp = new StatementParameter();
		sp.setInt( id);
		return this.jdbc.updateForBoolean(sql, sp);
	}

	public PayTypeConfig get(Integer id){
		String sql = "select * from " + TABLE + " where id = ?";
		StatementParameter sp = new StatementParameter();
		sp.setInt( id);
		return this.jdbc.query(sql,PayTypeConfig.class, sp);
	}

	public List<PayTypeConfig> list(PayTypeConfigBuilder builder){
		StatementParameter sp = new StatementParameter();
		String sql = builder.build(sp, "select * from " + TABLE , true);
		return this.jdbc.queryForList(sql, PayTypeConfig.class, sp, builder.getStart(), builder.getLimit());
	}

	public int count(PayTypeConfigBuilder builder){
		StatementParameter sp = new StatementParameter();
		String sql = builder.build(sp, "select * from " + TABLE , false);
		return this.jdbc.queryForInt(sql, sp);
	}

	@Override
	public List<PayTypeConfig> page(PayTypeConfigBuilder builder) {
		return this.list(builder);
	}

}