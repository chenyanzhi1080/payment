package com.xiaoerzuche.biz.permission.dao.mysql;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.xiaoerzuche.biz.permission.dao.UserDao;
import com.xiaoerzuche.biz.permission.enu.CommonStatus;
import com.xiaoerzuche.biz.permission.mode.User;
import com.xiaoerzuche.common.core.cache.RefreshCacheDefaultImpl;
import com.xiaoerzuche.common.core.data.jdbc.Jdbc;
import com.xiaoerzuche.common.core.data.jdbc.StatementParameter;
import com.xiaoerzuche.common.core.data.jdbc.builder.InsertBuilder;
import com.xiaoerzuche.common.core.data.jdbc.builder.UpdateBuilder;

/**
 * 用户DAO实现类
 * @author Nick C
 *
 */
@Repository
public class UserDaoMysqlImp extends RefreshCacheDefaultImpl<User> implements UserDao {
	private static final String TABLE = "user_info";
	@Autowired
	private Jdbc jdbc;

	@Override
	public boolean update(User user) {
		UpdateBuilder ub = new UpdateBuilder(TABLE);
		ub.setString("name", user.getName());
		ub.setString("nick_name", user.getNickName());
		ub.setString("phone", user.getPhone());
		ub.setInt("sex", user.getSex());
		ub.setString("address", user.getAddress());
		ub.setString("comment", user.getComment());
		ub.where.setInt("id", user.getId());
		return this.jdbc.updateForBoolean(ub);
	}

	@Override
	public boolean updatePwd(User user,String sourcePwd ,String newPwd) {
		UpdateBuilder ub = new UpdateBuilder(TABLE);
		ub.setString("pwd", newPwd);
		ub.where.setInt("id", user.getId());
//		ub.where.setString("pwd", sourcePwd);
		return this.jdbc.updateForBoolean(ub);
	}
	
	@Override
	public User get(Integer key) {
		String sql = "select * from "+TABLE +" where id = ?";
		StatementParameter sp = new StatementParameter();
		sp.setInt(key);
		return this.jdbc.query(sql, User.class);
	}

	@Override
	public boolean add(User user) {
		InsertBuilder ib = new InsertBuilder(TABLE);
		ib.setString("name", user.getName());
		ib.setString("account", user.getAccount());
		ib.setString("pwd", user.getPwd());
		ib.setString("nick_name", user.getNickName());
		ib.setString("phone", user.getPhone());
		ib.setInt("sex", user.getSex());
		ib.setDate("create_date", user.getCreateDate());
		ib.setString("address", user.getAddress());
		ib.setString("comment", user.getComment());
		ib.setInt("status", user.getStatus());
		return this.jdbc.insertForBoolean(ib);
	}

	@Override
	public boolean delete(Integer key) {
		UpdateBuilder ub = new UpdateBuilder(TABLE);
		ub.setInt("status", CommonStatus.DISABLE.getCode());
		ub.where.setInt("id", key);
		return this.jdbc.updateForBoolean(ub);
	}

	@Override
	public List<User> list(String account, String name, String nickName,
			boolean isAll, int pageStart, int pageSize) {
		String sql = "select * from "+ TABLE + " where 1=1 ";
		StatementParameter sp = new StatementParameter();
		if(StringUtils.isNotBlank(account)){
			sql += " and account = ? ";
			sp.setString(account);
		}
		if(StringUtils.isNotBlank(name)){
			sql += " and name = ? ";
			sp.setString(name);
		}
		if(StringUtils.isNotBlank(nickName)){
			sql += " and nick_name = ? ";
			sp.setString(nickName);
		}
		if(!isAll){
			sql += " and status = ? ";
			sp.setInt(CommonStatus.ENABLE.getCode());
		}
		sql += " order by id desc limit ?, ?";
		sp.setInt(pageStart);
		sp.setInt(pageSize);
		return this.jdbc.queryForList(sql, User.class, sp);
	}

	@Override
	public void subscribe(String message, boolean isMySelf) {
		throw new UnsupportedOperationException("不支持该操作");
	}

	@Override
	public User getBy(String account) {
		String sql = "select * from "+TABLE +" where account = ?";
		StatementParameter sp = new StatementParameter();
		sp.setString(account);
		return this.jdbc.query(sql, User.class);
	}

	@Override
	public long count(String account, String name, String nickName, boolean isAll) {
		String sql = "select count(1) from "+ TABLE + " where 1=1 ";
		StatementParameter sp = new StatementParameter();
		if(StringUtils.isNotBlank(account)){
			sql += " and account = ? ";
			sp.setString(account);
		}
		if(StringUtils.isNotBlank(name)){
			sql += " and name = ? ";
			sp.setString(name);
		}
		if(StringUtils.isNotBlank(nickName)){
			sql += " and nick_name = ? ";
			sp.setString(nickName);
		}
		if(!isAll){
			sql += " and status = ? ";
			sp.setInt(CommonStatus.ENABLE.getCode());
		}
		return this.jdbc.queryForLong(sql, sp);
	}

}
