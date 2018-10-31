package com.xiaoerzuche.biz.payment.vo.bcustomer;


/**
 * @Description : 企业电子钱包消费、退款交易结果
 * @Author : cyz
 * @Date : 2017/11/16
 **/
public class WalletTranResultVo {
    private String tranNo;//交易流水号

    public String getTranNo() {
        return tranNo;
    }

    public WalletTranResultVo setTranNo(String tranNo) {
        this.tranNo = tranNo;
        return this;
    }
}
