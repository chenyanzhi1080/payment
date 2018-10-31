package com.xiaoerzuche.biz.permission.mode;

/**
 * 角色跟资源的绑定（可以认为是对角色授权）
 * @author Nick C
 *
 */
public class RoleResource {
	private int roleId;//角色ID
	private int resourceId;//资源ID
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public int getResourceId() {
		return resourceId;
	}
	public void setResourceId(int resourceId) {
		this.resourceId = resourceId;
	}
	@Override
	public String toString() {
		return "RoleReource [roleId=" + roleId + ", resourceId=" + resourceId
				+ "]";
	}
}
