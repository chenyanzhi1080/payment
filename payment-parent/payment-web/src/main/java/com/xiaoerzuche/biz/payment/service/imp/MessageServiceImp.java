package com.xiaoerzuche.biz.payment.service.imp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.xiaoerzuche.biz.payment.dao.MessageDao;
import com.xiaoerzuche.biz.payment.dao.PaymentMessageQueueDao;
import com.xiaoerzuche.biz.payment.enu.MessageType;
import com.xiaoerzuche.biz.payment.model.PayAuth;
import com.xiaoerzuche.biz.payment.service.PayAuthService;
import com.xiaoerzuche.biz.payment.service.MessageService;
import com.xiaoerzuche.biz.payment.vo.MessageContent;
import com.xiaoerzuche.biz.payment.vo.MessageVo;
import com.xiaoerzuche.biz.payment.vo.WechatMessageVo;
import com.xiaoerzuche.biz.payment.web.rpc.PaymentRpcController;
import com.xiaoerzuche.common.util.JsonUtil;

/**
 * 微信公众号消息通知
 * @author: cyz
 * @version: payment
 * @time: 2016-8-21
 */
@Service
public class MessageServiceImp implements MessageService{
	private static final Logger logger = LoggerFactory.getLogger(MessageServiceImp.class);
	
	@Autowired
	private PayAuthService payAuthService;
	@Autowired
	private MessageDao messageDao;
	@Autowired
	private PaymentMessageQueueDao paymentMessageQueueDao;
	
	@Override
	@Async
	public void pushWechatMessageQueue(String openId, String orderId, String appid) {
		PayAuth payAuth = payAuthService.get(appid);
		String type = String.valueOf(payAuth.getChannel());
		WechatMessageVo wechatMessageVo = new WechatMessageVo(openId, orderId, type);
		MessageContent content = new MessageContent(MessageType.WECHAT.getCode(), 1, JsonUtil.toJson(wechatMessageVo));
		paymentMessageQueueDao.push(content);
	}

	@Override
	public void sendMessage() {
		MessageContent content = paymentMessageQueueDao.take();
		String result = "";
		while(null != content){
			logger.info("[支付网关信息推送定时任务推送内容{}]", new Object[]{content.toString()});
			MessageType type = MessageType.getPayType(content.getType());
			switch (type) {
			case WECHAT:
				WechatMessageVo wechatMessageVo = JsonUtil.fromJson(content.getContent(), WechatMessageVo.class);
				result = messageDao.notifyWechat(wechatMessageVo.getOpenId(), wechatMessageVo.getOrderId(), wechatMessageVo.getType());
				break;
			case SMS:
				MessageVo messageVo = JsonUtil.fromJson(content.getContent(), MessageVo.class);
				result = messageDao.sendMsg(messageVo);
				break;
			default:
				break;
			}
			content = paymentMessageQueueDao.take();
		}
		
	}

	@Override
	public void pushMsgeQueue(MessageVo messageVo) {
		String result = result = messageDao.sendMsg(messageVo);
		if(!"200".equals(result)){
			MessageContent content = new MessageContent(MessageType.SMS.getCode(), 1, JsonUtil.toJson(messageVo));
			paymentMessageQueueDao.push(content);
		}
	}
	
}
