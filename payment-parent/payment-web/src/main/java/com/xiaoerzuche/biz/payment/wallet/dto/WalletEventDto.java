package com.xiaoerzuche.biz.payment.wallet.dto;

import java.util.Date;

/** 
* 电子钱包交易事件
* @author: cyz
* @version: new-energy
* @time: 2016年7月22日
* 
*/
public class WalletEventDto {
	/**
	 * 交易流水
	 */
	private String tranId;
	/**
	 * 支付网关查询号
	 */
	private String paymentQueryId;
	/**
	 * 交易类型
	 */
	private int type;
	/**
	 * 时间代码，参考PaymentEnum
	 */
	private int code;
	/**
	 * 事件结果描述
	 */
	private String detail;
	/**
	 * 最近修改时间
	 */
	private Date lastModifyDate;
	public String getTranId() {
		return tranId;
	}
	public void setTranId(String tranId) {
		this.tranId = tranId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Date getLastModifyDate() {
		return lastModifyDate;
	}
	public void setLastModifyDate(Date lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
	}
	public String getPaymentQueryId() {
		return paymentQueryId;
	}
	public void setPaymentQueryId(String paymentQueryId) {
		this.paymentQueryId = paymentQueryId;
	}
	public WalletEventDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "WalletEventDto [tranId=" + tranId + ", paymentQueryId=" + paymentQueryId + ", type=" + type + ", code="
				+ code + ", detail=" + detail + ", lastModifyDate=" + lastModifyDate + "]";
	}
	
}
