package com.xiaoerzuche.biz.zmxy.service;

import java.util.List;

import com.xiaoerzuche.biz.zmxy.dao.sqlbuilder.ZmxyFeedbackBillBuilder;
import com.xiaoerzuche.biz.zmxy.model.ZmxyFeedbackBill;
public interface ZmxyFeedbackBillService  {

	boolean insert(ZmxyFeedbackBill zmxyFeedbackBill);

	boolean update(ZmxyFeedbackBill zmxyFeedbackBill);

	boolean delete(String billNo);
	
	boolean addOrUpdate(ZmxyFeedbackBill zmxyFeedbackBill);

	ZmxyFeedbackBill get(String billNo);
	
	List<ZmxyFeedbackBill> list(ZmxyFeedbackBillBuilder builder);
}