package com.xiaoerzuche.biz.payment.model;

import java.util.Date;

/** 
* 支付订单
* @author: cyz
* @version: payment
* @time: 2016-4-6
* 
*/
public class PayOrder {

	// 支付订单唯一标示,业务系统跟支付中心的交易单号,
	private String queryId;
	// 订单id,对应业务系统的支付订单号
	private String orderId;
	//商品号业务系统提供(业务系统订单号)
	private String goodsNo;
	// 第三方支付平台交易流水(交易流水)
	private String tranNo;
	//原支付流水号，支付时返回的值，在退款是记录
	private String origTranNo;
	//应用appid,在pay_auth里面维护。
	private String appid;
	// 支付方式(查看枚举PayType)
	private Integer payType;
	// 支付方式名称
	private String payName;
	// 交易金额
	private Integer tranAmount;
	private Integer origTranAmount;
	// 交易类型(查看枚举TranType)
	private Integer tranType;
	//用户登录时的手机号码
	private String account;
	// 交易时间
	private Date tranTime;
	// 交易结果(查看枚举TranStatus)
	private Integer tranStatus;
	private String expenseType;//费用类型
	private String channel;//业务类型时租、日租
	//业务商品订单描述
	private String body;
	//第三方交易响应码+响应消息
	private String comment;
	// 通知标记(0,未通知,1通知)，第三方支付平台是否通知到支付中心
	private Integer notifyFlag;
	// 通知时间
	private Date lastModifyTime;
	
	
	public String getAccount() {
		return account;
	}

	public PayOrder setAccount(String account) {
		this.account = account;
		return this;
	}

	public String getQueryId() {
		return queryId;
	}

	public PayOrder setQueryId(String queryId) {
		this.queryId = queryId;
		return this;
	}

	public String getOrderId() {
		return orderId;
	}

	public PayOrder setOrderId(String orderId) {
		this.orderId = orderId;
		return this;
	}

	public String getTranNo() {
		return tranNo;
	}

	public PayOrder setTranNo(String tranNo) {
		this.tranNo = tranNo;
		return this;
	}

	public String getOrigTranNo() {
		return origTranNo;
	}

	public PayOrder setOrigTranNo(String origTranNo) {
		this.origTranNo = origTranNo;
		return this;
	}

	public String getAppid() {
		return appid;
	}

	public PayOrder setAppid(String appid) {
		this.appid = appid;
		return this;
	}

	public Integer getPayType() {
		return payType;
	}

	public PayOrder setPayType(Integer payType) {
		this.payType = payType;
		return this;
	}

	public String getPayName() {
		return payName;
	}

	public PayOrder setPayName(String payName) {
		this.payName = payName;
		return this;
	}

	public Integer getTranAmount() {
		return tranAmount;
	}

	public PayOrder setTranAmount(Integer tranAmount) {
		this.tranAmount = tranAmount;
		return this;
	}

	public Integer getOrigTranAmount() {
		return origTranAmount;
	}

	public PayOrder setOrigTranAmount(Integer origTranAmount) {
		this.origTranAmount = origTranAmount;
		return this;
	}

	public Integer getTranType() {
		return tranType;
	}

	public PayOrder setTranType(Integer tranType) {
		this.tranType = tranType;
		return this;
	}

	public Date getTranTime() {
		return tranTime;
	}

	public PayOrder setTranTime(Date tranTime) {
		this.tranTime = tranTime;
		return this;
	}

	public Integer getTranStatus() {
		return tranStatus;
	}

	public PayOrder setTranStatus(Integer tranStatus) {
		this.tranStatus = tranStatus;
		return this;
	}

	public Integer getNotifyFlag() {
		return notifyFlag;
	}

	public PayOrder setNotifyFlag(Integer notifyFlag) {
		this.notifyFlag = notifyFlag;
		return this;
	}

	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	public PayOrder setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
		return this;
	}

	public String getGoodsNo() {
		return goodsNo;
	}

	public PayOrder setGoodsNo(String goodsNo) {
		this.goodsNo = goodsNo;
		return this;
	}

	public String getBody() {
		return body;
	}

	public PayOrder setBody(String body) {
		this.body = body;
		return this;
	}

	public String getComment() {
		return comment;
	}

	public PayOrder setComment(String comment) {
		this.comment = comment;
		return this;
	}

	public String getExpenseType() {
		return expenseType;
	}

	public PayOrder setExpenseType(String expenseType) {
		this.expenseType = expenseType;
		return this;
	}

	public String getChannel() {
		return channel;
	}

	public PayOrder setChannel(String channel) {
		this.channel = channel;
		return this;
	}

	public PayOrder(String queryId, String orderId, String goodsNo, String tranNo, String origTranNo, String appid,
			Integer payType, String payName, Integer tranAmount, Integer origTranAmount, Integer tranType,
			Date tranTime, Integer tranStatus, Integer notifyFlag, Date lastModifyTime, String account) {
		super();
		this.queryId = queryId;
		this.orderId = orderId;
		this.goodsNo = goodsNo;
		this.tranNo = tranNo;
		this.origTranNo = origTranNo;
		this.appid = appid;
		this.payType = payType;
		this.payName = payName;
		this.tranAmount = tranAmount;
		this.origTranAmount = origTranAmount;
		this.tranType = tranType;
		this.tranTime = tranTime;
		this.tranStatus = tranStatus;
		this.notifyFlag = notifyFlag;
		this.lastModifyTime = lastModifyTime;
		this.account = account;
	}

	public PayOrder() {
	}

	@Override
	public String toString() {
		return "PayOrder [queryId=" + queryId + ", orderId=" + orderId + ", goodsNo=" + goodsNo + ", tranNo=" + tranNo
				+ ", origTranNo=" + origTranNo + ", appid=" + appid + ", payType=" + payType + ", payName=" + payName
				+ ", tranAmount=" + tranAmount + ", origTranAmount=" + origTranAmount + ", tranType=" + tranType
				+ ", account=" + account + ", tranTime=" + tranTime + ", tranStatus=" + tranStatus + ", expenseType="
				+ expenseType + ", channel=" + channel + ", body=" + body + ", comment=" + comment + ", notifyFlag="
				+ notifyFlag + ", lastModifyTime=" + lastModifyTime + "]";
	}

}
