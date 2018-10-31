package com.xiaoerzuche.biz.payment.service;

import com.xiaoerzuche.biz.payment.model.PayOrder;
import com.xiaoerzuche.biz.payment.vo.QueryResultVo;

/** 
* 支付网关交易查询
* @author: cyz
* @version: payment
* @time: 2016年11月15日
* 
*/
public interface QueryService {
	/**
	 * 支付网关交易查询
	 * @param queryVo
	 * @return
	 */
	QueryResultVo paymentQuery(PayOrder srcPayOrder);
}
