package com.xiaoerzuche.common.core.pubsub;

import com.xiaoerzuche.common.core.context.Context;
import com.xiaoerzuche.common.core.data.redis.Redis;
import com.xiaoerzuche.common.util.JsonUtil;

public class MemdbRsyncServiceRedisImpl implements Context, MemdbRsyncService {

	private final Redis redis;

	private final String channel;

	private final String sender;

	private final QueueListener queueListener;

	public MemdbRsyncServiceRedisImpl(Redis redis, String channel, MemdbRsyncService memdbRsyncService) {
		this.redis = redis;
		this.channel = channel;
		this.sender = Integer.toString(redis.hashCode());
		queueListener = new QueueListener(memdbRsyncService, sender);
	}

	public boolean add(String type, String key, String value, boolean isMySelf) {
		QueueBean queueBean = new QueueBean();
		queueBean.setType(type);
		queueBean.setKey(key);
		queueBean.setValue(value);
		queueBean.setSender(sender);
		redis.publish(channel, JsonUtil.toJson(queueBean));
		return true;
	}

	@Override
	public void init() {
		new Thread() {
			@Override
			public void run() {
				redis.subscribe(queueListener, channel);
			};
		}.start();
	}

	@Override
	public void destroy() {

	}

}
