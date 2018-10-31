package junit;

import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import com.xiaoerzuche.biz.payment.enu.TranType;
import com.xiaoerzuche.biz.payment.model.PayOrder;
import com.xiaoerzuche.biz.payment.service.PayOrderService;
import com.xiaoerzuche.biz.payment.util.HttpClientUtils;
import com.xiaoerzuche.biz.payment.vo.CallbackVo;
import com.xiaoerzuche.biz.payment.vo.QueryVo;
import com.xiaoerzuche.common.util.Base64;
import com.xiaoerzuche.common.util.HttpUtils;

import net.sf.json.JSONObject;

public class junitTest extends BaseTest{
	@Autowired
	private PayOrderService payOrderService;

	// @Autowired
	private RestTemplate restTemplate;

	// @Test
	public void test() {
		Map<String, String> map = new HashMap<String, String>();
		// String callbackUrl =
		// "http://s-www.go711.net/hourlyRate/api/v1.0/pay/callback";
		String callbackUrl = "http://192.168.1.104:8080/hourlyRate-web/api/v1.0/pay/callback";
		String data = "{\"code\":200,\"msg\":\"交易成功\",\"orderId\":\"30146333187768810456\",\"queryId\":\"14633318547623992\",\"tranType\":1,\"amount\":1,\"result\":5,\"timeStamp\":\"1463331895384\",\"sign\":\"AC965E606F2DE6FE023B7CA582B8A8B1\"}";
		map.put("data", data);
		String callbackJson = HttpUtils.doPost(callbackUrl, map);
		System.out.println(callbackJson);
		// QueryVo vo = new QueryVo("15480282579", "8989898989898989100236",
		// null, TranType.REFUND.getCode());
		// PayOrder order = payOrderService.getPayOrderBy(vo);
		// System.out.println(order);
	}

	public static void main(String[] args) {
		String a = "sa123 ";
		String b = "sa123";
		a = Base64.encode(a);
		b = Base64.encode(b);
		System.out.println(a);
		System.out.println(b);
		a = Base64.decode(a);
		b = Base64.decode(b);
		System.out.println(a + "=");
		System.out.println(b + "=");
		/**
		 * Map<String, String> map = new HashMap<String, String>(); String
		 * callbackUrl =
		 * "http://s-www.go711.net/hourlyRate/api/v1.0/pay/callback"; // String
		 * callbackUrl =
		 * "http://192.168.0.158:8080/hourlyRate-web/api/v1.0/pay/callback";
		 * 
		 * // CallbackVo callbackVo = new CallbackVo(code, msg, orderId,
		 * queryId, tranNo, tranType, amount, result, timeStamp, sign); String
		 * data =
		 * "{\"code\":200,\"msg\":\"交易成功\",\"orderId\":\"30146333187768810456\",\"queryId\":\"14633318547623992\",\"tranType\":1,\"amount\":1,\"result\":5,\"timeStamp\":\"1463331895384\",\"sign\":\"AC965E606F2DE6FE023B7CA582B8A8B1\"}";
		 * map.put("data", data); String callbackJson =
		 * HttpUtils.doPost(callbackUrl, map, -1, -1);
		 * System.out.println(callbackJson);
		 * 
		 * try { //
		 * "{\"timeStamp\":\"1470039541891\",\"nonceStr\":\"C0102ED3489\",\"paySign\":\"D58940B50F0E99A310F2430821ED2996\",\"prepayId\":\"14700395428371460\",\"pwd\":\"123456\"}"
		 * //
		 * "timeStamp=1470039541891&nonceStr=C0102ED3489&paySign=D58940B50F0E99A310F2430821ED2996&prepayId=14700395428371460&pwd=123456"
		 * // String result =
		 * junitTest.post("http://localhost:8999/new-energy/api/wallet/pay", //
		 * "{\"timeStamp\":\"1470039541891\",\"nonceStr\":\"C0102ED3489\",\"paySign\":\"D58940B50F0E99A310F2430821ED2996\",\"prepayId\":\"14700395428371460\",\"pwd\":\"123456\"}",
		 * // MediaType.APPLICATION_JSON_VALUE, "UTF-8", -1, -1); //
		 * System.out.println(result); } catch (ConnectTimeoutException e) { //
		 * TODO Auto-generated catch block e.printStackTrace(); } catch
		 * (SocketTimeoutException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } catch (Exception e) { // TODO Auto-generated
		 * catch block e.printStackTrace(); }
		 */
	}

