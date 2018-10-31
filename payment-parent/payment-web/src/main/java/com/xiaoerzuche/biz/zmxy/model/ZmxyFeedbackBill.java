package com.xiaoerzuche.biz.zmxy.model;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.xiaoerzuche.common.util.JsonUtil;

/**
 * 芝麻信用数据反馈账单
 * 
 * @author: cyz
 * @version: payment-web
 * @time: 2017年8月25日
 * 
 */
public class ZmxyFeedbackBill {

	private String billNo; // 账单号,主键

	private Date bizDate; // 数据统计日期

	private String userCredentialsType; // 证件类型,默认0身份证

	private String userCredentialsNo; // 证件号码

	private String userName; // 姓名

	private String orderNo; // 业务号
	
	private String subOrderNo;//子业务号

	private Integer orderStatus; // 当前业务状态，3-开始使用0-正常（有账单待支付）1-违约（有账单逾期未结清费用）2-结清（使用结束并结清费用）4-提前终止8-预约

	private Date orderStartDate; // 业务开始时间

	private String objectName; // 物品名称

	private String objectId; // 物品标识

	private String rentDesc; // 租金描述

	private Integer depositAmt; // 押金

	private Integer billStatus; // 账单状态，0-正常1-违约2-完结

	private Integer billType; // 账单类型，100-租金未缴,200-车辆未还,508-违规停车(自行车专用),509-违章费用,510-维修费用

	private Integer billAmt; // 账单应还金额

	private Date billLastDate; // 账单应还款日

	private Date billPayoffDate; // 账单完结时间

	private Date createTime; // 创建时间

	private Date lastModifyTime; // 最近修改时间
	
	private Date uploadTime;//N+1上传的时间节点
	
	private Integer isUpload;//是否已N+1上传
	
	public ZmxyFeedbackBill setBizDate(Date bizDate) {
		this.bizDate = bizDate;
		return this;
	}

	public Date getBizDate() {
		return bizDate;
	}

	public ZmxyFeedbackBill setUserCredentialsType(String userCredentialsType) {
		this.userCredentialsType = userCredentialsType;
		return this;
	}

	public String getUserCredentialsType() {
		return userCredentialsType;
	}

	public ZmxyFeedbackBill setUserCredentialsNo(String userCredentialsNo) {
		if(StringUtils.isNotBlank(userCredentialsNo)){
			userCredentialsNo = userCredentialsNo.toUpperCase();
		}
		this.userCredentialsNo = userCredentialsNo;
		return this;
	}

	public String getUserCredentialsNo() {
		return userCredentialsNo;
	}

	public ZmxyFeedbackBill setUserName(String userName) {
		this.userName = userName;
		return this;
	}

	public String getUserName() {
		return userName;
	}

	public ZmxyFeedbackBill setOrderNo(String orderNo) {
		this.orderNo = orderNo;
		return this;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public String getSubOrderNo() {
		return subOrderNo;
	}

	public ZmxyFeedbackBill setSubOrderNo(String subOrderNo) {
		this.subOrderNo = subOrderNo;
		return this;
	}

	public ZmxyFeedbackBill setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
		return this;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public ZmxyFeedbackBill setOrderStartDate(Date orderStartDate) {
		this.orderStartDate = orderStartDate;
		return this;
	}

	public Date getOrderStartDate() {
		return orderStartDate;
	}

	public ZmxyFeedbackBill setObjectName(String objectName) {
		this.objectName = objectName;
		return this;
	}

	public String getObjectName() {
		return objectName;
	}

	public ZmxyFeedbackBill setObjectId(String objectId) {
		this.objectId = objectId;
		return this;
	}

	public String getObjectId() {
		return objectId;
	}

	public ZmxyFeedbackBill setRentDesc(String rentDesc) {
		this.rentDesc = rentDesc;
		return this;
	}

	public String getRentDesc() {
		return rentDesc;
	}

	public ZmxyFeedbackBill setDepositAmt(Integer depositAmt) {
		this.depositAmt = depositAmt;
		return this;
	}

	public Integer getDepositAmt() {
		return depositAmt;
	}

	public ZmxyFeedbackBill setBillNo(String billNo) {
		this.billNo = billNo;
		return this;
	}

	public String getBillNo() {
		return billNo;
	}

	public ZmxyFeedbackBill setBillStatus(Integer billStatus) {
		this.billStatus = billStatus;
		return this;
	}

	public Integer getBillStatus() {
		return billStatus;
	}

	public ZmxyFeedbackBill setBillType(Integer billType) {
		this.billType = billType;
		return this;
	}

	public Integer getBillType() {
		return billType;
	}

	public ZmxyFeedbackBill setBillAmt(Integer billAmt) {
		this.billAmt = billAmt;
		return this;
	}

	public Integer getBillAmt() {
		return billAmt;
	}

	public ZmxyFeedbackBill setBillLastDate(Date billLastDate) {
		this.billLastDate = billLastDate;
		return this;
	}

	public Date getBillLastDate() {
		return billLastDate;
	}

	public ZmxyFeedbackBill setBillPayoffDate(Date billPayoffDate) {
		this.billPayoffDate = billPayoffDate;
		return this;
	}

	public Date getBillPayoffDate() {
		return billPayoffDate;
	}

	public ZmxyFeedbackBill setCreateTime(Date createTime) {
		this.createTime = createTime;
		return this;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public ZmxyFeedbackBill setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
		return this;
	}

	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	public Date getUploadTime() {
		return uploadTime;
	}

	public ZmxyFeedbackBill setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
		return this;
	}

	public Integer getIsUpload() {
		return isUpload;
	}

	public ZmxyFeedbackBill setIsUpload(Integer isUpload) {
		this.isUpload = isUpload;
		return this;
	}

	public ZmxyFeedbackBill() {
		super();
	}

	@Override
	public String toString() {
		return "ZmxyFeedbackBill [billNo=" + billNo + ", bizDate=" + bizDate + ", userCredentialsType="
				+ userCredentialsType + ", userCredentialsNo=" + userCredentialsNo + ", userName=" + userName
				+ ", orderNo=" + orderNo + ", subOrderNo=" + subOrderNo + ", orderStatus=" + orderStatus
				+ ", orderStartDate=" + orderStartDate + ", objectName=" + objectName + ", objectId=" + objectId
				+ ", rentDesc=" + rentDesc + ", depositAmt=" + depositAmt + ", billStatus=" + billStatus + ", billType="
				+ billType + ", billAmt=" + billAmt + ", billLastDate=" + billLastDate + ", billPayoffDate="
				+ billPayoffDate + ", createTime=" + createTime + ", lastModifyTime=" + lastModifyTime + ", uploadTime="
				+ uploadTime + ", isUpload=" + isUpload + "]";
	}

}
