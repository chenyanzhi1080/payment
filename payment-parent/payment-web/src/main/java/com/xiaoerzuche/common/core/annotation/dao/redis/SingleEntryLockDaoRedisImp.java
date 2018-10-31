package com.xiaoerzuche.common.core.annotation.dao.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.xiaoerzuche.common.core.annotation.dao.SingleEntryLockDao;
import com.xiaoerzuche.common.core.data.redis.Redis;

@Repository
public class SingleEntryLockDaoRedisImp implements SingleEntryLockDao {
	private static final String KEY = "SINGLE_ENTRY_";
	private static final int SECONDS = 600;
	@Autowired
	private Redis redis;

	@Override
	public boolean lock(String lockKey) {
		long result = redis.setnx(KEY+lockKey, lockKey);
		if(result == 1){
			redis.expire(KEY+lockKey, SECONDS);
			return true;
		}
		return false;
	}

	@Override
	public void release(String lockKey) {
		this.redis.del(KEY+lockKey);
	}

}
