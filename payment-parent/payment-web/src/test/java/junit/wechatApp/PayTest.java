package junit.wechatApp;
import java.nio.charset.Charset;
import java.security.KeyStore;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.net.ssl.SSLContext;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.GsonBuilder;
import com.xiaoerzuche.biz.payment.util.HttpClientUtils;
import com.xiaoerzuche.biz.payment.util.Sha1Util;
import com.xiaoerzuche.biz.payment.util.TenpayUtil;

public class PayTest {
	public static final int connTimeout = 10000;
	public static final int readTimeout = 10000;
	public static final int connMaxTotal = 5;
	public static final int connDefaultMaxPerRoute = 5;
	public static final String charset = "UTF-8";
	private static volatile HttpClientUtils instance;
	private ConnectionConfig connConfig;
	private SocketConfig socketConfig;
	private ConnectionSocketFactory plainSF;
	private KeyStore trustStore;
	private SSLContext sslContext;
	private Registry<ConnectionSocketFactory> registry;
	private PoolingHttpClientConnectionManager connManager;
	private volatile HttpClient httpCient;
	private volatile BasicCookieStore cookieStore;
	private SSLConnectionSocketFactory sslsf;
	private static final Logger logger = LoggerFactory.getLogger(PayTest.class);
	private static String		local_server_ip	= "127.0.0.1";
	
	@Test
	public void doPay(){
		
		try {
			String json = this.appPayment("", "123456789153", 1, "http://sandbox.expose.95071222.net/api/wechatApp/callBack");
			System.out.println(json);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String APP_ID = "wxc6c9d0f73edb9dc5";
	private String PARTNER = "1483727072";
	private String PARTNER_KEY = "UlSvhj8ozeUqNdtdvc9jDZiKIEj1z9Y1";
	private String body = "测试";
	private String attach = "测试";
	private String app_trade_type="APP";
	private String appPayment(String timeExpire, String orderId, int price, String backNotifyUrl) throws Exception {
		String timeStart = "";//支付订单生成时间,设置超时时间时需要
		SortedMap<String, String> payParams = new TreeMap<String, String>();
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		String nonceStr = getNonceStr();
		packageParams.put("appid", APP_ID); // 公众号唯一标识符
		packageParams.put("mch_id", PARTNER); // 商户号
		packageParams.put("nonce_str", nonceStr);// 随机字符串
		packageParams.put("body", body);// 商品描述根据情况修改
		packageParams.put("attach", attach);// 商品描述根据情况修改
		packageParams.put("out_trade_no", orderId); // 订单号
		// 这里写的金额为1 分到时修改
		String totalFee = Integer.toString(price);
		packageParams.put("total_fee", totalFee); // 总金额以分为单位，不带小数点
		packageParams.put("spbill_create_ip", local_server_ip); // 服务器IP地址
		packageParams.put("notify_url", backNotifyUrl); // 回调接口地址
		packageParams.put("trade_type", app_trade_type); // 支付场景类型
		if(StringUtils.isNotBlank(timeExpire)){
			packageParams.put("time_expire", timeExpire);
		}
		String sign = TenpayUtil.createSign(packageParams, PARTNER_KEY);
		String xml = "<xml>" 
						+ "<appid>" + APP_ID + "</appid>" 
						+ "<mch_id>" + PARTNER + "</mch_id>"
						+ "<nonce_str>" + nonceStr + "</nonce_str>"
						+ "<sign>" + sign + "</sign>"
						+ "<body><![CDATA[" + body + "]]></body>" 
						+ "<out_trade_no>" + orderId + "</out_trade_no>" 
						+ "<attach>" + attach + "</attach>" 
						+ "<total_fee>" + totalFee + "</total_fee>" 
						+ "<spbill_create_ip>" + local_server_ip + "</spbill_create_ip>"
						+ "<notify_url>" + backNotifyUrl + "</notify_url>" 
						+ "<trade_type>" + app_trade_type + "</trade_type>";
		if(StringUtils.isNotBlank(timeExpire)){
			xml	+= "<time_expire>" + timeExpire + "</time_expire>";
		}
		xml	+= "</xml>";
		String result = "";
		// 设置连接参数
		connConfig = ConnectionConfig.custom()
				.setCharset(Charset.forName(charset)).build();
		socketConfig = SocketConfig.custom().setSoTimeout(connTimeout).build();
		RegistryBuilder<ConnectionSocketFactory> registryBuilder = RegistryBuilder
				.<ConnectionSocketFactory> create();
		plainSF = new PlainConnectionSocketFactory();
		registryBuilder.register("http", plainSF);
		// 指定cookie存储对象
		cookieStore = new BasicCookieStore();
		// 构建客户端
		httpCient = HttpClientBuilder.create()
				.setDefaultCookieStore(cookieStore)
				.setConnectionManager(connManager).build();
		
		HttpPost post = new HttpPost("https://api.mch.weixin.qq.com/pay/unifiedorder");
		// 得指明使用UTF-8编码，否则到API服务器XML的中文不能被成功识别
		StringEntity postEntity = new StringEntity(xml, "UTF-8");
		post.addHeader("Content-Type", "text/xml");
		post.setEntity(postEntity);
		try {
			Builder customReqConf = RequestConfig.custom();
			customReqConf.setConnectTimeout(connTimeout);
			customReqConf.setSocketTimeout(readTimeout);
			post.setConfig(customReqConf.build());
			HttpResponse res = httpCient.execute(post);
			result = IOUtils.toString(res.getEntity().getContent(), charset);
		} finally {
			post.releaseConnection();
		}
		System.out.println(result);
		
		String prepayId = TenpayUtil.getPayNo(result);
		logger.info("微信APP支付预支付编号:" + prepayId);
		// 提交支付--开始
		if (null != prepayId && !"".equals(prepayId)) {
			payParams.put("appid", APP_ID);
			payParams.put("noncestr", getNonceStr());
			payParams.put("package", "Sign=WXPay");
			payParams.put("partnerid",PARTNER);
			payParams.put("prepayid", prepayId);
			payParams.put("timestamp", Sha1Util.getTimeStamp());
			// 生成签名
			sign = TenpayUtil.createSign(payParams, PARTNER_KEY);
			payParams.put("sign", sign);
		}
		// 提交支付--结束
		GsonBuilder gb = new GsonBuilder();
		gb.disableHtmlEscaping();
		String json = gb.create().toJson(payParams);
		return json;
	}
	
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
}
