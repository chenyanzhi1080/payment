package com.xiaoerzuche.biz.payment.dao;

import com.xiaoerzuche.biz.payment.vo.MessageVo;

public interface MessageDao {
	/**
	 * 通知微信开开出行公众号服务
	 * @param openId 用户openid
	 * @param orderId 业务订单ID
	 * @param type 业务类型
	 * @return
	 */
	String notifyWechat(String openId, String orderId, String type);
	/**
	 * 短信通知
	 * @param msgVo
	 */
	String sendMsg(MessageVo msgVo);
}
