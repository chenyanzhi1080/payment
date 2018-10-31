package com.xiaoerzuche.biz.permission.interceptor;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.xiaoerzuche.biz.permission.util.SessionUtil;
import com.xiaoerzuche.common.enu.ErrorCode;
import com.xiaoerzuche.common.util.CheckUtil;

@Service
public class FrontPrivilegeInterceptor extends HandlerInterceptorAdapter {
	
	private static String[] noFilterUrls = new String[]
			{"front/powerStation/list.do", "front/pile/list.do", "front/provinceCity/listCity.do", "front/main/ssoLogin.do", 
			"front/takeReturnCarAddress/list.do","front/takeReturnCarAddress/all.do", "front/carType/get.do", "front/main/ssoLogout.do", "front/pay/changeUrl.do",
			"front/payment/notifyFroBackAliPay.do", "front/order/getPaysByPhone.do",
			"front/order/getPaysByPhone.do","front/credit/currentCreditPoint.do",
			"front/orderCenter/wechatJSPayCallback.do",
			"front/payment/notifyFroBackWechatPay.do",
			"front/payment/notifyPaymentGateway.do",
			/**TODO 电子钱包上线稳定后删掉*/
			"front/wallet/notifyFroFrontAliPay.do",
			"front/wallet/notifyFroBackWechatPay.do",
			"front/wallet/notifyFroBackAliPay.do",
			/**TODO 电子钱包上线稳定后删掉*/
			"front/wallet/callback.do",
			"front/wallet/consume.do",
			"front/wallet/getMoney.do",
			"front/wallet/genOrder.do",
			"front/wallet/menuList.do"
			};

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
		//addTest xdz
//		Members member = new Members();
//		member.setAccount("15384071538");
//		member.setId(1l);
//		member.setIdCard("46010319881217031x");
////		member.setIsForbid(isForbid);
//		member.setLastLoginDate(new Date());
//		member.setMemberStatus("1");
//		member.setName("张继科");
//		member.setPhone("15384071538");
//		SessionUtil.setLogingetLoginMember(request, member);
		//endTest xdz
		
		CheckUtil.assertNotNull(SessionUtil.getLoginMember(request), ErrorCode.NOLOGIN.getErrorCode(), "您还未登录");
		
		return true;
	}
}
