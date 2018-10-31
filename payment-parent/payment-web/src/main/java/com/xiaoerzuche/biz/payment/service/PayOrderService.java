package com.xiaoerzuche.biz.payment.service;

import java.util.Date;

import com.xiaoerzuche.biz.payment.model.PayOrder;
import com.xiaoerzuche.biz.payment.vo.PagerVo;
import com.xiaoerzuche.biz.payment.vo.PayorderQueryVo;
import com.xiaoerzuche.biz.payment.vo.QueryVo;
import com.xiaoerzuche.biz.payment.vo.TranRecordVo;

public interface PayOrderService {

	public boolean add(PayOrder PayOrder);

	public boolean update(PayOrder PayOrder);
	
	/**
	 * 根据订单号获取已经成功支付的支付流水
	 * @param paybill
	 * @return
	 */
	public PayOrder get(String queryId);
	
	/**
	 * 根据第三方支付流水号查找支付订单信息
	 * @param origTradeNo
	 * @param type
	 * @return
	 */
	public PayOrder getByOrigTranNo(String origTradeNo, int type);
	
	/**
	 * 查询支付订单
	 * @param vo
	 * @return
	 */
	public PayOrder getPayOrderBy(QueryVo vo);
	
	/**
	 * 插入或更新支付订单信息
	 * @param payOrder
	 */
	public boolean setOrUpdatePay(PayOrder payOrder);
	
	/**
	 * 插入或更新退款订单信息
	 * @param payOrder
	 * @return
	 */
	public boolean setOrUpdateRefund(PayOrder payOrder);
	
	/**
	 * 支付、退款结果服务端回调状态变更
	 * @param queryId
	 * @param tranNo
	 * @param tranStatus
	 * @return
	 */
	public boolean updateCallBackStatus(String queryId,String tranNo, int tranStatus);
	
	/**
	 * 交易记录查询
	 * @param tranType 查看TranType枚举
	 * @param channel 业务渠码
	 * @param goodsNo 业务主订单
	 * @param timeStartDate 交易完成时间起始
	 * @param timeEndDate 交易完成时间截止
	 * @param offset 分页偏移量
	 * @param limit 分页查看长度
	 * @return
	 */
	PagerVo<TranRecordVo> getTranRecorders(String type, PayorderQueryVo payorderQueryVo);
}
