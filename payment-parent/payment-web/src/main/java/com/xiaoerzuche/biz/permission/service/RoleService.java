package com.xiaoerzuche.biz.permission.service;

import java.util.List;

import com.xiaoerzuche.biz.permission.mode.Role;
import com.xiaoerzuche.common.core.data.jdbc.Page;

public interface RoleService {
	/**
	 * 增加角色
	 * @param resource
	 * @return
	 */
	boolean add(Role role);
	
	/**
	 * 更新角色
	 * @param role
	 * @return
	 */
	boolean update(Role role);
	
	/**
	 * 更新角色
	 * @param id 主键
	 * @return
	 */
	boolean delete(int id);
	
	/**
	 * 根据主键获取记录
	 * @param id 主键
	 * @return	
	 */
	Role get(int id);
	
	/**
	 * 查询角色接口
	 * @param name		角色名称
	 * @param isAll		是否查询所有的角色，包含已经禁用的角色
	 * @param pageStart 
	 * @param pageSize
	 * @return
	 */
	Page<Role> list(String name, boolean isAll, int pageStart, int pageSize);

	/**
	 * 查询所有的角色
	 * @return
	 */
	List<Role> listAll();
	
	/**
	 * 根据用户ID获取用户拥有的角色
	 * @param id
	 * @return
	 */
	List<Role> listByUser(Integer id);

	/**
	 * 对用户进行角色分配
	 * @param uid
	 * @param roleIds
	 */
	void grant(Integer uid, Integer[] roleIds);
}
