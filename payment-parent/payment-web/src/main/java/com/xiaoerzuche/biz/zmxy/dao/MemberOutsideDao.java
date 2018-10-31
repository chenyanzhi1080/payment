package com.xiaoerzuche.biz.zmxy.dao;

import java.util.List;

import com.xiaoerzuche.biz.zmxy.vo.antifraud.MemberOutsideVo;

public interface MemberOutsideDao {
	/**
	 * @param limit
	 * @param offset
	 * @return
	 */
	List<MemberOutsideVo> list(Integer limit, Integer offset);
	/**
	 * 设置黑名单
	 * @param memberId
	 * @return
	 */
	boolean setBlack(String memberId);
	/**
	 * 单个同步
	 * @param memberId
	 * @return
	 */
	MemberOutsideVo get(String memberId);
	
	/**
	 * 根据登录手机号获取用户信息
	 * @param mobilePhone
	 * @return
	 */
	MemberOutsideVo getByMobilePhone(String mobilePhone);
}
