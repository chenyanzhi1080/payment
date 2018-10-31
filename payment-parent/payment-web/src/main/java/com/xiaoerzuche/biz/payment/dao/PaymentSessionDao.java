package com.xiaoerzuche.biz.payment.dao;

/** 
 * 微信会话缓存DAO
 * @author: cyz
 * @version: wechat 
 * @time: 2016-2-16
 */
public interface PaymentSessionDao {
	/**
	 * @Description: 保存会话
	 * @author: cyz
	 * @date: 2016-2-16
	 * @param key
	 * @param seconds 缓存时间长度
	 * @param value
	 */
	public String setSession(String key, int seconds, String value); 
	/**
	 * @Description: 缓存，并设置过期时间戳
	 * @param key
	 * @param timeStampSecond 过期时间戳
	 * @param value
	 * @return
	 */
	public boolean setSession(String key, long timeStampSecond, String value);
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
	public Long removeSession(String key);
	/**
	 * @Description: 更新会话缓存信息，并返回原先的会话缓存信息
	 * @author: cyz
	 * @date: 2016-2-16
	 * @param key
	 * @return
	 */
	public String updateSession(String key, String value);
	/**
	 * @Description: 根据key获取剩余有效期，单位：秒
	 * @author: cyz
	 * @date: 2016-2-16
	 * @param key
	 * @return
	 */
	public long getRemainSeconds(String key);
	
	/**
	 * redis锁
	 * @param key
	 * @param timeStampSecond
	 * @return
	 */
	public long lock(String key, long timeStampSecond);
	
	/**
	 * 移除redis锁
	 * @param key
	 * @return
	 */
	public long removeLock(String key);
}
