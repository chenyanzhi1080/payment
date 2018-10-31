package com.xiaoerzuche.biz.payment.paymentConfig;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class DefaultAliOpendApiClient {
	private static final Logger logger = LoggerFactory.getLogger(DefaultAliOpendApiClient.class);
	
	//支付宝开放平台应用号
	public static final String APP_ID = "2018041002533468";
	//支付宝开放平台公钥设置对应的私钥(商家私钥)RSA（SHA256）
	public static final String PRIVATE_KEY = "MIIEuwIBADANBgkqhkiG9w0BAQEFAASCBKUwggShAgEAAoIBAQCD/6RSKYFpl695qpGPVMGklLkiku9Z0mEUr+Nv3KXA6p3hEz9f9c09bAITuhUF/kZJWfZFUXMhCnxo2j8aidtAwI+X8hEFLCmRc8IdqwNjMgAICoq+/1xXipejR2HBYYTfMCokCK2kPgVKs9sIRtspGBJ2YemtckvALXF6ofFEaoOLzb6bv3N1Zidj9ajz+ToMvXxKiSVbbWht9h/qxd5SC1ZyKuifZqlAS7XfZNVGAzM3hvpp/rFXs9rK3/IRZo0nIygRn7EpZ9kNXZP7m/sw7PysQz7wFKHWxcDtMQpclF/oX7qVecPaoKhaT11iJEwgiTS17L2pgxk1wbPr1JFlAgMBAAECggEAPka2eC0s6WyvVW+sDTunwMaKMk2wr2gadXAmlfg8G7CVnENAez20/22d7UC1+oXlE2ZmUn4DG2ufFrkhXJv93ab8GGwUq6QSWJWM2AJ/LqXAbgrLOyGvp32z3U0oSdyAm+xVdXrCseKxDHWeuWD0tYat7zxyy2pJYvoNuBX/ho8reUZzqheAGButzKb/hIzGMcihK6SE92+kHaowKINkMqBN91cYKpz+UlHb4XHjXAUds59wTyaQ8T7D/rhGhTrGCdDZ1yePr83LWJAFAMQjP3BLK1hAnGR2NN0mrjLMIe9p3Srm1SHxeLfHKDjTNzfhwZAx6UD0qwp/53HoEz8uYQKBgQDQkkj895pBG6G3WLX25pZETOXi8BOdOBWc/BMqLwmTGY6Dec8leMqAYyFv4J84MhrfrwYFN4tvDUiLzJAjJPbEALuC2GvcuUeKizNQ1UhoXmALKRZTz2nhTTQ/vExW+y5KASgOpCeoO0jrsiU/HON+up2dpYVBUaNUP3dwTSqgEwKBgQCiA8TlzR5sLWyxamOxbUKMUIKYMSW2iU6w6CGBEgLLque5JCoW/aI+3BqXLClkAAhvrVq1F8RDnfHKFGYnCZHNv53cqZen/8/N9YT1cvOQrF6fjI10/ckAC9NhK+nZB3znw/DVNQHGns1wOJ/KSJsBx0Utif0g+LNeBB5Jq7fnpwJ/aRlNrisWj7hcxQbuqnuHPOOC89gGCjM4sOJUxezt5jH6nkXDHB7B6/AH6VhG4wpcgFqxre9PU6Zd6BQsjYkSz7GOy2BC+fZjFm0Obq0Kz4y+2ODbX2Ak+mLs8TDxRuNLb/8lMDuHwD4fgKVEH4lvGWzv/4BopYF9y/YvOWepEwKBgQCXX/ArSlBfgL2qsp29HDnughbS/jCfcYFNt0X+o4ZrJx7lFE4jNw3Nr7dEgeWWYpV/SriStUcSalOMnSyo0r8hPyTPFMH3HQPbCsgU0TeTeAndBuTwZ2LTITl59B7FQcLJhYDSpHkpTBzk+nMJ/Haf3cDcAK66jMSCVlS6PrBQxwKBgFySghzSJc2/xUm/IKmuoFqxq4XBi1jVrqwVp+N1+BkReYnFw0uWzLL0sCdbv2PsJkUJEYfT0ZqrQoY7uRgGRSKUg77OAB/nkIMDW8IQUMGoJzsyLl/3r9iQyNbZEE1OZUXo3jlYBqKBzT3Ju4jcE2V0NngJGzIlklEP6Zhz40Fi";
	//支付宝公钥RSA（SHA256）
	public static final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmyKV79HER3bJPjVaoOZ3gRycX4TnsDdnRYrhZQy1RjWRvm6yA6fQRZu6LuzGWDw9YEuZQkrQ3W6koxJnOdI5s0/swRtdbpL7wpyRxxZL3M2DW9fhBSkRevIhxeR00u9tSE5fEd6sNul+yG73HmTNgT9nqdHmalY/daON0e+l9aC08jGhZYye1QPErFFD/rTqqELi48NsYPykfXXrcwXgApZcagZKCDoube47AhqKBYd2Mwsdp3H9cUtG4Cp1SRHKSA+PN4h3+SpTcnSo6HvZBlqCfiT0/yhnsO+jCRUwtOup1/9/yrIcT/ufHYBpG9xNujYGlLLVYDfm/sKsL7+1dQIDAQAB";
	//开放平台网关
	public static final String GATEWAY_URL = "https://openapi.alipay.com/gateway.do";
	
	public static final String CHARSET = "UTF-8";
	
	public static final String SIGN_TYPE = "RSA2";
	
	public static final String format = "json";
	
	public static DefaultAlipayClient alipayClient;
	
	
	@PostConstruct
	private void init(){
		this.alipayClient = new DefaultAlipayClient(GATEWAY_URL,APP_ID,PRIVATE_KEY,format,CHARSET,ALIPAY_PUBLIC_KEY,SIGN_TYPE);
	}
	
}
