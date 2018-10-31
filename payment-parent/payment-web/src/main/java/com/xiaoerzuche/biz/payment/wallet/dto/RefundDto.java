package com.xiaoerzuche.biz.payment.wallet.dto;

/** 
* 一句话描述
* @author: cyz
* @version: new-energy
* @time: 2016年7月13日
* 
*/
public class RefundDto {
	/**
	 * 原电子钱包支付流水号
	 * 用于确定是否存在支付订单，退款金额是否超出原支付金额
	 */
	private String origTranId;
	/**
	 * 支付网关交易流水号
	 */
	private String refundQueryId;
	/**
	 * 业务订单号
	 */
	private String goodsOrder;
	/**
	 * 退款金额
	 */
	private int price;
	//签名相关
	private String timeStamp;
	private String sign;
	public String getOrigTranId() {
		return origTranId;
	}
	public void setOrigTranId(String origTranId) {
		this.origTranId = origTranId;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getRefundQueryId() {
		return refundQueryId;
	}
	public void setRefundQueryId(String refundQueryId) {
		this.refundQueryId = refundQueryId;
	}
	public String getGoodsOrder() {
		return goodsOrder;
	}
	public void setGoodsOrder(String goodsOrder) {
		this.goodsOrder = goodsOrder;
	}
	public RefundDto() {
		super();
	}
	public RefundDto(String origTranId, String refundQueryId, String goodsOrder, int price, String timeStamp,
			String sign) {
		super();
		this.origTranId = origTranId;
		this.refundQueryId = refundQueryId;
		this.goodsOrder = goodsOrder;
		this.price = price;
		this.timeStamp = timeStamp;
		this.sign = sign;
	}
	@Override
	public String toString() {
		return "RefundDto [origTranId=" + origTranId + ", refundQueryId=" + refundQueryId + ", goodsOrder=" + goodsOrder
				+ ", price=" + price + ", timeStamp=" + timeStamp + ", sign=" + sign + "]";
	}
	
}
