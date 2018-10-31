package com.xiaoerzuche.biz.payment.model;

import java.util.Date;

/**
 * 用户
 * @author Nick C
 *
 */
public class Members {
    private Long id;

    private String account;

    private String name;

    private String phone;

    private Date lastLoginDate;
    
    private String idCard;
    
    //用户状态
  	private String memberStatus;
  	
  	//是否属于黑名单
  	private Double isForbid;
  	
  	//渠道
  	private String channelId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	@Override
	public String toString() {
		return "Members [id=" + id + ", account=" + account + ", name=" + name
				+ ", phone=" + phone + ", lastLoginDate=" + lastLoginDate
				+ ", idCard=" + idCard + ",memberStatus="+memberStatus+", isForbi="+isForbid+" ]";
	}

	public String getMemberStatus() {
		return memberStatus;
	}

	public void setMemberStatus(String memberStatus) {
		this.memberStatus = memberStatus;
	}

	public Double getIsForbid() {
		return isForbid;
	}

	public void setIsForbid(Double isForbid) {
		this.isForbid = isForbid;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
}
