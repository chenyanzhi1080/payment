package com.xiaoerzuche.common.core.data.redis.hash;

import com.xiaoerzuche.common.util.StringUtil;


public class DefaultHashType implements HashType {

	@Override
	public long getHashCode(String key) {
		long hashCode = StringUtil.getHashCode(key);
		return hashCode;
	}

}
