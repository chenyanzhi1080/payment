package com.xiaoerzuche.biz.payment.vo;

/** 
* 交易查询结果
* @author: cyz
* @version: payment
* @time: 2016年11月15日
* 
*/
public class QueryResultVo {
	//查询结果返回码
	private int code;
	//查询结果返回消息
	private String msg;
	//业务子订单号
	private String orderId;
	//业务主订单
	private String goodsOrderId;
	//支付网关查询号
	private String queryId;
	//第三方交易流水 号
	private String tranNo;
	//交易类型
	private int tranType;
	//原支付方式方式
	private int payType;
	//交易金额
	private int amount;
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
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getGoodsOrderId() {
		return goodsOrderId;
	}
	public void setGoodsOrderId(String goodsOrderId) {
		this.goodsOrderId = goodsOrderId;
	}
	public String getQueryId() {
		return queryId;
	}
	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}
	public String getTranNo() {
		return tranNo;
	}
	public void setTranNo(String tranNo) {
		this.tranNo = tranNo;
	}
	public int getTranType() {
		return tranType;
	}
	public void setTranType(int tranType) {
		this.tranType = tranType;
	}
	public int getPayType() {
		return payType;
	}
	public void setPayType(int payType) {
		this.payType = payType;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public QueryResultVo() {
		super();
	}
	@Override
	public String toString() {
		return "QueryResultVo [code=" + code + ", msg=" + msg + ", orderId=" + orderId + ", goodsOrderId="
				+ goodsOrderId + ", queryId=" + queryId + ", tranNo=" + tranNo + ", tranType=" + tranType + ", payType="
				+ payType + ", amount=" + amount + "]";
	}
	public QueryResultVo(int code, String msg, String orderId, String goodsOrderId, String queryId, String tranNo,
			int tranType, int payType, int amount) {
		super();
		this.code = code;
		this.msg = msg;
		this.orderId = orderId;
		this.goodsOrderId = goodsOrderId;
		this.queryId = queryId;
		this.tranNo = tranNo;
		this.tranType = tranType;
		this.payType = payType;
		this.amount = amount;
	}
	
}
