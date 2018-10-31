package com.xiaoerzuche.biz.payment.vo;

/** 
* 统一支付接口响应
* @author: cyz
* @version: payment
* @time: 2016-4-12
* 
*/
public class PayResponeVo {
	private int code;
	private String msg;
	/**
	 * 订单id(业务订单)
	 */
	private String orderId;
	/**
	 * 主订单ID
	 */
	private String goodsOrderId;
	/**
	 * 支付流水
	 */
	private String queryId;
	/**
	 * 支付类型
	 */
	private Integer payType;
	/**
	 * 拉起支付的关键信息
	 */
	private String toPayMsg;
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getQueryId() {
		return queryId;
	}
	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}
	public Integer getPayType() {
		return payType;
	}
	public void setPayType(Integer payType) {
		this.payType = payType;
	}
	public String getToPayMsg() {
		return toPayMsg;
	}
	public void setToPayMsg(String toPayMsg) {
		this.toPayMsg = toPayMsg;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public PayResponeVo() {
	}
	public String getGoodsOrderId() {
		return goodsOrderId;
	}
	public void setGoodsOrderId(String goodsOrderId) {
		this.goodsOrderId = goodsOrderId;
	}
	public PayResponeVo(int code, String msg, String orderId, String goodsOrderId, String queryId, Integer payType,
			String toPayMsg) {
		super();
		this.code = code;
		this.msg = msg;
		this.orderId = orderId;
		this.goodsOrderId = goodsOrderId;
		this.queryId = queryId;
		this.payType = payType;
		this.toPayMsg = toPayMsg;
	}
	@Override
	public String toString() {
		return "PayResponeVo [code=" + code + ", msg=" + msg + ", orderId=" + orderId + ", goodsOrderId=" + goodsOrderId
				+ ", queryId=" + queryId + ", payType=" + payType + ", toPayMsg=" + toPayMsg + "]";
	}
	
}
