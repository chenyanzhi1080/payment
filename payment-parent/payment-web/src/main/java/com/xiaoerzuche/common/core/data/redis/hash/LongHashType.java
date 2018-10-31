package com.xiaoerzuche.common.core.data.redis.hash;

public class LongHashType implements HashType {

	@Override
	public long getHashCode(String key) {
		String param = key.substring(key.lastIndexOf(":") + 1);
		long id = Long.parseLong(param);
		return id;
	}

}
