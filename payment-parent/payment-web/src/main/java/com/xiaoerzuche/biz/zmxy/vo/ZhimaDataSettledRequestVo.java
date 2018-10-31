package com.xiaoerzuche.biz.zmxy.vo;

import com.xiaoerzuche.biz.zmxy.enu.FeedbackTypeEnu;
import com.xiaoerzuche.biz.zmxy.enu.ZmOderStatusEnu;

public class ZhimaDataSettledRequestVo {
	private String orderNo;//业务订单号
	private String subOrderNo;//子业务号
	private Integer payFee;//应付金额
	private String bizDate;//业务记录时间
	private FeedbackTypeEnu typeEnu;//账单类型
	private ZmOderStatusEnu oderStatusEnu;//订单状态
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public Integer getPayFee() {
		return payFee;
	}
	public void setPayFee(Integer payFee) {
		this.payFee = payFee;
	}
	public String getBizDate() {
		return bizDate;
	}
	public void setBizDate(String bizDate) {
		this.bizDate = bizDate;
	}
	public FeedbackTypeEnu getTypeEnu() {
		return typeEnu;
	}
	public void setTypeEnu(FeedbackTypeEnu typeEnu) {
		this.typeEnu = typeEnu;
	}
	public ZmOderStatusEnu getOderStatusEnu() {
		return oderStatusEnu;
	}
	public void setOderStatusEnu(ZmOderStatusEnu oderStatusEnu) {
		this.oderStatusEnu = oderStatusEnu;
	}
	
	public String getSubOrderNo() {
		return subOrderNo;
	}
	public void setSubOrderNo(String subOrderNo) {
		this.subOrderNo = subOrderNo;
	}
	@Override
	public String toString() {
		return "ZhimaDataSettledRequestVo [orderNo=" + orderNo + ", subOrderNo=" + subOrderNo + ", payFee=" + payFee
				+ ", bizDate=" + bizDate + ", typeEnu=" + typeEnu + ", oderStatusEnu=" + oderStatusEnu + "]";
	}
	
}
