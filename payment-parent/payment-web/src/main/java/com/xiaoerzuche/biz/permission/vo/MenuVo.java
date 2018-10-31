package com.xiaoerzuche.biz.permission.vo;

import java.util.ArrayList;
import java.util.List;

import com.xiaoerzuche.biz.permission.mode.Resource;

public class MenuVo {
	
	private int id;
	
	private String name;
	
	private String url;
	
	private int type;
	
	List<MenuVo> childs;

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
	public List<MenuVo> getChilds() {
		return childs;
	}
	public void setChilds(List<MenuVo> childs) {
		this.childs = childs;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public static MenuVo createBy(Resource resource){
		MenuVo vo = new MenuVo();
		vo.setId(resource.getId());
		vo.setName(resource.getName());
		vo.setUrl(resource.getUrl());
		vo.setType(resource.getType());
		vo.setChilds(new ArrayList<MenuVo>());
		return vo;
	}
	
	@Override
	public String toString() {
		return "MenuVo [id=" + id + ", name=" + name + ", url=" + url
				+ ", type=" + type + ", childs=" + childs + "]";
	}
}
