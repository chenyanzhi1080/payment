package com.xiaoerzuche.biz.zmxy.dao;

import java.util.Date;
import java.util.List;

import com.xiaoerzuche.biz.zmxy.dao.sqlbuilder.ZmxyFeedbackBillBuilder;
import com.xiaoerzuche.biz.zmxy.model.ZmxyFeedbackBill;
public interface ZmxyFeedbackBillDao  {

	boolean insert(ZmxyFeedbackBill zmxyFeedbackBill);

	boolean update(ZmxyFeedbackBill zmxyFeedbackBill);

	boolean delete(String billNo);

	ZmxyFeedbackBill get(String billNo);
	
	/**
	 * 根据业务订单号订单类型查询
	 * @param orderNo
	 * @param billType
	 * @return
	 */
	List<ZmxyFeedbackBill> queryBills(String orderNo);
	
	/**
	 * 需要结清的账单
	 * @param orderNo
	 * @return
	 */
	List<ZmxyFeedbackBill> unSettledBills(String orderNo, Date billLastDate);
	
	/**
	 * 已违约的账单。大于履约截止日期的账单
	 * @return
	 */
	List<ZmxyFeedbackBill> billsToDefault();
	
	/**
	 * 已违约但订单状态未完结的账单
	 * @return
	 */
	List<ZmxyFeedbackBill> defaultBillsToSettled();
	
	List<ZmxyFeedbackBill> list(ZmxyFeedbackBillBuilder builder);
	
	/**
	 * 需要N+1上传的账单
	 * @return
	 */
	List<ZmxyFeedbackBill> needUpload();
	
}