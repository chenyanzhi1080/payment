package com.xiaoerzuche.biz.payment.dao.builder;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;

import com.xiaoerzuche.biz.payment.enu.Channel;
import com.xiaoerzuche.biz.payment.enu.ExpenseType;
import com.xiaoerzuche.biz.payment.enu.PayType;
import com.xiaoerzuche.biz.payment.enu.TranStatus;

public class PayorderBuilder extends BaseQueryBuilder<PayorderBuilder> {
	private Integer tranType;//交易类型：1支付;10退款
	private Integer payTypeCode; // 交易方式：支付宝、微信、银联。参考PayType枚举
	private Integer channel; // 业务渠道（时租、日租、电桩、电子钱包）
	private Integer expenseTypeCode; // 费用类型
	private String goodsNo; // 业务主订单号
	private String queryId; //
	private String account; // 用户账号（登陆手机号
	private String timeStart;// 交易时间范围起始
	private String timeEnd; // 交易时间范围结束

	public Integer getTranType() {
		return tranType;
	}

	public PayorderBuilder setTranType(Integer tranType) {
		this.tranType = tranType;
		return this;
	}

	public Integer getPayTypeCode() {
		return payTypeCode;
	}

	public PayorderBuilder setPayTypeCode(Integer payTypeCode) {
		this.payTypeCode = payTypeCode;
		return this;
	}

	public Integer getChannel() {
		return channel;
	}

	public PayorderBuilder setChannel(Integer channel) {
		this.channel = channel;
		return this;
	}

	public Integer getExpenseTypeCode() {
		return expenseTypeCode;
	}

	public PayorderBuilder setExpenseTypeCode(Integer expenseTypeCode) {
		this.expenseTypeCode = expenseTypeCode;
		return this;
	}

	public String getGoodsNo() {
		return goodsNo;
	}

	public PayorderBuilder setGoodsNo(String goodsNo) {
		this.goodsNo = goodsNo;
		return this;
	}

	public String getQueryId() {
		return queryId;
	}

	public PayorderBuilder setQueryId(String queryId) {
		this.queryId = queryId;
		return this;
	}

	public String getAccount() {
		return account;
	}

	public PayorderBuilder setAccount(String account) {
		this.account = account;
		return this;
	}

	public String getTimeStart() {
		return timeStart;
	}

	public PayorderBuilder setTimeStart(String timeStart) {
		this.timeStart = timeStart;
		return this;
	}

	public String getTimeEnd() {
		return timeEnd;
	}

	public PayorderBuilder setTimeEnd(String timeEnd) {
		this.timeEnd = timeEnd;
		return this;
	}
	
	public String build(String table,String sql,boolean appendOrderBy) {
		// 确定分表
		String tableName = "";// 当前分表名称
		String crossTableName = "";// 跨表名称
		// 无时间维度查询
		if ( !StringUtils.isNotBlank(getTimeStart()) && !StringUtils.isNotBlank(getTimeEnd())) {
			tableName = table + DateFormatUtils.format(new Date(), "yyyy");
		}else if (StringUtils.isNotBlank(getTimeStart())  && StringUtils.isNotBlank(getTimeEnd())) {//按时间维度查询
			String yearStart = getTimeStart().split("-")[0];
			String yearEnd = getTimeEnd().split("-")[0];
			if(yearStart.contentEquals(yearEnd)){//同一年
				tableName = table + yearEnd;
			}else{//跨年
				tableName = table + yearEnd;
				crossTableName = table + yearStart;
			}
		}
		StringBuffer whereSql = new StringBuffer();
		whereSql.append(" where (tran_status = ")
		.append(TranStatus.SUCCESS.getCode())
		.append(" or tran_status=").append(TranStatus.TRADE_FINISHED.getCode())
		.append(") and tran_type = ").append(tranType);
		
		if (null != getChannel()) {
			whereSql.append(" and channel = '").append(Channel.getByCode(getChannel()).getValue()).append("'");
		}
		if(null != getExpenseTypeCode() && getExpenseTypeCode() > 0 ){
			whereSql.append(" and expense_type = '").append(ExpenseType.getByCode(getExpenseTypeCode()).getValue()).append("'");
		}
		if(null != getPayTypeCode() && getPayTypeCode() > 0 ){
			whereSql.append(" and pay_type = '").append(PayType.getByCode(getPayTypeCode()).getCode()).append("'");
		}
		if (StringUtils.isNotBlank(goodsNo)) {
			whereSql.append(" and goods_no = '").append(goodsNo).append("'");
		}
		if (StringUtils.isNotBlank(getAccount())) {
			whereSql.append(" and account = '").append(getAccount()).append("'");
		}
		if(StringUtils.isNotBlank(getQueryId())){
			whereSql.append(" and query_id = '").append(getQueryId()).append("'");
		}
		
		if ( StringUtils.isNotBlank(getTimeStart()) && StringUtils.isNotBlank(getTimeEnd())) {
			whereSql.append(" and DATE(last_modify_time) >='")
					.append(getTimeStart()).append("' ")
					.append(" and DATE(last_modify_time) <='")
					.append(getTimeEnd())
					.append("' ");
		}
		
		StringBuilder builder = new StringBuilder();
		builder.append(sql).append(" ( ");
		builder.append("select * from ").append(tableName).append(whereSql.toString());
		if (StringUtils.isNotBlank(crossTableName) && !crossTableName.equals(tableName)) {
			builder.append(" UNION select * from ").append(crossTableName).append(whereSql.toString());
		}
		if(appendOrderBy){
			builder.append(" ORDER BY last_modify_time desc ");
		}
		builder.append(" ) as a"); 
		return builder.toString();
	}

	@Override
	public String toString() {
		return "PayorderBuilder [tranType=" + tranType + ", payTypeCode=" + payTypeCode + ", channel=" + channel
				+ ", expenseTypeCode=" + expenseTypeCode + ", goodsNo=" + goodsNo + ", queryId=" + queryId
				+ ", account=" + account + ", timeStart=" + timeStart + ", timeEnd=" + timeEnd + "]";
	}
	public static void main(String[] args){
		PayorderBuilder builder = new PayorderBuilder();
		builder
		.setAccount("18643689298")
		.setChannel(0)
		.setExpenseTypeCode(0)
		.setGoodsNo("08151473454210430491")
		.setPayTypeCode(8)
		.setTranType(1)
		.setQueryId("15147360071533137")
		.setTimeStart("2018-01-01")
		.setTimeEnd("2018-01-15")
		.offset(0)
		.limit(10)
		;
		String TABLE = "pay_order_";
		
		System.out.println(builder.build(TABLE, " select count(1) from ", false));
	}
}
