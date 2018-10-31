package com.xiaoerzuche.common.core.security;

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

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.xiaoerzuche.common.enu.ErrorCode;
import com.xiaoerzuche.common.exception.ErrorCodeException;
import com.xiaoerzuche.common.util.CheckDangerCharacter;
import com.xiaoerzuche.common.util.CheckUtil;
import com.xiaoerzuche.common.util.XssUtil;

/**
 * 检查XSS
 * 
 * @author Nick C
 */
public class SecurityInterceptor extends HandlerInterceptorAdapter {

	private static String[]	noFilterUrls	= new String[] { 
			"aliPay.do",
			"aliRefund.do",
			"notifyFroBackAliPay.do",
			"notifyFroFrontAliPay.do",
			"notifyFroBackAliRefund.do",
			"walletnotifyPay.do",
			"notifyForBackAliPay.do",
			"notifyForBackAliAppPay.do",
			"notifyForBackAliRefund.do",
			"notifyForBackUnionRefund.do",
			"notifyForBackWechatPay.do",
			"notifyForBackWechatJsPay.do",
			"notifyFrontAliPay.do",
			"notifyForBackAliOpenAppPay.do",
			"changeUrl.do","notifyOk.do",
			"/zmxy/auth/callback",
			"/fast/login/ali/auth"
	};

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String servletPath = request.getServletPath().substring(1);
		servletPath = servletPath.indexOf("?") > 0 ? servletPath.substring(0, servletPath.indexOf("?")) : servletPath;

		// 不需要拦截的url
		for (String noFilterUrl : noFilterUrls) {
			if (servletPath.endsWith(noFilterUrl)) {
				return true;
			}
		}

		if (XssUtil.isMultipartContent(request)) {
			return super.preHandle(request, response, handler);
		}
		if (XssUtil.checkAllXSS(request.getParameterMap())) {
			throw new ErrorCodeException(ErrorCode.PARAM.getErrorCode(), "参数包含非法字符");
		}

		CheckUtil.assertTrue(checkSqlInjection(request), ErrorCode.LIMIT.getErrorCode(), "参数包含非法字符");
		return super.preHandle(request, response, handler);
	}

	/**
	 * 检查SQL注入
	 * 
	 * @param request
	 * @return
	 */
	private boolean checkSqlInjection(HttpServletRequest request) {
		Map<String, String[]> map = request.getParameterMap();
		for (Entry<String, String[]> entry : map.entrySet()) {
			for (String parameValue : entry.getValue()) {
				if (!CheckDangerCharacter.checkSqlInjection(parameValue.toLowerCase())) {
					return false;
				}
			}
		}
		return true;
	}
}
