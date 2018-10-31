package com.xiaoerzuche.biz.zmxy.web.api;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.xiaoerzuche.biz.payment.web.rpc.BaseController;
import com.xiaoerzuche.biz.permission.util.AddressByIpUtil;
import com.xiaoerzuche.biz.zmxy.config.AliOpendApiClient;
import com.xiaoerzuche.biz.zmxy.handler.aliMember.AliMemberHandler;
import com.xiaoerzuche.biz.zmxy.model.AliMember;
import com.xiaoerzuche.biz.zmxy.service.AliMemberAuthService;

/** 
* 蚂蚁金服用户授权
* @author: cyz
* @version: payment-web
* @time: 2017年9月22日
* 
*/
@RestController
@RequestMapping("/api")
public class AliMemberAuthApiController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(AliMemberAuthApiController.class);

	@Autowired
	private AliOpendApiClient aliOpendApiClient;
	@Autowired
	private AliMemberAuthService aliMemberAuthService;
	@Autowired
	private AliMemberHandler aliMemberHandler;
	
	@RequestMapping(value = "/v1.0/fast/login/ali", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	private ModelAndView toAuth(HttpServletRequest request){
		String authUrl = aliOpendApiClient.authUrl();
		logger.info("[toAuth]"+authUrl);
		return new ModelAndView("redirect:"+authUrl);
	}
	
	/**
	 * 说明：支付宝授权之后，跳转回来是会带上auth_code、app_id、scope
	 * @param auth_code
	 * @param app_id
	 * @param scope
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/v1.0/fast/login/ali/auth", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	private ModelAndView auth(String auth_code, String app_id, String scope,HttpServletRequest request){
		String ip = AddressByIpUtil.getIpAddr2(request);
		logger.info("[auth][auth_code={}][app_id={}][scope={}][ip={}]", new Object[]{auth_code, app_id, scope,ip});
		AliMember aliMember = aliMemberAuthService.getAliMemberByAuth(auth_code);
		//异步处理支付宝会员信息
		aliMemberHandler.addAndUpdatAliMember(aliMember);
		return aliMemberAuthService.getXiaoerAuthUrl(aliMember);
	}
/*	@RequestMapping(value = "/v1.0/fast/login/ali/auth/test", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ModelAndView testAuth(){
		AliMember aliMember = new AliMember();
		aliMember.setAliUserId("20881069110346142423811422511225")
		.setMobilePhone("18289767660")
		.setIdCard("33546576567547654")
		.setName("陈彦志")
		.setGender("m")
		;
		return aliMemberAuthService.getXiaoerAuthUrl(aliMember);
	}*/
}
