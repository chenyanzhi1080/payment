package com.xiaoerzuche.biz.payment.dao;

import com.xiaoerzuche.biz.payment.vo.MessageContent;

public interface PaymentMessageQueueDao {
	/**
	 * @param wechatMessageVo
	 */
	void push(MessageContent messageContent);
	/**
	 * @return
	 */
	MessageContent take();
}
