package com.xiaoerzuche.biz.payment.service;

public interface PamentSessionService {
	/**
	 * @Description: 保存会话
	 * @author: cyz
	 * @date: 2016-2-16
	 * @param key
	 * @param seconds
	 * @param value
	 */
	public boolean setSession(String key, int seconds, String value); 
	/**
	 * @Description: 获取会话内容
	 * @author: cyz
	 * @date: 2016-2-16
	 * @param key
	 * @return
	 */
	public String getSession(String key);
	/**
	 * @Description: 移除会话缓存
	 * @author: cyz
	 * @date: 2016-2-16
	 * @param key
	 */
	public boolean removeSession(String key);
	/**
	 * @Description: 更新会话缓存信息，并返回原先的会话缓存信息
	 * @author: cyz
	 * @date: 2016-2-16
	 * @param key
	 * @return
	 */
	public String updateSession(String key, String value);
}
