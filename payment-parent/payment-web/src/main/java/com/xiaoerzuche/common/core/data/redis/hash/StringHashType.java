package com.xiaoerzuche.common.core.data.redis.hash;

import com.xiaoerzuche.common.util.StringUtil;


public class StringHashType implements HashType {

	@Override
	public long getHashCode(String key) {
		String param = key.substring(key.lastIndexOf(":") + 1);
		long hashCode = StringUtil.getHashCode(param);
		return hashCode;
	}

}