	 private String url = "http://localhost:8999/new-energy/api/wallet/pay";
	// private String url =
	// "http://localhost:8999/new-energy/api/wallet/modify/18289767660/888888";
	// private String url =
	// "http://localhost:8999/new-energy/api/wallet/set/18289767660/123456";
	// private String url =
	// "http://localhost:8999/new-energy/api/wallet/reset/18289767660/123456";

	// private String url = "http://s-www.go711.net/newEnergy/api/wallet/pay";
	// private String url = "http://s-m.go711.net/newEnergy/api/wallet/pay";

	// private String url =
	// "http://s-www.go711.net/newEnergy/api/wallet/set/18289767660/123456";
	// private String url =
	// "http://s-www.go711.net/newEnergy/api/wallet/modify/18289767660/123456?oldPwd=888888";
	// private String url =
	// "http://localhost:8999/new-energy/api/wallet/reset/18289767660/123456/316733";
	// private String url =
	// "http://192.168.199.226:8999/new-energy/api/wallet/reset/18289767660/123456/316733";
	@Test
	public void post2() {
		Map<String, String> map = new HashMap<String, String>();
		// map.put("sign", "1A66CA7CB038A5AEF279D744D5F24D70");
		//{"timeStamp":"1472435520292","nonceStr":"DAD66E02CA1","paySign":"3BFCB7F6A5D93F28C3E09062363D884F","prepayId":"14722033168746478354","price":0.01,"paydetail":"电子钱包支付测试"}

		map.put("timeStamp", "1472435520292");
		map.put("prepayId", "14722033168746478354");
		map.put("nonceStr", "DAD66E02CA1");
		map.put("paySign", "3BFCB7F6A5D93F28C3E09062363D884F");
		map.put("pwd", "888888");
		JSONObject jsonObject = JSONObject.fromObject(map);
		HttpEntity<String> formEntity = new HttpEntity<String>(jsonObject.toString(),
				getHeader(MediaType.APPLICATION_JSON));
		restTemplate = new RestTemplate();
		HttpEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, formEntity, String.class);
		System.out.println(response);
	}

	// @Test
	public void testSetPWD() {
		// String setPWDurl =
		// "http://s-www.go711.net/newEnergy/api/wallet/set/18289767660/123456";
		String setPWDurl = "http://localhost:8999/new-energy/api/wallet/set/18289237003/123456";
		Map<String, String> map = new HashMap<String, String>();
		// map.put("sign", "1A66CA7CB038A5AEF279D744D5F24D70");
		// {"timeStamp":"1472434984172","nonceStr":"54265AB0970","paySign":"BCD559AEDF01CB7CB419719E0E2FEFDC","prepayId":"14722033168746478189","price":0.01,"paydetail":"电子钱包支付测试"}
		map.put("timeStamp", "1472434984172");
		map.put("prepayId", "14722033168746478189");
		map.put("nonceStr", "54265AB0970");
		map.put("paySign", "BCD559AEDF01CB7CB419719E0E2FEFDC");
		map.put("pwd", "888888");
		JSONObject jsonObject = JSONObject.fromObject(map);
		HttpEntity<String> formEntity = new HttpEntity<String>(jsonObject.toString(),
				getHeader(MediaType.APPLICATION_JSON));
		restTemplate = new RestTemplate();
		HttpEntity<String> response = restTemplate.exchange(setPWDurl, HttpMethod.PUT, null, String.class);
	}

	
	private HttpHeaders getHeader(MediaType mediaType) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(mediaType);
		return headers;
	}

}
