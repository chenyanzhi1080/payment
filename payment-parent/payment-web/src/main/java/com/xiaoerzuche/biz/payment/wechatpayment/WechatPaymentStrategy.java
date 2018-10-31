package com.xiaoerzuche.biz.payment.wechatpayment;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.http.conn.ConnectTimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.gson.GsonBuilder;
import com.xiaoerzuche.biz.payment.PaymentStrategy;
import com.xiaoerzuche.biz.payment.common.BackVerification;
import com.xiaoerzuche.biz.payment.common.PaymentConf;
import com.xiaoerzuche.biz.payment.common.PaymentUtil;
import com.xiaoerzuche.biz.payment.enu.PayType;
import com.xiaoerzuche.biz.payment.model.PayOrder;
import com.xiaoerzuche.biz.payment.paymentConfig.WechatPayConfig;
import com.xiaoerzuche.biz.payment.util.HttpClientUtils;
import com.xiaoerzuche.biz.payment.util.MD5Util;
import com.xiaoerzuche.biz.payment.util.Sha1Util;
import com.xiaoerzuche.biz.payment.util.TenpayUtil;
import com.xiaoerzuche.biz.payment.util.XmlUtil;
import com.xiaoerzuche.common.exception.ErrorCodeException;

@Service
public class WechatPaymentStrategy implements PaymentStrategy {

	private static final Logger logger = LoggerFactory.getLogger(WechatPaymentStrategy.class);
	private static String		local_server_ip	= "127.0.0.1";

	@Override
	public String doPayment(String timeExpire, String account, String openid, String orderId, int price, Integer payType, String frontNotifyUrl, String backNotifyUrl)
					throws Exception {
		
		switch (PayType.getPayType(payType)) {
		case WEIXINAPPPAY:
			return this.appPayment(timeExpire, orderId, price, backNotifyUrl);
/*		case WEIXINNATIVEPAY:
			return this.scan2DBarCodePayment(orderId, price, backNotifyUrl);*/
		default:
			throw new ErrorCodeException(405,"微信支付组件不支持payType="+payType);
		}
		
	}

