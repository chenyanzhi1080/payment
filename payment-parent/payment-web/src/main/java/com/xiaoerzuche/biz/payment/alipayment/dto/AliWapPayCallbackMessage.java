package com.xiaoerzuche.biz.payment.alipayment.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

/** 
* 支付宝WAP支付异步通知报文
* @author: cyz
* @version: payment
* @time: 2016年11月11日
* 
*/
@XmlRootElement(name = "notify")
public class AliWapPayCallbackMessage {
	private String payment_type;
	//商品名
	private String subject;
	//交易号(流水号)
	private String trade_no;
	//交易状态
	private String trade_status;
	//买家ID
	private String buyer_id;
	//买家账号
	private String buyer_email;
	//商家ID
	private String seller_id;
	//商家账号
	private String seller_email;
	//交易创建时间
	private Date gmt_create;
	//通知类型(忽略该信息)
	private String notify_type;
	//交易数量(忽略该信息)
	private String quantity;
	//商家支付单号(对应支付网关queryId)
	private String out_trade_no;
	//通知时间
	private Date notify_time;
	//是否调整过价格
	private String is_total_fee_adjust;
	//交易总价,单位为元
	private double total_fee;
	//单个商品价格
	private double price;
	//交易的付款时间，如果交易未付款，没有该属性
	private Date gmt_payment;
	//唯一识别通知内容，重发相同内容的通知 notify_id 值不变。
	private String notify_id;
	//是否使用优惠券(忽略)
	private String use_coupon;
	public String getPayment_type() {
		return payment_type;
	}
	public void setPayment_type(String payment_type) {
		this.payment_type = payment_type;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
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
	public String getSeller_id() {
		return seller_id;
	}
	public void setSeller_id(String seller_id) {
		this.seller_id = seller_id;
	}
	public String getSeller_email() {
		return seller_email;
	}
	public void setSeller_email(String seller_email) {
		this.seller_email = seller_email;
	}
	public Date getGmt_create() {
		return gmt_create;
	}
	public void setGmt_create(Date gmt_create) {
		this.gmt_create = gmt_create;
	}
	public String getNotify_type() {
		return notify_type;
	}
	public void setNotify_type(String notify_type) {
		this.notify_type = notify_type;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	public Date getNotify_time() {
		return notify_time;
	}
	public void setNotify_time(Date notify_time) {
		this.notify_time = notify_time;
	}
	public String getIs_total_fee_adjust() {
		return is_total_fee_adjust;
	}
	public void setIs_total_fee_adjust(String is_total_fee_adjust) {
		this.is_total_fee_adjust = is_total_fee_adjust;
	}
	public double getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(double total_fee) {
		this.total_fee = total_fee;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public Date getGmt_payment() {
		return gmt_payment;
	}
	public void setGmt_payment(Date gmt_payment) {
		this.gmt_payment = gmt_payment;
	}
	public String getNotify_id() {
		return notify_id;
	}
	public void setNotify_id(String notify_id) {
		this.notify_id = notify_id;
	}
	public String getUse_coupon() {
		return use_coupon;
	}
	public void setUse_coupon(String use_coupon) {
		this.use_coupon = use_coupon;
	}
	@Override
	public String toString() {
		return "AliWapPayCallbackMessage [payment_type=" + payment_type + ", subject=" + subject + ", trade_no="
				+ trade_no + ", trade_status=" + trade_status + ", buyer_id=" + buyer_id + ", buyer_email="
				+ buyer_email + ", seller_id=" + seller_id + ", seller_email=" + seller_email + ", gmt_create="
				+ gmt_create + ", notify_type=" + notify_type + ", quantity=" + quantity + ", out_trade_no="
				+ out_trade_no + ", notify_time=" + notify_time + ", is_total_fee_adjust=" + is_total_fee_adjust
				+ ", total_fee=" + total_fee + ", price=" + price + ", gmt_payment=" + gmt_payment + ", notify_id="
				+ notify_id + ", use_coupon=" + use_coupon + "]";
	}
	
}
