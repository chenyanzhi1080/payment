package com.xiaoerzuche.biz.permission.dao.mysql;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.xiaoerzuche.biz.permission.dao.RoleDao;
import com.xiaoerzuche.biz.permission.enu.CommonStatus;
import com.xiaoerzuche.biz.permission.mode.Role;
import com.xiaoerzuche.common.core.cache.RefreshCacheDefaultImpl;
import com.xiaoerzuche.common.core.data.jdbc.Jdbc;
import com.xiaoerzuche.common.core.data.jdbc.StatementParameter;
import com.xiaoerzuche.common.core.data.jdbc.builder.InsertBuilder;
import com.xiaoerzuche.common.core.data.jdbc.builder.UpdateBuilder;

@Repository
public class RoleDaoMysqlImp extends RefreshCacheDefaultImpl<Role> implements RoleDao {
	private static final String TABLE = "role";
	@Autowired
	private Jdbc jdbc;

	@Override
	public boolean update(Role role) {
		UpdateBuilder ub = new UpdateBuilder(TABLE);
		ub.setString("name", role.getName());
		ub.setString("comment", role.getComment());
		ub.where.setInt("id", role.getId());
		return this.jdbc.updateForBoolean(ub);
	}

	@Override
	public boolean delete(Integer key) {
		String sql = "update "+TABLE+" set status = ? where id = ?";
		StatementParameter sp = new StatementParameter();
		sp.setInt(CommonStatus.ENABLE.getCode());
		sp.setInt(key);
		return this.jdbc.updateForBoolean(sql, sp);
	}

	@Override
	public Role get(Integer key) {
		String sql = "select * from "+TABLE+" where id = ?";
		StatementParameter sp = new StatementParameter();
		sp.setInt(key);
		return this.jdbc.query(sql, Role.class, sp);
	}

	@Override
	public boolean add(Role role) {
		InsertBuilder ib = new InsertBuilder(TABLE);
		ib.setString("name", role.getName());
		ib.setString("comment", role.getComment());
		ib.setInt("status", role.getStatus());
		return this.jdbc.insertForBoolean(ib);
	}

	@Override
	public List<Role> list(String name, boolean isAll, int pageStart, int pageSize) {
		String sql = "select * from "+ TABLE +" where 1=1 ";
		StatementParameter sp = new StatementParameter();
		if(StringUtils.isNotBlank(name)){
			sql += " and name=? ";
			sp.setString(name);
		}
		if(!isAll){
			sql += " and status = ? ";
			sp.setInt(CommonStatus.ENABLE.getCode());
		}
		sql += " order by id desc limit ?, ?";
		sp.setInt(pageStart);
		sp.setInt(pageSize);
		return this.jdbc.queryForList(sql, Role.class, sp);
	}

	@Override
	public void subscribe(String message, boolean isMySelf) {
		throw new UnsupportedOperationException("不支持该操作");
	}

	@Override
	public long count(String name, boolean isAll) {
		String sql = "select count(1) from "+ TABLE +" where 1=1 ";
		StatementParameter sp = new StatementParameter();
		if(StringUtils.isNotBlank(name)){
			sql += " and name=? ";
			sp.setString(name);
		}
		if(!isAll){
			sql += " and status = ? ";
			sp.setInt(CommonStatus.ENABLE.getCode());
		}
		return this.jdbc.queryForLong(sql, sp);
	}

	@Override
	public List<Role> listByUser(Integer id) {
		String sql = "select ro.* from " +
				" user_info usr inner join user_r_role urr on usr.id = urr.user_id " +
				" inner join role ro on urr.role_id = ro.id" +
				" where usr.status = ? and ro.status = ? and usr.id = ?" +
				" order by ro.id";
		StatementParameter sp = new StatementParameter();
		sp.setInt(CommonStatus.ENABLE.getCode());
		sp.setInt(CommonStatus.ENABLE.getCode());
		sp.setInt(id);
		return this.jdbc.queryForList(sql, Role.class, sp);
	}

}
