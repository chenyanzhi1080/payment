package com.xiaoerzuche.biz.payment.wechatpayment;


import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.http.conn.ConnectTimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.GsonBuilder;
import com.xiaoerzuche.biz.payment.PaymentStrategy;
import com.xiaoerzuche.biz.payment.common.BackVerification;
import com.xiaoerzuche.biz.payment.common.PaymentConf;
import com.xiaoerzuche.biz.payment.common.PaymentUtil;
import com.xiaoerzuche.biz.payment.enu.TranType;
import com.xiaoerzuche.biz.payment.paymentConfig.WechatJsPayConfig;
import com.xiaoerzuche.biz.payment.util.HttpClientWeChatJSSDKUtils;
import com.xiaoerzuche.biz.payment.util.Sha1Util;
import com.xiaoerzuche.biz.payment.util.TenpayUtil;
import com.xiaoerzuche.common.enu.ErrorCode;
import com.xiaoerzuche.common.exception.ErrorCodeException;
import com.xiaoerzuche.common.util.CheckUtil;

@Service
public class WechatJSPaymentStrategy implements PaymentStrategy {

	private static final Logger logger = LoggerFactory.getLogger(WechatJSPaymentStrategy.class);

	private static String local_server_ip = "127.0.0.1";
	
	@Autowired
	private WechatJsPayConfig wechatJsPayConfig;

	@Override
	public String doPayment(String timeExpire, String account, String openid, String orderId, int price, Integer payType, String frontNotifyUrl,
			String backNotifyUrl) throws Exception {
		String json = "";
		try {
			json = this.jsSdkPyament(timeExpire, orderId, price, wechatJsPayConfig.body, openid,backNotifyUrl);
		} catch (Exception e) {
			throw new ErrorCodeException(ErrorCode.NOENOUGH.getErrorCode() , e.getMessage());
		}
		return json;
	}

	@Override
	public Map<String, String> getPaymentFrontNotifaction(
			Map<String, String> valideData) throws Exception {
		throw new UnsupportedOperationException("不支持该操作");
	}

