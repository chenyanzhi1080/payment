package com.xiaoerzuche.biz.permission.dao.mysql;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.xiaoerzuche.biz.permission.dao.ResourceDao;
import com.xiaoerzuche.biz.permission.enu.CommonStatus;
import com.xiaoerzuche.biz.permission.mode.Resource;
import com.xiaoerzuche.common.core.cache.RefreshCacheDefaultImpl;
import com.xiaoerzuche.common.core.data.jdbc.Jdbc;
import com.xiaoerzuche.common.core.data.jdbc.StatementParameter;
import com.xiaoerzuche.common.core.data.jdbc.builder.InsertBuilder;
import com.xiaoerzuche.common.core.data.jdbc.builder.UpdateBuilder;

@Repository
public class ResourceDaoMysqlImp extends RefreshCacheDefaultImpl<Resource> implements ResourceDao {
	private static final String TABLE = "resource";
	@Autowired
	private Jdbc jdbc;

	@Override
	public boolean update(Resource resource) {
		UpdateBuilder ub = new UpdateBuilder(TABLE);
		ub.setString("name", resource.getName());
		ub.setString("url", resource.getUrl());
		ub.setInt("display", resource.getDisplay());
		ub.setInt("seq", resource.getSeq());
		ub.setString("code", resource.getCode());
		ub.where.setInt("id", resource.getId());
		return this.jdbc.updateForBoolean(ub);
	}

	@Override
	public boolean delete(Integer key) {
		String sql = "update "+TABLE+" set status = ? where id = ?";
		StatementParameter sp = new StatementParameter();
		sp.setInt(CommonStatus.DISABLE.getCode());
		sp.setInt(key);
		return this.jdbc.updateForBoolean(sql, sp);
	}

	@Override
	public Resource get(Integer key) {
		String sql = "select * from "+TABLE+" where id = ?";
		StatementParameter sp = new StatementParameter();
		sp.setInt(key);
		return this.jdbc.query(sql, Resource.class, sp);
	}

	@Override
	public boolean add(Resource resource) {
		InsertBuilder ib = new InsertBuilder(TABLE);
		ib.setString("name", resource.getName());
		ib.setString("url", resource.getUrl());
		ib.setInt("type", resource.getType());
		ib.setInt("display", resource.getDisplay());
		ib.setInt("seq", resource.getSeq());
		ib.setString("code", resource.getCode());
		ib.setInt("status", resource.getStatus());
		ib.setInt("pid", resource.getPid());
		return this.jdbc.updateForBoolean(ib);
	}

	@Override
	public List<Resource> list(String name, boolean isAll, int pageStart, int pageSize) {
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
		sql += " limit ?, ? ";
		sp.setInt(pageStart);
		sp.setInt(pageSize);
		return this.jdbc.queryForList(sql, Resource.class, sp);
	}

	@Override
	public void subscribe(String message, boolean isMySelf) {
		throw new UnsupportedOperationException("不支持该操作");
	}

	@Override
	public List<Resource> listAccessableByUserId(int userId) {
		String sql = "select distinct res.* from " +
				" user_info usr inner join user_r_role urr on usr.id = urr.user_id " +
				" inner join role ro on urr.role_id = ro.id" +
				" inner join role_r_resource rrr on rrr.role_id = ro.id" +
				" inner join resource res on res.id = rrr.resource_id" +
				" where usr.id = ? and usr.status = ? and ro.status = ? and res.status = ? and res.display = ?" +
				" order by type asc, seq desc";
		StatementParameter sp = new StatementParameter();
		sp.setInt(userId);
		sp.setInt(CommonStatus.ENABLE.getCode());
		sp.setInt(CommonStatus.ENABLE.getCode());
		sp.setInt(CommonStatus.ENABLE.getCode());
		sp.setInt(CommonStatus.ENABLE.getCode());
		return this.jdbc.queryForList(sql, Resource.class, sp);
	}

	@Override
	public List<Resource> listAccessableByRoleId(int roleId) {
		String sql = "select res.* from" +
				" role ro inner join role_r_resource rrr on rrr.role_id = ro.id" +
				" inner join resource res on res.id = rrr.resource_id" +
				" where ro.id = ? and res.status = ?" +
				" order by type asc, seq desc";
		StatementParameter sp = new StatementParameter();
		sp.setInt(roleId);
		sp.setInt(CommonStatus.ENABLE.getCode());
		return this.jdbc.queryForList(sql, Resource.class, sp);
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
	public boolean hasChild(int id) {
		throw new UnsupportedOperationException("不支持该操作");
	}

}
