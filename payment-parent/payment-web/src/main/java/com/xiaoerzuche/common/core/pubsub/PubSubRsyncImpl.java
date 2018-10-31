package com.xiaoerzuche.common.core.pubsub;

import com.xiaoerzuche.common.constant.Constants;
import com.xiaoerzuche.common.core.context.Context;
import com.xiaoerzuche.common.core.data.redis.Redis;

public abstract class PubSubRsyncImpl implements ISubscribe, MemdbRsyncService, Context {

	protected MemdbRsyncService memdbRsyncService;

	private String channel;
	private Redis redis;

	public PubSubRsyncImpl(Redis redis, String channel) {
		this.redis = redis;
		this.channel = channel;
	}

	@Override
	public void init() {
		memdbRsyncService = new MemdbRsyncServiceRedisImpl(redis, channel, this);
		memdbRsyncService.init();
	}

	@Override
	public void destroy() {
		memdbRsyncService.destroy();
	}

	// @Override
	public boolean publish(String message) {
		memdbRsyncService.add(Constants.TYPE_SET, "key", message, false);
		return true;
	}

	@Override
	public boolean add(String type, String key, String value, boolean isMySelf) {
		// System.err.println("isMySelf:" + isMySelf + " type:" + type + " key:" + key + " value:" + value);
		this.subscribe(value, isMySelf);
		return true;
	}

}
