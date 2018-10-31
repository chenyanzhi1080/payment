package com.xiaoerzuche.biz.zmxy.service;

import org.springframework.web.servlet.ModelAndView;

import com.xiaoerzuche.biz.zmxy.model.AliMember;

public interface AliMemberAuthService {
	public AliMember getAliMemberByAuth(String authCode);
	public ModelAndView getXiaoerAuthUrl(AliMember aliMember);
	/**
	 * 根据payment系统提供的token获取用户信息
	 * @param token
	 * @return
	 */
	public AliMember getByPaymentToken(String token);
}
