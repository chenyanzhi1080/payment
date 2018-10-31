package com.xiaoerzuche.biz.permission.enu;

/**
 * 记录状态枚举类
 * @author Nick C
 *
 */
public enum ResourceType {
	
    MENUE (1, "一级菜单"), LINK (2, "二级菜单"), BUTTON(3, "按钮级权限");

    //code
    private int code ;
    //名称
    private String name;

    private ResourceType(int code, String name) {
		this.code = code;
		this.name = name;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
    
}
