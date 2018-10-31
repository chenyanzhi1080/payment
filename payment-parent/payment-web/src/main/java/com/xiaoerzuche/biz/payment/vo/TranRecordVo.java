package com.xiaoerzuche.biz.payment.vo;

/**
 * 交易记录VO
 * 
 * @author: cyz
 * @version: payment-web
 * @time: 2016年12月14日
 * 
 */
public class TranRecordVo {
	// 支付订单唯一标示,业务系统跟支付中心的交易单号,
	private String queryId;
	// 商品号业务系统提供(业务系统订单号)
	private String goodsNo;
	// 第三方支付平台交易流水(交易流水)
	private String tranNo;
	// 交易金额
	private String amount;
	//业务系统名称
	private String channelName;
	//支付方式名称
	private String payTypeName;
	//商户名称
	private String merchant;
	// 交易结果描述(第三方返回码或返回码+描述)
	private String comment;
	// 通知时间
	private long time;
	//手机号码
	private String account;
	//费用类型名称
	private String expenseTypeName;
	
	public String getQueryId() {
		return queryId;
	}
	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}
	public String getGoodsNo() {
		return goodsNo;
	}
	public void setGoodsNo(String goodsNo) {
		this.goodsNo = goodsNo;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public String getTranNo() {
		return tranNo;
	}
	public void setTranNo(String tranNo) {
		this.tranNo = tranNo;
	}
	public String getPayTypeName() {
		return payTypeName;
	}
	public void setPayTypeName(String payTypeName) {
		this.payTypeName = payTypeName;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public String getMerchant() {
		return merchant;
	}
	public void setMerchant(String merchant) {
		this.merchant = merchant;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getExpenseTypeName() {
		return expenseTypeName;
	}
	public void setExpenseTypeName(String expenseTypeName) {
		this.expenseTypeName = expenseTypeName;
	}
	public TranRecordVo() {
		super();
	}
	@Override
	public String toString() {
		return "TranRecordVo [queryId=" + queryId + ", goodsNo=" + goodsNo + ", tranNo=" + tranNo + ", amount=" + amount
				+ ", channelName=" + channelName + ", payTypeName=" + payTypeName + ", merchant=" + merchant
				+ ", comment=" + comment + ", time=" + time + ", account=" + account + ", expenseTypeName="
				+ expenseTypeName + "]";
	}
	public TranRecordVo(String queryId, String goodsNo, String tranNo, String amount, String channelName,
			String payTypeName, String merchant, String comment, long time, String account, String expenseTypeName) {
		super();
		this.queryId = queryId;
		this.goodsNo = goodsNo;
		this.tranNo = tranNo;
		this.amount = amount;
		this.channelName = channelName;
		this.payTypeName = payTypeName;
		this.merchant = merchant;
		this.comment = comment;
		this.time = time;
		this.account = account;
		this.expenseTypeName = expenseTypeName;
	}

	
}
