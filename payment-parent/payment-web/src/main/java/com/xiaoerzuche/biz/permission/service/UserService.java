package com.xiaoerzuche.biz.permission.service;

import com.xiaoerzuche.biz.permission.exception.NotFoundAccountException;
import com.xiaoerzuche.biz.permission.exception.NotMatchingPwdException;
import com.xiaoerzuche.biz.permission.mode.User;
import com.xiaoerzuche.common.core.data.jdbc.Page;

public interface UserService{

	/**
	 * 增加用户
	 * @param resource
	 * @return
	 */
	boolean add(User user);
	
	/**
	 * 更新用户
	 * @param resource
	 * @return
	 */
	boolean update(User user);
	
	/**
	 * 更新用户
	 * @param id 主键
	 * @return
	 */
	boolean delete(int id);
	
	/**
	 * 根据主键获取记录
	 * @param id 主键
	 * @return	
	 */
	User get(int id);
	
	/**
	 * 验证用户登录
	 * @param account	账号
	 * @param pwd		密码
	 * @return 登录的用户
	 * @throws NotFoundAccountException	找不到对应的账号 	
	 * @throws NotMatchingPwdException 	密码不正确
	 */
	User loginValidate(String account, String pwd) throws NotFoundAccountException, NotMatchingPwdException;
	
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
	Page<User> list(String account, String name, String nickName, boolean isAll, int pageStart, int pageSize);
	
	
	/**
	 * 修改密码
	 * @param user
	 * @param sourcePwd
	 * @param newPwd
	 * @return
	 */
	boolean updatePwd(User user, String sourcePwd, String newPwd);
	
}
