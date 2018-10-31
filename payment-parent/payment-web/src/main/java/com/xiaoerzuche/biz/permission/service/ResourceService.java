package com.xiaoerzuche.biz.permission.service;

import java.util.List;

import com.xiaoerzuche.biz.permission.mode.Resource;
import com.xiaoerzuche.common.core.data.jdbc.Page;

/**
 * 资源操作Service
 * @author Nick C
 *
 */
public interface ResourceService {
	/**
	 * 通过UserID获取该用户可以访问的所有资源列表
	 * @param userId
	 * @return
	 */
	List<Resource> listAccessableByUserId(int userId);
	
	/**
	 * 通过RoleID获取该角色可以访问的所有资源列表
	 * @param roleId
	 * @return
	 */
	List<Resource> listAccessableByRoleId(int roleId);
	
	/**
	 * 增加资源
	 * @param resource
	 * @return
	 */
	boolean add(Resource resource);
	
	/**
	 * 更新资源
	 * @param resource
	 * @return
	 */
	boolean update(Resource resource);
	
	/**
	 * 更新资源
	 * @param id 主键
	 * @return
	 */
	boolean delete(int id);
	
	/**
	 * 根据主键获取记录
	 * @param id 主键
	 * @return	
	 */
	Resource get(int id);
	
	/**
	 * 查询角色接口
	 * @param name		资源名称
	 * @param isAll		是否查询所有的资源，包含已经禁用的资源
	 * @param pageStart	分页开始的索引
	 * @param pageSize	一页数据的数量
	 * @return
	 */
	Page<Resource> list(String name, boolean isAll, int pageStart, int pageSize);
	
	/**
	 * 获取所有有效的资源
	 * @return
	 */
	List<Resource> listValid();
	
	/**
	 * 角色进行资源绑定
	 * @param resIds
	 * @param roleId
	 */
	void grant(Integer[] resIds, Integer roleId);
}
