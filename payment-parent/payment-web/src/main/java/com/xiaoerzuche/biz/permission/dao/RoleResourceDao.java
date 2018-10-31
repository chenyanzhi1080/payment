package com.xiaoerzuche.biz.permission.dao;

import com.xiaoerzuche.biz.permission.mode.RoleResource;
import com.xiaoerzuche.common.core.data.spi.IUpdate;
/**
 * 角色与资源之间的关联
 * @author Nick C
 *
 */
public interface RoleResourceDao extends IUpdate<RoleResource, RoleResource>{

	/**
	 * 清除一个角色拥有的权限
	 * @param roleId
	 */
	boolean clear(Integer roleId);

}
