package com.xiaoerzuche.biz.zmxy.model;

import java.util.Date;

public class AliMember {

	private String aliUserId; // 支付宝用户id

	private String mobilePhone; // 手机号

	private String idCard; // 证件号

	private String name; // 昵称

	private String avatar; // 头像

	private String province; // 省

	private String city; // 城市

	private String gender; // 性别：M为男性，F为女性

	private String isCertified; // 是否通过实名认证(T是通过 F是没有实名认证 )

	private String accessToken; // 授权令牌，有效期为一年

	private Date createTime; // 创建时间

	private Date lastModifyTime; // 最新变更时间

	public String getAliUserId() {
		return aliUserId;
	}

	public AliMember setAliUserId(String aliUserId) {
		this.aliUserId = aliUserId;
		return this;
	}

	public AliMember setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
		return this;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public AliMember setIdCard(String idCard) {
		this.idCard = idCard;
		return this;
	}

	public String getIdCard() {
		return idCard;
	}

	public AliMember setName(String name) {
		this.name = name;
		return this;
	}

	public String getName() {
		return name;
	}

	public AliMember setAvatar(String avatar) {
		this.avatar = avatar;
		return this;
	}

	public String getAvatar() {
		return avatar;
	}

	public AliMember setProvince(String province) {
		this.province = province;
		return this;
	}

	public String getProvince() {
		return province;
	}

	public AliMember setCity(String city) {
		this.city = city;
		return this;
	}

	public String getCity() {
		return city;
	}

	public AliMember setGender(String gender) {
		this.gender = gender;
		return this;
	}

	public String getGender() {
		return gender;
	}

	public AliMember setIsCertified(String isCertified) {
		this.isCertified = isCertified;
		return this;
	}

	public String getIsCertified() {
		return isCertified;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public AliMember setAccessToken(String accessToken) {
		this.accessToken = accessToken;
		return this;
	}

	public AliMember setCreateTime(Date createTime) {
		this.createTime = createTime;
		return this;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public AliMember setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
		return this;
	}

	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	public AliMember() {
		super();
	}

	@Override
	public String toString() {
		return "AliMember [aliUserId=" + aliUserId + ", mobilePhone=" + mobilePhone + ", idCard=" + idCard + ", name="
				+ name + ", avatar=" + avatar + ", province=" + province + ", city=" + city + ", gender=" + gender
				+ ", isCertified=" + isCertified + ", accessToken=" + accessToken + ", createTime=" + createTime
				+ ", lastModifyTime=" + lastModifyTime + "]";
	}

}
