package com.xiaoerzuche.biz.payment.vo.credit;

import java.util.Date;

public class CreditCardUserAdminVo extends BaseQueryVO<CreditCardUserAdminVo> {
	/**
	 * 唯一标识
	 */
	private String id;
	/**
	 * 账户登录手机号(会员账号)
	 */
	private String account;
	/**
	 * 会员名称
	 */
	private String menberName;
	/**
	 * 银行预留手机号
	 */
	private String mobilePhone;
	/**
	 * 证件类型
	 */
	private int idType;
	/**
	 * 证件号
	 */
	private String cardHolderId;
	/**
	 * 持卡者姓名
	 */
	private String cardHolderName;
	/**
	 * 信用卡后四位(用于作为展示和索引,管理平台会用到)
	 */
	private String cardNo;
	/**
	 * 卡过期时间(时间戳)
	 */
	private long expiredDate;
	/**
	 * 发卡银行代码
	 */
	private String bankId;
	/**
	 * 发卡银行名称
	 */
	private String bankName;
	/**
	 * 是否绑定
	 */
	private Integer isBound;
	/**
	 * 最新变更时间(时间戳)
	 */
	private long lastModifyTime;
	/**
	 * 操作者
	 */
	private String operation;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getMenberName() {
		return menberName;
	}
	public void setMenberName(String menberName) {
		this.menberName = menberName;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public int getIdType() {
		return idType;
	}
	public void setIdType(int idType) {
		this.idType = idType;
	}
	public String getCardHolderId() {
		return cardHolderId;
	}
	public void setCardHolderId(String cardHolderId) {
		this.cardHolderId = cardHolderId;
	}
	public String getCardHolderName() {
		return cardHolderName;
	}
	public void setCardHolderName(String cardHolderName) {
		this.cardHolderName = cardHolderName;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public long getExpiredDate() {
		return expiredDate;
	}
	public void setExpiredDate(long expiredDate) {
		this.expiredDate = expiredDate;
	}
	public String getBankId() {
		return bankId;
	}
	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public Integer getIsBound() {
		return isBound;
	}
	public void setIsBound(Integer isBound) {
		this.isBound = isBound;
	}
	public long getLastModifyTime() {
		return lastModifyTime;
	}
	public void setLastModifyTime(long lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	@Override
	public String toString() {
		return "CreditCardUserAdminVo [id=" + id + ", account=" + account + ", menberName=" + menberName
				+ ", mobilePhone=" + mobilePhone + ", idType=" + idType + ", cardHolderId=" + cardHolderId
				+ ", cardHolderName=" + cardHolderName + ", cardNo=" + cardNo + ", expiredDate=" + expiredDate
				+ ", bankId=" + bankId + ", bankName=" + bankName + ", isBound=" + isBound + ", lastModifyTime="
				+ lastModifyTime + ", operation=" + operation + "]";
	}
	
}
