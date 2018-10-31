package com.xiaoerzuche.biz.zmxy.config;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alipay.api.DefaultAlipayClient;

/** 
* 支付宝（蚂蚁金服）开发平台api客户端
* @author: cyz
* @version: payment-web
* @time: 2017年9月23日
* 
*/
@Component
public class AliOpendApiClient {
	private static final Logger logger = LoggerFactory.getLogger(AliOpendApiClient.class);
	
	//支付宝开放平台应用号
	public static final String APP_ID = "2018041002533468";
	//支付宝开放平台公钥设置对应的私钥(商家私钥)RSA（SHA256）
	public static final String PRIVATE_KEY = "MIIEuwIBADANBgkqhkiG9w0BAQEFAASCBKUwggShAgEAAoIBAQCD/6RSKYFpl695qpGPVMGklLkiku9Z0mEUr+Nv3KXA6p3hEz9f9c09bAITuhUF/kZJWfZFUXMhCnxo2j8aidtAwI+X8hEFLCmRc8IdqwNjMgAICoq+/1xXipejR2HBYYTfMCokCK2kPgVKs9sIRtspGBJ2YemtckvALXF6ofFEaoOLzb6bv3N1Zidj9ajz+ToMvXxKiSVbbWht9h/qxd5SC1ZyKuifZqlAS7XfZNVGAzM3hvpp/rFXs9rK3/IRZo0nIygRn7EpZ9kNXZP7m/sw7PysQz7wFKHWxcDtMQpclF/oX7qVecPaoKhaT11iJEwgiTS17L2pgxk1wbPr1JFlAgMBAAECggEAPka2eC0s6WyvVW+sDTunwMaKMk2wr2gadXAmlfg8G7CVnENAez20/22d7UC1+oXlE2ZmUn4DG2ufFrkhXJv93ab8GGwUq6QSWJWM2AJ/LqXAbgrLOyGvp32z3U0oSdyAm+xVdXrCseKxDHWeuWD0tYat7zxyy2pJYvoNuBX/ho8reUZzqheAGButzKb/hIzGMcihK6SE92+kHaowKINkMqBN91cYKpz+UlHb4XHjXAUds59wTyaQ8T7D/rhGhTrGCdDZ1yePr83LWJAFAMQjP3BLK1hAnGR2NN0mrjLMIe9p3Srm1SHxeLfHKDjTNzfhwZAx6UD0qwp/53HoEz8uYQKBgQDQkkj895pBG6G3WLX25pZETOXi8BOdOBWc/BMqLwmTGY6Dec8leMqAYyFv4J84MhrfrwYFN4tvDUiLzJAjJPbEALuC2GvcuUeKizNQ1UhoXmALKRZTz2nhTTQ/vExW+y5KASgOpCeoO0jrsiU/HON+up2dpYVBUaNUP3dwTSqgEwKBgQCiA8TlzR5sLWyxamOxbUKMUIKYMSW2iU6w6CGBEgLLque5JCoW/aI+3BqXLClkAAhvrVq1F8RDnfHKFGYnCZHNv53cqZen/8/N9YT1cvOQrF6fjI10/ckAC9NhK+nZB3znw/DVNQHGns1wOJ/KSJsBx0Utif0g+LNeBB5Jq7fnpwJ/aRlNrisWj7hcxQbuqnuHPOOC89gGCjM4sOJUxezt5jH6nkXDHB7B6/AH6VhG4wpcgFqxre9PU6Zd6BQsjYkSz7GOy2BC+fZjFm0Obq0Kz4y+2ODbX2Ak+mLs8TDxRuNLb/8lMDuHwD4fgKVEH4lvGWzv/4BopYF9y/YvOWepEwKBgQCXX/ArSlBfgL2qsp29HDnughbS/jCfcYFNt0X+o4ZrJx7lFE4jNw3Nr7dEgeWWYpV/SriStUcSalOMnSyo0r8hPyTPFMH3HQPbCsgU0TeTeAndBuTwZ2LTITl59B7FQcLJhYDSpHkpTBzk+nMJ/Haf3cDcAK66jMSCVlS6PrBQxwKBgFySghzSJc2/xUm/IKmuoFqxq4XBi1jVrqwVp+N1+BkReYnFw0uWzLL0sCdbv2PsJkUJEYfT0ZqrQoY7uRgGRSKUg77OAB/nkIMDW8IQUMGoJzsyLl/3r9iQyNbZEE1OZUXo3jlYBqKBzT3Ju4jcE2V0NngJGzIlklEP6Zhz40Fi";
	//支付宝公钥RSA（SHA256）
	public static final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmyKV79HER3bJPjVaoOZ3gRycX4TnsDdnRYrhZQy1RjWRvm6yA6fQRZu6LuzGWDw9YEuZQkrQ3W6koxJnOdI5s0/swRtdbpL7wpyRxxZL3M2DW9fhBSkRevIhxeR00u9tSE5fEd6sNul+yG73HmTNgT9nqdHmalY/daON0e+l9aC08jGhZYye1QPErFFD/rTqqELi48NsYPykfXXrcwXgApZcagZKCDoube47AhqKBYd2Mwsdp3H9cUtG4Cp1SRHKSA+PN4h3+SpTcnSo6HvZBlqCfiT0/yhnsO+jCRUwtOup1/9/yrIcT/ufHYBpG9xNujYGlLLVYDfm/sKsL7+1dQIDAQAB";
	//开放平台网关
	public static final String GATEWAY_URL = "https://openapi.alipay.com/gateway.do";
	/**
	 * 成功授权的接口权限值，目前只支持auth_user（获取用户信息、网站支付宝登录）、auth_base（用户信息授权）、auth_ecard（商户会员卡）、auth_invoice_info（支付宝闪电开票）、auth_puc_charge（生活缴费）五个值
	 */
//	public static String SCOPE = "auth_user,auth_base,auth_zhima";
	public static String SCOPE = "auth_user,auth_zhima";
	
