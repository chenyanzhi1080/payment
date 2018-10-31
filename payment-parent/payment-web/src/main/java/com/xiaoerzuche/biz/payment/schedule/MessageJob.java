package com.xiaoerzuche.biz.payment.schedule;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.xiaoerzuche.biz.payment.service.MessageService;

/** 
* 定期扫描队列，推送短信或微信模板消息
* @author: CYZ
* @version: payment-web
* @time: 2017年2月15日
* 
*/
public class MessageJob implements Job{
	private static final Logger logger = LoggerFactory.getLogger(MessageJob.class);
	
	@Autowired
	private MessageService messageService;
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("[支付网关信息推送定时任务]");
		try {
			messageService.sendMessage();
		} catch (Exception e) {
			logger.error("[支付网关信息推送定时任务异常]{}", new Object[]{e});
		}
		logger.info("[支付网关信息推送定时任务结束]");
	}

}
