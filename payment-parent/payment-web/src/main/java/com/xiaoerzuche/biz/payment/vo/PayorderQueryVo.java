package com.xiaoerzuche.biz.payment.vo;

import com.xiaoerzuche.biz.payment.dao.builder.PayorderBuilder;

public class PayorderQueryVo {
	private  Integer payTypeCode; //交易方式
	private Integer channel; //业务渠道（时租、日租、电桩、电子钱包）
	private Integer expenseTypeCode; //费用类型
	private  String goodsNo; //业务主订单号
	private  String queryId; //
	private  String account; //用户账号（登陆手机号
	private Integer offset; //页码
	private  Integer limit; //页长
	private  String timeStart;//交易时间范围起始
	private  String timeEnd; //交易时间范围结束
	public Integer getPayTypeCode() {
		return payTypeCode;
	}
	public void setPayTypeCode(Integer payTypeCode) {
		this.payTypeCode = payTypeCode;
	}
	public Integer getChannel() {
		return channel;
	}
	public void setChannel(Integer channel) {
		this.channel = channel;
	}
	public Integer getExpenseTypeCode() {
		return expenseTypeCode;
	}
	public void setExpenseTypeCode(Integer expenseTypeCode) {
		this.expenseTypeCode = expenseTypeCode;
	}
	public String getGoodsNo() {
		return goodsNo;
	}
	public void setGoodsNo(String goodsNo) {
		this.goodsNo = goodsNo;
	}
	public String getQueryId() {
		return queryId;
	}
	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public Integer getOffset() {
		return offset;
	}
	public void setOffset(Integer offset) {
		this.offset = offset;
	}
	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	public String getTimeStart() {
		return timeStart;
	}
	public void setTimeStart(String timeStart) {
		this.timeStart = timeStart;
	}
	public String getTimeEnd() {
		return timeEnd;
	}
	public void setTimeEnd(String timeEnd) {
		this.timeEnd = timeEnd;
	}
	@Override
	public String toString() {
		return "PayorderQueryVo [payTypeCode=" + payTypeCode + ", channel=" + channel + ", expenseTypeCode="
				+ expenseTypeCode + ", goodsNo=" + goodsNo + ", queryId=" + queryId + ", account=" + account
				+ ", offset=" + offset + ", limit=" + limit + ", timeStart=" + timeStart + ", timeEnd=" + timeEnd + "]";
	}
	
	public PayorderBuilder builder(){
		PayorderBuilder builder = new PayorderBuilder();
		builder.setAccount(this.account)
		.setChannel(this.channel)
		.setExpenseTypeCode(this.expenseTypeCode)
		.setGoodsNo(this.goodsNo)
		.setPayTypeCode(this.payTypeCode)
		.setQueryId(this.queryId)
		.setTimeEnd(this.timeEnd)
		.setTimeStart(this.timeStart)
		.offset(this.offset)
		.limit(this.limit);
		return builder;
	}
}
