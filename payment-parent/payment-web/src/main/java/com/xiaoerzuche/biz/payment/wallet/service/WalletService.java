package com.xiaoerzuche.biz.payment.wallet.service;

import com.xiaoerzuche.biz.payment.vo.PayVo;
import com.xiaoerzuche.biz.payment.wallet.dto.RefundResp;

/** 
* 电子钱包支付
* @author: cyz
* @version: payment
* @time: 2016年7月26日
* 
*/
public interface WalletService {
	/**
	 * 
	 * @param payVo
	 * @return
	 */
	String doPayment(PayVo payVo, String queryId, int channel);
	boolean callback(String data);
	RefundResp doRefund(String origTranId, String refundQueryId, String goodsOrderId, int refundPrice);
	
	String doBigCustomerPay(PayVo payVo, String queryId, int channel);
	RefundResp doBigCustomerRefund(String origTranId, String refundQueryId, String goodsOrderId, int refundPrice);
	
}
