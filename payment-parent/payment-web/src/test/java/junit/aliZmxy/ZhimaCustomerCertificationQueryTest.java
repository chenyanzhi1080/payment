package junit.aliZmxy;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alipay.api.AlipayApiException;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayUserInfoShareRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayUserInfoShareResponse;
import com.xiaoerzuche.biz.zmxy.config.AliOpendApiClient;
import com.xiaoerzuche.biz.zmxy.service.imp.AliMemberAuthServiceImp;
import com.xiaoerzuche.common.util.JsonUtil;

import junit.BaseTest;

public class ZhimaCustomerCertificationQueryTest extends BaseTest {
	private static final Logger logger = LoggerFactory.getLogger(ZhimaCustomerCertificationQueryTest.class);
	@Autowired
	private AliOpendApiClient aliOpendApiClient;
	@Test
	public void getAliMemberByAuth(){
		String authCode = "4b203fe6c11548bcabd8da5bb087a83b";
		AlipaySystemOauthTokenRequest alipaySystemOauthTokenRequest = new AlipaySystemOauthTokenRequest();
		alipaySystemOauthTokenRequest.setCode(authCode);
		alipaySystemOauthTokenRequest.setGrantType("authorization_code");
		String accessToken = "";
		try {
//			AlipaySystemOauthTokenResponse oauthTokenResponse = aliOpendApiClient.alipayClient.execute(alipaySystemOauthTokenRequest);
//			logger.info("[获取支付宝accessToken结果]{}",JsonUtil.toJson(oauthTokenResponse));
//			if(oauthTokenResponse.isSuccess()){
//				accessToken = oauthTokenResponse.getAccessToken();
//				System.out.println(oauthTokenResponse.getCode());
//			}else{
//				logger.info("==============="+oauthTokenResponse.getCode());
//			}
			accessToken = "composeB454e2c08cbbf408cbc15c3a678fc4X25";
			if(StringUtils.isNotBlank(accessToken)){
				AlipayUserInfoShareRequest alipayUserInfoShareRequest = new AlipayUserInfoShareRequest();
				AlipayUserInfoShareResponse userinfoShareResponse = (AlipayUserInfoShareResponse) aliOpendApiClient.alipayClient.execute(alipayUserInfoShareRequest, accessToken);
//				aliOpendApiClient.alipayClient.execute(alipayUserInfoShareRequest, accessToken);
				logger.info("[查询支付宝会员信息结果]{}",JsonUtil.toJson(userinfoShareResponse));
				if(userinfoShareResponse.isSuccess()){
					System.out.println(userinfoShareResponse.getBody());
					System.out.println(userinfoShareResponse.getCode());
				}else{
					logger.info("==============="+userinfoShareResponse.getCode());
				}
			}
		} catch (AlipayApiException e) {
			logger.info("[支付宝会员信息查询erro]"+e);
		}
	}
	
}
