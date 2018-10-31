package com.xiaoerzuche.common.core.cache;

/**
 * 在启动时需要加载缓存操作的就实现这个接口
 * @author Nick C
 *
 */
public interface CacheLoaderOnServerStart {
	/**
	 * 加载在启动时加载缓存
	 */
	void start();
}
