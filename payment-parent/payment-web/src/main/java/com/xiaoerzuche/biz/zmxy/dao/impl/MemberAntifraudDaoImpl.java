package com.xiaoerzuche.biz.zmxy.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.xiaoerzuche.biz.zmxy.dao.MemberAntifraudDao;
import com.xiaoerzuche.biz.zmxy.dao.sqlbuilder.MemberAntifraudBuilder;
import com.xiaoerzuche.biz.zmxy.model.MemberAntifraud;
import com.xiaoerzuche.common.core.data.jdbc.Jdbc;
import com.xiaoerzuche.common.core.data.jdbc.StatementParameter;
import com.xiaoerzuche.common.core.data.jdbc.builder.InsertBuilder;
import com.xiaoerzuche.common.core.data.jdbc.builder.UpdateBuilder;
@Repository
public class MemberAntifraudDaoImpl  implements MemberAntifraudDao {
	private static final String	TABLE	= "member_antifraud";
	@Autowired
	private Jdbc				jdbc;

	public boolean insert(MemberAntifraud memberAntifraud){
		InsertBuilder ib = new InsertBuilder(TABLE);
		ib.setString("id",memberAntifraud.getId());
		ib.setString("account",memberAntifraud.getAccount());
		ib.setString("name",memberAntifraud.getName());
		if(StringUtils.isNotBlank(memberAntifraud.getIdCard())){
			ib.setString("id_card",memberAntifraud.getIdCard());
		}
		if(StringUtils.isNotBlank(memberAntifraud.getRegisterCity())){
			ib.setString("register_city",memberAntifraud.getRegisterCity());
		}
		if(null != memberAntifraud.getRegisterTime()){
			ib.setDate("register_time",memberAntifraud.getRegisterTime());
		}
		if(null != memberAntifraud.getAntifraudScore()){
			ib.setInt("antifraud_score",memberAntifraud.getAntifraudScore());
		}
		if(StringUtils.isNotBlank(memberAntifraud.getAntifraudVerify())){
			ib.setString("antifraud_verify",memberAntifraud.getAntifraudVerify());
		}
		if(StringUtils.isNotBlank(memberAntifraud.getAntifraudRisk())){
			ib.setString("antifraud_risk",memberAntifraud.getAntifraudRisk());
		}
		if(null != memberAntifraud.getHit()){
			ib.setInt("hit",memberAntifraud.getHit());
		}
		if(StringUtils.isNotBlank(memberAntifraud.getErrorMessage())){
			ib.setString("error_message", memberAntifraud.getErrorMessage());
		}
		if(StringUtils.isNotBlank(memberAntifraud.getOperation())){
			ib.setString("operation",memberAntifraud.getOperation());
		}
		ib.setDate("create_time",memberAntifraud.getCreateTime());
		ib.setDate("last_modify_time",memberAntifraud.getLastModifyTime());
		
		return this.jdbc.updateForBoolean(ib);
	}

	public boolean update(MemberAntifraud memberAntifraud){
		UpdateBuilder ub = new UpdateBuilder(TABLE);
		if(StringUtils.isNotBlank(memberAntifraud.getAccount())){
			ub.setString("account",memberAntifraud.getAccount());
		}
		if(StringUtils.isNotBlank(memberAntifraud.getName())){
			ub.setString("name",memberAntifraud.getName());
		}
		if(StringUtils.isNotBlank(memberAntifraud.getIdCard())){
			ub.setString("id_card",memberAntifraud.getIdCard());
		}
		if(StringUtils.isNotBlank(memberAntifraud.getRegisterCity())){
			ub.setString("register_city",memberAntifraud.getRegisterCity());
		}
		if(null != memberAntifraud.getRegisterTime()){
			ub.setDate("register_time",memberAntifraud.getRegisterTime());
		}
		if(null != memberAntifraud.getAntifraudScore()){
			ub.setInt("antifraud_score",memberAntifraud.getAntifraudScore());
		}
		if(StringUtils.isNotBlank(memberAntifraud.getAntifraudVerify())){
			ub.setString("antifraud_verify",memberAntifraud.getAntifraudVerify());
		}
		if(StringUtils.isNotBlank(memberAntifraud.getAntifraudRisk())){
			ub.setString("antifraud_risk",memberAntifraud.getAntifraudRisk());
		}
		if(null != memberAntifraud.getHit()){
			ub.setInt("hit",memberAntifraud.getHit());
		}
		if(StringUtils.isNotBlank(memberAntifraud.getErrorMessage())){
			ub.setString("error_message", memberAntifraud.getErrorMessage());
		}
		if(StringUtils.isNotBlank(memberAntifraud.getOperation())){
			ub.setString("operation",memberAntifraud.getOperation());
		}
		if(StringUtils.isNotBlank(memberAntifraud.getRemark())){
			ub.setString("remark",memberAntifraud.getRemark());
		}
		ub.setDate("last_modify_time",memberAntifraud.getLastModifyTime());
		ub.where.setString("id",memberAntifraud.getId());
		return this.jdbc.updateForBoolean(ub);
	}

	public boolean delete(String id){
		String sql = "delete  from " + TABLE + " where id = ?";
		StatementParameter sp = new StatementParameter();
		sp.setString( id);
		return this.jdbc.updateForBoolean(sql, sp);
	}

	public MemberAntifraud get(String id){
		String sql = "select * from " + TABLE + " where id = ?";
		StatementParameter sp = new StatementParameter();
		sp.setString( id);
		return this.jdbc.query(sql,MemberAntifraud.class, sp);
	}

	public List<MemberAntifraud> list(MemberAntifraudBuilder builder){
		String sql = "select * from " + TABLE + " where 1=1 ";
		StatementParameter sp = new StatementParameter();
		sql = builder.build(sp, sql, true);
		return this.jdbc.queryForList(sql, MemberAntifraud.class, sp, builder.getStart(), builder.getLimit());
	}

	public int count(MemberAntifraudBuilder builder){
		String sql = "select count(1) from " + TABLE + " where 1=1 ";
		StatementParameter sp = new StatementParameter();
		sql = builder.build(sp, sql, false);
		return this.jdbc.queryForInt(sql,sp);
	}

}