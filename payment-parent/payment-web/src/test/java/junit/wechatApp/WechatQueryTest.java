package junit.wechatApp;

import java.util.ArrayList;
import java.io.StringReader;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.parser.json.JsonConverter;
import com.xiaoerzuche.biz.payment.model.PayOrder;
import com.xiaoerzuche.biz.payment.paymentConfig.WechatPayConfig;
import com.xiaoerzuche.biz.payment.util.HttpClientUtils;
import com.xiaoerzuche.biz.payment.util.MD5Util;
import com.xiaoerzuche.biz.payment.util.TenpayUtil;
import com.xiaoerzuche.biz.payment.util.XmlUtil;
import com.xiaoerzuche.biz.payment.wechatpayment.OrderQueryResultBo;
import com.xiaoerzuche.common.util.JsonUtil;

import junit.BaseTest;

public class WechatQueryTest extends BaseTest{
	
//	@Test
	public void refundOrderQuery(){
		String url = "https://api.mch.weixin.qq.com/pay/refundquery";
		PayOrder order = new PayOrder();
		order.setQueryId("14837789861206704");
//		order.setTranNo("4003282001201707110146971540");
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		String nonceStr = getNonceStr();
		packageParams.put("appid", WechatPayConfig.APP_ID); // 公众号唯一标识符
		packageParams.put("mch_id", WechatPayConfig.PARTNER); // 商户号
		packageParams.put("nonce_str", nonceStr);// 随机字符串
		if(StringUtils.isNotBlank(order.getQueryId())){
			packageParams.put("out_trade_no", order.getQueryId()); // 商户订单号
		}
		if(StringUtils.isNotBlank(order.getTranNo())){
			packageParams.put("out_refund_no", order.getTranNo()); // 流水号
		}
		String sign = TenpayUtil.createSign(packageParams, WechatPayConfig.PARTNER_KEY);
		StringBuffer xml = new StringBuffer();
		xml.append("<xml>")
		.append("<appid>").append(WechatPayConfig.APP_ID).append("</appid>")
		.append("<mch_id>").append(WechatPayConfig.PARTNER).append("</mch_id>")
		.append("<nonce_str>").append(nonceStr).append("</nonce_str>");
		if(StringUtils.isNotBlank(order.getQueryId())){
			xml.append("<out_trade_no>").append(order.getQueryId()).append("</out_trade_no>");
		}
		if(StringUtils.isNotBlank(order.getTranNo())){
			xml.append("<out_refund_no>").append(order.getTranNo()).append("</out_refund_no>");
		}
		xml.append("<sign>").append(sign).append("</sign>")
		.append("</xml>");
		try {
			String resp = HttpClientUtils.getInstance().sendPost(url, xml.toString());
			System.out.println(resp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 */
	@Test
	public void payOrderQuery(){
		String url = "https://api.mch.weixin.qq.com/pay/orderquery";
		PayOrder order = new PayOrder();
		order.setQueryId("15144975138259765");
//		order.setTranNo("4003282001201707110146971540");
		
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		String nonceStr = getNonceStr();
		packageParams.put("appid", WechatPayConfig.APP_ID); // 公众号唯一标识符
		packageParams.put("mch_id", WechatPayConfig.PARTNER); // 商户号
		packageParams.put("nonce_str", nonceStr);// 随机字符串
		if(StringUtils.isNotBlank(order.getQueryId())){
			packageParams.put("out_trade_no", order.getQueryId()); // 商户订单号
		}
		if(StringUtils.isNotBlank(order.getTranNo())){
			packageParams.put("transaction_id", order.getTranNo()); // 流水号
		}
//		String sign = TenpayUtil.createSign(packageParams, WechatPayConfig.PARTNER_KEY);
		StringBuffer sb = new StringBuffer();
		List<String> keys = new ArrayList<String>(packageParams.keySet());
        Collections.sort(keys);
		for(String key: keys){
			String v = (String) packageParams.get(key);
			if (null != v && !"".equals(v) && !"sign".equals(key) && !"key".equals(key)) {
				sb.append(key + "=" + v + "&");
			}
		}
		sb.append("key=" + WechatPayConfig.PARTNER_KEY);
		String sign = MD5Util.MD5Encode(sb.toString(), "UTF-8").toUpperCase();
		StringBuffer xml = new StringBuffer();
		xml.append("<xml>")
		.append("<appid>").append(WechatPayConfig.APP_ID).append("</appid>")
		.append("<mch_id>").append(WechatPayConfig.PARTNER).append("</mch_id>")
		.append("<nonce_str>").append(nonceStr).append("</nonce_str>");
		if(StringUtils.isNotBlank(order.getQueryId())){
			xml.append("<out_trade_no>").append(order.getQueryId()).append("</out_trade_no>");
		}
		if(StringUtils.isNotBlank(order.getTranNo())){
			xml.append("<transaction_id>").append(order.getTranNo()).append("</transaction_id>");
		}
		xml.append("<sign>").append(sign).append("</sign>")
		.append("</xml>");
		try {
			System.out.println("xml:"+xml.toString());
			String resp = HttpClientUtils.getInstance().sendPost(url, xml.toString());
			System.out.println(resp);
			System.out.println("==sign1=="+TenpayUtil.createSign(packageParams, WechatPayConfig.PARTNER_KEY));
			System.out.println("==sign1=="+sign);
			OrderQueryResultBo bo = null;
			try {
				bo = XmlUtil.parseXmlToBean(resp, OrderQueryResultBo.class);
				System.out.println(JsonUtil.toJson(bo));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取随机字符串
	 * 
	 * @return
	 */
	private String getNonceStr() {
		// 随机数
		String currTime = TenpayUtil.getCurrTime();
		// 8位日期
		String strTime = currTime.substring(8, currTime.length());
		// 四位随机数
		String strRandom = String.valueOf(TenpayUtil.buildRandom(4));
		// 10位序列号,可以自行调整。
		return strTime + strRandom;
	}
	
	
	public static void main(String[] args){
		long start = System.currentTimeMillis();
		String xml = "<xml><return_code><![CDATA[SUCCESS]]></return_code>"+
			"<return_msg><![CDATA[OK]]></return_msg>"+
			"<appid><![CDATA[wxe9382b6cbb3510e9]]></appid>"+
			"<mch_id><![CDATA[1316554301]]></mch_id>"+
			"<nonce_str><![CDATA[49n1qxlkjrLjvx52]]></nonce_str>"+
			"<sign><![CDATA[AECB464F53BDF08D89B047FB4F9B7549]]></sign>"+
			"<result_code><![CDATA[SUCCESS]]></result_code>"+
			"<openid><![CDATA[oU7_Bs6qQOubtLTm0BScNVYjIjkA]]></openid>"+
			"<is_subscribe><![CDATA[N]]></is_subscribe>"+
			"<trade_type><![CDATA[APP]]></trade_type>"+
			"<bank_type><![CDATA[CFT]]></bank_type>"+
			"<total_fee>10</total_fee>"+
			"<fee_type><![CDATA[CNY]]></fee_type>"+
			"<transaction_id><![CDATA[4003282001201707110146971540]]></transaction_id>"+
			"<out_trade_no><![CDATA[14997620093815405]]></out_trade_no>"+
			"<attach><![CDATA[附带信息]]></attach>"+
			"<time_end><![CDATA[20170711163333]]></time_end>"+
			"<trade_state><![CDATA[SUCCESS]]></trade_state>"+
			"<cash_fee>10</cash_fee>"+
			"<aaa>10</aaa>"+
			"</xml>";
		
		OrderQueryResultBo bo = null;
		try {
			bo = XmlUtil.parseXmlToBean(xml, OrderQueryResultBo.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/**
		Map<String, String> packageParams = new HashMap<String, String>();
		OrderQueryResultBo bo = null;
		try {
			StringReader read = new StringReader(xml.toString());
			// 创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
			InputSource source = new InputSource(read);
			// 创建一个新的SAXBuilder
			SAXBuilder sb = new SAXBuilder();
			// 通过输入源构造一个Document
			Document doc = (Document) sb.build(source);
			Element root = doc.getRootElement();// 指向根节点
			List<Element> es = root.getChildren();
			if (es != null && es.size() != 0) {
				for (Element element : es) {
					packageParams.put(element.getName(), element.getValue());
				}
			}
			
			JsonConverter converter = new JsonConverter();
				bo = converter.fromJson(packageParams, OrderQueryResultBo.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		long end = System.currentTimeMillis();
        System.out.println("end-start="+(end-start));
        System.out.println(JsonUtil.toJson(bo));
	}
}
