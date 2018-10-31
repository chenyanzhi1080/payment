package com.xiaoerzuche.biz.payment.dao.mysql;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.xiaoerzuche.biz.payment.dao.PayOrderDao;
import com.xiaoerzuche.biz.payment.dao.builder.PayorderBuilder;
import com.xiaoerzuche.biz.payment.enu.TranStatus;
import com.xiaoerzuche.biz.payment.model.PayOrder;
import com.xiaoerzuche.biz.payment.util.TableUtil;
import com.xiaoerzuche.biz.payment.vo.QueryVo;
import com.xiaoerzuche.common.core.data.jdbc.Jdbc;
import com.xiaoerzuche.common.core.data.jdbc.StatementParameter;
import com.xiaoerzuche.common.core.data.jdbc.builder.InsertBuilder;
import com.xiaoerzuche.common.core.data.jdbc.builder.UpdateBuilder;

@Repository
public class PayOrderDaoMysqlImp implements PayOrderDao {

	private static final Logger logger = LoggerFactory.getLogger(PayOrderDaoMysqlImp.class);

	private static final String TABLE = "pay_order_";

	@Autowired
	private Jdbc jdbc;

	@Override
	public boolean update(PayOrder bean) {
		UpdateBuilder ub = new UpdateBuilder(TableUtil.getYearByOrderId(bean.getQueryId(), TABLE));
		if (StringUtils.isNotBlank(bean.getOrderId())) {
			ub.setString("order_id", bean.getOrderId());
		}
		if (StringUtils.isNotBlank(bean.getGoodsNo())) {
			ub.setString("goods_no", bean.getGoodsNo());
		}
		if (StringUtils.isNotBlank(bean.getTranNo())) {
			ub.setString("tran_no", bean.getTranNo());
		}
		if (StringUtils.isNotBlank(bean.getOrigTranNo())) {
			ub.setString("orig_tran_no", bean.getOrigTranNo());
		}
		if (StringUtils.isNotBlank(bean.getAppid())) {
			ub.setString("appid", bean.getAppid());
		}
		if (null != bean.getPayType()) {
			ub.setInt("pay_type", bean.getPayType());
		}
		if (StringUtils.isNotBlank(bean.getPayName())) {
			ub.setString("pay_name", bean.getPayName());
		}
		if (null != bean.getTranAmount()) {
			ub.setInt("tran_amount", bean.getTranAmount());
		}
		if (null != bean.getOrigTranAmount()) {
			ub.setInt("orig_tran_amount", bean.getOrigTranAmount());
		}
		if (null != bean.getTranType()) {
			ub.setInt("tran_type", bean.getTranType());
		}
		if (null != bean.getTranTime()) {
			ub.setDate("tran_time", bean.getTranTime());
		}
		if(StringUtils.isNotBlank(bean.getAccount())){
			ub.setString("account", bean.getAccount());
		}
		if(StringUtils.isNotBlank(bean.getExpenseType())){
			ub.setString("expense_type", bean.getExpenseType());
		}
		if(StringUtils.isNotBlank(bean.getChannel())){
			ub.setString("channel", bean.getChannel());
		}
		ub.setInt("tran_status", bean.getTranStatus());
		ub.setInt("notify_flag", bean.getNotifyFlag());
		if (null != bean.getLastModifyTime()) {
			ub.setDate("last_modify_time", bean.getLastModifyTime());
		}
		ub.where.setString("query_id", bean.getQueryId());
		return this.jdbc.updateForBoolean(ub);
	}

