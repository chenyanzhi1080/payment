package com.xiaoerzuche.biz.payment.paymentConfig;


/** 
* 微信支付基础配置，固定不变
* @author: cyz
* @version: payment
* @time: 2016-4-5
* 
*/
public class WechatPayConfig {
	public static String		APP_ID				= "wx4bc65b8f48962ff2";
	public static String		NATIVE_ID			= "wx4bc65b8f48962ff2";// 微信开发平台应用id
//	public static String		APP_SECRET			= "9REdgAsGRE8g9hdy4E720Z2d6tg81E96";
//	public static String		NATIVE_SECRET		= "9REdgAsGRE8g9hdy4E720Z2d6tg81E96";// 应用对应的凭证
	// 应用对应的密钥 这个在开放平台不同的手机应用自行绑定
//	public static String		APP_KEY				= "L8LrMqqeGRxST5reouB0K66CaYAWpqhAVsq7ggKkxHCOastWksvuX1uvmvQclxaHoYd3ElNBrNO2DHnnzgfVG9Qs473M3DTOZug5er46FhuGofumV8H2FVR9qkjSlC5K";
	//商户号
	public static String		PARTNER				= "1502354281";
	public static String		NATIVE_PARTNER		= "1502354281";
	// 财付通商户号
	public static String		PARTNER_KEY			= "9REdgAsGRE8g9hdy4E720Z2d6tg81E96";// 商户号对应的密钥
	public static String		TOKENURL			= "https://api.weixin.qq.com/cgi-bin/token";// 获取access_token对应的url
	public static String		GRANT_TYPE			= "client_credential";// 常量固定值
	public static String		EXPIRE_ERRCODE		= "42001";// access_token失效后请求返回的errcode
	public static String		FAIL_ERRCODE		= "40001";// 重复获取导致上一次获取的access_token失效,返回错误码
	public static String		GATEURL				= "https://api.weixin.qq.com/pay/genprepay?access_token=";// 获取预支付id的接口url
	public static String		ACCESS_TOKEN		= "access_token";// access_token常量值
	public static String		ERRORCODE			= "errcode";// 用来判断access_token是否失效的值
	public static String		SIGN_METHOD			= "sha1";// 签名算法常量值
	public static final String	input_charset		= "GBK";
	// 银行渠道
	public static final String	bank_type			= "WX";
	// 商品描述
	public static final String	body				= "开开出行交易商品";
	public static final String	attach				= "附带信息";
	public static final String	success_code		= "SUCCESS";
	// app支付
	public static final String	app_trade_type		= "APP";
	// 扫码支付
	public static final String	native_trade_type	= "NATIVE";
	public static final String	create_order_url	= "https://api.mch.weixin.qq.com/pay/unifiedorder";

	public static final String	refund_order_url	= "https://api.mch.weixin.qq.com/secapi/pay/refund";

	public static String		certLocalPath		= "/privateKey/payment/wechatApp/apiclient_cert.p12";
	
	// HTTPS证书密码，默认密码等于商户号MCHID
	public static String		certPassword		= "1502354281";

}
