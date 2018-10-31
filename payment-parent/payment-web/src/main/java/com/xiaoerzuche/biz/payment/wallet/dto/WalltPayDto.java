package com.xiaoerzuche.biz.payment.wallet.dto;

/** 
* 电子钱包支付窗口所需数据
* @author: cyz
* @version: new-energy
* @time: 2016年7月20日
* 
*/
public class WalltPayDto {
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
	/**
	 * 电子钱包预支付订单,支付会话标识
	 */
	private String prepayId;
	/**
	 * 支付金额
	 */
	private Double price;
	/**
	 * 订单描述
	 */
	private String paydetail;
	
	
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getNonceStr() {
		return nonceStr;
	}
	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}
	public String getPaySign() {
		return paySign;
	}
	public void setPaySign(String paySign) {
		this.paySign = paySign;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getPaydetail() {
		return paydetail;
	}
	public void setPaydetail(String paydetail) {
		this.paydetail = paydetail;
	}
	public String getPrepayId() {
		return prepayId;
	}
	public void setPrepayId(String prepayId) {
		this.prepayId = prepayId;
	}
	@Override
	public String toString() {
		return "WalltPayDto [timeStamp=" + timeStamp + ", nonceStr=" + nonceStr + ", paySign=" + paySign + ", prepayId="
				+ prepayId + ", price=" + price + ", paydetail=" + paydetail + "]";
	}
	public WalltPayDto(String timeStamp, String nonceStr, String paySign, String prepayId, Double price,
			String paydetail) {
		super();
		this.timeStamp = timeStamp;
		this.nonceStr = nonceStr;
		this.paySign = paySign;
		this.prepayId = prepayId;
		this.price = price;
		this.paydetail = paydetail;
	}
	public WalltPayDto() {
		super();
	}
	
}
