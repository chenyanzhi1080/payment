package com.xiaoerzuche.biz.payment.service.imp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.xiaoerzuche.biz.payment.paymentConfig.DefaultAliOpendApiClient;
import com.xiaoerzuche.biz.payment.service.AliTranQueryService;
import com.xiaoerzuche.biz.zmxy.config.AliOpendApiClient;
import com.xiaoerzuche.common.util.JsonUtil;
@Service
public class AliTranQueryServiceImp implements AliTranQueryService{
	private static final Logger logger = LoggerFactory.getLogger(AliTranQueryServiceImp.class);
	@Autowired
	private DefaultAliOpendApiClient aliClient;
	/**
	 * 支付宝逻辑：
	 * 如果支付宝单未支付，则在支付宝那边查询不到支付单的记录
	 *
	 */
	@Override
	public AlipayTradeQueryResponse tradeQuery(String queryId) {
		
		try {
//			AlipayClient alipayClient = new DefaultAlipayClient(AliOpendApiClient.GATEWAY_URL,AliOpendApiClient.APP_ID,AliOpendApiClient.PRIVATE_KEY,"json","UTF-8",AliOpendApiClient.ALIPAY_PUBLIC_KEY,"RSA");
			AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
			request.setBizContent("{\"out_trade_no\":\""+queryId+"\"}");
			AlipayTradeQueryResponse response = (AlipayTradeQueryResponse) aliClient.alipayClient.execute(request);
			logger.info("[支付宝支付订单查询]"+JsonUtil.toJson(response));
			if(response.isSuccess()){
				return response;
			} 
		} catch (AlipayApiException e) {
			logger.info("[支付宝支付订单查询异常]"+e);
		}
		return null;
	}
	
}
