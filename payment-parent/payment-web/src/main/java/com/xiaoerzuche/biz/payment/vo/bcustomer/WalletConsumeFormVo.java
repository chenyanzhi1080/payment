package com.xiaoerzuche.biz.payment.vo.bcustomer;


/**
 * @Description :电子钱包消费表单
 * @Author : cyz
 * @Date : 2017/11/16
 **/
public class WalletConsumeFormVo {
    private String memberId;//企业账户ID
    private String account;//企业账户登陆账号
    private String queryId;//支付网关查询码
    private String goodsOrderId;//业务主订单号
    private String orderId;//子订单号
    private Integer price;//交易金额，以分为单位
    private String type;//电子类型。按业务区分

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getGoodsOrderId() {
        return goodsOrderId;
    }

    public void setGoodsOrderId(String goodsOrderId) {
        this.goodsOrderId = goodsOrderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    public String getQueryId() {
		return queryId;
	}

	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}

	@Override
	public String toString() {
		return "WalletConsumeFormVo [memberId=" + memberId + ", account=" + account + ", queryId=" + queryId
				+ ", goodsOrderId=" + goodsOrderId + ", orderId=" + orderId + ", price=" + price + ", type=" + type
				+ "]";
	}

}
