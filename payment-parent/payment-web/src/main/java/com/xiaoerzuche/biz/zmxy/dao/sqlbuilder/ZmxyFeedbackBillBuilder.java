package com.xiaoerzuche.biz.zmxy.dao.sqlbuilder;

import org.apache.commons.lang.StringUtils;

import com.xiaoerzuche.common.core.data.jdbc.StatementParameter;

public class ZmxyFeedbackBillBuilder extends BaseQueryBuilder<ZmxyFeedbackBillBuilder>{
	private String billNo; // 账单号,主键

	private String orderNo; // 业务号
	
	private String subOrderNo;//子业务号

	private Integer orderStatus; // 当前业务状态，3-开始使用0-正常（有账单待支付）1-违约（有账单逾期未结清费用）2-结清（使用结束并结清费用）4-提前终止8-预约

	private Integer billStatus; // 账单状态，0-正常1-违约2-完结

	private Integer billType; // 账单类型，100-租金未缴,200-车辆未还,508-违规停车(自行车专用),509-违章费用,510-维修费用

	public String getBillNo() {
		return billNo;
	}

	public ZmxyFeedbackBillBuilder setBillNo(String billNo) {
		this.billNo = billNo;
		return this;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public ZmxyFeedbackBillBuilder setOrderNo(String orderNo) {
		this.orderNo = orderNo;
		return this;
	}
	
	public String getSubOrderNo() {
		return subOrderNo;
	}

	public ZmxyFeedbackBillBuilder setSubOrderNo(String subOrderNo) {
		this.subOrderNo = subOrderNo;
		return this;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public ZmxyFeedbackBillBuilder setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
		return this;
	}

	public Integer getBillStatus() {
		return billStatus;
	}

	public ZmxyFeedbackBillBuilder setBillStatus(Integer billStatus) {
		this.billStatus = billStatus;
		return this;
	}

	public Integer getBillType() {
		return billType;
	}

	public ZmxyFeedbackBillBuilder setBillType(Integer billType) {
		this.billType = billType;
		return this;
	}
	
	public String build(StatementParameter sp,String sql,boolean appendOrderBy) {
		StringBuilder builder = new StringBuilder();
		builder.append(sql);
		if(StringUtils.isNotBlank(getBillNo())){
			builder.append(" and bill_no=? ");
			sp.setString(getOrderNo());
		}
		if(StringUtils.isNotBlank(getOrderNo())){
			builder.append(" and order_no=? ");
			sp.setString(getOrderNo());
		}
		if(StringUtils.isNotBlank(getSubOrderNo())){
			builder.append(" and sub_order_no=? ");
			sp.setString(getSubOrderNo());
		}
		if(null!=getOrderStatus()){
			builder.append(" and order_status=? ");
			sp.setInt(getOrderStatus());
		}
		if(null!=getBillStatus()){
			builder.append(" and bill_status=? ");
			sp.setInt(getBillStatus());
		}
		if(null!=getBillStatus()){
			builder.append(" and bill_status=? ");
			sp.setInt(getBillStatus());
		}
		if(null!=getBillType() && getBillType()>0){
			builder.append(" and bill_type=? ");
			sp.setInt(getBillType());
		}
		if(appendOrderBy){
			builder.append(" ORDER BY last_modify_time desc ");
		}
		return builder.toString();
	}
	
	@Override
	public String toString() {
		return "ZmxyFeedbackBillBuilder [billNo=" + billNo + ", orderNo=" + orderNo + ", orderStatus=" + orderStatus
				+ ", billStatus=" + billStatus + ", billType=" + billType + "]";
	}
	
}