	@Override
	public Map<String, String> getPaymentBackNotifaction(
			Map<String, String> sortedMap) throws Exception {

		Map<String, String> valideData = new HashMap<String, String>();
		valideData.put(PaymentConf.code_pay, PaymentConf.errorcode);
		logger.info("sortedMap = " + sortedMap);
		// 签名验证
		if (!validateSign((SortedMap<String, String>) sortedMap)) {
			logger.error("签名验证失败");
			return valideData;
		}
		String queryId = sortedMap.get("transaction_id");
		String orderId = sortedMap.get("out_trade_no");
		// total_fee
		String money = sortedMap.get("total_fee");
		String openId = sortedMap.get("openid");
		valideData.put(PaymentConf.queryId_pay, queryId);
		valideData.put(PaymentConf.orderid_name, orderId);
		valideData.put("openid", openId);
		if (wechatJsPayConfig.success_code.equalsIgnoreCase(sortedMap
				.get("result_code"))) {
			// 返回码成功
			valideData.put(PaymentConf.code_pay, PaymentConf.successcode);
			valideData.put(PaymentConf.transaction_money,
					PaymentUtil.changeCentToYuan(money));
		}
		return valideData;
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

	/**
	 * jsSDK支付
	 * 
	 * @param acountName
	 * @param orderId
	 * @return
	 */
	@SuppressWarnings("unused")
	private String jsSdkPyament(String timeExpire, String orderId, int price, String body,
			String openId, String backNotifyUrl) throws Exception {
		logger.info("执行jsSdkPyament,body=" + body + ",openId=" + openId);
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		String nonceStr = PaymentUtil.getNonceStr();
		String partner = wechatJsPayConfig.partner;
		String appId = wechatJsPayConfig.appId;
		String partnerKey = wechatJsPayConfig.partnerKey;
		packageParams.put("appid", appId); // 公众号唯一标识符
		packageParams.put("mch_id", partner); // 商户号
		packageParams.put("nonce_str", nonceStr);// 随机字符串
		packageParams.put("body", body);// 商品描述根据情况修改
		packageParams.put("out_trade_no", orderId); // 订单号
		packageParams.put("attach", wechatJsPayConfig.attach);// 商品描述根据情况修改
		// 这里写的金额为1 分到时修改
		String totalFee = Integer.toString(price);
		packageParams.put("total_fee", totalFee); // 总金额以分为单位，不带小数点
		packageParams.put("spbill_create_ip", local_server_ip); // 服务器IP地址
		packageParams.put("notify_url", backNotifyUrl); // 回调接口地址
		packageParams.put("trade_type", wechatJsPayConfig.jsapi_trade_type); // 支付场景类型
		packageParams.put("openid", openId);
		if(StringUtils.isNotBlank(timeExpire)){
			packageParams.put("time_expire", timeExpire);
		}
		String sign = TenpayUtil.createSign(packageParams, partnerKey);
		String xml = "<xml>" 
					+ "<appid>" + appId + "</appid>" 
					+ "<mch_id>" + partner + "</mch_id>" 
					+ "<nonce_str>" + nonceStr + "</nonce_str>" 
					+ "<sign>" + sign + "</sign>"
					+ "<body><![CDATA[" + body + "]]></body>"
					+ "<out_trade_no>" + orderId + "</out_trade_no>" 
					+ "<attach>" + wechatJsPayConfig.attach + "</attach>" 
					+ "<total_fee>" + totalFee + "</total_fee>" 
					+ "<spbill_create_ip>" + local_server_ip + "</spbill_create_ip>" 
					+ "<notify_url>" + backNotifyUrl + "</notify_url>"
					+ "<trade_type>" + wechatJsPayConfig.jsapi_trade_type + "</trade_type>"
					+ "<openid>" + openId + "</openid>";
		if(StringUtils.isNotBlank(timeExpire)){
			xml	+= "<time_expire>" + timeExpire + "</time_expire>";
		}
		xml	+= "</xml>";
		logger.info("微信js支付组装的报文包xml:" + xml);
		
		String result = HttpClientWeChatJSSDKUtils.getInstance(wechatJsPayConfig.certPath, wechatJsPayConfig.certPassword).sendPost(wechatJsPayConfig.create_order_url, xml);
		logger.info("微信js预支付返回报文:" + result);
		String prepayId = TenpayUtil.getPayNo(result);
		CheckUtil.assertNotNull(prepayId, 201, "请求失败,prepayId is null");
		logger.info("微信js支付预支付编号:" + prepayId);
		// 获取prepayId后，拼接最后请求支付所需要的package
		SortedMap<String, String> finalpackage = new TreeMap<String, String>();
		String timestamp = Sha1Util.getTimeStamp();
		String packages = "prepay_id=" + prepayId;
		finalpackage.put("appId", appId);
		finalpackage.put("timeStamp", timestamp);
		finalpackage.put("nonceStr", nonceStr);
		finalpackage.put("package", packages);
		finalpackage.put("signType", "MD5");
		// 要签名
		String finalsign = TenpayUtil.createSign(finalpackage, partnerKey);
		finalpackage.put("paySign", finalsign);
		logger.info("微信js支付组装的报文包finaPackage:" + finalpackage.toString());
		GsonBuilder gb = new GsonBuilder();
		gb.disableHtmlEscaping();
		String json = gb.create().toJson(finalpackage);
		return json;
	}

	/**
	 * 验证签名
	 * 
	 * @param packageParams
	 *            微信支付系统返回结果
	 * @return 成功或是失败
	 */
	private boolean validateSign(SortedMap<String, String> packageParams) {
		String wechatSign = packageParams.get("sign");
		String mySign = TenpayUtil.createSign(packageParams,
				wechatJsPayConfig.partnerKey);
		if (null != wechatSign && wechatSign.equals(mySign)) {
			return true;
		}
		return false;
	}

	@Override
	public Map<String, String> doRefund(String orderId, String queryId, int price, String refundNo, int refundPrice,
			String frontUrl, String backUrl) throws Exception {
		
		String totalFee = Integer.toString(price);
		String refundFee = Integer.toString(refundPrice);
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		String nonceStr = getNonceStr();
		packageParams.put("appid", wechatJsPayConfig.appId); // 公众号唯一标识符
		packageParams.put("mch_id", wechatJsPayConfig.partner); // 商户号
		packageParams.put("nonce_str", nonceStr);// 随机字符串
		packageParams.put("transaction_id", queryId); // 流水号号
		packageParams.put("op_user_id", wechatJsPayConfig.partner); // 操作员id
		packageParams.put("refund_fee", refundFee); // 退款金额
		packageParams.put("out_refund_no", refundNo); // 退款单号
		packageParams.put("total_fee", totalFee); // 总金额以分为单位，不带小数点
		String sign = TenpayUtil.createSign(packageParams, wechatJsPayConfig.partnerKey);
		String xml =  "<xml>"
					+ "<appid>" + wechatJsPayConfig.appId + "</appid>" 
					+ "<mch_id>" + wechatJsPayConfig.partner + "</mch_id>" 
					+ "<nonce_str>" + nonceStr + "</nonce_str>" 
					+ "<sign>" + sign + "</sign>"
					+ "<transaction_id>" + queryId + "</transaction_id>" 
					+ "<op_user_id>" + wechatJsPayConfig.partner + "</op_user_id>" 
					+ "<refund_fee>" + refundFee + "</refund_fee>" 
					+ "<out_refund_no>" + refundNo + "</out_refund_no>" 
					+ "<total_fee>" + totalFee + "</total_fee>" 
					+ "</xml>";
		logger.info("[微信公众号退款提交表单xml={}]",new Object[]{xml});
		String resp = HttpClientWeChatJSSDKUtils.getInstance(wechatJsPayConfig.certPath, wechatJsPayConfig. certPassword).sendPost(wechatJsPayConfig.refund_order_url, xml);
		logger.info("[orderId={}][微信公众号退款申请响应resp={}]",new Object[]{orderId,resp});
		SortedMap<String, String> xmlMap = BackVerification.parseXmlToMap(resp);
		Map<String, String> valideData = new HashMap<String, String>();
		valideData.put(PaymentConf.code_pay, PaymentConf.errorcode);
		// 签名验证
		if(!validateSign(xmlMap)){
			logger.error("公众号支付退款签名验证失败");
			return valideData;
		}
		if (wechatJsPayConfig.success_code.equalsIgnoreCase(xmlMap.get("result_code"))) {
			// 返回码成功
			valideData.put(PaymentConf.code_pay, PaymentConf.successcode);
			valideData.put(PaymentConf.queryId_pay, xmlMap.get("refund_id"));
			valideData.put(PaymentConf.orderid_name, xmlMap.get("out_refund_no"));
		}else{
			valideData.put(PaymentConf.businessCode, xmlMap.get("err_code"));
			valideData.put(PaymentConf.businessDes, xmlMap.get("err_code_des"));
		}
		logger.info("valideData:"+valideData);
		return valideData;
	}
	
	public void refundquery(String queryId ,String tranNo, int tranType) throws ConnectTimeoutException, SocketTimeoutException, Exception{
		
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		String nonceStr = getNonceStr();
		packageParams.put("appid", wechatJsPayConfig.appId); // 公众号唯一标识符
		packageParams.put("mch_id", wechatJsPayConfig.partner); // 商户号
		packageParams.put("nonce_str", nonceStr);// 随机字符串
//		packageParams.put("out_trade_no", queryId); // 流水号
		packageParams.put("refund_id", tranNo); // 流水号
//		packageParams.put("op_user_id", wechatJsPayConfig.getPartner()); // 操作员id
		String sign = TenpayUtil.createSign(packageParams, wechatJsPayConfig.partnerKey);
		String xml =  "<xml>"
					+ "<appid>" + wechatJsPayConfig.appId + "</appid>" 
					+ "<mch_id>" + wechatJsPayConfig.partner + "</mch_id>" 
					+ "<nonce_str>" + nonceStr + "</nonce_str>" 
					+ "<sign>" + sign + "</sign>"
					+ "<refund_id>" + tranNo + "</refund_id>" 
//					+ "<out_trade_no>" + queryId + "</out_trade_no>" 
					+ "</xml>";
		logger.info("[微信公众号退款查询表单xml={}]",new Object[]{xml});
		String queryUrl = (tranType == TranType.CONSUME.getCode()) ?  "https://api.mch.weixin.qq.com/pay/orderquery" : "https://api.mch.weixin.qq.com/pay/refundquery";
		String resp = HttpClientWeChatJSSDKUtils.getInstance(wechatJsPayConfig.certPath, wechatJsPayConfig.certPassword).sendPost(queryUrl, xml);
		
		
		logger.info("[orderId={}][微信公众号退款查询结果resp={}]",new Object[]{tranNo,resp});
		SortedMap<String, String> xmlMap = BackVerification.parseXmlToMap(resp);
		Map<String, String> valideData = new HashMap<String, String>();
		valideData.put(PaymentConf.code_pay, PaymentConf.errorcode);
//		logger.info("valideData:"+valideData);
//		return valideData;
	}
	
	public void orderquery(String queryId ,String tranNo, int tranType) throws ConnectTimeoutException, SocketTimeoutException, Exception{
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		String nonceStr = getNonceStr();
		packageParams.put("appid", wechatJsPayConfig.appId); // 公众号唯一标识符
		packageParams.put("mch_id", wechatJsPayConfig.partner); // 商户号
		packageParams.put("nonce_str", nonceStr);// 随机字符串
//		packageParams.put("out_trade_no", queryId); // 商户订单号
		packageParams.put("transaction_id", tranNo); // 流水号
//		packageParams.put("op_user_id", wechatJsPayConfig.getPartner()); // 操作员id
		String sign = TenpayUtil.createSign(packageParams, wechatJsPayConfig.partnerKey);
		String xml =  "<xml>"
					+ "<appid>" + wechatJsPayConfig.appId + "</appid>" 
					+ "<mch_id>" + wechatJsPayConfig.partner + "</mch_id>" 
					+ "<nonce_str>" + nonceStr + "</nonce_str>" 
					+ "<sign>" + sign + "</sign>"
					+ "<transaction_id>" + tranNo + "</transaction_id>" 
//					+ "<out_trade_no>" + queryId + "</out_trade_no>" 
					+ "</xml>";
		logger.info("[微信公众号交易查询表单xml={}]",new Object[]{xml});
		String resp = HttpClientWeChatJSSDKUtils.getInstance(wechatJsPayConfig.certPath, wechatJsPayConfig.certPassword).sendPost("https://api.mch.weixin.qq.com/pay/orderquery", xml);
		
		logger.info("[orderId={}][微信公众号退款查询结果resp={}]",new Object[]{tranNo,resp});
		SortedMap<String, String> xmlMap = BackVerification.parseXmlToMap(resp);
		Map<String, String> valideData = new HashMap<String, String>();
		valideData.put(PaymentConf.code_pay, PaymentConf.errorcode);
//		logger.info("valideData:"+valideData);
//		return valideData;
	}
	
	
	@Override
	public Map<String, String> doPwdRefund(String orderId, String queryId, int price, String refundNo, int refundPrice,
			String frontUrl, String backUrl) {
		throw new UnsupportedOperationException("不支持该操作");
	}

	@Override
	public Map<String, String> getRefundBackNotifaction(Map<String, String> valideData) throws Exception {
		throw new UnsupportedOperationException("不支持该操作");
	}
}
