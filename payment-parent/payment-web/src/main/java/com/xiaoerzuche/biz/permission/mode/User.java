package com.xiaoerzuche.biz.permission.mode;

import java.util.Date;

/**
 * 用户信息
 * @author Nick C
 *
 */
public class User implements Cloneable{
	private int id;
	private String name;//'用户名',
	private String account;//'登陆账号',
	private String pwd;//'密码',
	private String nickName;//'昵称',
	private String phone;//'联系电话',
	private int sex;//'性别1是男2是女',
	private String address;//'地址',
	private int status;//'用户状态1是启用0是禁用',
	private String comment;//用户备注
	private Date createDate;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", account=" + account
				+ ", pwd=" + pwd + ", nickName=" + nickName + ", phone="
				+ phone + ", sex=" + sex + ", address=" + address + ", status="
				+ status + ", comment=" + comment + ", createDate="
				+ createDate + "]";
	}

	@Override
	public User clone(){
		User user = null;
		try {
			user = (User) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return user;
	}
}
