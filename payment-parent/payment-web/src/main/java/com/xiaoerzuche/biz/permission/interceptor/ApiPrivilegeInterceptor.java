package com.xiaoerzuche.biz.permission.interceptor;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.xiaoerzuche.biz.payment.model.Members;
import com.xiaoerzuche.biz.permission.util.SessionUtil;
import com.xiaoerzuche.common.enu.ErrorCode;
import com.xiaoerzuche.common.util.CheckUtil;
import com.xiaoerzuche.common.util.JsonUtil;

@Service
public class ApiPrivilegeInterceptor extends HandlerInterceptorAdapter {
	private static final Logger logger = LoggerFactory.getLogger(ApiPrivilegeInterceptor.class);
	@Value("${profile}")
	private String profile;
	
	private static String[] noFilterUrls = new String[]
			{
					"credit/bankList","/zmxy/auth/callback","/zmxy/auth/callback/result",
					"fast/login/ali"
			};

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String servletPath = request.getServletPath().substring(1);
		servletPath = servletPath.indexOf("?") > 0 ? servletPath.substring(0,servletPath.indexOf("?")) : servletPath;
		
		//不需要拦截的url
		for(String noFilterUrl : noFilterUrls){
			if (servletPath.contains(noFilterUrl)){
				return true;
			}
		}
/*		if("dev".equals(profile)){
			//addTest xdz
			Members member = new Members();
			member.setAccount("18289767660");
			member.setId(1l);
			member.setIdCard("46010319881217031x");
//			member.setIsForbid(isForbid);
			member.setLastLoginDate(new Date());
			member.setMemberStatus("1");
			member.setName("陈彦志");
			member.setPhone("18289767660");
			SessionUtil.setLogingetLoginMember(request, member);
			//endTest xdz
		}*/
		if(null==SessionUtil.getLoginMember(request)){
			logger.info("[未登录拦截]url={} cookies={} User-Agent={} X-User-Agent={}",
					new Object[]{servletPath,JsonUtil.toJson(request.getCookies()),request.getHeader("User-Agent"),request.getHeader("X-User-Agent")});
			CheckUtil.assertNotNull(null, ErrorCode.NOLOGIN.getErrorCode(), "您还未登录");
		}
//		CheckUtil.assertNotNull(SessionUtil.getLoginMember(request), ErrorCode.NOLOGIN.getErrorCode(), "您还未登录");
		
		return true;
	}
	
}
