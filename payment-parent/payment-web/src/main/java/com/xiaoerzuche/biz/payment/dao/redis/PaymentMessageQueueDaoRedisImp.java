package com.xiaoerzuche.biz.payment.dao.redis;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.xiaoerzuche.biz.payment.dao.PaymentMessageQueueDao;
import com.xiaoerzuche.biz.payment.vo.MessageContent;
import com.xiaoerzuche.biz.payment.vo.WechatMessageVo;
import com.xiaoerzuche.common.core.data.redis.Redis;
import com.xiaoerzuche.common.util.JsonUtil;

@Repository
public class PaymentMessageQueueDaoRedisImp implements PaymentMessageQueueDao{
	private static final Logger logger = LoggerFactory.getLogger(PaymentMessageQueueDaoRedisImp.class);
	/**
	 * 快处理队列
	 */
//	private static final String PAYMENTWECHATMESSAGEQUEUEKEY = "PAYMENT:WECHATMESSAGE:QUEUE";
	private static final String PAYMENTMESSAGEQUEUEKEY = "PAYMENT:MESSAGE:QUEUE";
	
	@Autowired
	private Redis redis;
	
	@Override
	public void push(MessageContent messageContent) {
		long length = this.redis.llen(PAYMENTMESSAGEQUEUEKEY);
		if(length <= 300){
			logger.info("PaymentMessageQueueDaoRedisImp.push:"+messageContent.toString());
			logger.info("push.key.llen.before:"+this.redis.llen(PAYMENTMESSAGEQUEUEKEY));
			this.redis.lpush(PAYMENTMESSAGEQUEUEKEY, JsonUtil.toJson(messageContent));
			logger.info("[支付网关消息通知对列]push.key.llen.after:"+this.redis.llen(PAYMENTMESSAGEQUEUEKEY));
		}else{
			logger.warn("[支付网关消息通知对列]，请检查支付网关回调接口是否可用，messageContent对象为："+messageContent);
		}
	}

	@Override
	public MessageContent take() {
		String json = redis.rpop(PAYMENTMESSAGEQUEUEKEY);
		logger.info("[支付网关消息通知对列读取]PaymentMessageQueueDaoRedisImp.take:"+json);
		if(StringUtils.isNotBlank(json)){
			MessageContent messageContent = JsonUtil.fromJson(json, MessageContent.class);
			return messageContent;
		}
		return null;
	}

}
