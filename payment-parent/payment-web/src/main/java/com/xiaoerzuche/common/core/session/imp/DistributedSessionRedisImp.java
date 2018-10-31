package com.xiaoerzuche.common.core.session.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xiaoerzuche.common.core.data.redis.Redis;
import com.xiaoerzuche.common.core.session.DistributedBaseInterface;

/**
 * 分布式Session的redis实现
 * @author Nick C
 *
 */
@Component
public class DistributedSessionRedisImp implements DistributedBaseInterface {
	@Autowired
	private Redis redis;
	
	private static final int DEFAULT_SECOND = 1800;

	@Override
	public String get(String key, int seconds) {
		seconds = seconds <= 0 ? DEFAULT_SECOND : seconds;
		this.redis.expire(key, seconds);
		String result = this.redis.get(key);
		return result;
	}

	@Override
	public void set(String key, String json, int seconds) {
		seconds = seconds <= 0 ? DEFAULT_SECOND : seconds;
		this.redis.setex(key, seconds, json);
	}

	@Override
	public void del(String key) {
		this.redis.del(key);
	}

	@Override
	public void expire(String key, int seconds) {
		seconds = seconds <= 0 ? DEFAULT_SECOND : seconds;
		this.redis.expire(key, seconds);
	}

}