	@Override
	public boolean delete(String key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public PayOrder get(String key) {
		logger.info("[PayOrderDaoMysqlImp.get]key={}", key);
		String sql = "select * from " + TableUtil.getYearByOrderId(key, TABLE) + " where query_id = ?";
		StatementParameter sp = new StatementParameter();
		sp.setString(key);
		return this.jdbc.query(sql, PayOrder.class, sp);
	}

	@Override
	public boolean add(PayOrder bean) {
		InsertBuilder ib = new InsertBuilder(TableUtil.getYearByOrderId(bean.getQueryId(), TABLE));
		ib.setString("query_id", bean.getQueryId());
		ib.setString("order_id", bean.getOrderId());
		if (StringUtils.isNotBlank(bean.getGoodsNo())) {
			ib.setString("goods_no", bean.getGoodsNo());
		}
		if (StringUtils.isNotBlank(bean.getTranNo())) {
			ib.setString("tran_no", bean.getTranNo());
		}
		if (StringUtils.isNotBlank(bean.getOrigTranNo())) {
			ib.setString("orig_tran_no", bean.getOrigTranNo());
		}
		if(StringUtils.isNotBlank(bean.getAccount())){
			ib.setString("account", bean.getAccount());
		}
		ib.setString("appid", bean.getAppid());
		ib.setInt("pay_type", bean.getPayType());
		ib.setString("pay_name", bean.getPayName());
		ib.setInt("tran_amount", bean.getTranAmount());
		if (null != bean.getOrigTranAmount()) {
			ib.setInt("orig_tran_amount", bean.getOrigTranAmount());
		}
		ib.setInt("tran_type", bean.getTranType());
		ib.setDate("tran_time", bean.getTranTime());
		ib.setInt("tran_status", bean.getTranStatus());
		if(StringUtils.isNotBlank(bean.getExpenseType())){
			ib.setString("expense_type", bean.getExpenseType());
		}
		if(StringUtils.isNotBlank(bean.getChannel())){
			ib.setString("channel", bean.getChannel());
		}
		ib.setInt("notify_flag", bean.getNotifyFlag());
		if (null != bean.getLastModifyTime()) {
			ib.setDate("last_modify_time", bean.getLastModifyTime());
		}

		return this.jdbc.updateForBoolean(ib);
	}

	@Override
	public PayOrder getByOrder(QueryVo queryVo) {
		logger.info("[支付网关支付订单查询入参][{}]", new Object[] { queryVo.toString() });
		StringBuffer sql = new StringBuffer();
		StatementParameter sp = new StatementParameter();
		sql.append("select * from ");
		if (StringUtils.isNotBlank(queryVo.getQueryId())) {
			sql.append(TableUtil.getYearByOrderId(queryVo.getQueryId(), TABLE)).append(" where query_id = ?");
			sp.setString(queryVo.getQueryId());
		} else {
			sql.append(TABLE).append(DateFormatUtils.format(new Date(), "yyyy")).append(" where 1=1 ");
		}

		if (null != queryVo.getTranType()) {
			sql.append(" and tran_type = ? ");
			sp.setInt(queryVo.getTranType());
		}

		if (null != queryVo.getPaytype()) {
			sql.append(" and pay_type = ? ");
			sp.setInt(queryVo.getPaytype());
		}

		if (StringUtils.isNotBlank(queryVo.getOrderId())) {
			sql.append(" and order_id = ? ");
			sp.setString(queryVo.getOrderId());
		}

		if (StringUtils.isNotBlank(queryVo.getAppid())) {
			sql.append(" and appid = ? ");
			sp.setString(queryVo.getAppid());
		}
		logger.info("[交易查询sql]" + this.jdbc.getSQL(sql.toString(), sp));
		return this.jdbc.query(sql.toString(), PayOrder.class, sp);
	}

	@Override
	public PayOrder getByOrigTranNo(String origTranNo, int payType) {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from ");
		sql.append(TableUtil.getYearByTranNo(origTranNo, payType, TABLE));
		sql.append(" where orig_tran_no = ? ");
		sql.append(" UNION select * from ").append(TableUtil.getTableNameByYear(TABLE))
		.append(" where orig_tran_no = ? ");
		
		StatementParameter sp = new StatementParameter();
		sp.setString(origTranNo);
		sp.setString(origTranNo);
		logger.info("[getByOrigTranNo][origTranNo={},payType={}]{}",
				new Object[] { origTranNo, payType, this.jdbc.getSQL(sql.toString(), sp) });
		return this.jdbc.query(sql.toString(), PayOrder.class, sp);
	}

	@Override
	public List<PayOrder> getTranRecorders(PayorderBuilder builder) {
		String sql = builder.build(TABLE, "select * from ", true);
		return this.jdbc.queryForList(sql, PayOrder.class, builder.getStart(), builder.getLimit());
	}

	@Override
	public long getTranRecordersCount(PayorderBuilder builder) {
		String sql = builder.build(TABLE, "select count(1) from ", false);
		return this.jdbc.queryForLong(sql);
	}

	@Override
	public boolean tradeFinished(String queryId) {
		UpdateBuilder ub = new UpdateBuilder(TableUtil.getYearByOrderId(queryId, TABLE));
		ub.setInt("tran_status", TranStatus.TRADE_FINISHED.getCode());
		ub.setDate("last_modify_time", new Date());
		ub.where.setString("query_id", queryId);
		return this.jdbc.updateForBoolean(ub);
	}
}
