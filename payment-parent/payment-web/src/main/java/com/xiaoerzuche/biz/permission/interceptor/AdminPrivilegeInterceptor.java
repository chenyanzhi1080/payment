package com.xiaoerzuche.biz.permission.interceptor;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.xiaoerzuche.biz.permission.mode.Resource;
import com.xiaoerzuche.biz.permission.util.SessionUtil;
import com.xiaoerzuche.common.constant.Constants;
import com.xiaoerzuche.common.enu.ErrorCode;
import com.xiaoerzuche.common.exception.ErrorCodeException;
import com.xiaoerzuche.common.exception.NotPermissionException;
import com.xiaoerzuche.common.util.CheckDangerCharacter;
import com.xiaoerzuche.common.util.CheckUtil;
import com.xiaoerzuche.common.util.EnvUtil;

@Service
public class AdminPrivilegeInterceptor extends HandlerInterceptorAdapter {

	private static String[] noFilterUrls = new String[]{
		"admin/main/toLogin.do", "admin/main/doLogin.do", "admin/main/main.do", "admin/main/left.do", "admin/main/top.do",
		"admin/messagepushlog/pushonewithalidayu.do",
		"admin/payment/notifyFroBackAliRefund.do","admin/payment/notifyFroBackUnionRefund.do","admin/orderRefund/applyRefund",
		"admin/orderRefund/applyRefundSuccess"
	};
	
	private static final String loginUrl = "/admin/main/toLogin.do";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String servletPath = request.getServletPath().substring(1);
		servletPath = servletPath.indexOf("?") > 0 ? servletPath.substring(0,servletPath.indexOf("?")) : servletPath;

		//不需要拦截的url
		for(String noFilterUrl : noFilterUrls){
			if (servletPath.endsWith(noFilterUrl)){
				return true;
			}
		}

		if(request.getSession().getAttribute(Constants.ADMIN_LOGIN_USER) == null){
			if(EnvUtil.isAjax(request)){
				throw new ErrorCodeException(ErrorCode.NOLOGIN.getErrorCode(), "您还未登录");
			}else{
				response.sendRedirect(request.getContextPath()+loginUrl);
				return false;
			}
		}
		return true;
		//检查权限
//		List<Resource> resources = SessionUtil.getResources(request);
//		for(Resource resource : resources){
//			if (resource.getUrl().contains(servletPath)){
//				return true;
//			}
//		}
//		throw new NotPermissionException("您没有访问权限");
	}

}
