package com.xiaoerzuche.common.util;

import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

/**
 * 系统环境相关工具类
 * @author Nick C
 *
 */
public class EnvUtil {

	/**
	 * 判断是当前的运行环境是不是Window操作系统
	 * @return
	 */
	public static boolean isWindows(){
		Properties prop = System.getProperties();
		String os = prop.getProperty("os.name").toLowerCase();
		return os.startsWith("win");
	}
	
	/**
	 * 判断ajax请求
	 * @param request
	 * @return
	 */
	public static boolean isAjax(HttpServletRequest request){
		String callback = request.getParameter("callback");
	    return StringUtils.isNotBlank(callback) || (request.getHeader("X-Requested-With") != null  && "XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"))) ;
	}
}
