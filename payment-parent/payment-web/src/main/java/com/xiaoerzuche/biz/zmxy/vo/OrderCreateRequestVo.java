package com.xiaoerzuche.biz.zmxy.vo;

import com.xiaoerzuche.biz.zmxy.enu.FeedbackTypeEnu;
import com.xiaoerzuche.biz.zmxy.enu.ZmOderStatusEnu;

/** 
* 芝麻信用数据反馈：业务订单开始使用
* @author: cyz
* @version: payment-web
* @time: 2018年1月3日
* 
*/
public class OrderCreateRequestVo {
	private String account;//用户登录手机号
	private String idCard;//身份证号吗
	private String memberName;//用户名
	private String orderNo;//车辆业务订单号
	private String carCard;//车牌号
	private Integer rentFee;//租金,这个是车辆租金
	private Integer depositFee;//押金
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
	public String getCarCard() {
		return carCard;
	}
	public void setCarCard(String carCard) {
		this.carCard = carCard;
	}
	public Integer getRentFee() {
		return rentFee;
	}
	public void setRentFee(Integer rentFee) {
		this.rentFee = rentFee;
	}
	public Integer getDepositFee() {
		return depositFee;
	}
	public void setDepositFee(Integer depositFee) {
		this.depositFee = depositFee;
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
	@Override
	public String toString() {
		return "OrderCreateRequestVo [account=" + account + ", idCard=" + idCard + ", memberName=" + memberName
				+ ", orderNo=" + orderNo + ", carCard=" + carCard + ", rentFee=" + rentFee + ", depositFee="
				+ depositFee + ", bizDate=" + bizDate + ", typeEnu=" + typeEnu + ", oderStatusEnu=" + oderStatusEnu
				+ "]";
	}
	
}
