package com.xiaoerzuche.common.constant;

/**
 * 重要的系统级别常量定义，其它业务级别的常量建议不要放在这里
 * @author Nick C
 *
 */
public interface Constants {
	//没有登录的标识from Session
	public static final String ADMIN_LOGIN_USER = "ADMIN_LOGIN_USER";
	//用户拥有的权限from Session
	public static final String ADMIN_PERMISSION = "ADMIN_PERMISSION";
	
	//前台用户登录的标识from Session
	public static final String FRONT_LOGIN_MEMBER = "FRONT_LOGIN_MEMBER";
	
	//订阅发布类型
	public static final String TYPE_SET = "set";
	//订阅发布类型
	public static final String TYPE_REMOVE = "remove";
	
	//默认一页是20条记录
	public static final int DEFAULT_PAGE_SIZE = 20;
}
