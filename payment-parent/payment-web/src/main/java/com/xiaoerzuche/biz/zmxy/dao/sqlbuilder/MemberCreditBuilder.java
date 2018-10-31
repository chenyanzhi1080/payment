package com.xiaoerzuche.biz.zmxy.dao.sqlbuilder;

import org.apache.commons.lang.StringUtils;
import com.xiaoerzuche.common.core.data.jdbc.StatementParameter;
import com.xiaoerzuche.common.util.DateTimeUtil;

public class MemberCreditBuilder extends BaseQueryBuilder<MemberCreditBuilder>{
	private String account;
	private String cardId;
	private Integer authStatus;
	private String authTimeEnd;
	private String authTimeStart;
	private boolean hasOpenid;
	private String riskCode;
	
	public boolean isHasOpenid() {
		return hasOpenid;
	}
	public MemberCreditBuilder setHasOpenid(boolean hasOpenid) {
		this.hasOpenid = hasOpenid;
		return this;
	}
	public String getAccount() {
		return account;
	}
	public MemberCreditBuilder setAccount(String account) {
		this.account = account;
		return this;
	}
	public String getCardId() {
		return cardId;
	}
	public MemberCreditBuilder setCardId(String cardId) {
		this.cardId = cardId;
		return this;
	}
	public String getAuthTimeEnd() {
		return authTimeEnd;
	}
	public MemberCreditBuilder setAuthTimeEnd(String authTimeEnd) {
		this.authTimeEnd = authTimeEnd;
		return this;
	}
	public String getAuthTimeStart() {
		return authTimeStart;
	}
	public MemberCreditBuilder setAuthTimeStart(String authTimeStart) {
		this.authTimeStart = authTimeStart;
		return this;
	}
	
	public Integer getAuthStatus() {
		return authStatus;
	}
	public MemberCreditBuilder setAuthStatus(Integer authStatus) {
		this.authStatus = authStatus;
		return this;
	}
	public String getRiskCode() {
		return riskCode;
	}
	public MemberCreditBuilder setRiskCode(String riskCode) {
		this.riskCode = riskCode;
		return this;
	}
	public String build(StatementParameter sp,String sql,boolean appendOrderBy) {
		StringBuilder builder = new StringBuilder();
		builder.append(sql);
		if(StringUtils.isNotBlank(getAccount())){
			builder.append(" and account=? ");
			sp.setString(account);
		}
		if(StringUtils.isNotBlank(getCardId())){
			builder.append(" and card_id=? ");
			sp.setString(cardId);
		}
		if(null != getAuthStatus() && getAuthStatus()>0){
			builder.append(" and auth_status=? ");
			sp.setInt(authStatus);
		}
		if (StringUtils.isNotBlank(getAuthTimeStart())) {
			builder.append(" and auth_change_time >= ? ");
			sp.setDate(DateTimeUtil.parseYYYYMMddHHmmss(getAuthTimeStart()));
		}
		if (StringUtils.isNotBlank(getAuthTimeEnd())) {
			builder.append(" and auth_change_time <= ? ");
			sp.setDate(DateTimeUtil.parseYYYYMMddHHmmss(getAuthTimeEnd()));
		}
		if(isHasOpenid()){
			builder.append(" and zmopen_id is not null and zmopen_id <> '' ");
		}
		if(StringUtils.isNotBlank(getRiskCode())){
			builder.append(" and zm_details like '%\"bizCode\":\""+getRiskCode()+"\"%' ");
		}
		if(appendOrderBy){
			builder.append(" ORDER BY last_modify_time desc ");
		}
		return builder.toString();
	}
	@Override
	public String toString() {
		return "MemberCreditBuilder [account=" + account + ", cardId=" + cardId + ", authTimeEnd=" + authTimeEnd
				+ ", authTimeStart=" + authTimeStart + "]";
	}
	
}
