package junit;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import com.xiaoerzuche.biz.payment.enu.TranStatus;
import com.xiaoerzuche.biz.payment.util.MD5Util;
import com.xiaoerzuche.biz.payment.vo.CallbackVo;
import com.xiaoerzuche.biz.zmxy.util.RestClien;
import com.xiaoerzuche.common.util.HttpUtils;
import com.xiaoerzuche.common.util.JsonUtil;

import net.sf.json.JSONObject;

public class CallBackTest extends BaseTest{
	
//	@Test
	public void callbackToBusinesses(){
		try {
//			String callbackUrl = "http://www-test.95071222.net/pay/paymentGatwayWeb/callBack.ihtml";
			String callbackUrl = "https://www-test.95071222.net/pay/paymentGatwayWeb/callBack.ihtml";
			String timeStamp = Long.toString(System.currentTimeMillis());
			String apiKey = "acb1a823bb8e61558ed616d014643a58";
			String sign = MD5Util.getAuthSign(timeStamp, "d7cfdda6a57", apiKey);
			
			CallbackVo callbackVo = new CallbackVo(200, "交易成功", "096907987171027092728Z", "096907987171027092728", "15090677579841683",  "15090677691545287", 1,
					7, 102200, TranStatus.SUCCESS.getCode(), timeStamp, sign);
			Map<String, String> map = new HashMap<String, String>();
			String data = JsonUtil.toJson(callbackVo);
			map.put("data", data);
			String callbackJson = HttpUtils.doPost(callbackUrl, map, -1, 20000);
			System.out.println(callbackJson);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Autowired
	private RestClien restClien;
	@Test
	public void test(){
		String callbackUrl = "https://www-test.95071222.net/gateway/invoice/rpc/v1.0/pay/callback";
		String timeStamp = Long.toString(System.currentTimeMillis());
		String apiKey = "acb1a823bb8e61558ed616d014643a58";
		String sign = MD5Util.getAuthSign(timeStamp, "d7cfdda6a57", apiKey);
		CallbackVo callbackVo = new CallbackVo(200, "交易成功", "096907987171027092728Z", "096907987171027092728", "15090677579841683",  "15090677691545287", 1,
				7, 102200, TranStatus.SUCCESS.getCode(), timeStamp, sign);
		String callbackJson = restClien.restClienForJson(callbackUrl, callbackVo, MediaType.APPLICATION_JSON, HttpMethod.POST);
		System.out.println(callbackJson);
		JSONObject jsonObject = JSONObject.fromObject(callbackJson);
		boolean isSucess = "200".equals(jsonObject.getString("code"));//如果业务接收不成功也返回失败
		System.out.println(isSucess);
	}

}
