package com.xiaoerzuche.common.core.pubsub;

/**
 * 发布订阅必须实现的接口
 * @author Nick C
 *
 */
public interface ISubscribe {

	// /**
	// * 发布消息
	// *
	// * @param message
	// * @return
	// */
	// boolean publish(String message);

	/**
	 * 订阅消息.
	 * 
	 * @param message
	 */
	void subscribe(String message, boolean isMySelf);
}
