package com.xiaoerzuche.biz.payment.alipayment;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.internal.parser.json.JsonConverter;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.internal.util.json.JSONWriter;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.xiaoerzuche.biz.payment.PaymentStrategy;
import com.xiaoerzuche.biz.payment.alipayment.alipay.AlipayCore;
import com.xiaoerzuche.biz.payment.alipayment.alipay.AlipayNotify;
import com.xiaoerzuche.biz.payment.alipayment.alipay.AlipaySubmit;
import com.xiaoerzuche.biz.payment.alipayment.alipay.RSA;
import com.xiaoerzuche.biz.payment.common.PaymentConf;
import com.xiaoerzuche.biz.payment.enu.PayType;
import com.xiaoerzuche.biz.payment.paymentConfig.AliPayConfig;
import com.xiaoerzuche.biz.payment.paymentConfig.DefaultAliOpendApiClient;
import com.xiaoerzuche.common.enu.ErrorCode;
import com.xiaoerzuche.common.exception.ErrorCodeException;
import com.xiaoerzuche.common.util.DateTimeUtil;
import com.xiaoerzuche.common.util.JsonUtil;

@Service
public class AliPaymentStrategy implements PaymentStrategy {
	private static final Logger logger = LoggerFactory.getLogger(AliPaymentStrategy.class);
	private static String		successCode		= "TRADE_SUCCESS";
	private static String		finishedCode	= "TRADE_FINISHED";

	@Autowired
	private DefaultAliOpendApiClient aliOpendApiClient;
	@SuppressWarnings("finally")
	@Override
	public String doPayment(String timeExpire, String account, String openid, String orderId, int price, Integer payType, String frontNotifyUrl, String backNotifyUrl) {
		String payParam = null;
		try {
			switch (PayType.getPayType(payType)) {
			case ALIWAPPAY://支付宝手机网页支付,返回支付页面
				payParam = this.alipayTradeWapPay(timeExpire, orderId, price, frontNotifyUrl, backNotifyUrl);
				break;
			case ALIAPPPAY://支付宝移动支付，返回app端sdk所需订单参数
				payParam = this.appAlipay(timeExpire, orderId, price, backNotifyUrl);
				break;
			case ALIOPENAPPPAY://支付宝开放平台app支付
				payParam = this.aliOpenAppPay(timeExpire, orderId, price, backNotifyUrl);
			default:
				break;
			}
			
		} catch (Exception e) {
			logger.error("阿里支付异常," + e);
		} finally {
			logger.info("执行阿里支付");
			return payParam;
		}
	}
	
	public String alipayTradeWapPay(String timeExpire, String orderId, int money, String frontNotifyUrl, String backNotifyUrl){
		AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();//创建API对应的request
	    alipayRequest.setReturnUrl(frontNotifyUrl);
	    alipayRequest.setNotifyUrl(backNotifyUrl);//在公共参数中设置回跳和通知地址
	    String totalFee = new BigDecimal(money).movePointLeft(2).toString();// 付款金额
	    AlipayTradeWapPayModel bizModel = new AlipayTradeWapPayModel();
	    bizModel.setOutTradeNo(orderId);
	    bizModel.setTotalAmount(totalFee);
	    bizModel.setSubject(AliPayConfig.subject);
	    bizModel.setProductCode("QUICK_WAP_WAY");
	    
		if(StringUtils.isNotBlank(timeExpire)){
			//yyyy-MM-dd HH:mm
			Date dateExpire = DateTimeUtil.parse(timeExpire, "yyyyMMddHHmmss");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			timeExpire = sdf.format(dateExpire);
			bizModel.setTimeExpire(timeExpire);
		}
		alipayRequest.setBizModel(bizModel);
	    logger.info("手机网站支付alipayRequest"+JsonUtil.toJson(alipayRequest));
	    String form;
		try {
			form = this.aliOpendApiClient.alipayClient.pageExecute(alipayRequest).getBody();
			logger.info("手机网站支付form"+form);
			return form;
		} catch (AlipayApiException e) {
        	logger.info("手机网站支付请求异常"+e);
            throw new ErrorCodeException(ErrorCode.UNKOWN.getErrorCode(),"网关异常");
		} //调用SDK生成表单
	}
	
