package junit.redirect;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.xiaoerzuche.biz.payment.service.PayAuthService;
import com.xiaoerzuche.biz.payment.util.DESUtil;
import com.xiaoerzuche.biz.payment.vo.PayVo;
import com.xiaoerzuche.common.enu.ErrorCode;
import com.xiaoerzuche.common.util.CheckUtil;
import com.xiaoerzuche.common.util.JsonUtil;

import junit.BaseTest;
import net.sf.json.JSONObject;

public class RedirectTest extends BaseTest{
	
	@Autowired
	private PayAuthService payAuthService;
	
	@Test
	public void test() {
		try {
			String localKey = "xiaoer_payment";
			String payParam = "b3d164eed850eb3604055a11cfb88616e024f63f5f16cdbf51ac6ab30b893be8e95316e956858e565bd943a1057349ecf681792de5325eb235d75ec4a30a23b005ea3d0dce9ff5849ebcc460f62ce2bb27cb8ee7ddf7d8b75bb0f218e82651bc369560e7d475c413cdc64f95dbc52a87fbc7675c23dd1b09785447342afa4eb39f196beb2c06668f6693c62237d9f55e43140678814f261e755c44efd64bb75d9219d6f95de8cf4c4ac2ac4399a538353ef114df6ca77d0b17d0ec1b9552dfd6bda9e4829383e56f7e6faf2a6cfd4c411396279640abf1996442553f2a347e2db237de5e0b80032e12557603d5f6421e928672c9f86d505bda2f634bffe8d908af88cada3dd058809d70e34d1aa857de29082c3c128186e5c2e3bc2fe98d3efffae5c489ed3602d0cf282ff3c49bf9dd3c447f2e93d6efdb384ff4f63ff5bc1cd9ce08c3c686f9018f02c29def4ab3646d4b4bb0fb068e80182b1466d7a4d0b4b33ec2249211a68a";
			String payParamJson = DESUtil.decrypt(payParam,localKey);
			System.out.println(payParamJson);
			
			JSONObject jsonObject = JSONObject.fromObject(payParamJson);
			String appid = jsonObject.getString("appid");
			payParamJson = jsonObject.getString("payParam");
			String apiKey = payAuthService.get(appid).getApiKey();
			CheckUtil.assertNotBlank(apiKey, ErrorCode.LIMIT.getErrorCode(), "appid未授权，请联系管理员");
			PayVo payVo = JsonUtil.fromJson(payParamJson, PayVo.class);
			
			System.out.println(JsonUtil.toJson(payVo));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
