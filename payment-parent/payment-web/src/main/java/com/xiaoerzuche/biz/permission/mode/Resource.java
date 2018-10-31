package com.xiaoerzuche.biz.permission.mode;

/**
 * 资源点管理点（权限点）
 * @author Nick C
 *
 */
public class Resource {
	private int id;
	private String name;//'名称',
	private String url;//'路径',
	private int type;//'资源类型1是一级菜单，2是二级菜单，3是按钮级别资源',
	private int display;//'1显示0不显示',
	private int seq;//'排序字段，数值越小越靠前',
	private String code;//'资源唯一标示',
	private int status;//'状态1是启动，0是禁用'
	private int pid;//父资源ID
	
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getDisplay() {
		return display;
	}
	public void setDisplay(int display) {
		this.display = display;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	@Override
	public String toString() {
		return "Resource [id=" + id + ", name=" + name + ", url=" + url
				+ ", type=" + type + ", display=" + display + ", seq=" + seq
				+ ", code=" + code + ", status=" + status + ", pid=" + pid
				+ "]";
	}
}
