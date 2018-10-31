package com.xiaoerzuche.biz.payment.job;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xiaoerzuche.biz.payment.enu.TranStatus;
import com.xiaoerzuche.biz.payment.model.PayOrder;
import com.xiaoerzuche.biz.payment.service.CallbackService;
import com.xiaoerzuche.biz.payment.service.PayOrderService;
/*
 * 建议五分钟启动异常，sql的话查询当前时间五分钟之前的待支付的订单
 * 
 * */
@Component("checkPayingOrderJob")
public class CheckPayingOrderJob {
	private static final Logger logger = LoggerFactory.getLogger(CheckPayingOrderJob.class);
	
	
	@Autowired
	private PayOrderService payOrderService;
	
	@Autowired
	CallbackService callbackService;
	
	public void checkPayingOrder(){
		/**
		logger.info("CheckPayingOrderJob checkPayingOrder start..........");
		List<PayOrder> list = payOrderService.listByCheckPayingOrderJob();
		logger.info("CheckPayingOrderJob checkPayingOrder list:{}",new Object[]{list!=null?list.size():0});
		
		for (PayOrder each : list) {
			//TODO 查询第三方
			String queryId=null;
			String tranNo=null;
			int tranStatus=0;
			payOrderService.updateCallBackStatus(queryId, tranNo, tranStatus);
			if(tranStatus==TranStatus.SUCCESS.getCode()){
				boolean isFailure=true;
				Map<String, String> valideData=null;
				boolean result =callbackService.callback(valideData, isFailure);
				logger.info("CheckPayingOrderJob checkPayingOrder order:{},result:{}",new Object[]{each,result});
			}
		}
		*/
		
		
	}
}
