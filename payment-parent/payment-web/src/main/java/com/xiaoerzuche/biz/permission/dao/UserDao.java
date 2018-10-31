package com.xiaoerzuche.biz.permission.dao;

import java.util.List;

import com.xiaoerzuche.biz.permission.mode.User;
import com.xiaoerzuche.common.core.cache.RefreshInterface;
import com.xiaoerzuche.common.core.data.spi.IUpdate;
import com.xiaoerzuche.common.core.pubsub.ISubscribe;

/**
 * 用户Dao
 * @author Nick C
 *
 */
public interface UserDao extends IUpdate<User, Integer>, RefreshInterface<User>,ISubscribe {
	/**
	 * 查询用户接口
	 * @param account	用户账号
	 * @param name		用户名
	 * @param nickName	昵称
	 * @param isAll		是否查询全部用户，false的话只是查询启用的用户，禁用的不会出来
	 * @param pageStart
	 * @param pageSize
	 * @return
	 */
	List<User> list(String account, String name, String nickName, boolean isAll, int pageStart, int pageSize);
	
	/**
	 * 查询用户数量接口
	 * @param account	用户账号
	 * @param name		用户名
	 * @param nickName	昵称
	 * @param isAll		是否查询全部用户，false的话只是查询启用的用户，禁用的不会出来
	 * @return
	 */
	long count(String account, String name, String nickName, boolean isAll);
	
	/**
	 * 根据账号获取用户信息，account已经做了唯一索引
	 * @param account
	 * @return
	 */
	User getBy(String account);

	/**
	 * 修改密码
	 * @param user
	 * @param sourcePwd
	 * @param newPwd
	 * @return
	 */
	boolean updatePwd(User user, String sourcePwd, String newPwd);
}
