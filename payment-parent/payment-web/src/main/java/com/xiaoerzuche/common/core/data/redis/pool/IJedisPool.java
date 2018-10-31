package com.xiaoerzuche.common.core.data.redis.pool;

import redis.clients.jedis.Jedis;

/**
 * redis pool的基础接口
 * @author Nick C
 *
 */
public interface IJedisPool {
	/**
	 * 从池中获取资源
	 * @return
	 */
	Jedis getResource();
	/**
	 * 返回坏掉的链接资源
	 * @param jedis
	 */
	void returnBrokenResource(Jedis jedis);
	/**
	 * 将链接资源返还回到池中
	 * @param jedis
	 */
	void returnResource(Jedis jedis);
	/**
	 * 销毁整个连接池
	 */
	void destroy();

}
