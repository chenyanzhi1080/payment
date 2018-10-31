package com.xiaoerzuche.biz.payment.dao.sqlBuilder;

import org.apache.commons.lang.StringUtils;

import com.xiaoerzuche.common.core.data.jdbc.StatementParameter;

public class PayTypeConfigBuilder extends BaseQueryBuilder<PayTypeConfigBuilder>{
	private String bzAppid; // 业务appid

	private String terminalAppid; // 终端appid

	private Integer status; // 状态(开启:1，关闭:0)

	public String getBzAppid() {
		return bzAppid;
	}

	public PayTypeConfigBuilder setBzAppid(String bzAppid) {
		this.bzAppid = bzAppid;
		return this;
	}

	public String getTerminalAppid() {
		return terminalAppid;
	}

	public PayTypeConfigBuilder setTerminalAppid(String terminalAppid) {
		this.terminalAppid = terminalAppid;
		return this;
	}

	public Integer getStatus() {
		return status;
	}

	public PayTypeConfigBuilder setStatus(Integer status) {
		this.status = status;
		return this;
	}
	
	public String build(StatementParameter sp,String sql,boolean appendOrderBy) {
		StringBuilder builder = new StringBuilder();
		builder.append(sql);
		builder.append(" where 1=1 ");
		if (StringUtils.isNotBlank(getBzAppid())) {
			builder.append(" and bz_appid=?");
			sp.setString(getBzAppid());
		}
		if (StringUtils.isNotBlank(getTerminalAppid())) {
			builder.append(" and terminal_appid=?");
			sp.setString(getTerminalAppid());
		}
		if(getStatus() != null){
			builder.append(" and status=?");
			sp.setInt(getStatus());
		}
		if (appendOrderBy) {
			builder.append(" order by order_no ");
		}
		return builder.toString();
	}
}
