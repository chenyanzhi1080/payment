package com.xiaoerzuche.common.core.data.redis;

import org.apache.commons.pool.impl.GenericObjectPool.Config;

import redis.clients.jedis.Jedis;

/**
 * 这里预留对RedisPool的二次封装，可以考虑在这里开发对redis的监控操作
 * @author Nick C
 *
 */
public class JedisPool extends redis.clients.jedis.JedisPool {
	
	public JedisPool(final Config poolConfig, final String host, final int port, final int timeout) {
		super(poolConfig, host, port, timeout);
	}
	
	public JedisPool(final Config poolConfig, final String host, final int port, final int timeout, String passWord) {
		super(poolConfig, host, port, timeout, passWord);
	}
	
	public JedisPool(final Config poolConfig, final String host, final int port, final int timeout, String passWord, int database) {
		super(poolConfig, host, port, timeout, passWord, database);
	}


	@Override
	public Jedis getResource() {
		Jedis resource = null;
		resource = super.getResource();
		return resource;
	}

	@Override
	public void returnBrokenResource(Jedis resource) {
		super.returnBrokenResource(resource);
	}

	@Override
	public void returnResource(Jedis resource) {
		super.returnResource(resource);
	}
}
