package com.xiaoerzuche.biz.payment.wechatpayment;

import com.alipay.api.internal.mapping.ApiField;

public class OrderQueryResultBo {
	@ApiField("return_code")
	private String returnCode;
	@ApiField("return_msg")
	private String returnMsg;
	@ApiField("appid")
	private String appid;
	@ApiField("mch_id")
	private String mchId;
	@ApiField("nonce_str")
	private String nonceStr;
	@ApiField("sign")
	private String sign;
	@ApiField("result_code")
	private String resultCode;
	@ApiField("err_code_des")
	private String errCodeDes;
	@ApiField("openid")
	private String openid;
	@ApiField("trade_type")
	private String tradeType;
	@ApiField("trade_state")
	private String tradeState;
	@ApiField("bank_type")
	private String bankType;
	@ApiField("total_fee")
	private Integer totalFee;
	@ApiField("cash_fee")
	private Integer cashFee;
	@ApiField("transaction_id")
	private String transactionId;
	@ApiField("out_trade_no")
	private String outTradeNo;
	@ApiField("time_end")
	private String timeEnd;
	@ApiField("trade_state_desc")
	private String tradeStateDesc;
	public String getReturnCode() {
		return returnCode;
	}
	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}
	public String getReturnMsg() {
		return returnMsg;
	}
	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getMchId() {
		return mchId;
	}
	public void setMchId(String mchId) {
		this.mchId = mchId;
	}
	public String getNonceStr() {
		return nonceStr;
	}
	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getErrCodeDes() {
		return errCodeDes;
	}
	public void setErrCodeDes(String errCodeDes) {
		this.errCodeDes = errCodeDes;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	public String getTradeState() {
		return tradeState;
	}
	public void setTradeState(String tradeState) {
		this.tradeState = tradeState;
	}
	public String getBankType() {
		return bankType;
	}
	public void setBankType(String bankType) {
		this.bankType = bankType;
	}
	public Integer getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(Integer totalFee) {
		this.totalFee = totalFee;
	}
	public Integer getCashFee() {
		return cashFee;
	}
	public void setCashFee(Integer cashFee) {
		this.cashFee = cashFee;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getOutTradeNo() {
		return outTradeNo;
	}
	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	public String getTimeEnd() {
		return timeEnd;
	}
	public void setTimeEnd(String timeEnd) {
		this.timeEnd = timeEnd;
	}
	public String getTradeStateDesc() {
		return tradeStateDesc;
	}
	public void setTradeStateDesc(String tradeStateDesc) {
		this.tradeStateDesc = tradeStateDesc;
	}
	@Override
	public String toString() {
		return "OrderQueryResultBo [returnCode=" + returnCode + ", returnMsg=" + returnMsg + ", appid=" + appid
				+ ", mchId=" + mchId + ", nonceStr=" + nonceStr + ", sign=" + sign + ", resultCode=" + resultCode
				+ ", errCodeDes=" + errCodeDes + ", openid=" + openid + ", tradeType=" + tradeType + ", tradeState="
				+ tradeState + ", bankType=" + bankType + ", totalFee=" + totalFee + ", cashFee=" + cashFee
				+ ", transactionId=" + transactionId + ", outTradeNo=" + outTradeNo + ", timeEnd=" + timeEnd
				+ ", tradeStateDesc=" + tradeStateDesc + "]";
	}

}
