package com.xiaoerzuche.biz.zmxy.service;

import com.xiaoerzuche.biz.zmxy.model.ZmxyFeedbackBill;
import com.xiaoerzuche.biz.zmxy.model.ZmxyUploadDetail;
import com.xiaoerzuche.common.core.data.jdbc.Page;
public interface ZmxyUploadDetailService  {

	boolean insert(ZmxyUploadDetail zmxyUploadDetail);

	boolean update(ZmxyUploadDetail zmxyUploadDetail);

	boolean delete(String id);

	ZmxyUploadDetail get(String id);

	Page< ZmxyUploadDetail>  list(int pageStart, int pageSize);
	
	/**
	 * 记录上传芝麻信用
	 * @param zmxyUploadDetail 明细项
	 * @return 成功返回true，失败返回false
	 */
	boolean uploadFeedbackBillToZmxy(ZmxyUploadDetail zmxyUploadDetail);
	/**
	 * @param zmxyFeedbackBill
	 * @return
	 */
	ZmxyUploadDetail build(ZmxyFeedbackBill zmxyFeedbackBill);
	
	/**
	 * 上报账单
	 * @param zmxyFeedbackBill 需要上报的账单数据
	 * @param taskClassName 触发上报任务的类名（用于观察该次上报是哪里触发的）
	 */
	void doUpload(ZmxyFeedbackBill zmxyFeedbackBill, String taskClassName);
}