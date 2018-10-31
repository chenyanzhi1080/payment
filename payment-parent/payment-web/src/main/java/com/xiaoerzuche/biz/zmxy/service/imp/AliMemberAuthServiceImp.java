package com.xiaoerzuche.biz.zmxy.service.imp;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.alipay.api.AlipayApiException;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayUserInfoShareRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayUserInfoShareResponse;
import com.xiaoerzuche.biz.payment.dao.PaymentSessionDao;
import com.xiaoerzuche.biz.zmxy.config.AliOpendApiClient;
import com.xiaoerzuche.biz.zmxy.model.AliMember;
import com.xiaoerzuche.biz.zmxy.service.AliMemberAuthService;
import com.xiaoerzuche.common.enu.ErrorCode;
import com.xiaoerzuche.common.util.CheckUtil;
import com.xiaoerzuche.common.util.EncryptUtil;
import com.xiaoerzuche.common.util.JsonUtil;
@Service
public class AliMemberAuthServiceImp implements AliMemberAuthService{
	private static final Logger logger = LoggerFactory.getLogger(AliMemberAuthServiceImp.class);
	private String ALI_MEMBER_AUTH_KEY = "ALI_MEMBER:AUTH_KEY:";
	@Autowired
	private AliOpendApiClient aliOpendApiClient;
	@Autowired
	private PaymentSessionDao paymentSessionDao;
	
	@Override
	public AliMember getAliMemberByAuth(String authCode) {
		AlipaySystemOauthTokenRequest alipaySystemOauthTokenRequest = new AlipaySystemOauthTokenRequest();
		alipaySystemOauthTokenRequest.setCode(authCode);
		alipaySystemOauthTokenRequest.setGrantType("authorization_code");
		AlipaySystemOauthTokenResponse oauthTokenResponse = null;
		try {
			oauthTokenResponse = aliOpendApiClient.alipayClient.execute(alipaySystemOauthTokenRequest);
		} catch (AlipayApiException e1) {
			logger.info("[获取支付宝accessTokene erro]"+e1);
		}
		logger.info("[获取支付宝accessToken结果]{}",JsonUtil.toJson(oauthTokenResponse));
		CheckUtil.assertNotNull(oauthTokenResponse, ErrorCode.UNKOWN.getErrorCode(),"授权异常");
		CheckUtil.assertTrue(oauthTokenResponse.isSuccess(), ErrorCode.UNKOWN.getErrorCode(),"授权异常");
		String accessToken = oauthTokenResponse.getAccessToken();
		String aliUserId = oauthTokenResponse.getAlipayUserId();
		
		AlipayUserInfoShareRequest alipayUserInfoShareRequest = new AlipayUserInfoShareRequest();
		AlipayUserInfoShareResponse userinfoShareResponse = null;
		try {
				userinfoShareResponse = (AlipayUserInfoShareResponse) aliOpendApiClient.alipayClient.execute(alipayUserInfoShareRequest, accessToken);
		} catch (AlipayApiException e2) {
			logger.info("[支付宝会员信息查询erro]"+e2);
		}
		logger.info("[查询支付宝会员信息结果]{}",JsonUtil.toJson(userinfoShareResponse));
		CheckUtil.assertNotNull(userinfoShareResponse, ErrorCode.UNKOWN.getErrorCode(),"授权异常");
		CheckUtil.assertTrue(userinfoShareResponse.isSuccess(), ErrorCode.UNKOWN.getErrorCode(),"授权异常");
		AliMember aliMember = new AliMember();
		aliMember.setAliUserId(aliUserId)
		.setAccessToken(accessToken)
		.setMobilePhone(userinfoShareResponse.getMobile())
		.setIdCard(userinfoShareResponse.getCertNo())
		.setName(userinfoShareResponse.getUserName())
		.setAvatar(userinfoShareResponse.getAvatar())
		.setProvince(userinfoShareResponse.getProvince())
		.setCity(userinfoShareResponse.getCity())
		.setGender(userinfoShareResponse.getGender())
		.setIsCertified(userinfoShareResponse.getIsCertified())
		;
		logger.info("[支付宝会员信息]{}",aliMember);
		return aliMember;
	}

	@Override
	public ModelAndView getXiaoerAuthUrl(AliMember aliMember) {
		Map<String, Object> map = new HashMap<String, Object>();
		String token = (new Random()).nextInt(9000)+1000+"18289767660"+System.currentTimeMillis();
		token = (EncryptUtil.md5(token)).toUpperCase();
		paymentSessionDao.setSession(ALI_MEMBER_AUTH_KEY+token, 45, JsonUtil.toJson(aliMember));
		map.put("token", token);
		map.put("thirdUserId", aliMember.getAliUserId());
		ModelAndView view = new ModelAndView("redirect:"+aliOpendApiClient.xiaoerAuthUrl(), map);
		return view;
	}

	@Override
	public AliMember getByPaymentToken(String token) {
		String json = paymentSessionDao.getSession(ALI_MEMBER_AUTH_KEY+token);
		paymentSessionDao.removeLock(ALI_MEMBER_AUTH_KEY+token);
		if(StringUtils.isNotBlank(json)){
			AliMember aliMember = JsonUtil.fromJson(json, AliMember.class);
			return aliMember;
		}
		return null;
	}
}
