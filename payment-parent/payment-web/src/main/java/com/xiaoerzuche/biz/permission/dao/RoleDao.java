package com.xiaoerzuche.biz.permission.dao;

import java.util.List;

import com.xiaoerzuche.biz.permission.mode.Role;
import com.xiaoerzuche.common.core.cache.RefreshInterface;
import com.xiaoerzuche.common.core.data.spi.IUpdate;
import com.xiaoerzuche.common.core.pubsub.ISubscribe;
/**
 * 角色DAO
 * @author Nick C
 *
 */
public interface RoleDao extends IUpdate<Role, Integer>, RefreshInterface<Role>, ISubscribe {
	
	/**
	 * 查询角色接口
	 * @param name		角色名称
	 * @param isAll		是否查询所有的角色，包含已经禁用的角色
	 * @param pageStart
	 * @param pageSize
	 * @return
	 */
	List<Role> list(String name, boolean isAll, int pageStart, int pageSize);
	
	/**
	 * 查询角色数量接口
	 * @param name		角色名称
	 * @param isAll		是否查询所有的角色，包含已经禁用的角色
	 * @return
	 */
	long count(String name, boolean isAll);
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	List<Role> listByUser(Integer id);

}
