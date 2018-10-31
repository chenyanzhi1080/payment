package com.xiaoerzuche.biz.zmxy.handler.bill;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xiaoerzuche.biz.zmxy.dao.sqlbuilder.ZmxyFeedbackBillBuilder;
import com.xiaoerzuche.biz.zmxy.enu.ZmBillStatus;
import com.xiaoerzuche.biz.zmxy.model.ZmxyFeedbackBill;
import com.xiaoerzuche.biz.zmxy.service.ZmxyFeedbackBillService;
import com.xiaoerzuche.biz.zmxy.service.ZmxyUploadDetailService;
import com.xiaoerzuche.biz.zmxy.vo.ZhimaDataFeedbackRequstVo;
import com.xiaoerzuche.common.core.annotation.SingleEntryLimit;

@Component
public class OrderFinishHandler {
	private static final Logger logger = LoggerFactory.getLogger(OrderFinishHandler.class);
	@Autowired
	private ZmxyFeedbackBillService feedbackBillService;
    @Autowired
    private ZmxyUploadDetailService zmxyUploadDetailService;
    
	@SingleEntryLimit(lockKey = "Order_finish_${1}")
    public void orderFinish(ZhimaDataFeedbackRequstVo feedbackRequstVo, String subOrderNo){
		logger.info("[芝麻信用][{}订单已完成]",new Object[]{feedbackRequstVo.getOrderNo()});
		ZmxyFeedbackBillBuilder builder = new ZmxyFeedbackBillBuilder();
		builder.setOrderNo(feedbackRequstVo.getOrderNo());
		List<ZmxyFeedbackBill> bills = feedbackBillService.list(builder);
//		Date bizDate = DateTimeUtil.parse(feedbackRequstVo.getBizDate(), "yyyy-MM-dd HH:mm:ss");
		Date bizDate = new Date();
		if (!bills.isEmpty()) {
			for(ZmxyFeedbackBill bill : bills){
				bill.setBillStatus(ZmBillStatus.SETTLED.getCode())
				.setOrderStatus(ZmBillStatus.SETTLED.getCode())
//				.setBillPayoffDate(bizDate)
				.setBizDate(bizDate)
				.setIsUpload(1);
				if(bill.getBillPayoffDate()==null){
					bill.setBillPayoffDate(bizDate);
				}
		    	feedbackBillService.addOrUpdate(bill);
		    	zmxyUploadDetailService.doUpload(bill,this.getClass().getSimpleName());
		    	logger.info("[{}订单已完成][当前账单{}]",new Object[]{feedbackRequstVo.getOrderNo(),bill});
			}
		}
    }
}
