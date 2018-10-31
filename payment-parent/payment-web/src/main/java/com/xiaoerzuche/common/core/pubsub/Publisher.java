package com.xiaoerzuche.common.core.pubsub;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.xiaoerzuche.common.core.data.redis.Redis;

public class Publisher {

	private static Map<ISubscribe, PubSubRsyncImpl> map = new ConcurrentHashMap<ISubscribe, PubSubRsyncImpl>();

	public static void listen(final ISubscribe subscribe, Redis redis) {
		if (map.containsKey(subscribe)) {
			throw new IllegalArgumentException("Pub[" + subscribe + "]已经监听过.");
		}
		String channel = getChannel(subscribe);
		PubSubRsyncImpl pubRsyncImpl = new PubSubRsyncImpl(redis, channel) {
			@Override
			public void subscribe(String message, boolean isMySelf) {
				subscribe.subscribe(message, isMySelf);
			}
		};
		pubRsyncImpl.init();
		map.put(subscribe, pubRsyncImpl);
	}

	protected static String getChannel(final ISubscribe subscribe) {
		Class<?> clazz = subscribe.getClass();
		String classSimpleName = clazz.getSimpleName();
		return "pubsub:" + classSimpleName;
	}

	public static boolean publish(ISubscribe subscribe, String message) {
		PubSubRsyncImpl pubRsyncImpl = map.get(subscribe);
		return pubRsyncImpl.publish(message);
	}

}
