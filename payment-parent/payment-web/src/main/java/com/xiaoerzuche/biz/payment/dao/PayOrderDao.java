package com.xiaoerzuche.biz.payment.dao;


import java.util.Date;
import java.util.List;

import com.xiaoerzuche.biz.payment.dao.builder.PayorderBuilder;
import com.xiaoerzuche.biz.payment.model.PayOrder;
import com.xiaoerzuche.biz.payment.vo.QueryVo;
import com.xiaoerzuche.common.core.data.spi.IUpdate;

public interface PayOrderDao extends IUpdate<PayOrder, String> {
	
	PayOrder getByOrder(QueryVo queryVo);
	
	/**
	 * 根据第三方支付流水号和支付方式查找支付订单信息
	 * @param origTranNo
	 * @param payType
	 * @return
	 */
	PayOrder getByOrigTranNo(String origTranNo, int payType);
	
	/**
	 * 查询交易记录
	 * @return
	 */
	List<PayOrder> getTranRecorders(PayorderBuilder builder);
	/**
	 * 查询总记录数
	 * @return
	 */
	long getTranRecordersCount(PayorderBuilder builder);
	
	/**
	 * 交易关闭
	 * @param queryId
	 * @return
	 */
	boolean tradeFinished(String queryId); 
}
