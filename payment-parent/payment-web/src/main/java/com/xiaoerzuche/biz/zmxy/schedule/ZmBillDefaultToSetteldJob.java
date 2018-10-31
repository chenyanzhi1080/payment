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
* 已违约账单结清
* @author: cyz
* @version: payment-web
* @time: 2017年8月31日
* 
*/
public class ZmBillDefaultToSetteldJob implements Job{
	private static final Logger logger = LoggerFactory.getLogger(ZmBillDefaultToSetteldJob.class);
	@Autowired
	private OrderService orderService;
	@Autowired
	private ZmxyFeedbackBillService feedbackBillService;
    @Autowired
    private ZmxyFeedbackBillDao zmxyFeedbackBillDao;
    @Autowired
    private ZmxyUploadDetailService zmxyUploadDetailService;
	@Autowired
	private RecoveryService recoveryService;//费用追缴查询
    
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
/*		logger.info("[未完结账单结清检查处理开始]");
		long starage = System.currentTimeMillis();
		List<ZmxyFeedbackBill> unSettledBills = zmxyFeedbackBillDao.defaultBillsToSettled();//当前需要处理的账单
		QueryOrder query = null;
		OrderDetailVo orderDetailVo = null;
		if(! unSettledBills.isEmpty()){
			Date bizDate = DateUtils.addMinutes(new Date(), -1);
			for(ZmxyFeedbackBill bill :unSettledBills){
				query = new QueryOrder();
				query.setOrderNo(bill.getOrderNo()).setOperator("payment");
				orderDetailVo = orderService.getOrderDetail(query);
				if(orderDetailVo.getCode()==HttpStatus.OK.value()){
					try {
						bill.setBizDate(bizDate).setBillPayoffDate(bizDate);
						this.process(bill, orderDetailVo);
					} catch (Exception e) {
						logger.info("[未完结账单结清检查处理][bill={}][erro]{}",new Object[]{bill,e});
					}
				}else{
					logger.info("[未完结账单结清检查处理][时租账单查询异常][code={}][msg={}]", new Object[]{orderDetailVo.getCode(),orderDetailVo.getMsg()});
				}
				orderDetailVo = null;
				query = null;
			}
		}
		long endage = System.currentTimeMillis();
		logger.info("[未完结账单结清检查处理结束][size={}][age={}]",new Object[]{unSettledBills.size(),(endage-starage)});
*/
	}
	
	private void process(ZmxyFeedbackBill bill, OrderDetailVo orderDetailVo){
		Date bizDate = new Date();
		int orderBzStatus = orderDetailVo.getStatus();
		if(orderBzStatus == OrderEnum.AFTER_HISTORY.getCode()){
			bill.setBillStatus(ZmBillStatus.SETTLED.getCode())
			.setOrderStatus(ZmBillStatus.SETTLED.getCode())
			.setBizDate(bizDate);
			feedbackBillService.addOrUpdate(bill);
			logger.info("[未完结账单结清检查处理]{}",new Object[]{bill});
			zmxyUploadDetailService.doUpload(bill,this.getClass().getSimpleName());
		}else if(orderBzStatus == OrderEnum.AFTER_ILLAGAL.getCode()){
			if(bill.getBillType()==FeedbackTypeEnu.RENT.getCode() && bill.getBillStatus()!= ZmBillStatus.SETTLED.getCode()){
				bill.setBillStatus(ZmBillStatus.SETTLED.getCode())
				.setOrderStatus(ZmBillStatus.NORMAL.getCode())
				.setBizDate(bizDate)
				.setBillPayoffDate(bizDate);
				feedbackBillService.addOrUpdate(bill);
				logger.info("[未完结账单结清检查处理]{}",new Object[]{bill});
				zmxyUploadDetailService.doUpload(bill,this.getClass().getSimpleName());
			}else{
				ExpensesTypeEnum expensesTypeEnum = null;
				if(bill.getBillType()==FeedbackTypeEnu.ILLEGAL.getCode()){
					expensesTypeEnum = ExpensesTypeEnum.ILLEGAL;
				}else{
					expensesTypeEnum = ExpensesTypeEnum.EXPENSES;
				}
				SesamaCreditDataPushVo dataPushVo = recoveryService.queryRecoveryFeeDetail(bill.getOrderNo(), bill.getSubOrderNo(),expensesTypeEnum);
				if(dataPushVo.getCode() == HttpStatus.OK.value()){
					if(dataPushVo.getPayFee()==0){
						bill.setBillStatus(ZmBillStatus.SETTLED.getCode())
						.setOrderStatus(ZmBillStatus.NORMAL.getCode())
						.setBizDate(bizDate)
						.setBillPayoffDate(bizDate);
						feedbackBillService.addOrUpdate(bill);
						logger.info("[违约截止前账单结清检查处理]{}",new Object[]{bill});
						zmxyUploadDetailService.doUpload(bill,this.getClass().getSimpleName());
					}
				}
			}
		}
	}
}
