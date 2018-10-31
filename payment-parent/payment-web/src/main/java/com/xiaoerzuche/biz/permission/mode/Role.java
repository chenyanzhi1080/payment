package com.xiaoerzuche.biz.permission.mode;

/**
 * 角色信息
 * @author Nick C
 *
 */
public class Role {
	private int id;
	private String name;//'名称',
	private String comment;//'备注',
	private int status;//'状态,1是启用，0是禁用'
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "Role [id=" + id + ", name=" + name + ", comment=" + comment
				+ ", status=" + status + "]";
	}
}
