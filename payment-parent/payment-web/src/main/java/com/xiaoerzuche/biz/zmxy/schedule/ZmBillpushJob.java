package com.xiaoerzuche.biz.zmxy.schedule;

import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.xiaoerzuche.biz.hourlyrate.enu.OrderEnum;
import com.xiaoerzuche.biz.hourlyrate.service.OrderService;
import com.xiaoerzuche.biz.hourlyrate.vo.OrderDetailVo;
import com.xiaoerzuche.biz.hourlyrate.vo.QueryOrder;
import com.xiaoerzuche.biz.invoice.enu.ExpensesTypeEnum;
import com.xiaoerzuche.biz.invoice.service.RecoveryService;
import com.xiaoerzuche.biz.invoice.vo.SesamaCreditDataPushVo;
import com.xiaoerzuche.biz.zmxy.dao.ZmxyFeedbackBillDao;
import com.xiaoerzuche.biz.zmxy.enu.FeedbackTypeEnu;
import com.xiaoerzuche.biz.zmxy.enu.ZmBillStatus;
import com.xiaoerzuche.biz.zmxy.model.ZmxyFeedbackBill;
import com.xiaoerzuche.biz.zmxy.service.ZmxyFeedbackBillService;
import com.xiaoerzuche.biz.zmxy.service.ZmxyUploadDetailService;

/** 
* 芝麻信用n+1上报
* 需求背景：产生费用后，N+1天上报
* @author: cyz
* @version: payment-web
* @time: 2018年1月8日
* 
*/
public class ZmBillpushJob implements Job{
	private static final Logger logger = LoggerFactory.getLogger(ZmBillpushJob.class);
	@Autowired
	private ZmxyFeedbackBillDao zmxyFeedbackBillDao;
    @Autowired
    private ZmxyUploadDetailService zmxyUploadDetailService;
	@Autowired
	private ZmxyFeedbackBillService feedbackBillService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private RecoveryService recoveryService;//费用追缴查询
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("[芝麻信用n+1处理开始]");
		long starage = System.currentTimeMillis();
		//获取n+1天
		List<ZmxyFeedbackBill> list = zmxyFeedbackBillDao.needUpload();
		for(ZmxyFeedbackBill bill : list){
			this.process(bill);
		}
		long endage = System.currentTimeMillis();
		logger.info("[芝麻信用n+1处理结束][size={}][age={}]",new Object[]{list.size(),(endage-starage)});
	}
	private void process(ZmxyFeedbackBill bill){
		try {
			if(bill.getBillType()==FeedbackTypeEnu.RENT.getCode()){
				QueryOrder query = new QueryOrder();
				query.setOperator("payment").setOrderNo(bill.getOrderNo());
				OrderDetailVo orderDetailVo = orderService.getOrderDetail(query);
				if(orderDetailVo.getCode()==HttpStatus.OK.value() 
						&& orderDetailVo.getZmOderStatus()==OrderEnum.AFTER_PAYING.getCode()){
					bill.setBillAmt(orderDetailVo.getRentFee())
					.setIsUpload(1);
					logger.debug("[芝麻信用n+1]{}",new Object[]{bill});
					zmxyUploadDetailService.doUpload(bill,this.getClass().getSimpleName());
					feedbackBillService.addOrUpdate(bill);
				}
			}else{
				ExpensesTypeEnum expensesTypeEnum = null;
				if(bill.getBillType()==FeedbackTypeEnu.ILLEGAL.getCode()){
					expensesTypeEnum = ExpensesTypeEnum.ILLEGAL;
				}else{
					expensesTypeEnum = ExpensesTypeEnum.EXPENSES;
				}
				SesamaCreditDataPushVo dataPushVo = recoveryService.queryRecoveryFeeDetail(bill.getOrderNo(),bill.getSubOrderNo(), expensesTypeEnum);
				if(dataPushVo.getCode() == HttpStatus.OK.value() && dataPushVo.getPayFee()>0){
						bill.setBillAmt(dataPushVo.getPayFee())
						.setIsUpload(1);
						logger.debug("[芝麻信用n+1]{}",new Object[]{bill});
						zmxyUploadDetailService.doUpload(bill,this.getClass().getSimpleName());
						feedbackBillService.addOrUpdate(bill);
				}
				
			}

		} catch (Exception e) {
			logger.info("[芝麻信用n+1][bill={}][erro]{}",new Object[]{bill,e});
		}
	}
}
