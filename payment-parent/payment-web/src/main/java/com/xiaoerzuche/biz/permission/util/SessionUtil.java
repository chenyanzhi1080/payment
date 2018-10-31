package com.xiaoerzuche.biz.permission.util;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.reflect.TypeToken;
import com.xiaoerzuche.biz.payment.model.Members;
import com.xiaoerzuche.biz.permission.mode.Resource;
import com.xiaoerzuche.biz.permission.mode.User;
import com.xiaoerzuche.common.constant.Constants;
import com.xiaoerzuche.common.util.JsonUtil;

/**
 * Session数据操作工具类
 * @author Nick C
 *
 */
public class SessionUtil {
	private static final Logger logger = LoggerFactory.getLogger(SessionUtil.class);

	public static User getLoginUser(HttpServletRequest request){
		String userJson = (String)request.getSession().getAttribute(Constants.ADMIN_LOGIN_USER);
		if(StringUtils.isBlank(userJson)){
			return null;
		}
		return JsonUtil.fromJson(userJson, User.class);
	}
	
	public static void setLoginUser(HttpServletRequest request, User user){
		request.getSession().setAttribute(Constants.ADMIN_LOGIN_USER, JsonUtil.toJson(user));
	}
	
	public static List<Resource> getResources(HttpServletRequest request){
		String resourcesJson = (String)request.getSession().getAttribute(Constants.ADMIN_PERMISSION);
		if(StringUtils.isBlank(resourcesJson)){
			return null;
		}		
		return JsonUtil.fromJson(resourcesJson, new TypeToken<List<Resource>>(){});
	}
	
	public static void setResources(HttpServletRequest request, List<Resource> resources){
		request.getSession().setAttribute(Constants.ADMIN_PERMISSION, JsonUtil.toJson(resources));
	}

	public static void clearAll(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();
	}
	
	
	public static Members getLoginMember(HttpServletRequest request){
		String json = (String)request.getSession().getAttribute(Constants.FRONT_LOGIN_MEMBER);
		return JsonUtil.fromJson(json, Members.class);
	}
	
	public static void setLogingetLoginMember(HttpServletRequest request, Members member){
		request.getSession().setAttribute(Constants.FRONT_LOGIN_MEMBER, JsonUtil.toJson(member));
	}
}
