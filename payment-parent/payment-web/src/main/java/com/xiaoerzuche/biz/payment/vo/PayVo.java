package com.xiaoerzuche.biz.payment.vo;

import com.xiaoerzuche.biz.payment.enu.Channel;
import com.xiaoerzuche.biz.payment.enu.ExpenseType;

/** 
* 统一支付请求参数
* @author: cyz
* @version: payment
* @time: 2016-4-12
* 
*/
public class PayVo {
	private String timeStamp;
	private String sign;
	private Integer payType;
	/**
	 * 子订单ID
	 */
	private String orderId;
	/**
	 * 主订单ID
	 */
	private String goodsOrderId;
	/**
	 * 交易金额
	 */
	private Integer price;
	/**
	 * 微信公众号支付的时候需要获取用户openid
	 */
	private String openid;
	/**
	 * 信用卡、电子钱包支付的时候需要获取用户电子钱包账号
	 */
	private String account;
	/**
	 * 证件号（信用卡支付场景必填，用于判断是否绑定信用卡用户）
	 */
	private String idCard;
	/**
	 * 订单超时时间 
	 */
	private String timeExpire;
	
	private ExpenseType expenseType;
	
	private Channel channel;
	/**
	 * 
	 */
	private String body;
	
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
	public Integer getPayType() {
		return payType;
	}
	public void setPayType(Integer payType) {
		this.payType = payType;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public String getGoodsOrderId() {
		return goodsOrderId;
	}
	public void setGoodsOrderId(String goodsOrderId) {
		this.goodsOrderId = goodsOrderId;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getTimeExpire() {
		return timeExpire;
	}
	public void setTimeExpire(String timeExpire) {
		this.timeExpire = timeExpire;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public ExpenseType getExpenseType() {
		return expenseType;
	}
	public void setExpenseType(ExpenseType expenseType) {
		this.expenseType = expenseType;
	}
	public Channel getChannel() {
		return channel;
	}
	public void setChannel(Channel channel) {
		this.channel = channel;
	}
	@Override
	public String toString() {
		return "PayVo [timeStamp=" + timeStamp + ", sign=" + sign + ", payType=" + payType + ", orderId=" + orderId
				+ ", goodsOrderId=" + goodsOrderId + ", price=" + price + ", openid=" + openid + ", account=" + account
				+ ", idCard=" + idCard + ", timeExpire=" + timeExpire + ", expenseType=" + expenseType + ", channel="
				+ channel + ", body=" + body + "]";
	}
	
}
