package com.xiaoerzuche.biz.payment.common;

public class PaymentConf {

	// 消费
	public static final String	trantype_consumption		= "01";
	// 退款
	public static final String	trantype_refund				= "04";
	// 支付方式支付通道(tenPay 1财付通,aliPay 2阿里,cmbPay 3招商)
	public static final Integer	paytype_alipay				= 2;
	public static final Integer	paytype_tenpay				= 1;
	// 支付宝
	public static final String	paycompany_alipay			= "alipay";
	// 微信支付
	public static final String	paycompany_tenpay			= "tenpay";
	// 有效时长
	public static final long	intervalMilli				= 3 * 24 * 60 * 60 * 1000;
//	// 订单编号
	public static final String	transaction_source_name		= "transactionSourceName";
	public static final String	transaction_source_id		= "transactionSourceId";
	// 支付中心交易单号
//	public static final String	queryId				= "queryId";
	public static final String queryId_pay = "queryId";//TODO
	public static final String orderid_name = "orderId";//TODO
	public static final String origTranNo = "origTranNo";
	// 支付信息
	public static final String	notfound_message			= "未找到支付信息";
	public static final String	invalidate_message			= "支付验证失败";
	public static final String	expired_order_message		= "过期订单";
	public static final String	succesPay_message			= "支付成功";
	public static final String	failurePay_message			= "支付失败";
	public static final String	unsupport_bussiness_meesage	= "未支持业务";
	public static final String	empty_data_meesage			= "数据为空";
	public static final String	system_admin				= "sysAdmin";
	// 错误返回码
	public static final String	errorcode					= "201";
	// 成功返回码
	public static final String	successcode					= "200";
	// 返回码
	public static final String	code_pay					= "code";
	public static final String	orderDetail			= "orderDetail";
	public static final String businessCode = "businessCode";
	public static final String businessDes = "businessDes";
	
	// 返回数据
	public static final String	datas_pay					= "datas";
	public static String		input_charset				= "utf-8";
	public static String		quality_certification		= "ISO-8859-1";
	public static final String	transaction_money			= "transaction_money";
	public static final String	html			= "html";
	/**
	 * 支付宝服务名称
	 */
	public static final String	ALI_PAYMENT_SERVICE_NAME	= "aliPaymentStrategy";
	/**
	 * 微信支付服务名称
	 */
	public static final String	WECHAT_PAYMENT_SERVICE_NAME	= "wechatPaymentStrategy";
	
	/**
	 * 微信公众号支付服务名称
	 */
	public static final String	WECHAT_JSPAYMENT_SERVICE_NAME	= "wechatJSPaymentStrategy";
	
	/**
	 * 微信公众号支付服务名称
	 */
	public static final String	WALLET_SERVICE_NAME	= "walletPaymentStrategy";
}
