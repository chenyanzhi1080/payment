package com.xiaoerzuche.biz.permission.dao;

import com.xiaoerzuche.biz.permission.mode.UserRole;
import com.xiaoerzuche.common.core.data.spi.IUpdate;

/**
 * 用户与角色之间关联的Dao
 * @author Nick C
 *
 */
public interface UserRoleDao extends IUpdate<UserRole, UserRole> {
	
	/**
	 * 用处用户的所有权限
	 * @param uid
	 */
	boolean clear(Integer uid);

}
