package com.xiaoerzuche.biz.zmxy.schedule;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.xiaoerzuche.biz.zmxy.dao.ZmxyFeedbackBillDao;
import com.xiaoerzuche.biz.zmxy.enu.ZmBillStatus;
import com.xiaoerzuche.biz.zmxy.model.ZmxyFeedbackBill;
import com.xiaoerzuche.biz.zmxy.service.ZmxyFeedbackBillService;
import com.xiaoerzuche.biz.zmxy.service.ZmxyUploadDetailService;

/** 
* 违约账单处理job.只查询
* @author: cyz
* @version: payment-web
* @time: 2017年8月29日
* 
*/
public class ZmBillDefaultJob implements Job{
    private static final Logger logger = LoggerFactory.getLogger(ZmBillDefaultJob.class);
	@Autowired
	private ZmxyFeedbackBillDao zmxyFeedbackBillDao;
    @Autowired
    private ZmxyUploadDetailService zmxyUploadDetailService;
	@Autowired
	private ZmxyFeedbackBillService feedbackBillService;
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("[已违约账单处理开始]");
		long starage = System.currentTimeMillis();
		List<ZmxyFeedbackBill> bills = zmxyFeedbackBillDao.billsToDefault();
		if(!bills.isEmpty()){
			Date bizDate = DateUtils.addMinutes(new Date(), -1);
			for(ZmxyFeedbackBill bill : bills){
				try {
					bill.setBillStatus(ZmBillStatus.DEFAULT.getCode())
					.setOrderStatus(ZmBillStatus.DEFAULT.getCode())
					.setBizDate(bizDate).setBillPayoffDate(null);
					feedbackBillService.addOrUpdate(bill);
					logger.info("[已违约账单处理]{}",new Object[]{bill});
					zmxyUploadDetailService.doUpload(bill,this.getClass().getSimpleName());
				} catch (Exception e) {
					logger.info("[已违约账单处理异常][bill={}][erro]{}",new Object[]{bill,e});
				}
			}
		}
		long endage = System.currentTimeMillis();
		logger.info("[已违约账单处理结束][size={}][age={}]",new Object[]{bills.size(),(endage-starage)});
	}
	
}