	@Override
	public Map<String, String> getPaymentFrontNotifaction(Map<String, String> params) throws Exception {
		String orderId = null;
		String respCode = PaymentConf.errorcode;
		Map<String, String> result = new HashMap<String, String>();
		logger.info("支付回调-支付宝用户前台支付回调");
		// 获取支付宝GET过来反馈信息
		logger.info("params=" + params.toString());
		String trade_status = params.get("result");
		// 商户订单号
		orderId = params.get("out_trade_no");
		// 计算得出通知验证结果
//		if (AlipayNotify.verifyReturn(params)) {// 验证成功
			if (!trade_status.equals("success")) {
				respCode = PaymentConf.errorcode;
				logger.info("alipay verify success trade fail");
			} else {
				respCode = PaymentConf.successcode;
			}
//		}
		result.put(PaymentConf.orderid_name, orderId);
		result.put(PaymentConf.code_pay, respCode);
		return result;
	}

	@Override
	public Map<String, String> getPaymentBackNotifaction(Map<String, String> params) throws Exception {
		logger.info("[支付宝服务端异步回调通知][{}]", new Object[]{params});
		// 获取支付宝POST过来反馈信息
		// XML解析data数据
		Map<String, String> valideData = new HashMap<String, String>();
		if(StringUtils.isNotBlank(params.get("notify_data"))){
			this.parseWapPayNotify(params, valideData);//解析支付宝WAP支付
		}else{
			this.parseAppPayNotify(params, valideData);//解析支付宝APP支付
		}
		return valideData;

	}
	
	/**
	 * 解析支付宝WAP支付异步通知报文
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public  Map<String, String> parseWapPayNotify(Map<String, String> params, Map<String, String> valideData) throws Exception{
		logger.info("[支付宝wap支付异步通知内容] : " + params.toString());
		// 商户订单号
		String orderId = params.get("out_trade_no");
		// 支付宝交易号
		String queryId = params.get("trade_no");
		// 交易状态
		String tradeStatus = params.get("trade_status");
		String money = params.get("total_amount");
		params.put("subject", AliPayConfig.subject);
//		Map<String, String> valideData = new HashMap<String, String>();
		valideData.put(PaymentConf.queryId_pay, queryId);
		valideData.put(PaymentConf.orderid_name, orderId);
		valideData.put(PaymentConf.code_pay, PaymentConf.errorcode);
		valideData.put(PaymentConf.transaction_money, money);
		// 验证成功
		boolean signVerified = AlipaySignature.rsaCheckV1(params, DefaultAliOpendApiClient.ALIPAY_PUBLIC_KEY, DefaultAliOpendApiClient.CHARSET, DefaultAliOpendApiClient.SIGN_TYPE); //调用SDK验证签名
		if (signVerified) {
			if ((finishedCode.equals(tradeStatus.toUpperCase()) || successCode.equals(tradeStatus.toUpperCase()))) {
				valideData.put(PaymentConf.code_pay, PaymentConf.successcode);
			} else {
				valideData.put(PaymentConf.code_pay, PaymentConf.errorcode);
			}
		}
		return valideData;
	}
	
	/**
	 * 解析支付宝APP支付(既移动支付)异步通知报文
	 * @param params
	 * @param valideData
	 * @return
	 * @throws Exception
	 */
	private Map<String, String> parseAppPayNotify(Map<String, String> params, Map<String, String> valideData) throws Exception{
		
		if(StringUtils.isNotBlank(params.get("body"))){
			params.put("body", AliPayConfig.subject);
		}
		if(StringUtils.isNotBlank(params.get("subject"))){
			params.put("subject", AliPayConfig.subject);
		}
		// 商户订单号
		String orderId = String.valueOf(params.get("out_trade_no"));
		// 支付宝交易号
		String queryId = String.valueOf(params.get("trade_no"));
		// 交易状态
		String tradeStatus = String.valueOf(params.get("trade_status"));
		String money = String.valueOf(params.get("total_fee"));

		valideData.put(PaymentConf.queryId_pay, queryId);
		valideData.put(PaymentConf.orderid_name, orderId);
		valideData.put(PaymentConf.code_pay, PaymentConf.errorcode);
		valideData.put(PaymentConf.transaction_money, money);
		valideData.put(AliPayConfig.trade_status, tradeStatus);
		// 验证成功
		if (AlipayNotify.verifyAppNotify(params, true)) {
			if ((finishedCode.equals(tradeStatus.toUpperCase()) || successCode.equals(tradeStatus.toUpperCase()))) {
				valideData.put(PaymentConf.code_pay, PaymentConf.successcode);
			} else {
				valideData.put(PaymentConf.code_pay, PaymentConf.errorcode);
			}
		}
		return valideData;
	}
	
