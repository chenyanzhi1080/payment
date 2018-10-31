package com.xiaoerzuche.common.core.context;
/**
 * 容器接口
 * @author Nick C
 *
 */
public interface Context {

	/**
	 * 容器初始化
	 */
	void init();

	/**
	 * 容器销毁
	 */
	void destroy();
}