	@Override
	public Map<String, String> getPaymentFrontNotifaction(Map<String, String> valideData) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> getPaymentBackNotifaction(Map<String, String> sortedMap) throws Exception {

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
		valideData.put(PaymentConf.queryId_pay, queryId);
		valideData.put(PaymentConf.orderid_name, orderId);
		if (WechatPayConfig.success_code.equalsIgnoreCase(sortedMap.get("result_code"))) {
			// 返回码成功
			valideData.put(PaymentConf.code_pay, PaymentConf.successcode);
			valideData.put(PaymentConf.transaction_money, PaymentUtil.changeCentToYuan(money));
		}
		return valideData;
	}
/**
	// 扫码支付的退款
	@Override
	public Map<String, String> doRefund(String orderId, String queryId, int price, String refundNo,
			int refundPrice, String frontUrl, String backUrl) throws Exception {
//		String totalFee = doubleToIntCent(price);
//		String refundFee = doubleToIntCent(refundPrice);
		String totalFee = Integer.toString(price);
		String refundFee = Integer.toString(refundPrice);
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		String nonceStr = getNonceStr();
		packageParams.put("appid", WechatPayConfig.NATIVE_ID); // 公众号唯一标识符
		packageParams.put("mch_id", WechatPayConfig.NATIVE_PARTNER); // 商户号
		packageParams.put("nonce_str", nonceStr);// 随机字符串
		packageParams.put("out_trade_no", orderId); // 订单号
		packageParams.put("op_user_id", WechatPayConfig.PARTNER); // 操作员id
		packageParams.put("refund_fee", refundFee); // 退款金额
		packageParams.put("out_refund_no", refundNo); // 退款单号
		packageParams.put("total_fee", totalFee); // 总金额以分为单位，不带小数点
		String sign = TenpayUtil.createSign(packageParams, WechatPayConfig.PARTNER_KEY);
		String xml = "<xml>" + "<appid>" + WechatPayConfig.NATIVE_ID + "</appid>" + "<mch_id>"
						+ WechatPayConfig.NATIVE_PARTNER + "</mch_id>" + "<nonce_str>" + nonceStr + "</nonce_str>"
						+ "<sign>" + sign + "</sign>" + "<out_trade_no>" + orderId + "</out_trade_no>" + "<op_user_id>"
						+ WechatPayConfig.PARTNER + "</op_user_id>" + "<refund_fee>" + refundFee + "</refund_fee>"
						+ "<out_refund_no>" + refundNo + "</out_refund_no>" + "<total_fee>" + totalFee + "</total_fee>"
						+ "</xml>";
		logger.info(xml);
//		String resp = req.sendPost(WechatPayConfig.refund_order_url, xml);
		String resp = HttpClientUtils.getInstance().sendPost(WechatPayConfig.create_order_url, xml);
		logger.info("resp=" + resp);
		SortedMap<String, String> xmlMap = BackVerification.parseXmlToMap(resp);
		// 获取新的流水号
		queryId = xmlMap.get("transaction_id");
		Map<String, String> valideData = new HashMap<String, String>();
		valideData.put(PaymentConf.code_pay, PaymentConf.errorcode);
		// 签名验证
		if (!validateSign(xmlMap)) {
			logger.error("签名验证失败");
			return valideData;
		}
		if (WechatPayConfig.success_code.equalsIgnoreCase(xmlMap.get("result_code"))) {
			// 返回码成功

			valideData.put(PaymentConf.code_pay, PaymentConf.successcode);
			valideData.put(PaymentConf.queryId_pay, queryId);
		}
		logger.info("valideData=" + valideData);
		return valideData;
	}
*/
	// app支付的退款
	public Map<String, String> doRefund(String orderId, String queryId, int price, String refundNo,
					int refundPrice, String frontUrl, String backUrl) throws Exception {
		String totalFee = Integer.toString(price);
		Integer.toString(price);
		String refundFee = Integer.toString(refundPrice);
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		String nonceStr = getNonceStr();
		packageParams.put("appid", WechatPayConfig.APP_ID); // 公众号唯一标识符
		packageParams.put("mch_id", WechatPayConfig.PARTNER); // 商户号
		packageParams.put("nonce_str", nonceStr);// 随机字符串
//		packageParams.put("out_trade_no", orderId); // 订单号
		packageParams.put("transaction_id", queryId); // 原支付流水号
		packageParams.put("op_user_id", WechatPayConfig.PARTNER); // 操作员id
		packageParams.put("refund_fee", refundFee); // 退款金额
		packageParams.put("out_refund_no", refundNo); // 退款单号
		packageParams.put("total_fee", totalFee); // 总金额以分为单位，不带小数点
		String sign = TenpayUtil.createSign(packageParams, WechatPayConfig.PARTNER_KEY);
		String xml = "<xml>" 
						+ "<appid>" + WechatPayConfig.APP_ID + "</appid>" 
						+ "<mch_id>" + WechatPayConfig.PARTNER + "</mch_id>" 
						+ "<nonce_str>" + nonceStr + "</nonce_str>" 
						+ "<sign>" + sign + "</sign>"
//						+ "<out_trade_no>" + orderId + "</out_trade_no>" 
						+ "<transaction_id>" + queryId + "</transaction_id>" 
						+ "<op_user_id>" + WechatPayConfig.PARTNER + "</op_user_id>" 
						+ "<refund_fee>" + refundFee + "</refund_fee>" 
						+ "<out_refund_no>" + refundNo + "</out_refund_no>" 
						+ "<total_fee>" + totalFee + "</total_fee>" 
					+ "</xml>";
		logger.info("微信app支付退款组装消息:{}",xml);
		String resp = HttpClientUtils.getInstance().sendPost(WechatPayConfig.refund_order_url, xml);
		logger.info("微信app支付退款resp=" + resp);
		SortedMap<String, String> xmlMap = BackVerification.parseXmlToMap(resp);
		Map<String, String> valideData = new HashMap<String, String>();
		valideData.put(PaymentConf.code_pay, PaymentConf.errorcode);
		// 签名验证
		if (!validateSign(xmlMap)) {
			logger.error("微信app支付退款签名验证失败");
			return valideData;
		}
		if (WechatPayConfig.success_code.equalsIgnoreCase(xmlMap.get("result_code"))) {
			// 返回码成功
			valideData.put(PaymentConf.code_pay, PaymentConf.successcode);
			valideData.put(PaymentConf.queryId_pay, xmlMap.get("refund_id"));
			valideData.put(PaymentConf.orderid_name, xmlMap.get("out_refund_no"));
		}else if("FAIL".equals(xmlMap.get("result_code"))){
			valideData.put(PaymentConf.businessCode, xmlMap.get("err_code"));
			valideData.put(PaymentConf.businessDes, xmlMap.get("err_code_des"));
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
	 * app支付
	 * 
	 * @param acountName
	 * @param orderId
	 * @return
	 * @throws IOException
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 * @throws UnrecoverableKeyException
	 */
	private String appPayment(String timeExpire, String orderId, int price, String backNotifyUrl) throws Exception {
		String timeStart = "";//支付订单生成时间,设置超时时间时需要
		SortedMap<String, String> payParams = new TreeMap<String, String>();
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		String nonceStr = getNonceStr();
		packageParams.put("appid", WechatPayConfig.APP_ID); // 公众号唯一标识符
		packageParams.put("mch_id", WechatPayConfig.PARTNER); // 商户号
		packageParams.put("nonce_str", nonceStr);// 随机字符串
		packageParams.put("body", WechatPayConfig.body);// 商品描述根据情况修改
		packageParams.put("attach", WechatPayConfig.attach);// 商品描述根据情况修改
		packageParams.put("out_trade_no", orderId); // 订单号
		// 这里写的金额为1 分到时修改
		String totalFee = Integer.toString(price);
		packageParams.put("total_fee", totalFee); // 总金额以分为单位，不带小数点
		packageParams.put("spbill_create_ip", local_server_ip); // 服务器IP地址
		packageParams.put("notify_url", backNotifyUrl); // 回调接口地址
		packageParams.put("trade_type", WechatPayConfig.app_trade_type); // 支付场景类型
		if(StringUtils.isNotBlank(timeExpire)){
			packageParams.put("time_expire", timeExpire);
		}
		String sign = TenpayUtil.createSign(packageParams, WechatPayConfig.PARTNER_KEY);
		String xml = "<xml>" 
						+ "<appid>" + WechatPayConfig.APP_ID + "</appid>" 
						+ "<mch_id>" + WechatPayConfig.PARTNER + "</mch_id>"
						+ "<nonce_str>" + nonceStr + "</nonce_str>"
						+ "<sign>" + sign + "</sign>"
						+ "<body><![CDATA[" + WechatPayConfig.body + "]]></body>" 
						+ "<out_trade_no>" + orderId + "</out_trade_no>" 
						+ "<attach>" + WechatPayConfig.attach + "</attach>" 
						+ "<total_fee>" + totalFee + "</total_fee>" 
						+ "<spbill_create_ip>" + local_server_ip + "</spbill_create_ip>"
						+ "<notify_url>" + backNotifyUrl + "</notify_url>" 
						+ "<trade_type>" + WechatPayConfig.app_trade_type + "</trade_type>";
		if(StringUtils.isNotBlank(timeExpire)){
			xml	+= "<time_expire>" + timeExpire + "</time_expire>";
		}
		xml	+= "</xml>";
		
		logger.info("微信APP支付订单报文:{}" , xml);
//		String result = req.sendPost(WechatPayConfig.create_order_url, xml);
		String result = HttpClientUtils.getInstance().sendPost(WechatPayConfig.create_order_url, xml);
		logger.info("微信APP支付返回结果:{}" , result);
		String prepayId = TenpayUtil.getPayNo(result);
		logger.info("微信APP支付预支付编号:" + prepayId);
		// 提交支付--开始
		if (null != prepayId && !"".equals(prepayId)) {
			payParams.put("appid", WechatPayConfig.APP_ID);
			payParams.put("noncestr", getNonceStr());
			payParams.put("package", "Sign=WXPay");
			payParams.put("partnerid", WechatPayConfig.PARTNER);
			payParams.put("prepayid", prepayId);
			payParams.put("timestamp", Sha1Util.getTimeStamp());
			// 生成签名
			sign = TenpayUtil.createSign(payParams, WechatPayConfig.PARTNER_KEY);
			payParams.put("sign", sign);
		}
		// 提交支付--结束
		GsonBuilder gb = new GsonBuilder();
		gb.disableHtmlEscaping();
		String json = gb.create().toJson(payParams);
		return json;
	}

	/**
	 * 扫描二维码支付
	 * 
	 * @param acountName
	 * @param orderId
	 * @return
	 * @throws IOException
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 * @throws UnrecoverableKeyException
	 */
	private String scan2DBarCodePayment(String orderId, int price, String backNotifyUrl) throws Exception {
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		String nonceStr = getNonceStr();
		packageParams.put("appid", WechatPayConfig.NATIVE_ID); // 公众号唯一标识符
		packageParams.put("mch_id", WechatPayConfig.NATIVE_PARTNER); // 商户号
		packageParams.put("nonce_str", nonceStr);// 随机字符串
		packageParams.put("body", WechatPayConfig.body);// 商品描述根据情况修改
		packageParams.put("attach", WechatPayConfig.attach);// 商品描述根据情况修改
		packageParams.put("out_trade_no", orderId); // 订单号
		// 这里写的金额为1 分到时修改
//		String totalFee = doubleToIntCent(price);
		String totalFee = Integer.toString(price);
		packageParams.put("total_fee", totalFee); // 总金额以分为单位，不带小数点
		packageParams.put("spbill_create_ip", local_server_ip); // 服务器IP地址
		packageParams.put("notify_url", backNotifyUrl); // 回调接口地址
		packageParams.put("trade_type", WechatPayConfig.native_trade_type); // 支付场景类型
		String sign = TenpayUtil.createSign(packageParams, WechatPayConfig.PARTNER_KEY);
		String xml = "<xml>" + "<appid>" + WechatPayConfig.NATIVE_ID + "</appid>" + "<mch_id>"
						+ WechatPayConfig.NATIVE_PARTNER + "</mch_id>" + "<nonce_str>" + nonceStr + "</nonce_str>"
						+ "<sign>" + sign + "</sign>" + "<body><![CDATA[" + WechatPayConfig.body + "]]></body>"
						+ "<out_trade_no>" + orderId + "</out_trade_no>" + "<attach>" + WechatPayConfig.attach
						+ "</attach>" + "<total_fee>" + totalFee + "</total_fee>" + "<spbill_create_ip>"
						+ local_server_ip + "</spbill_create_ip>" + "<notify_url>" + backNotifyUrl + "</notify_url>"
						+ "<trade_type>" + WechatPayConfig.native_trade_type + "</trade_type>" + "</xml>";
		logger.info("微信扫描二维码支付订单报文:{}", xml);
		//		String result = req.sendPost(WechatPayConfig.create_order_url, xml);
		String result = HttpClientUtils.getInstance().sendPost(WechatPayConfig.create_order_url, xml);
		logger.info("微信扫描二维码支付返回结果:{}" , result);
		String codeUrl = TenpayUtil.getCodeUrl(result);
		logger.info("微信扫码支付二维码codeUrl=" + codeUrl);
		return codeUrl;
	}

	private String scan2DBarCodePaymentTest(String orderId, int price, String backNotifyUrl) throws Exception {
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		String nonceStr = getNonceStr();
		packageParams.put("appid", WechatPayConfig.APP_ID); // 公众号唯一标识符
		packageParams.put("mch_id", WechatPayConfig.PARTNER); // 商户号
		packageParams.put("nonce_str", nonceStr);// 随机字符串
		packageParams.put("body", WechatPayConfig.body);// 商品描述根据情况修改
		packageParams.put("attach", WechatPayConfig.attach);// 商品描述根据情况修改
		packageParams.put("out_trade_no", orderId); // 订单号
		// 这里写的金额为1 分到时修改
		String totalFee = Integer.toString(price);
		packageParams.put("total_fee", totalFee); // 总金额以分为单位，不带小数点
		packageParams.put("spbill_create_ip", local_server_ip); // 服务器IP地址
		packageParams.put("notify_url", backNotifyUrl); // 回调接口地址
		packageParams.put("trade_type", WechatPayConfig.native_trade_type); // 支付场景类型
		String sign = TenpayUtil.createSign(packageParams, WechatPayConfig.PARTNER_KEY);
		String xml = "<xml>" + "<appid>" + WechatPayConfig.APP_ID + "</appid>" + "<mch_id>" + WechatPayConfig.PARTNER
						+ "</mch_id>" + "<nonce_str>" + nonceStr + "</nonce_str>" + "<sign>" + sign + "</sign>"
						+ "<body><![CDATA[" + WechatPayConfig.body + "]]></body>" + "<out_trade_no>" + orderId
						+ "</out_trade_no>" + "<attach>" + WechatPayConfig.attach + "</attach>" + "<total_fee>"
						+ totalFee + "</total_fee>" + "<spbill_create_ip>" + local_server_ip + "</spbill_create_ip>"
						+ "<notify_url>" + backNotifyUrl + "</notify_url>" + "<trade_type>"
						+ WechatPayConfig.native_trade_type + "</trade_type>" + "</xml>";

		//		String result = req.sendPost(WechatPayConfig.create_order_url, xml);
		String result = HttpClientUtils.getInstance().sendPost(WechatPayConfig.create_order_url, xml);
		String codeUrl = TenpayUtil.getCodeUrl(result);
		logger.info("微信扫码支付二维码codeUrl=" + codeUrl);
		return codeUrl;
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
		String mySign = TenpayUtil.createSign(packageParams, WechatPayConfig.PARTNER_KEY);
		if (null != wechatSign && wechatSign.equals(mySign)) {
			return true;
		}
		return false;
	}

	@Override
	public Map<String, String> doPwdRefund(String orderId, String queryId, int price, String refundNo,
			int refundPrice, String frontUrl, String backUrl) {
		throw new UnsupportedOperationException("不支持该操作");
	}

	@Override
	public Map<String, String> getRefundBackNotifaction(Map<String, String> valideData) throws Exception {
		throw new UnsupportedOperationException("不支持该操作");
	}
	
	public void refundquery(String queryId, String refundId) throws ConnectTimeoutException, SocketTimeoutException, Exception{
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		String nonceStr = getNonceStr();
		packageParams.put("appid", WechatPayConfig.APP_ID); // 公众号唯一标识符
		packageParams.put("mch_id", WechatPayConfig.PARTNER); // 商户号
		packageParams.put("nonce_str", nonceStr);// 随机字符串
//		packageParams.put("out_trade_no", queryId); // 流水号
//		packageParams.put("out_refund_no", refundId); // 流水号
		packageParams.put("refund_id", refundId); // 流水号
		String sign = TenpayUtil.createSign(packageParams, WechatPayConfig.PARTNER_KEY);
		String xml =  "<xml>"
					+ "<appid>" + WechatPayConfig.APP_ID + "</appid>" 
					+ "<mch_id>" + WechatPayConfig.PARTNER + "</mch_id>" 
					+ "<nonce_str>" + nonceStr + "</nonce_str>" 
					+ "<sign>" + sign + "</sign>"
//					+ "<out_trade_no>" + queryId + "</out_trade_no>" 
//					+ "<out_refund_no>" + refundId + "</out_refund_no>" 
					+ "<refund_id>" + refundId + "</refund_id>" 
					+ "</xml>";
		logger.info("[微信公众号退款查询表单xml={}]",new Object[]{xml});
		String resp = HttpClientUtils.getInstance().sendPost("https://api.mch.weixin.qq.com/pay/refundquery", xml);
		logger.info("[orderId={}][微信支付退款查询结果resp={}]",new Object[]{queryId,resp});
		SortedMap<String, String> xmlMap = BackVerification.parseXmlToMap(resp);
		Map<String, String> valideData = new HashMap<String, String>();
		valideData.put(PaymentConf.code_pay, PaymentConf.errorcode);
		/**
		// 签名验证
		if(!validateSign(xmlMap)){
			logger.error("公众号支付退款签名验证失败");
			return valideData;
		}
		if (WechatPayConfig.success_code.equalsIgnoreCase(xmlMap.get("result_code"))) {
			// 返回码成功
			valideData.put(PaymentConf.code_pay, PaymentConf.successcode);
			valideData.put(PaymentConf.queryId_pay, xmlMap.get("refund_id"));
			valideData.put(PaymentConf.orderid_name, xmlMap.get("out_refund_no"));
		}else{
			valideData.put("businessCode", xmlMap.get("err_code"));
			valideData.put("businessDes", xmlMap.get("err_code_des"));
		}
		*/
//		logger.info("valideData:"+valideData);
//		return valideData;
	}
	
	
	/**
	 * 微信支付订单查询
	 * @param order
	 * @return
	 * @throws ConnectTimeoutException
	 * @throws SocketTimeoutException
	 * @throws Exception
	 */
	public OrderQueryResultBo orderQuery(PayOrder order ) throws ConnectTimeoutException, SocketTimeoutException, Exception{
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
		//TODO 签名，抽出公共方法
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
		//TODO 签名，抽出公共方法
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
		
		Map<String, String> xmlMap = new HashMap<String, String>();
		OrderQueryResultBo bo = null;
		try {
			String resp = HttpClientUtils.getInstance().sendPost("https://api.mch.weixin.qq.com/pay/orderquery", xml.toString());
			bo = XmlUtil.parseXmlToBean(resp, OrderQueryResultBo.class);
		} catch (Exception e) {
			logger.error("[微信支付订单查询error]", e);
		}
		return bo;
	}
	
}
