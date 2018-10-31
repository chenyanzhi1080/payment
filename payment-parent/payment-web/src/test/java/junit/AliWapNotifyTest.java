package junit;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.reflect.TypeToken;
import com.xiaoerzuche.biz.payment.alipayment.AliPaymentStrategy;
import com.xiaoerzuche.common.util.JsonUtil;

import net.sf.json.JSONObject;

public class AliWapNotifyTest extends BaseTest{
	
	private static final String HTTPS_VERIFY_URL = "https://mapi.alipay.com/gateway.do?service=notify_verify&";
	
	public static void main(String[] args){
		try {
			AliPaymentStrategy aliPaymentStrategy = new AliPaymentStrategy();
			Map<String, String> params = new HashMap<String, String>();
			String jsonStr = "{'sign'='0397266fc4a819a0c0319e9fb92a5a2a', 'v'='1.0', 'sec_id'='MD5', 'notify_data'='<notify><payment_type>1</payment_type><subject>xiaoerzuche order</subject><trade_no>2017010921001004250210347673</trade_no><buyer_email>18289767660</buyer_email><gmt_create>2017-01-09 10:08:56</gmt_create><notify_type>trade_status_sync</notify_type><quantity>1</quantity><out_trade_no>14839277797473397</out_trade_no><notify_time>2017-01-09 10:29:41</notify_time><seller_id>2088811891419125</seller_id><trade_status>TRADE_SUCCESS</trade_status><is_total_fee_adjust>N</is_total_fee_adjust><total_fee>0.01</total_fee><gmt_payment>2017-01-09 10:08:57</gmt_payment><seller_email>xiaoerzuche@hnair.com</seller_email><price>0.01</price><buyer_id>2088702643769259</buyer_id><notify_id>6add884498a74b2606ba2ec2de5d646hxi</notify_id><use_coupon>N</use_coupon></notify>', 'service'='alipay.wap.trade.create.direct'}";
			System.out.println(jsonStr);
			JSONObject jsonObject = JSONObject.fromObject(jsonStr);
			String json = jsonObject.toString();
			System.out.println(json);
			params = JsonUtil.fromJson(json, new TypeToken<Map<String, String>>(){});
			System.out.println(params);
			Map<String, String> valideData = aliPaymentStrategy.getPaymentBackNotifaction(params);
			System.out.println(valideData);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