	public static DefaultAlipayClient alipayClient;
	
	private static String authUrl;
	@Value("${profile}")
	private String profile;
	/*支付宝授权跳转服务端地址*/
	@Value("${ALI_AUTH_REDIRECT_SERVER_DEV}")
	private String ALI_AUTH_REDIRECT_SERVER_DEV;
	@Value("${ALI_AUTH_REDIRECT_SERVER_TEST}")
	private String ALI_AUTH_REDIRECT_SERVER_TEST;
	@Value("${ALI_AUTH_REDIRECT_SERVER_PRO}")
	private String ALI_AUTH_REDIRECT_SERVER_PRO;
	/*支付宝授权跳转H5地址*/
	@Value("${ALI_AUTH_REDIRECT_H5_DEV}")
	private String ALI_AUTH_REDIRECT_H5_DEV;	
	@Value("${ALI_AUTH_REDIRECT_H5_TEST}")
	private String ALI_AUTH_REDIRECT_H5_TEST;
	@Value("${ALI_AUTH_REDIRECT_H5_PRO}")
	private String ALI_AUTH_REDIRECT_H5_PRO;
	private static String aliAuthRedirectH5Url;
	/*开开出行用户静默授权*/
	@Value("${XIAOER_AUTH_DEV}")
	private String XIAOER_AUTH_DEV;
	@Value("${XIAOER_AUTH_TEST}")
	private String XIAOER_AUTH_TEST;
	@Value("${XIAOER_AUTH_PRO}")
	private String XIAOER_AUTH_PRO;
	private static String xiaoerAuthUrl;
	
	@PostConstruct
	private void init(){
		this.alipayClient = new DefaultAlipayClient(GATEWAY_URL,APP_ID,PRIVATE_KEY,"json","UTF-8",ALIPAY_PUBLIC_KEY,"RSA2");
		String aliAuthRedirectServerUrl = "";
		switch (profile) {
		case "test":
			aliAuthRedirectServerUrl = this.ALI_AUTH_REDIRECT_SERVER_TEST;
			this.aliAuthRedirectH5Url = this.ALI_AUTH_REDIRECT_H5_TEST;
			this.xiaoerAuthUrl = this.XIAOER_AUTH_TEST;
			break;
		case "prod":
			aliAuthRedirectServerUrl = this.ALI_AUTH_REDIRECT_SERVER_PRO;
			this.aliAuthRedirectH5Url = this.ALI_AUTH_REDIRECT_H5_PRO;
			this.xiaoerAuthUrl = this.XIAOER_AUTH_PRO;
			break;
		default:
			aliAuthRedirectServerUrl = this.ALI_AUTH_REDIRECT_SERVER_DEV;
			this.aliAuthRedirectH5Url = this.ALI_AUTH_REDIRECT_H5_DEV;
			this.xiaoerAuthUrl = this.XIAOER_AUTH_DEV;
			break;
		}
		//拼接参数redirect_uri的值需要URLEncoder
		try {
			aliAuthRedirectServerUrl = URLEncoder.encode(aliAuthRedirectServerUrl, "UTF-8");
			aliAuthRedirectH5Url = URLEncoder.encode(aliAuthRedirectH5Url, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.info("[支付宝开发平台配置初始化][erro]"+e);
		}
		
		StringBuffer sb = new StringBuffer();
		sb.append("https://openauth.alipay.com/oauth2/publicAppAuthorize.htm?app_id=")
		.append(AliOpendApiClient.APP_ID).append("&scope=").append(AliOpendApiClient.SCOPE)
		.append("&redirect_uri=").append(aliAuthRedirectServerUrl);
		this.authUrl = sb.toString();
	}
	/**
	 * 获取支付宝授权地址
	 * @return
	 */
	public String authUrl(){
		return this.authUrl;
	}
	/**
	 * 获取静默授权登录成功后的跳转地址
	 * @return
	 */
	public String aliAuthRedirectH5Url(){
		return this.aliAuthRedirectH5Url;
	}
	/**
	 * 获取开开出行静默授权登录地址
	 * @return
	 */
	public String xiaoerAuthUrl(){
		return this.xiaoerAuthUrl;
	}
}
