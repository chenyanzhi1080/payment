package com.xiaoerzuche.common.core.data.redis.pool;

import org.apache.commons.lang.StringUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;

import com.xiaoerzuche.common.core.data.redis.JedisPool;
import com.xiaoerzuche.common.util.NumberUtil;


/**
 * redis连接池的common pool实现，但是这里暂时不代理它的Pool，等以后再扩展
 * @author Nick C
 *
 */
public class JedisPoolApacheImpl implements IJedisPool {
	private JedisPool pool;
	
	public JedisPoolApacheImpl(String host, int port, int timeout, int maxActive, String password, int database) {
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxActive(maxActive);
		poolConfig.setMaxIdle(maxActive);
		// poolConfig.setMinEvictableIdleTimeMillis(24 * 3600 * 1000);
		poolConfig.setMinEvictableIdleTimeMillis(-1);//  已建立的连接不回收，高并发时建立连接会很耗资源 by Nick C
		// poolConfig.setTimeBetweenEvictionRunsMillis(-1);
		if(StringUtils.isBlank(password)){
			password = null;
		}
		
		pool = new JedisPool(poolConfig, host, port, timeout, password, database);
	}

	@Override
	public Jedis getResource() {
		return pool.getResource();
	}

	@Override
	public void returnBrokenResource(Jedis jedis) {
		pool.returnBrokenResource(jedis);
	}

	@Override
	public void returnResource(Jedis jedis) {
		pool.returnResource(jedis);
	}

	@Override
	public void destroy() {
		pool.destroy();
	}
}
