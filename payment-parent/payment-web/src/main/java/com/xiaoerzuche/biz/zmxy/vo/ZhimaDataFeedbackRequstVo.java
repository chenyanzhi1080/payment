package com.xiaoerzuche.biz.zmxy.vo;

import com.xiaoerzuche.biz.zmxy.enu.FeedbackTypeEnu;
import com.xiaoerzuche.biz.zmxy.enu.ZmOderStatusEnu;

public class ZhimaDataFeedbackRequstVo {
	private String account;//用户登录手机号
	private String idCard;//身份证号吗
	private String memberName;//用户名
	private String orderNo;//业务订单号
	private String subOrderNo;//子业务号
	private Integer rentFee;//租金,这个是车辆租金
	private Integer depositFee;//押金
	private Integer payFee;//应付金额
	private String carCard;//车牌号
	private String bizDate;//业务记录时间
	private FeedbackTypeEnu typeEnu;
	private ZmOderStatusEnu oderStatusEnu;
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public Integer getDepositFee() {
		return depositFee;
	}
	public void setDepositFee(Integer depositFee) {
		this.depositFee = depositFee;
	}
	public String getCarCard() {
		return carCard;
	}
	public void setCarCard(String carCard) {
		this.carCard = carCard;
	}
	public String getBizDate() {
		return bizDate;
	}
	public void setBizDate(String bizDate) {
		this.bizDate = bizDate;
	}
	public Integer getPayFee() {
		return payFee;
	}
	public void setPayFee(Integer payFee) {
		this.payFee = payFee;
	}
	
	public Integer getRentFee() {
		return rentFee;
	}
	public void setRentFee(Integer rentFee) {
		this.rentFee = rentFee;
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
		return "ZhimaDataFeedbackRequstVo [account=" + account + ", idCard=" + idCard + ", memberName=" + memberName
				+ ", orderNo=" + orderNo + ", subOrderNo=" + subOrderNo + ", rentFee=" + rentFee + ", depositFee="
				+ depositFee + ", payFee=" + payFee + ", carCard=" + carCard + ", bizDate=" + bizDate + ", typeEnu="
				+ typeEnu + ", oderStatusEnu=" + oderStatusEnu + "]";
	}

}
