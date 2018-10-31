package com.xiaoerzuche.common.core.pubsub;

import com.xiaoerzuche.common.core.context.Context;

public interface MemdbRsyncService extends Context {

	boolean add(String type, String key, String value, boolean isMySelf);
}
