package com.xiaoerzuche.biz.payment.dao.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.xiaoerzuche.biz.payment.dao.PaymentSessionDao;
import com.xiaoerzuche.common.core.data.redis.Redis;

@Repository
public class PaymentSessionDaoImp implements PaymentSessionDao{
	private static final Logger logger = LoggerFactory.getLogger(PaymentSessionDaoImp.class);
	@Autowired
	private Redis redis;

	@Override
	public String setSession(String key, int seconds, String value) {
		logger.info("设置session,key:{},seconds:{},value:{}",new Object[]{key,seconds,value});
		return this.redis.setex(key, seconds, value);
	}

	@Override
	public String getSession(String key) {
		return this.redis.get(key);
	}

	@Override
	public Long removeSession(String key) {
		return this.redis.del(key);
	}

	@Override
	public String updateSession(String key, String value) {
		return this.redis.getSet(key, value);
	}

	@Override
	public long getRemainSeconds(String key) {
		return this.redis.ttl(key);
	}

	@Override
	public boolean setSession(String key, long timeStampSecond, String value) {
		this.redis.set(key, value);
		long expireAt = this.redis.expireAt(key, timeStampSecond);
		return 1==expireAt;
	}

	@Override
	public long lock(String key, long timeStampSecond) {
		long result = redis.setnx(key, key);
		long expireAt = redis.expireAt(key, timeStampSecond);
		return 0;
	}

	@Override
	public long removeLock(String key) {
		// TODO Auto-generated method stub
		return 0;
	}
}
