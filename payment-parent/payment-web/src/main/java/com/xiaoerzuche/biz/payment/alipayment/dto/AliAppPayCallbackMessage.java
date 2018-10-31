package com.xiaoerzuche.biz.payment.alipayment.dto;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.xiaoerzuche.common.util.JsonUtil;

/** 
* 阿里APP支付回调报文,支付回调和退款回调
* @author: cyz
* @version: payment
* @time: 2016年11月9日
* 交易状态说明
* WAIT_BUYER_PAY	交易创建，等待买家付款。
* TRADE_CLOSED	在指定时间段内未支付时关闭的交易；
* 在交易完成全额退款成功时关闭的交易。
* TRADE_SUCCESS	交易成功，且可对该交易做操作，如：多级分润、退款等。
* TRADE_FINISHED	交易成功且结束，即不可再做任何操作。
*/
public class AliAppPayCallbackMessage {
	//通知时间
	private Date notify_time;
	//通知的类型
	private String notify_type;
	//通知校验ID
	private String notify_id;
	//签名方式RSA
	private String sign_type;
	// 签名
	private String sign;
	//商户网站唯一订单号
	private String out_trade_no;
	//商品名称
	private String subject;
	//支付类型
	private String payment_type;
	//支付宝交易号
	private String trade_no;
	//交易状态 
	private String trade_status;
	//卖家支付宝用户号
	private String seller_id;
	//买家支付宝用户号
	private String buyer_id;
	//买家支付宝账号
	private String buyer_email;
	//交易金额
	private Double total_fee;
	//购买数量
	private String quantity;
	//商品单价
	private Double price;
	//商品描述
	private String body;
	//交易创建时间
	private Date gmt_create;
	//交易付款时间
	private Date gmt_payment;
	//是否调整总价
	private String is_total_fee_adjust;
	//是否使用红包买家
	private String use_coupon;
	//折扣
	private String discount;
	//退款状态
	private String refund_status;
	//退款时间
	private Date gmt_refund;

	public Date getNotify_time() {
		return notify_time;
	}
	public void setNotify_time(Date notify_time) {
		this.notify_time = notify_time;
	}
	public String getNotify_type() {
		return notify_type;
	}
	public void setNotify_type(String notify_type) {
		this.notify_type = notify_type;
	}
	public String getNotify_id() {
		return notify_id;
	}
	public void setNotify_id(String notify_id) {
		this.notify_id = notify_id;
	}
	public String getSign_type() {
		return sign_type;
	}
	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getPayment_type() {
		return payment_type;
	}
	public void setPayment_type(String payment_type) {
		this.payment_type = payment_type;
	}
	public String getTrade_no() {
		return trade_no;
	}
	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
	}
	public String getTrade_status() {
		return trade_status;
	}
	public void setTrade_status(String trade_status) {
		this.trade_status = trade_status;
	}
	public String getSeller_id() {
		return seller_id;
	}
	public void setSeller_id(String seller_id) {
		this.seller_id = seller_id;
	}
	public String getBuyer_id() {
		return buyer_id;
	}
	public void setBuyer_id(String buyer_id) {
		this.buyer_id = buyer_id;
	}
	public String getBuyer_email() {
		return buyer_email;
	}
	public void setBuyer_email(String buyer_email) {
		this.buyer_email = buyer_email;
	}
	public Double getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(Double total_fee) {
		this.total_fee = total_fee;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public Date getGmt_create() {
		return gmt_create;
	}
	public void setGmt_create(Date gmt_create) {
		this.gmt_create = gmt_create;
	}
	public Date getGmt_payment() {
		return gmt_payment;
	}
	public void setGmt_payment(Date gmt_payment) {
		this.gmt_payment = gmt_payment;
	}
	public String getIs_total_fee_adjust() {
		return is_total_fee_adjust;
	}
	public void setIs_total_fee_adjust(String is_total_fee_adjust) {
		this.is_total_fee_adjust = is_total_fee_adjust;
	}
	public String getUse_coupon() {
		return use_coupon;
	}
	public void setUse_coupon(String use_coupon) {
		this.use_coupon = use_coupon;
	}
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	public String getRefund_status() {
		return refund_status;
	}
	public void setRefund_status(String refund_status) {
		this.refund_status = refund_status;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public Date getGmt_refund() {
		return gmt_refund;
	}
	public void setGmt_refund(Date gmt_refund) {
		this.gmt_refund = gmt_refund;
	}
	
	@Override
	public String toString() {
		return "AliAppPayCallbackMessage [notify_time=" + notify_time + ", notify_type=" + notify_type + ", notify_id="
				+ notify_id + ", sign_type=" + sign_type + ", sign=" + sign + ", out_trade_no=" + out_trade_no
				+ ", subject=" + subject + ", payment_type=" + payment_type + ", trade_no=" + trade_no
				+ ", trade_status=" + trade_status + ", seller_id=" + seller_id + ", buyer_id=" + buyer_id
				+ ", buyer_email=" + buyer_email + ", total_fee=" + total_fee + ", quantity=" + quantity + ", price="
				+ price + ", body=" + body + ", gmt_create=" + gmt_create + ", gmt_payment=" + gmt_payment
				+ ", is_total_fee_adjust=" + is_total_fee_adjust + ", use_coupon=" + use_coupon + ", discount="
				+ discount + ", refund_status=" + refund_status + ", gmt_refund=" + gmt_refund + "]";
	}
	public static void main(String[] args){
		String json = "{\"buyer_id\"=\"2088502957001294\", \"trade_no\"=\"2016110921001004290243432515\", \"body\"=\"xiaoerzucheOrder\", \"use_coupon\"=\"N\", \"notify_time\"=\"2016-11-09 09:53:14\", \"subject\"=\"xiaoerzucheOrder\", \"sign_type\"=\"RSA\", \"is_total_fee_adjust\"=\"Y\", \"notify_type\"=\"trade_status_sync\", \"out_trade_no\"=\"14786548594214330\", \"trade_status\"=\"WAIT_BUYER_PAY\", \"discount\"=\"0.00\", \"sign\"=\"OUBfyVjPdtMGXUzxuT+U/55Mw1ZkO1eNvaJSbJc7ABfehC27XQAPLyrJce8bEYo1FGk9X+8da3x72FL3/Ll+wqbJbQ/o+XDjMyMuflqMzQQTgxDSXT5w/shFZHMlTnTmKcaXtiiirsH8RnXNkzqURpJ3zzIR6s+YxbtomVylfnM=\", \"buyer_email\"=\"1791773297@qq.com\", \"gmt_create\"=\"2016-11-09 09:39:42\", \"price\"=\"0.01\", \"total_fee\"=\"0.01\", \"quantity\"=\"1\", \"seller_id\"=\"2088811891419125\", \"notify_id\"=\"1892423b97fee1a97149cdbbf6650aai8m\", \"seller_email\"=\"xiaoerzuche@hnair.com\", \"payment_type\"=\"1\"}";
		AliAppPayCallbackMessage callbackMessage = JsonUtil.fromJson(json, AliAppPayCallbackMessage.class);
		System.out.println(callbackMessage.toString());
		Map<String, Object> map = JsonUtil.fromJson(json, Map.class);
		AliAppPayCallbackMessage callbackMessage2 = new AliAppPayCallbackMessage();
		try {
			BeanUtils.populate(callbackMessage2, map);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(callbackMessage2);
	}
}
