package com.xiaoerzuche.biz.payment.wallet.dto;

/** 
* 消费表单
* @author: cyz
* @version: new-energy
* @time: 2016年7月13日
* 
*/
public class ConsumeDto {
	
	/**
	 * 电子钱包账号
	 */
	private String account;
	/**
	 * 支付网关产生的交易号
	 */
	private String paymentQueryId;
	/**
	 * 业务订单号
	 */
	private String goodsOrder;
	/**
	 * 业务渠道ID
	 */
	private int channelId;
	/**
	 * 消费金额
	 */
	private int price;
	/**
	 * 消费记录备注
	 */
	private String name;
	
	/**
	 * 订单超时时间 
	 */
	private String timeExpire;
	
	//签名相关
	private String timeStamp;
	private String sign;
	
	
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPaymentQueryId() {
		return paymentQueryId;
	}
	public void setPaymentQueryId(String paymentQueryId) {
		this.paymentQueryId = paymentQueryId;
	}
	public int getChannelId() {
		return channelId;
	}
	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTimeExpire() {
		return timeExpire;
	}
	public void setTimeExpire(String timeExpire) {
		this.timeExpire = timeExpire;
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
	
	public String getGoodsOrder() {
		return goodsOrder;
	}
	public void setGoodsOrder(String goodsOrder) {
		this.goodsOrder = goodsOrder;
	}
	public ConsumeDto() {
		super();
	}
	@Override
	public String toString() {
		return "ConsumeDto [account=" + account + ", paymentQueryId=" + paymentQueryId + ", goodsOrder=" + goodsOrder
				+ ", channelId=" + channelId + ", price=" + price + ", name=" + name + ", timeExpire=" + timeExpire
				+ ", timeStamp=" + timeStamp + ", sign=" + sign + "]";
	}
	
}
