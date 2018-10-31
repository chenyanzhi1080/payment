package com.xiaoerzuche.biz.permission.dao.mysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.xiaoerzuche.biz.permission.dao.RoleResourceDao;
import com.xiaoerzuche.biz.permission.mode.RoleResource;
import com.xiaoerzuche.common.core.data.jdbc.Jdbc;
import com.xiaoerzuche.common.core.data.jdbc.StatementParameter;
import com.xiaoerzuche.common.core.data.jdbc.builder.InsertBuilder;

@Repository
public class RoleResourceDaoMysqlImp implements RoleResourceDao{
	
	private static final String TABLE = "role_r_resource";
	@Autowired
	private Jdbc jdbc;

	@Override
	public boolean update(RoleResource bean) {
		throw new UnsupportedOperationException("不支持该操作");
	}

	@Override
	public boolean delete(RoleResource roleResource) {
		String sql = "delete from "+ TABLE +" where role_id = ? and resource_id = ? ";
		StatementParameter sp = new StatementParameter();
		sp.setInt(roleResource.getRoleId());
		sp.setInt(roleResource.getResourceId());
		return this.jdbc.updateForBoolean(sql, sp);
	}

	@Override
	public RoleResource get(RoleResource roleResource) {
		throw new UnsupportedOperationException("不支持该操作");
	}

	@Override
	public boolean add(RoleResource roleResource) {
		InsertBuilder ib = new InsertBuilder(TABLE);
		ib.setInt("role_id", roleResource.getRoleId());
		ib.setInt("resource_id", roleResource.getResourceId());
		return this.jdbc.insertForBoolean(ib);
	}

	@Override
	public boolean clear(Integer roleId) {
		String sql = "delete from "+TABLE+" where role_id=?";
		StatementParameter sp = new StatementParameter();
		sp.setInt(roleId);
		return this.jdbc.updateForBoolean(sql, sp);
	}

}