	/**
	 * 解析支付宝APP支付(既移动支付)异步通知报文
	 * @param params
	 * @param valideData
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> parseOpenAppPayNotify(Map<String, String> params, Map<String, String> valideData) throws Exception{
		
		if(StringUtils.isNotBlank(params.get("body"))){
			params.put("body", AliPayConfig.subject);
		}
		if(StringUtils.isNotBlank(params.get("subject"))){
			params.put("subject", AliPayConfig.subject);
		}
		// 商户订单号
		String orderId = String.valueOf(params.get("out_trade_no"));
		// 支付宝交易号
		String queryId = String.valueOf(params.get("trade_no"));
		// 交易状态
		String tradeStatus = String.valueOf(params.get("trade_status"));
		String money = String.valueOf(params.get("total_amount"));

		valideData.put(PaymentConf.queryId_pay, queryId);
		valideData.put(PaymentConf.orderid_name, orderId);
		valideData.put(PaymentConf.code_pay, PaymentConf.errorcode);
		valideData.put(PaymentConf.transaction_money, money);
		valideData.put(AliPayConfig.trade_status, tradeStatus);
		// 验证成功
		// 验证成功
		boolean signVerified = AlipaySignature.rsaCheckV1(params, DefaultAliOpendApiClient.ALIPAY_PUBLIC_KEY, DefaultAliOpendApiClient.CHARSET, DefaultAliOpendApiClient.SIGN_TYPE); //调用SDK验证签名
		if (signVerified) {
			if ((finishedCode.equals(tradeStatus.toUpperCase()) || successCode.equals(tradeStatus.toUpperCase()))) {
				valideData.put(PaymentConf.code_pay, PaymentConf.successcode);
			} else {
				valideData.put(PaymentConf.code_pay, PaymentConf.errorcode);
			}
		}
		return valideData;
	}
	
	/**
	 * 支付宝移动支付
	 * @param orderId
	 * @param money
	 * @param backNotifyUrl
	 * @return
	 */
	public String appAlipay(String timeExpire, String orderId, int money, String backNotifyUrl){
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", "mobile.securitypay.pay");
		sParaTemp.put("partner", AliPayConfig.partner);
		sParaTemp.put("_input_charset", AliPayConfig.input_charset);
		sParaTemp.put("notify_url", backNotifyUrl);
		
		sParaTemp.put("out_trade_no", orderId);
		sParaTemp.put("subject", AliPayConfig.subject);
		sParaTemp.put("payment_type", "1");
		sParaTemp.put("seller_id", AliPayConfig.seller_email);
		sParaTemp.put("total_fee", new BigDecimal(money).movePointLeft(2).toString());
		sParaTemp.put("body", AliPayConfig.subject);
		//未付款交易的超时时间
		if(StringUtils.isNotBlank(timeExpire)){
			Date dateExpire = DateTimeUtil.parse(timeExpire, "yyyyMMddHHmmss");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			timeExpire = sdf.format(dateExpire);
			sParaTemp.put("it_b_pay", timeExpire);
		}
		String preSignStr = AlipayCore.createLinkString(sParaTemp);
		logger.info("[支付宝移动支付参数]"+preSignStr);
		
		String sign = RSA.sign(preSignStr, AliPayConfig.rsa_private_key, AliPayConfig.input_charset);
		//URLencode
		try {
			sign = URLEncoder.encode(sign, AliPayConfig.input_charset);
			logger.info("[支付宝移动支付参数签名sign]"+sign);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return preSignStr+"&sign="+sign+"&sign_type=RSA";
	}
	
	public String aliOpenAppPay(String timeExpire, String orderId, int money, String backNotifyUrl){
		AlipayTradeAppPayRequest appPayRequest = new AlipayTradeAppPayRequest();
//		appPayRequest.setReturnUrl(backNotifyUrl);
		appPayRequest.setNotifyUrl(backNotifyUrl);
	    String totalFee = new BigDecimal(money).movePointLeft(2).toString();// 付款金额
	    //业务参数组装开始
	    AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
	    model.setSubject(AliPayConfig.subject);
	    model.setOutTradeNo(orderId);
		if(StringUtils.isNotBlank(timeExpire)){
			Date dateExpire = DateTimeUtil.parse(timeExpire, "yyyyMMddHHmmss");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			timeExpire = sdf.format(dateExpire);
			model.setTimeExpire(timeExpire);
		}
	    model.setTotalAmount(totalFee);
	    model.setProductCode("QUICK_MSECURITY_PAY");
	    appPayRequest.setBizModel(model);
	    //业务参数组装结束
	    try {
	    		logger.info("opendApp支付请求appPayRequest"+JsonUtil.toJson(appPayRequest));
	            //这里和普通的接口调用不同，使用的是sdkExecute
	            AlipayTradeAppPayResponse response = this.aliOpendApiClient.alipayClient.sdkExecute(appPayRequest);
	            logger.info("opendApp支付请求response"+JsonUtil.toJson(response));
	            System.out.println(response.getBody());//就是orderString 可以直接给客户端请求，无需再做处理。
	            return response.getBody();
	        } catch (AlipayApiException e) {
	        	logger.info("opendApp支付请求异常"+e);
	            throw new ErrorCodeException(ErrorCode.UNKOWN.getErrorCode(),"网关异常");
	    }
	}
	@Override
	public Map<String, String> doRefund(String orderId, String queryId, int price, String refundNo,
					int refundPrice, String frontUrl, String backUrl) throws Exception {
		Map<String, String> result = new HashMap<String, String>();
		// 退款详细数据
		logger.info("alipay 退款操作");
//		String refundFee = Integer.toString(refundPrice);
		String refundFee = new BigDecimal(refundPrice).movePointLeft(2).toString();//退款金额
		String detailData = queryId + "^" + refundFee + "^" + "协商退款";
		// 必填，具体格式请参见接口技术文档
		String timeStamp = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
		String batchNo = DateFormatUtils.format(new Date(), "yyyyMMdd") + refundNo;
		// 把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		// 设置有密码和无密码字段
		sParaTemp.put("service", "refund_fastpay_by_platform_nopwd");
		sParaTemp.put("partner", AliPayConfig.partner);
		sParaTemp.put("_input_charset", AliPayConfig.input_charset);
		sParaTemp.put("notify_url", backUrl);
		sParaTemp.put("seller_email", AliPayConfig.seller_email);
		sParaTemp.put("batch_no", batchNo);
		sParaTemp.put("refund_date", timeStamp);
		sParaTemp.put("batch_num", "1");
		sParaTemp.put("detail_data", detailData);
		logger.info("[阿里退款申请表单信息]sParaTemp=" + sParaTemp);
		// 建立请求，有密码接口，拉起退款页面
//		String sHtmlText = AlipaySubmit.buildRequest(sParaTemp, "get", "确认");
		// 无密码接口，拉起退款页面
//		String sHtmlText = AlipaySubmit.buildRequest("", "", sParaTemp);
		String sHtmlText;
		sHtmlText = AlipaySubmit.buildRequest("", "", sParaTemp);
//		System.out.println("sHtmlText=" + sHtmlText);
		logger.info("[阿里退款申请返回信息]sHtmlText=" + sHtmlText);
		result.put(PaymentConf.html, sHtmlText);

		return result;
	}
	@Override
	public Map<String, String> doPwdRefund(String orderId, String queryId, int price, String refundNo,
			int refundPrice, String frontUrl, String backUrl) {
		Map<String, String> result = new HashMap<String, String>();
		// 退款详细数据
		logger.info("alipay 退款操作");
//		String refundFee = Integer.toString(refundPrice);// 付款金额
		String refundFee = new BigDecimal(refundPrice).movePointLeft(2).toString();//退款金额
		String detailData = queryId + "^" + refundFee + "^" + "协商退款";
		// 必填，具体格式请参见接口技术文档
		String timeStamp = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
		String batchNo = DateFormatUtils.format(new Date(), "yyyyMMdd") + refundNo;
		// 把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		// 设置有密码和无密码字段
		sParaTemp.put("service", "refund_fastpay_by_platform_pwd");
		sParaTemp.put("partner", AliPayConfig.partner);
		sParaTemp.put("_input_charset", AliPayConfig.input_charset);
		sParaTemp.put("notify_url", backUrl);
		sParaTemp.put("seller_email", AliPayConfig.seller_email);
		sParaTemp.put("refund_date", timeStamp);
		sParaTemp.put("batch_no", batchNo);
		sParaTemp.put("batch_num", "1");
		sParaTemp.put("detail_data", detailData);
		// 建立请求，有密码接口，拉起退款页面
		String sHtmlText = AlipaySubmit.buildRequest(sParaTemp, "get", "确认");
		logger.info("sHtmlText=" + sHtmlText);
		result.put(PaymentConf.html, sHtmlText);
		return result;
	}
	
	
	public void query(String queryId, String tranNo) throws Exception{
		// 把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		// 设置有密码和无密码字段
		sParaTemp.put("service", "single_trade_query");
		sParaTemp.put("partner", AliPayConfig.partner);
		sParaTemp.put("_input_charset", AliPayConfig.input_charset);
		if(StringUtils.isNotBlank(queryId)){
			sParaTemp.put("out_trade_no", queryId);
		}
		if(StringUtils.isNotBlank(tranNo)){
			sParaTemp.put("trade_no", tranNo);
		}
		
		logger.info("[阿里单笔查询表单信息]sParaTemp=" + sParaTemp);
		String sHtmlText = AlipaySubmit.buildRequest("", "", sParaTemp);
		logger.info("[阿里单笔查询结果]sHtmlText=" + sHtmlText);
		
	}
	
	public String query2(String queryId, String tranNo) throws Exception{
		// 把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		// 设置有密码和无密码字段
		sParaTemp.put("service", "single_trade_query");
		sParaTemp.put("partner", AliPayConfig.partner);
		sParaTemp.put("_input_charset", AliPayConfig.input_charset);
		if(StringUtils.isNotBlank(queryId)){
			sParaTemp.put("out_trade_no", queryId);
		}
		if(StringUtils.isNotBlank(tranNo)){
			sParaTemp.put("trade_no", tranNo);
		}
		
//		logger.info("[阿里单笔查询表单信息]sParaTemp=" + sParaTemp);
		String sHtmlText = AlipaySubmit.buildRequest("", "", sParaTemp);
//		logger.info("[阿里单笔查询结果]sHtmlText=" + sHtmlText);
		return sHtmlText;
	}
	
	@Override
	public Map<String, String> getRefundBackNotifaction(Map<String, String> params) throws Exception {
		logger.info("[阿里退款服务端通知报文内容]params"+params);
		
		Map<String, String> valideData = new HashMap<String, String>();
		valideData.put(PaymentConf.code_pay, PaymentConf.errorcode);
		String result_details = "";
		if(StringUtils.isNotBlank(params.get("result_details")) && params.get("result_details").split("\\^").length>2 ){
			//result_details=2016052521001004250220397031^0.01^SUCCESS
			String[] detailsArray = params.get("result_details").split("\\^");
			String origTradeNo = detailsArray[0];//原支付流水号
			String money = detailsArray[1];//退款金额
			String tradeStatus = detailsArray[2];//退款结果
			String tranNo = origTradeNo;
			String batchNo = params.get("batch_no");
			String orderId = batchNo.substring(8);
			
			valideData.put(PaymentConf.queryId_pay, tranNo);
			valideData.put(PaymentConf.origTranNo, origTradeNo);
			valideData.put(PaymentConf.orderid_name, orderId);
			valideData.put(PaymentConf.code_pay, PaymentConf.errorcode);
			valideData.put(PaymentConf.transaction_money, money);
			
			// 验证成功
//			if (AlipayNotify.verifyReturn2(params)) {
				if (!("REFUND_SUCCESS".equals(tradeStatus.toUpperCase()) || "SUCCESS".equals(tradeStatus.toUpperCase()) )) {
					valideData.put(PaymentConf.code_pay, PaymentConf.errorcode);
				} else {
					valideData.put(PaymentConf.code_pay, PaymentConf.successcode);
				}
//			}
		}
		
		return valideData;
	}

}
