package com.xiaoerzuche.common.core.session;

/**
 * 
 * @author Nick C
 *
 */
public interface DistributedBaseInterface {
	
	/**
	 * 根据key获取缓存数据
	 * @param key   
	 * @param seconds  
	 * @return  
	 */
	public String get(String key,int seconds);
	
	/**
	 * 更新缓存数据
	 * @param key    
	 * @param json
	 * @param seconds
	 */
	public void set(String key, String json,int  seconds);
	
	/**
	 * 删除缓存
	 * @param key
	 */
	public void del(String key);
	
	/**
	 * 设置过期数据
	 * @param key
	 * @param seconds
	 */
	public void expire(String key,int  seconds);
	
}
