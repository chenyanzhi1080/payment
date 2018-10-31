package com.xiaoerzuche.common.core.cache;

import java.util.List;

/**
 * 需要刷新缓存的接口都要实现，通常用来实现刷新JVM级别的缓存
 * @author Nick C
 *
 */
public interface RefreshInterface<T> {
	/**
	 * 根据list的内容进行刷新缓存
	 * @param list
	 */
	void refresh(List<T> list);

	/**
	 * 刷新的具体实现
	 * @return	是否刷新成功
	 */
	void refresh();
}
