package com.xiaoerzuche.biz.permission.dao;

import java.util.List;

import com.xiaoerzuche.biz.permission.mode.Resource;
import com.xiaoerzuche.common.core.cache.RefreshInterface;
import com.xiaoerzuche.common.core.data.spi.IUpdate;
import com.xiaoerzuche.common.core.pubsub.ISubscribe;

/**
 * 资源DAO
 * @author Nick C
 *
 */
public interface ResourceDao extends IUpdate<Resource, Integer>, RefreshInterface<Resource>, ISubscribe {
	/**
	 * 查询角色接口
	 * @param name		资源名称
	 * @param isAll		是否查询所有的资源，包含已经禁用的资源
	 * @param pageStart	分页开始的索引
	 * @param pageSize	一页数据的数量
	 * @return
	 */
	List<Resource> list(String name, boolean isAll, int pageStart, int pageSize);

	/**
	 * 通过UserID获取该用户可以访问的所有资源列表
	 * @param userId	用户ID
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
	 * 计算分页总数
	 * @param name
	 * @param isAll
	 * @return
	 */
	long count(String name, boolean isAll);

	/**
	 * 判断该资源节点还是否存在孩子节点
	 * @param id
	 * @return
	 */
	boolean hasChild(int id);
}
