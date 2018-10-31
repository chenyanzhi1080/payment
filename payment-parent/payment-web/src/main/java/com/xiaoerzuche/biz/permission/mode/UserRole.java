package com.xiaoerzuche.biz.permission.mode;

/**
 * 用户与角色的授权关系
 * @author Nick C
 *
 */
public class UserRole {
	private int userId;//'用户ID',
	private int roleId;//'角色ID',
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	@Override
	public String toString() {
		return "UserRole [userId=" + userId + ", roleId=" + roleId + "]";
	}
	
}
