package com.xiaoerzuche.biz.payment.model;


/** 
* 支付交易记录,记录并监控“支付中心”与“支付平台”之间的交互情况
* @author: cyz
* @version: payment
* @time: 2016年5月5日
* 
*/
public class TransactionRecord {
	/**
	 * 唯一标示
	 */
	private String id;
	/**
	 * 支付中心交易订单查询号
	 */
	private String queryId;
	/**
	 * 业务系统的订单号
	 */
	private String orderId;
	/**
	 * 支付平台交易号，如果有值则存
	 */
	private String tradeNo;
	
	
}
