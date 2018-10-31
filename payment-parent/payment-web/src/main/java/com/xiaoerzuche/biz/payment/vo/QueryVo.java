package com.xiaoerzuche.biz.payment.vo;

/** 
* 一句话描述
* @author: cyz
* @version: payment
* @time: 2016年5月9日
* 
*/
public class QueryVo {
	private String appid;
	private String orderId;
	private String queryId;
	private Integer tranType;
	private Integer paytype;
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
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
	public Integer getTranType() {
		return tranType;
	}
	public void setTranType(Integer tranType) {
		this.tranType = tranType;
	}
	public Integer getPaytype() {
		return paytype;
	}
	public void setPaytype(Integer paytype) {
		this.paytype = paytype;
	}
	public QueryVo() {
		super();
	}
	public QueryVo(String appid, String orderId, String queryId, Integer tranType, Integer paytype) {
		super();
		this.appid = appid;
		this.orderId = orderId;
		this.queryId = queryId;
		this.tranType = tranType;
		this.paytype = paytype;
	}
	@Override
	public String toString() {
		return "QueryVo [appid=" + appid + ", orderId=" + orderId + ", queryId=" + queryId + ", tranType=" + tranType
				+ ", paytype=" + paytype + "]";
	}
	
}
