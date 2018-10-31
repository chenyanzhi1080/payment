package com.xiaoerzuche.biz.payment.vo;

public class MasPayVo {
	/**
	 * 信用卡交易预支付单
	 */
	private String prepayId;
	/**
	 * 时间戳
	 */
	private String timeStamp;
	/**
	 * 随机串
	 */
	private String nonceStr;
	/**
	 * 电子钱包支付加密签名
	 */
	private String paySign;
}
