package com.xiaoerzuche.biz.zmxy.dao.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.xiaoerzuche.biz.zmxy.dao.ZmxyFeedbackBillDao;
import com.xiaoerzuche.biz.zmxy.dao.sqlbuilder.ZmxyFeedbackBillBuilder;
import com.xiaoerzuche.biz.zmxy.enu.ZmBillStatus;
import com.xiaoerzuche.biz.zmxy.model.ZmxyFeedbackBill;
import com.xiaoerzuche.common.core.data.jdbc.Jdbc;
import com.xiaoerzuche.common.core.data.jdbc.StatementParameter;
import com.xiaoerzuche.common.core.data.jdbc.builder.InsertBuilder;
import com.xiaoerzuche.common.core.data.jdbc.builder.UpdateBuilder;
@Repository
public class ZmxyFeedbackBillDaoImpl  implements ZmxyFeedbackBillDao {
	private static final Logger logger = LoggerFactory.getLogger(ZmxyFeedbackBillDaoImpl.class);
	private static final String	TABLE	= "zmxy_feedback_bill";
	@Autowired
	private Jdbc				jdbc;

	public boolean insert(ZmxyFeedbackBill zmxyFeedbackBill){
		InsertBuilder ib = new InsertBuilder(TABLE);
		ib.setString("bill_no",zmxyFeedbackBill.getBillNo());
		ib.setDate("biz_date",zmxyFeedbackBill.getBizDate());
		if(StringUtils.isNotBlank(zmxyFeedbackBill.getUserCredentialsType())){
			ib.setString("user_credentials_type",zmxyFeedbackBill.getUserCredentialsType());
		}
		if(StringUtils.isNotBlank(zmxyFeedbackBill.getUserCredentialsNo())){
			ib.setString("user_credentials_no",zmxyFeedbackBill.getUserCredentialsNo());
		}
		if(StringUtils.isNotBlank(zmxyFeedbackBill.getUserName())){
			ib.setString("user_name",zmxyFeedbackBill.getUserName());
		}
		ib.setString("order_no",zmxyFeedbackBill.getOrderNo());
		ib.setString("sub_order_no",zmxyFeedbackBill.getSubOrderNo());
		ib.setInt("order_status",zmxyFeedbackBill.getOrderStatus());
		ib.setDate("order_start_date",zmxyFeedbackBill.getOrderStartDate());
		ib.setString("object_name",zmxyFeedbackBill.getObjectName());
		ib.setString("object_id",zmxyFeedbackBill.getObjectId());
		ib.setString("rent_desc",zmxyFeedbackBill.getRentDesc());
		ib.setInt("deposit_amt",zmxyFeedbackBill.getDepositAmt());
		ib.setInt("bill_status",zmxyFeedbackBill.getBillStatus());
		ib.setInt("bill_type",zmxyFeedbackBill.getBillType());
		ib.setInt("bill_amt",zmxyFeedbackBill.getBillAmt());
		if(null != zmxyFeedbackBill.getBillLastDate()){
			ib.setDate("bill_last_date",zmxyFeedbackBill.getBillLastDate());
		}
		if(null != zmxyFeedbackBill.getBillPayoffDate()){
			ib.setDate("bill_payoff_date",zmxyFeedbackBill.getBillPayoffDate());
		}
		ib.setDate("create_time",zmxyFeedbackBill.getCreateTime());
		if(null != zmxyFeedbackBill.getLastModifyTime()){
			ib.setDate("last_modify_time",zmxyFeedbackBill.getLastModifyTime());
		}
		if(null != zmxyFeedbackBill.getUploadTime()){
			ib.setDate("upload_time",zmxyFeedbackBill.getUploadTime());
		}
		if(null != zmxyFeedbackBill.getIsUpload()){
			ib.setInt("is_upload",zmxyFeedbackBill.getIsUpload());
		}
		return this.jdbc.updateForBoolean(ib);
	}

