package com.xiaoerzuche.biz.payment.service;

import com.xiaoerzuche.biz.payment.vo.MessageVo;

/**
 * 微信公众号消息通知
 * @author: cyz
 * @version: payment
 * @time: 2016-8-21
 */
public interface MessageService {
	/**
	 * 加入微信通知队列
	 * @param openId
	 * @param orderId
	 * @param appid
	 */
	void pushWechatMessageQueue(String openId, String orderId, String appid);
	/**
	 * 微信通知
	 */
	void sendMessage();
	/**
	 * 加入短信队列
	 * @param messageVo
	 */
	void pushMsgeQueue(MessageVo messageVo);
}
