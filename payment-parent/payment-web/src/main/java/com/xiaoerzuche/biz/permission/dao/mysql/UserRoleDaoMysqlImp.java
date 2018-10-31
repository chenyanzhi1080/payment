package com.xiaoerzuche.biz.permission.dao.mysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.xiaoerzuche.biz.permission.dao.UserRoleDao;
import com.xiaoerzuche.biz.permission.mode.UserRole;
import com.xiaoerzuche.common.core.data.jdbc.Jdbc;
import com.xiaoerzuche.common.core.data.jdbc.StatementParameter;
import com.xiaoerzuche.common.core.data.jdbc.builder.InsertBuilder;

@Repository
public class UserRoleDaoMysqlImp implements UserRoleDao{
	
	private static final String TABLE = "user_r_role";
	@Autowired
	private Jdbc jdbc;

	@Override
	public boolean update(UserRole bean) {
		throw new UnsupportedOperationException("不支持该操作");
	}

	@Override
	public boolean delete(UserRole userRole) {
		String sql = "delete from "+ TABLE +" where user_id = ? and role_id = ? ";
		StatementParameter sp = new StatementParameter();
		sp.setInt(userRole.getUserId());
		sp.setInt(userRole.getRoleId());
		return this.jdbc.updateForBoolean(sql, sp);
	}

	@Override
	public UserRole get(UserRole userRole) {
		throw new UnsupportedOperationException("不支持该操作");
	}

	@Override
	public boolean add(UserRole userRole) {
		InsertBuilder ib = new InsertBuilder(TABLE);
		ib.setInt("user_id", userRole.getUserId());
		ib.setInt("role_id", userRole.getRoleId());
		return this.jdbc.insertForBoolean(ib);
	}

	@Override
	public boolean clear(Integer uid) {
		String sql = "delete from "+TABLE+" where user_id = ?";
		StatementParameter sp = new StatementParameter();
		sp.setInt(uid);
		return this.jdbc.updateForBoolean(sql, sp);
	}

}