	public boolean update(ZmxyFeedbackBill zmxyFeedbackBill){
		UpdateBuilder ub = new UpdateBuilder(TABLE);
		ub.setString("bill_no",zmxyFeedbackBill.getBillNo());
		ub.setDate("biz_date",zmxyFeedbackBill.getBizDate());
		if(StringUtils.isNotBlank(zmxyFeedbackBill.getUserCredentialsType())){
			ub.setString("user_credentials_type",zmxyFeedbackBill.getUserCredentialsType());
		}
		if(StringUtils.isNotBlank(zmxyFeedbackBill.getUserCredentialsNo())){
			ub.setString("user_credentials_no",zmxyFeedbackBill.getUserCredentialsNo());
		}
		if(StringUtils.isNotBlank(zmxyFeedbackBill.getUserName())){
			ub.setString("user_name",zmxyFeedbackBill.getUserName());
		}
		if(StringUtils.isNotBlank(zmxyFeedbackBill.getOrderNo())){
			ub.setString("order_no",zmxyFeedbackBill.getOrderNo());
		}
		if(StringUtils.isNotBlank(zmxyFeedbackBill.getSubOrderNo())){
			ub.setString("sub_order_no",zmxyFeedbackBill.getSubOrderNo());
		}
		if(null != zmxyFeedbackBill.getOrderStatus()){
			ub.setInt("order_status",zmxyFeedbackBill.getOrderStatus());
		}
		ub.setDate("order_start_date",zmxyFeedbackBill.getOrderStartDate());
		if(StringUtils.isNotBlank(zmxyFeedbackBill.getObjectName())){
			ub.setString("object_name",zmxyFeedbackBill.getObjectName());	
		}
		if(StringUtils.isNotBlank(zmxyFeedbackBill.getObjectId())){
			ub.setString("object_id",zmxyFeedbackBill.getObjectId());
		}
		if(StringUtils.isNotBlank(zmxyFeedbackBill.getRentDesc())){
			ub.setString("rent_desc",zmxyFeedbackBill.getRentDesc());
		}
		if(null != zmxyFeedbackBill.getDepositAmt()){
			ub.setInt("deposit_amt",zmxyFeedbackBill.getDepositAmt());
		}
		
		ub.setInt("bill_status",zmxyFeedbackBill.getBillStatus());
		ub.setInt("bill_type",zmxyFeedbackBill.getBillType());
		ub.setInt("bill_amt",zmxyFeedbackBill.getBillAmt());
		if(null != zmxyFeedbackBill.getBillLastDate()){
			ub.setDate("bill_last_date",zmxyFeedbackBill.getBillLastDate());
		}
		if(null != zmxyFeedbackBill.getBillPayoffDate()){
			ub.setDate("bill_payoff_date",zmxyFeedbackBill.getBillPayoffDate());
		}
		ub.setDate("create_time",zmxyFeedbackBill.getCreateTime());
		ub.setDate("last_modify_time",zmxyFeedbackBill.getLastModifyTime());
		if(null != zmxyFeedbackBill.getUploadTime()){
			ub.setDate("upload_time",zmxyFeedbackBill.getUploadTime());
		}
		if(null != zmxyFeedbackBill.getIsUpload()){
			ub.setInt("is_upload",zmxyFeedbackBill.getIsUpload());
		}
		ub.where.setString("bill_no",zmxyFeedbackBill.getBillNo());
		return this.jdbc.updateForBoolean(ub);
	}

	public boolean delete(String billNo){
		String sql = "delete  from " + TABLE + " where bill_no = ?";
		StatementParameter sp = new StatementParameter();
		sp.setString( billNo);
		return this.jdbc.updateForBoolean(sql, sp);
	}

	public ZmxyFeedbackBill get(String billNo){
		String sql = "select * from " + TABLE + " where bill_no = ?";
		StatementParameter sp = new StatementParameter();
		sp.setString( billNo);
		return this.jdbc.query(sql,ZmxyFeedbackBill.class, sp);
	}

	public List<ZmxyFeedbackBill> list(int pageStart, int pageSize){
		StringBuilder sb = new StringBuilder();
		sb.append("select * from ");
		sb.append(TABLE);
		sb.append(" where 1=1 ");
		StatementParameter sp = new StatementParameter();
		return this.jdbc.queryForList(sb.toString(), ZmxyFeedbackBill.class, sp);
	}

	public int count(){
		StringBuilder sb = new StringBuilder();
		sb.append("select count(1) from ");
		sb.append(TABLE);
		sb.append(" where 1=1 ");
		StatementParameter sp = new StatementParameter();
		return this.jdbc.queryForInt(sb.toString(),sp);
	}

