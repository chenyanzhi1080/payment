package com.xiaoerzuche.biz.zmxy.vo.antifraud;

public class MemberOutsideVo {
	private String memberId;
	private String idCard;
	private String memberName;
	private String memberStatus;
	private String mobilePhone;
	private String registerCity;
	private Integer isForbid;
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
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
	public String getMemberStatus() {
		return memberStatus;
	}
	public void setMemberStatus(String memberStatus) {
		this.memberStatus = memberStatus;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public String getRegisterCity() {
		return registerCity;
	}
	public void setRegisterCity(String registerCity) {
		this.registerCity = registerCity;
	}
	public Integer getIsForbid() {
		return isForbid;
	}
	public void setIsForbid(Integer isForbid) {
		this.isForbid = isForbid;
	}
	@Override
	public String toString() {
		return "MemberOutsideVo [memberId=" + memberId + ", idCard=" + idCard + ", memberName=" + memberName
				+ ", memberStatus=" + memberStatus + ", mobilePhone=" + mobilePhone + ", registerCity=" + registerCity
				+ ", isForbid=" + isForbid + "]";
	}
	
}
