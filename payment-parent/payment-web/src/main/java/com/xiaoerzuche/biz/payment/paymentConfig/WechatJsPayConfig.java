package com.xiaoerzuche.biz.payment.paymentConfig;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.antgroup.zmxy.openplatform.api.DefaultZhimaClient;

@Component
public class WechatJsPayConfig {
	private static final Logger logger = LoggerFactory.getLogger(WechatJsPayConfig.class);
	// 商品描述
	public static final String body = "开开出行交易商品";
	public static final String attach = "附带信息";
	public static final String success_code = "SUCCESS";
	// app支付
	public static final String app_trade_type = "APP";
	// 公众号支付
	public static final String jsapi_trade_type = "JSAPI";
	// 扫码支付
	public static final String native_trade_type = "NATIVE";
	// 下单
	public static final String create_order_url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	// 退款
	public static final String refund_order_url = "https://api.mch.weixin.qq.com/secapi/pay/refund";
	
	// 微信返回结果码
	public static final String result_code = "result_code";
	// 微信返回码
	public static final String return_code = "return_code";
	
	public static String appId;
	public static String appSecret;
	public static String partner;
	public static String partnerKey;
	public static String certPath;
	public static String certPassword;
	
	@Value("${profile}")
	private String profile;
	
	@Value("${prod_wechat_publicNumber_appId}")
	private String prodAppId;
	@Value("${prod_wechat_publicNumber_appSecret}")
	private String prodAppSecret;
	@Value("${prod_wechat_publicNumber_partner}")
	private String prodPartner;
	@Value("${prod_wechat_publicNumber_partner_key}")
	private String prodPartnerKey;
	@Value("${prod_wechat_publicNumber_cert_path}")
	private String prodCertPath;
	@Value("${prod_wechat_publicNumber_cert_password}")
	private String prodCertPassword;
	
	@PostConstruct
	private void init(){
		this.appId = this.prodAppId;
		this.appSecret = this.prodAppSecret;
		this.partner = this.prodPartner;
		this.partnerKey = this.prodPartnerKey;
		this.certPath = this.prodCertPath;
		this.certPassword = this.prodCertPassword;
    	logger.info("[微信公众号初始化][运行环境][profile={}][appId={}][appSecret={}][partner={}][partnerKey={}][certPath={}][certPassword={}]"
    			,new Object[]{profile,appId,appSecret,partner,partnerKey,certPath,certPassword});
	}
}
