package com.xiaoerzuche.biz.payment.service;

import java.util.Map;

import com.xiaoerzuche.biz.payment.model.PayOrder;

public interface CallbackService {
	public boolean callback(Map<String, String> valideData, boolean isFailure);
	
	public void callback(PayOrder payOrder);
	/**
	 * 订单交易已完结
	 * @param queryId
	 * @return
	 */
	public boolean tradeFinished(String queryId);
}
