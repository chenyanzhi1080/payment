package com.xiaoerzuche.biz.zmxy.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.xiaoerzuche.biz.zmxy.dao.AliMemberDao;
import com.xiaoerzuche.biz.zmxy.model.AliMember;
import com.xiaoerzuche.common.core.data.jdbc.Jdbc;
import com.xiaoerzuche.common.core.data.jdbc.StatementParameter;
import com.xiaoerzuche.common.core.data.jdbc.builder.InsertBuilder;
import com.xiaoerzuche.common.core.data.jdbc.builder.UpdateBuilder;
@Repository
public class AliMemberDaoImpl  implements AliMemberDao {

	private static final String	TABLE	= "ali_member";
	@Autowired
	private Jdbc				jdbc;

	public boolean insert(AliMember aliMember){
		InsertBuilder ib = new InsertBuilder(TABLE);
		ib.setString("ali_user_id",aliMember.getAliUserId());
		ib.setString("mobile_phone",aliMember.getMobilePhone());
		ib.setString("id_card",aliMember.getIdCard());
		ib.setString("name",aliMember.getName());
		ib.setString("avatar",aliMember.getAvatar());
		ib.setString("province",aliMember.getProvince());
		ib.setString("city",aliMember.getCity());
		if(StringUtils.isNotBlank(aliMember.getGender())){
			ib.setString("gender",aliMember.getGender());
		}
		if(StringUtils.isNotBlank(aliMember.getIsCertified())){
			ib.setString("is_certified",aliMember.getIsCertified());
		}
		ib.setString("access_token",aliMember.getAccessToken());
		ib.setDate("create_time",aliMember.getCreateTime());
//		ib.setDate("last_modify_time",aliMember.getLastModifyTime());
		return this.jdbc.updateForBoolean(ib);
	}

	public boolean update(AliMember aliMember){
		UpdateBuilder ub = new UpdateBuilder(TABLE);
		ub.setString("ali_user_id",aliMember.getAliUserId());
		ub.setString("mobile_phone",aliMember.getMobilePhone());
		ub.setString("id_card",aliMember.getIdCard());
		ub.setString("name",aliMember.getName());
		ub.setString("avatar",aliMember.getAvatar());
		ub.setString("province",aliMember.getProvince());
		ub.setString("city",aliMember.getCity());
		if(StringUtils.isNotBlank(aliMember.getGender())){
			ub.setString("gender",aliMember.getGender());
		}
		if(StringUtils.isNotBlank(aliMember.getIsCertified())){
			ub.setString("is_certified",aliMember.getIsCertified());
		}
		ub.setString("access_token",aliMember.getAccessToken());
		ub.setDate("last_modify_time",aliMember.getLastModifyTime());
		ub.where.setString("ali_user_id",aliMember.getAliUserId());
		return this.jdbc.updateForBoolean(ub);
	}

	public boolean delete(String aliUserId){
		String sql = "delete  from " + TABLE + " where ali_user_id = ?";
		StatementParameter sp = new StatementParameter();
		sp.setString(aliUserId);
		return this.jdbc.updateForBoolean(sql, sp);
	}

	public AliMember get(String aliUserId){
		String sql = "select * from " + TABLE + " where ali_user_id = ?";
		StatementParameter sp = new StatementParameter();
		sp.setString( aliUserId);
		return this.jdbc.query(sql,AliMember.class, sp);
	}

	public List<AliMember> list(int pageStart, int pageSize){
		StringBuilder sb = new StringBuilder();
		sb.append("select * from ");
		sb.append(TABLE);
		sb.append(" where 1=1 ");
		StatementParameter sp = new StatementParameter();
		return this.jdbc.queryForList(sb.toString(), AliMember.class, sp);
	}

	public int count(){
		StringBuilder sb = new StringBuilder();
		sb.append("select count(1) from ");
		sb.append(TABLE);
		sb.append(" where 1=1 ");
		StatementParameter sp = new StatementParameter();
		return this.jdbc.queryForInt(sb.toString(),sp);
	}
	
}