	@Override
	public List<ZmxyFeedbackBill> queryBills(String orderNo) {
		String sql = "select * from "+TABLE+" where order_no=? ";
		StatementParameter sp = new StatementParameter();
		sp.setString(orderNo);
		return this.jdbc.queryForList(sql, ZmxyFeedbackBill.class,sp);
	}

	@Override
	public List<ZmxyFeedbackBill> unSettledBills(String orderNo, Date billLastDate) {
		StringBuilder sb = new StringBuilder();
		StatementParameter sp = new StatementParameter();
		sb.append("select * from ").append(TABLE).append(" where 1=1 ");
		if(StringUtils.isNotBlank(orderNo)){
			sb.append(" and order_no=? ");
			sp.setString(orderNo);
		}
		if(null != billLastDate){
			sb.append(" and bill_last_date >= ? ");
			String billLastDateStr = DateFormatUtils.format(billLastDate, "yyyy-MM-dd");
			sp.setString(billLastDateStr);
		}
		sb.append(" and order_status =").append(ZmBillStatus.NORMAL.getCode()).append(" ORDER BY last_modify_time desc ");
		logger.debug(this.jdbc.getSQL(sb.toString(), sp));
		return this.jdbc.queryForList(sb.toString(),ZmxyFeedbackBill.class, sp);
	}

	@Override
	public List<ZmxyFeedbackBill> billsToDefault() {
		StringBuilder sb = new StringBuilder();
		StatementParameter sp = new StatementParameter();
		sb.append("select * from ").append(TABLE).append(" where 1=1 ");
		sb.append(" and bill_last_date < ? ");
		String billLastDateStr = DateFormatUtils.format(new Date(), "yyyy-MM-dd");
		sp.setString(billLastDateStr);
		sb.append(" and bill_status = 0 ORDER BY last_modify_time desc ");
		logger.debug(this.jdbc.getSQL(sb.toString(), sp));
		return this.jdbc.queryForList(sb.toString(),ZmxyFeedbackBill.class, sp);
	}

	@Override
	public List<ZmxyFeedbackBill> defaultBillsToSettled() {
		Date maxDate = new Date();
		Date minDate = DateUtils.addDays(maxDate, -60);
		StringBuilder sb = new StringBuilder();
		StatementParameter sp = new StatementParameter();
		sb.append("select * from ").append(TABLE).append(" where 1=1 ");
		sb.append(" and bill_last_date > ? and bill_last_date < ? ");
		String maxbillLastDateStr = DateFormatUtils.format(maxDate, "yyyy-MM-dd");
		String minbillLastDateStr = DateFormatUtils.format(minDate, "yyyy-MM-dd");
		sp.setString(maxbillLastDateStr);
		sp.setString(minbillLastDateStr);
		sb.append(" and order_status =1 ORDER BY last_modify_time desc ");
		logger.debug(this.jdbc.getSQL(sb.toString(), sp));
		return this.jdbc.queryForList(sb.toString(),ZmxyFeedbackBill.class, sp);
	}
	public static void main(String[] args){
		Date maxDate = new Date();
		Date minDate = DateUtils.addDays(maxDate, -60);
		String maxbillLastDateStr = DateFormatUtils.format(maxDate, "yyyy-MM-dd");
		String minbillLastDateStr = DateFormatUtils.format(minDate, "yyyy-MM-dd");
		System.out.println(maxbillLastDateStr);
		System.out.println(minbillLastDateStr);
	}
	@Override
	public List<ZmxyFeedbackBill> list(ZmxyFeedbackBillBuilder builder) {
		String sql = "select * from " + TABLE + " where 1=1 ";
		StatementParameter sp = new StatementParameter();
		sql = builder.build(sp, sql, true);
		return this.jdbc.queryForList(sql, ZmxyFeedbackBill.class, sp, builder.getStart(), builder.getLimit());
	}

	@Override
	public List<ZmxyFeedbackBill> needUpload() {
		StringBuilder sb = new StringBuilder();
		StatementParameter sp = new StatementParameter();
		sb.append("select * from ").append(TABLE)
		.append(" where  order_status=0 and bill_status=0 and is_upload = 0  and upload_time <=now() ");
		logger.debug(this.jdbc.getSQL(sb.toString(), sp));
		return this.jdbc.queryForList(sb.toString(),ZmxyFeedbackBill.class, sp);
	}

}