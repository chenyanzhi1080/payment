package com.xiaoerzuche.common.core.session;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Cookie处理工具类
 * @author Nick C
 *
 */
class CookieUtil {
	/**
	 * 返回域名</br>
	 * 
	 * @param serverName 服务器地址
	 * @return 域名
	 */
	public static String getDomain(String serverName) {
		int index = serverName.indexOf(".");
		String domain = serverName.substring(index);
		return domain;
	}

	/**
	 * 设置cookie</br>
	 * 
	 * @param name cookie名称
	 * @param value cookie值
	 * @param request http请求
	 * @param response http响应
	 */
	public static void setCookie(String name, String value, HttpServletRequest request, HttpServletResponse response) {
		int maxAge = -1;
		CookieUtil.setCookie(name, value, maxAge, request, response);
	}

	/**
	 * 设置cookie</br>
	 * 
	 * @param name cookie名称
	 * @param value cookie值
	 * @param maxAge 最大生存时间
	 * @param request http请求
	 * @param response http响应
	 */
	public static void setCookie(String name, String value, int maxAge, HttpServletRequest request,
			HttpServletResponse response) {
		boolean httpOnly = false;
		CookieUtil.setCookie(name, value, maxAge, httpOnly, request, response);
	}

	/**
	 * 设置cookie</br>
	 * 
	 * @param name cookie名称
	 * @param value cookie值
	 * @param maxAge 最大生存时间
	 * @param httpOnly cookie的路径
	 * @param request http请求
	 * @param response http响应
	 */
	public static void setCookie(String name, String value, int maxAge, boolean httpOnly, HttpServletRequest request,
			HttpServletResponse response) {
		boolean currentDomain = true;
		CookieUtil.setCookie(name, value, maxAge, httpOnly, currentDomain, request, response);
	}

	/**
	 * 设置cookie</br>
	 * 
	 * @param name cookie名称
	 * @param value cookie值
	 * @param maxAge 最大生存时间
	 * @param httpOnly cookie的路径
	 * @param currentDomain 是否使用当前的域名
	 * @param request http请求
	 * @param response http响应
	 */
	public static void setCookie(String name, String value, int maxAge, boolean httpOnly, boolean currentDomain,
			HttpServletRequest request, HttpServletResponse response) {
		if (isEmpty(name)) {
			throw new NullPointerException("cookie名称不能为空.");
		}
		if (value == null) {
			throw new NullPointerException("cookie值不能为空.");
		}
		/*
		String serverName = request.getServerName();
		String domain;
		if (currentDomain) {
			domain = serverName;
		} else {
			domain = CookieUtil.getDomain(serverName);
		}*/

		Cookie cookie = new Cookie(name, value);
		//cookie.setDomain(domain);TODO:这里暂时不设置的domain by Nick C
		cookie.setMaxAge(maxAge);
		if (httpOnly) {
			cookie.setPath("/;HttpOnly");
		} else {
			cookie.setPath("/");
		}
		response.addCookie(cookie);

	}

	/**
	 * 获取cookie的值</br>
	 * 
	 * @param name cookie名称
	 * @param request http请求
	 * @return cookie值
	 */
	public static String getCookie(String name, HttpServletRequest request) {
		if (isEmpty(name)) {
			throw new NullPointerException("cookie名称不能为空.");
		}
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			return null;
		}
		for (int i = 0; i < cookies.length; i++) {
			if (name.equalsIgnoreCase(cookies[i].getName())) {
				return cookies[i].getValue();
			}
		}
		return null;
	}

	/**
	 * 删除cookie</br>
	 * 
	 * @param name cookie名称
	 * @param request http请求
	 * @param response http响应
	 */
	public static void deleteCookie(String name, HttpServletRequest request, HttpServletResponse response) {
		if (isEmpty(name)) {
			throw new NullPointerException("cookie名称不能为空.");
		}
		CookieUtil.setCookie(name, "", -1, false, request, response);
	}

	/**
	 * 判断字符串是否为空</br>
	 * 
	 * @param str 字符串
	 * @return 字符串为空则返回true
	 */
	private static boolean isEmpty(String str) {
		return (str == null || str.length() == 0);
	}

	public static void main(String[] args) {
		// String serverName = "udb.sc2.com.cn";
		String serverName = "udb.xiaoer.com";
		String domain = CookieUtil.getDomain(serverName);
		System.out.println("domain:" + domain);
	}

}
