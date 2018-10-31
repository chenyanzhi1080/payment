package com.xiaoerzuche.biz.zmxy.dao.sqlbuilder;

import org.apache.commons.lang.StringUtils;

import com.xiaoerzuche.biz.zmxy.enu.AntifraudScoreRiskEnu;
import com.xiaoerzuche.common.core.data.jdbc.StatementParameter;
import com.xiaoerzuche.common.util.DateTimeUtil;

public class MemberAntifraudBuilder extends BaseQueryBuilder<MemberAntifraudBuilder>{
	private String account;//用户登录手机号	
	private String idCard;//身份证号
	private Integer hit;//欺诈关注清单是否命中（1命中，2未命中）
	private String name;//会员姓名
	private String scoreRisk;//欺诈分风险级别
	private String registerCity;//注册城市
	private String registerTimeEnd;//注册时间结束
	private String registerTimeStart;//注册时间开始
	
	public String getAccount() {
		return account;
	}
	public MemberAntifraudBuilder setAccount(String account) {
		this.account = account;
		return this;
	}
	public String getIdCard() {
		return idCard;
	}
	public MemberAntifraudBuilder setIdCard(String idCard) {
		this.idCard = idCard;
		return this;
	}
	public Integer getHit() {
		return hit;
	}
	public MemberAntifraudBuilder setHit(Integer hit) {
		this.hit = hit;
		return this;
	}
	public String getName() {
		return name;
	}
	public MemberAntifraudBuilder setName(String name) {
		this.name = name;
		return this;
	}
	public String getScoreRisk() {
		return scoreRisk;
	}
	public MemberAntifraudBuilder setScoreRisk(String scoreRisk) {
		this.scoreRisk = scoreRisk;
		return this;
	}
	public String getRegisterCity() {
		return registerCity;
	}
	public MemberAntifraudBuilder setRegisterCity(String registerCity) {
		this.registerCity = registerCity;
		return this;
	}
	public String getRegisterTimeEnd() {
		return registerTimeEnd;
	}
	public MemberAntifraudBuilder setRegisterTimeEnd(String registerTimeEnd) {
		this.registerTimeEnd = registerTimeEnd;
		return this;
	}
	public String getRegisterTimeStart() {
		return registerTimeStart;
	}
	public MemberAntifraudBuilder setRegisterTimeStart(String registerTimeStart) {
		this.registerTimeStart = registerTimeStart;
		return this;
	}
	
	public String build(StatementParameter sp,String sql,boolean appendOrderBy){
		StringBuilder builder = new StringBuilder();
		builder.append(sql);
		if(StringUtils.isNotBlank(getAccount())){
			builder.append(" and account=? ");
			sp.setString(getAccount());
		}
		if(StringUtils.isNotBlank(getIdCard())){
			builder.append(" and id_card=? ");
			sp.setString(getIdCard());
		}
		if(null != getHit() && getHit()>0){
			builder.append(" and hit=? ");
			sp.setInt(getHit());
		}
		if(StringUtils.isNotBlank(getName())){
			builder.append(" and name=? ");
			sp.setString(getName());
		}
		if(StringUtils.isNotBlank(getScoreRisk())){
			AntifraudScoreRiskEnu enu = AntifraudScoreRiskEnu.getByEnumName(getScoreRisk());
			switch (enu) {
			case UNKNOWN:
				builder.append(" and antifraud_score=? ");
				sp.setInt(AntifraudScoreRiskEnu.UNKNOWN.getScore());
				break;
			case HIGH:
				builder.append(" and (antifraud_score>=? and antifraud_score<=?) ");
				sp.setInt(AntifraudScoreRiskEnu.HIGH.getScore());
				sp.setInt(AntifraudScoreRiskEnu.MEDIUM.getScore());
				break;
			case MEDIUM:
				builder.append(" and (antifraud_score>=? and antifraud_score<?) ");
				sp.setInt(AntifraudScoreRiskEnu.MEDIUM.getScore());
				sp.setInt(AntifraudScoreRiskEnu.LOW.getScore());
				break;	
			case LOW:
				builder.append(" and antifraud_score>? ");
				sp.setInt(AntifraudScoreRiskEnu.LOW.getScore());
				break;					
			default:
				break;
			}
			
		}
		if(StringUtils.isNotBlank(getRegisterCity())){
			builder.append(" and registerCity=? ");
			sp.setString(getRegisterCity());
		}
		if(StringUtils.isNotBlank(getRegisterTimeEnd())){
			builder.append(" and registerTimeEnd=? ");
			sp.setDate(DateTimeUtil.parseYYYYMMddHHmmss(getRegisterTimeEnd()));
		}
		if(StringUtils.isNotBlank(getRegisterTimeStart())){
			builder.append(" and registerTimeStart=? ");
			sp.setDate(DateTimeUtil.parseYYYYMMddHHmmss(getRegisterTimeStart()));
		}
		if(appendOrderBy){
			builder.append(" ORDER BY last_modify_time desc ");
		}
		
		return builder.toString();
	}
	
}
