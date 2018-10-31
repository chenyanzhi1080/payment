package com.xiaoerzuche.biz.zmxy.dao.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.xiaoerzuche.biz.zmxy.dao.MemberCreditDao;
import com.xiaoerzuche.biz.zmxy.dao.sqlbuilder.MemberCreditBuilder;
import com.xiaoerzuche.biz.zmxy.enu.BLACKLISTEnu;
import com.xiaoerzuche.biz.zmxy.enu.CreditStatus;
import com.xiaoerzuche.biz.zmxy.model.MemberCredit;
import com.xiaoerzuche.common.core.data.jdbc.Jdbc;
import com.xiaoerzuche.common.core.data.jdbc.StatementParameter;
import com.xiaoerzuche.common.core.data.jdbc.builder.InsertBuilder;
import com.xiaoerzuche.common.core.data.jdbc.builder.UpdateBuilder;
@Repository
public class MemberCreditDaoImpl  implements MemberCreditDao {

	private static final String	TABLE	= "member_credit";
	@Autowired
	private Jdbc				jdbc;

	public boolean insert(MemberCredit memberCredit){
		InsertBuilder ib = new InsertBuilder(TABLE);
		if(StringUtils.isNotBlank(memberCredit.getId())){
			ib.setString("id",memberCredit.getId());
		}
		if(StringUtils.isNotBlank(memberCredit.getZmopenId())){
			ib.setString("zmopen_id",memberCredit.getZmopenId());
		}
		if(StringUtils.isNotBlank(memberCredit.getAccount())){
			ib.setString("account",memberCredit.getAccount());
		}
		if(StringUtils.isNotBlank(memberCredit.getName())){
			ib.setString("name",memberCredit.getName());
		}
		if(StringUtils.isNotBlank(memberCredit.getCardId())){
			ib.setString("card_id",memberCredit.getCardId());
		}
		if(StringUtils.isNotBlank(memberCredit.getZmDetails())){
			ib.setString("zm_details",memberCredit.getZmDetails());
		}
		if(StringUtils.isNotBlank(memberCredit.getDetails())){
			ib.setString("details",memberCredit.getDetails());
		}
		if(StringUtils.isNotBlank(memberCredit.getAuthResult())){
			ib.setString("auth_result",memberCredit.getAuthResult());
		}
		if(StringUtils.isNotBlank(memberCredit.getAuthRecord())){
			ib.setString("auth_record",memberCredit.getAuthRecord());
		}
		if(null != memberCredit.getAuthChangeTime()){
			ib.setDate("auth_change_time", memberCredit.getAuthChangeTime());
		}
		if(null != memberCredit.getAuthStatus()){
			ib.setInt("auth_status",memberCredit.getAuthStatus());
		}
		if(null != memberCredit.getIsBlacklist()){
			ib.setInt("is_blacklist",memberCredit.getIsBlacklist());
		}
		if(StringUtils.isNotBlank(memberCredit.getTransactionId())){
			ib.setString("transaction_id",memberCredit.getTransactionId());
		}
		if(null != memberCredit.getZmScore()){
			ib.setInt("zm_score",memberCredit.getZmScore());
		}
		if(null != memberCredit.getLevel()){
			ib.setInt("level",memberCredit.getLevel());
		}
		ib.setDate("create_time",memberCredit.getCreateTime());
		ib.setDate("last_modify_time",memberCredit.getLastModifyTime());
		ib.setString("operation",memberCredit.getOperation());
		return this.jdbc.updateForBoolean(ib);
	}

