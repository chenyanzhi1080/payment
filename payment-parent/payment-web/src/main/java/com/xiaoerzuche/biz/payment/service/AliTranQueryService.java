package com.xiaoerzuche.biz.payment.service;

import com.alipay.api.response.AlipayTradeQueryResponse;

/** 
* 支付宝交易查询（支付、退款）
* @author: cyz
* @version: payment-web
* @time: 2017年12月26日
* 
*/
public interface AliTranQueryService {
	public AlipayTradeQueryResponse tradeQuery(String queryId);
}
