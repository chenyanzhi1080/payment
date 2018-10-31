package com.xiaoerzuche.biz.zmxy.vo;

public class MemberCreditVo {
	private String id;
	private String idCard;
	private String memberName;
	private Integer creditStatus; // 参考CreditStatus枚举
	private String zmScore;//芝麻信用分
	private String zmScoreLimit;//芝麻分限制分数

	public Integer getCreditStatus() {
		return creditStatus;
	}
	public void setCreditStatus(Integer creditStatus) {
		this.creditStatus = creditStatus;
	}
	public String getZmScore() {
		return zmScore;
	}
	public void setZmScore(String zmScore) {
		this.zmScore = zmScore;
	}
	public String getZmScoreLimit() {
		return zmScoreLimit;
	}
	public void setZmScoreLimit(String zmScoreLimit) {
		this.zmScoreLimit = zmScoreLimit;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	
	public MemberCreditVo() {
		super();
	}
	
	public MemberCreditVo(String id, String idCard, String memberName, Integer creditStatus, String zmScore,
			String zmScoreLimit) {
		super();
		this.id = id;
		this.idCard = idCard;
		this.memberName = memberName;
		this.creditStatus = creditStatus;
		this.zmScore = zmScore;
		this.zmScoreLimit = zmScoreLimit;
	}
	@Override
	public String toString() {
		return "MemberCreditVo [creditStatus=" + creditStatus + ", zmScore=" + zmScore + ", zmScoreLimit="
				+ zmScoreLimit + ", id=" + id + ", idCard=" + idCard + ", memberName=" + memberName + "]";
	}
	
}
