package com.xiaoerzuche.biz.payment.vo;

/** 
* 退款Vo
* @author: cyz
* @version: payment
* @time: 2016年5月6日
* 
*/
public class RefundVo {
	private String timeStamp;
	private String sign;
	/**
	 * 业务订单号
	 */
	private String orderId;
	private String goodsOrderId;
	/**
	 * 退款金额
	 */
	private Integer price;
	/**
	 * 支付中心消费查询号
	 */
	private String  payQueryId;
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
	public String getPayQueryId() {
		return payQueryId;
	}
	public void setPayQueryId(String payQueryId) {
		this.payQueryId = payQueryId;
	}
	public String getGoodsOrderId() {
		return goodsOrderId;
	}
	public void setGoodsOrderId(String goodsOrderId) {
		this.goodsOrderId = goodsOrderId;
	}
	@Override
	public String toString() {
		return "{timeStamp=;sign=;orderId="+orderId+";goodsOrderId="+goodsOrderId+";price="+price+";payQueryId="+payQueryId+"}";
	}
	
}
