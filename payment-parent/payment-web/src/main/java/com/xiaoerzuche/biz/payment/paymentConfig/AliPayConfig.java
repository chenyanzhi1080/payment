package com.xiaoerzuche.biz.payment.paymentConfig;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *版本：3.3
 *日期：2012-08-10
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
	
 *提示：如何获取安全校验码和合作身份者ID
 *1.用您的签约支付宝账号登录支付宝网站(www.alipay.com)
 *2.点击“商家服务”(https://b.alipay.com/order/myOrder.htm)
 *3.点击“查询合作者身份(PID)”、“查询安全校验码(Key)”

 *安全校验码查看时，输入支付密码后，页面呈灰色的现象，怎么办？
 *解决方法：
 *1、检查浏览器配置，不让浏览器做弹框屏蔽设置
 *2、更换浏览器或电脑，重新登录查询。
 */
public class AliPayConfig {
	
	//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	// 合作身份者ID，以2088开头由16位纯数字组成的字符串
	public static String partner = "2088031732973182";
	// 商户的MD5私钥,目前不需要
	public static String key = "lntuodgqlwy8a4cwbvm8ede1xbsdkgba";

	// 字符编码格式 目前支持 gbk 或 utf-8在线客服
	public static String input_charset = "utf-8";
	public static String quality_certification="ISO-8859-1";
	// 签名方式 不需修改
	public static String sign_type = "MD5";
	// 字符编码格式 目前支持 gbk 或 utf-8在线客服
	public static String seller_email = "fwmy_fm@faw.com.cn";
	
	public static String subject = "开开出行";
	
	// 签名方式 不需修改
	public static String agentid = "11532464a1";
	
	public static String alipay_gateway = "https://wappaygw.alipay.com/service/rest.htm?";
	
	//返回格式
	public static String format = "xml";
	//必填，不需要修改
	
	//返回格式 
	public static String version = "2.0";
	
	public static String trade_status="trade_status";
	
	// 商户的RAS私钥
	public static String rsa_private_key = "MIICeQIBADANBgkqhkiG9w0BAQEFAASCAmMwggJfAgEAAoGBAMOqJlidR/oAgjMoCdOJ9p/+tJp2miE3HGhBoW4DQt6wbWMjTkbT4fa9+bj/OXgx/+BQ2aopBwXyUfNmHPGMX6D5s/COt9S30WRiB2oNLbvkj308IA4NH+jfuCZ8IXfJUrszXD50OEC3nwEtUDtNKyKnGGae+wujIJDo6uvVEd8VAgMBAAECgYEAjMEVDsAuLAk7oGNYFkX0FYqcD1XscKJ9m/5Ci6pq61mNhzi5sbfwoJhvUYbAp9eIE6z1uDQFamC0VV0KyzTJAPfAxHymUaKZmBS5+ztpFyuMh8Eik/N65OL46QcxUMqCwYy9hiKJjjIX04KsAvqHm5k3VrQbg/HB2G4wZ9OlLM0CQQD2bjmZNp/OitkJjjSRS95pq5ri0/AKFCbvSuK7W/eVOckLppfnr03Ejg3NNZl7GT7MhR8HhLUKsWID0NRJ6hVvAkEAy0NCTYboHUiMu+RcRiv+PdSwcWesnwOnaJyBktpih3kVsS/1NVtp/TQ5+s8V7FDKh2t2JB8J+dLRed68Zlm5uwJBAIm/GTEcSH2yPRlLRPUGnJ8ijiRGZYGsvAq8KDqkcKMYNtntWDHoWqcI5IwVbuJRPTojGzIfqvb8KhaJ73AVib8CQQCgR3Ma6wxROwouhw30D3C1lOdlJVlLMnhp1y+dezNCnEpso+J5ppHXJb2qdm1VIQfy7NwF86vMIV8o+GgbVRlNAkEAnXsCkmn91Bio29hUjs9trBri3iUQAg2kpwz9hld8UjghhE6O/AzrAnOKjNJ5GXyGH0Q6AmNSJyZVyQ9nwMiOSw==";
		
	// 支付宝的公钥，无需修改该值
	public static String ali_public_key  = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";

}
