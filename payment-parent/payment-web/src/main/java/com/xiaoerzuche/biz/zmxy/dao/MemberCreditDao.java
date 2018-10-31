package com.xiaoerzuche.biz.zmxy.dao;

import java.util.Date;
import java.util.List;

import com.xiaoerzuche.biz.zmxy.dao.sqlbuilder.MemberCreditBuilder;
import com.xiaoerzuche.biz.zmxy.model.MemberCredit;
public interface MemberCreditDao  {

	boolean insert(MemberCredit memberCredit);

	boolean update(MemberCredit memberCredit);

	boolean delete(String id);

	MemberCredit get(String id);
	
	MemberCredit getByAccount(String account);

	List< MemberCredit>  list(MemberCreditBuilder builder);
	int count(MemberCreditBuilder builder);
	
	/**
	 * 设置黑名单
	 * @param id 开开出行平台芝麻授权用户ID
	 * @param blackReasonCode 拉黑原因业务代码
	 * @param remark 拉黑备注
	 * @param operator 操作用户
	 * @param lastModifyTime 操作时间
	 * @return
	 */
	boolean setBlacklist(String id, String blackReasonCode, String remark, String operator, Date lastModifyTime);
	/**
	 * 上报行业名单记录
	 * @param details
	 * @return
	 */
	boolean sendZhimaCreditWatch(String id, String details);
	
	/**
	 * 解绑
	 * @param account
	 * @param id
	 * @return
	 */
	public boolean unbind(String account,String id,String operation);
}