	public boolean update(MemberCredit memberCredit){
		UpdateBuilder ub = new UpdateBuilder(TABLE);
		if(StringUtils.isNotBlank(memberCredit.getId())){
			ub.setString("id",memberCredit.getId());
		}
		if(StringUtils.isNotBlank(memberCredit.getZmopenId())){
			ub.setString("zmopen_id",memberCredit.getZmopenId());
		}
		if(StringUtils.isNotBlank(memberCredit.getAccount())){
			ub.setString("account",memberCredit.getAccount());
		}
		if(StringUtils.isNotBlank(memberCredit.getAccount())){
			ub.setString("account",memberCredit.getAccount());
		}
		if(StringUtils.isNotBlank(memberCredit.getName())){
			ub.setString("name",memberCredit.getName());
		}
		if(StringUtils.isNotBlank(memberCredit.getCardId())){
			ub.setString("card_id",memberCredit.getCardId());
		}
		if(StringUtils.isNotBlank(memberCredit.getZmDetails())){
			ub.setString("zm_details",memberCredit.getZmDetails());
		}
		if(StringUtils.isNotBlank(memberCredit.getDetails())){
			ub.setString("details",memberCredit.getDetails());
		}
		if(StringUtils.isNotBlank(memberCredit.getAuthResult())){
			ub.setString("auth_result",memberCredit.getAuthResult());
		}
		if(null != memberCredit.getAuthStatus()){
			ub.setInt("auth_status",memberCredit.getAuthStatus());
		}
		if(null != memberCredit.getIsBlacklist()){
			ub.setInt("is_blacklist",memberCredit.getIsBlacklist());
		}
		if(StringUtils.isNotBlank(memberCredit.getTransactionId())){
			ub.setString("transaction_id",memberCredit.getTransactionId());
		}
		if(StringUtils.isNotBlank(memberCredit.getAuthRecord())){
			ub.setString("auth_record",memberCredit.getAuthRecord());
		}
		if(null != memberCredit.getAuthChangeTime()){
			ub.setDate("auth_change_time", memberCredit.getAuthChangeTime());
		}
		if(null != memberCredit.getZmScore()){
			ub.setInt("zm_score",memberCredit.getZmScore());
		}
		ub.setDate("last_modify_time",memberCredit.getLastModifyTime());
		ub.setString("operation",memberCredit.getOperation());
		ub.where.setString("id",memberCredit.getId());
		
		return this.jdbc.updateForBoolean(ub);
	}

	public boolean delete(String id){
		String sql = "delete  from " + TABLE + " where id = ?";
		StatementParameter sp = new StatementParameter();
		sp.setString( id);
		return this.jdbc.updateForBoolean(sql, sp);
	}

	public MemberCredit get(String id){
		String sql = "select * from " + TABLE + " where id = ?";
		StatementParameter sp = new StatementParameter();
		sp.setString(id);
		return this.jdbc.query(sql,MemberCredit.class, sp);
	}

	public List<MemberCredit> list(MemberCreditBuilder builder){
		String sql = "select * from " + TABLE + " where 1=1 ";
		StatementParameter sp = new StatementParameter();
		sql = builder.build(sp, sql, true);
		return this.jdbc.queryForList(sql, MemberCredit.class, sp, builder.getStart(), builder.getLimit());
	}

	public int count(MemberCreditBuilder builder){
		String sql = "select count(1) from " + TABLE + " where 1=1 ";
		StatementParameter sp = new StatementParameter();
		sql = builder.build(sp, sql, false);
		return this.jdbc.queryForInt(sql,sp);
	}

	@Override
	public MemberCredit getByAccount(String account) {
		String sql = "select * from " + TABLE + " where account = ?";
		StatementParameter sp = new StatementParameter();
		sp.setString(account);
		return this.jdbc.query(sql,MemberCredit.class, sp);
	}

	@Override
	public boolean setBlacklist(String id, String blackReasonCode, String remark, String operator,Date lastModifyTime) {
		UpdateBuilder ub = new UpdateBuilder(TABLE);
		ub.setInt("is_blacklist", BLACKLISTEnu.BLACK_LIST.getCode());
		ub.setString("reason",blackReasonCode);
		if(StringUtils.isNotBlank(remark)){
			ub.setString("remark",remark);
		}
		ub.setDate("last_modify_time",lastModifyTime);
		ub.setString("operation",operator);
		ub.where.setString("id",id);
		return this.jdbc.updateForBoolean(ub);
	}

	@Override
	public boolean sendZhimaCreditWatch(String id, String details) {
		UpdateBuilder ub = new UpdateBuilder(TABLE);
//		ub.setInt("credit_status",CreditStatus.HAD_BAD_RECORD.getCode());
		ub.setString("details",details);
		ub.where.setString("id",id);
		return this.jdbc.updateForBoolean(ub);
	}

	@Override
	public boolean unbind(String account, String id,String operation) {
		UpdateBuilder ub = new UpdateBuilder(TABLE);
		ub.setInt("auth_status",CreditStatus.UNBIND.getCode());
		ub.setDate("last_modify_time",new Date());
		ub.setString("operation",operation);
//		ub.where.setString("id",memberCredit.getId());
		ub.where.setString("account",account);
		return this.jdbc.updateForBoolean(ub);
	}

}