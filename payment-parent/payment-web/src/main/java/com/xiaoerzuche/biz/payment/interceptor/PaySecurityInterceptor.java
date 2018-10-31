package com.xiaoerzuche.biz.payment.interceptor;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.xiaoerzuche.biz.payment.paymentConfig.WechatPayConfig;
import com.xiaoerzuche.biz.payment.service.PayAuthService;
import com.xiaoerzuche.biz.payment.service.PaySystemConfigService;
import com.xiaoerzuche.biz.payment.util.MD5Util;
import com.xiaoerzuche.common.core.security.SecurityIpChecker;
import com.xiaoerzuche.common.enu.ErrorCode;
import com.xiaoerzuche.common.exception.ErrorCodeException;
import com.xiaoerzuche.common.util.CheckDangerCharacter;
import com.xiaoerzuche.common.util.CheckUtil;
import com.xiaoerzuche.common.util.IPUtil;
import com.xiaoerzuche.common.util.XssUtil;

/**
 * 检查支付接口是否授权
 * 
 * @author Nick C
 */
public class PaySecurityInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private SecurityIpChecker securityIpChecker;
	@Autowired
	private PaySystemConfigService paySystemConfigService;
	@Autowired
	private PayAuthService payAuthService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		/**
		//检查ip白名单是否授权
		String ip = IPUtil.getClientIp(request);
		CheckUtil.assertTrue(securityIpChecker.isPass(ip), ErrorCode.LIMIT.getErrorCode(), "非法IP"+ip);
		*/
		
		return super.preHandle(request, response, handler);
	}
	
}
