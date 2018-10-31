package com.xiaoerzuche.common.core.data.redis;

import com.xiaoerzuche.common.core.context.ContextImpl;

/**
 * 抽象的redis实现
 * @author Nick C
 *
 */
public abstract class AbstractRedis extends ContextImpl {
	protected int maxActive;
	protected int timeout;
	protected int initialPoolSize;// 默认初始化连接数量
	protected String password;

	public void setMaxActive(int maxActive) {
		this.maxActive = maxActive;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public void setInitialPoolSize(int initialPoolSize) {
		this.initialPoolSize = initialPoolSize;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
