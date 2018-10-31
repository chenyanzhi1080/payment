package com.xiaoerzuche.biz.zmxy.schedule;

import java.util.Date;
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
 * 检查目前未结清的账单最新状态
* 主动结清处理账单范围：违约周期内的订单状态为非完结的账单。违约周期为产生账单当天+6天=7天。
* 去业务方查验，订单状态是否为已完成。则orderStatus为2，billStatus为2。如果只是不是所有账单完成（订单状态未变更未已完成），则orderStatus为0，billStatus为2。
* @author: cyz
* @version: payment-web
* @time: 2017年8月29日
* 
*/
public class ZmUnSettledBillJob implements Job{
	private static final Logger logger = LoggerFactory.getLogger(ZmUnSettledBillJob.class);
	@Autowired
	private OrderService orderService;
	@Autowired
	private RecoveryService recoveryService;//费用追缴查询
	@Autowired
	private ZmxyFeedbackBillService feedbackBillService;
    @Autowired
    private ZmxyFeedbackBillDao zmxyFeedbackBillDao;
    @Autowired
    private ZmxyUploadDetailService zmxyUploadDetailService;
    
	/**
	 * 暂时取消该作业
	 */
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
/*		logger.info("[违约截止前账单结清检查处理开始]");
		long starage = System.currentTimeMillis();
		List<ZmxyFeedbackBill> unSettledBills = zmxyFeedbackBillDao.unSettledBills(null, new Date());//当前需要处理的账单
		if(! unSettledBills.isEmpty()){
			for(ZmxyFeedbackBill bill :unSettledBills){
				try {
					this.process(bill);
				} catch (Exception e) {
					logger.info("[违约截止前账单结清检查处理异常][bill={}][erro]{}",new Object[]{bill,e});
				}
			}
		}
		long endage = System.currentTimeMillis();
		logger.info("[违约截止前账单结清检查处理结束][size={}][age={}]",new Object[]{unSettledBills.size(),(endage-starage)});*/
	}
	
	private void process(ZmxyFeedbackBill bill){
		Date bizDate = new Date();
		QueryOrder query = null;
		OrderDetailVo orderDetailVo = null;
		query = new QueryOrder();
		query.setOrderNo(bill.getOrderNo()).setOperator("payment");
		orderDetailVo = orderService.getOrderDetail(query);

		if(bill.getBillType()==FeedbackTypeEnu.RENT.getCode()){
			if(orderDetailVo.getCode()==HttpStatus.OK.value()){
				if(orderDetailVo.getStatus() == OrderEnum.AFTER_ILLAGAL.getCode()){
					bill.setBillStatus(ZmBillStatus.SETTLED.getCode())
					.setOrderStatus(ZmBillStatus.NORMAL.getCode())
					.setBizDate(bizDate);
					if(bill.getBillPayoffDate()==null){
						bill.setBillPayoffDate(bizDate);
					}
					
					feedbackBillService.addOrUpdate(bill);
					logger.info("[违约截止前账单结清检查处理]{}",new Object[]{bill});
					zmxyUploadDetailService.doUpload(bill,this.getClass().getSimpleName());
				}
				if(orderDetailVo.getStatus() == OrderEnum.AFTER_HISTORY.getCode()){
					bill.setBillStatus(ZmBillStatus.SETTLED.getCode())
					.setOrderStatus(ZmBillStatus.SETTLED.getCode())
					.setBizDate(bizDate);
					if(bill.getBillPayoffDate()==null){
						bill.setBillPayoffDate(bizDate);
					}
					feedbackBillService.addOrUpdate(bill);
					logger.info("[违约截止前账单结清检查处理]{}",new Object[]{bill});
					zmxyUploadDetailService.doUpload(bill,this.getClass().getSimpleName());
				}
			}else{
				logger.info("[违约截止前账单结清检查处理][时租账单查询异常][code={}][msg={}]", new Object[]{orderDetailVo.getCode(),orderDetailVo.getMsg()});
			}
		}else{//违章欠费类账单,需要去费用补交那边查询结果。
			ExpensesTypeEnum expensesTypeEnum = null;
			if(bill.getBillType()==FeedbackTypeEnu.ILLEGAL.getCode()){
				expensesTypeEnum = ExpensesTypeEnum.ILLEGAL;
			}else{
				expensesTypeEnum = ExpensesTypeEnum.EXPENSES;
			}
			SesamaCreditDataPushVo dataPushVo = recoveryService.queryRecoveryFeeDetail(bill.getOrderNo(),bill.getSubOrderNo(), expensesTypeEnum);
			if(orderDetailVo.getCode()==HttpStatus.OK.value() && dataPushVo.getCode() == HttpStatus.OK.value()){
				if(dataPushVo.getPayFee()==0){
					if(orderDetailVo.getStatus() == OrderEnum.AFTER_ILLAGAL.getCode()){
						bill.setBillStatus(ZmBillStatus.SETTLED.getCode())
						.setOrderStatus(ZmBillStatus.NORMAL.getCode())
						.setBizDate(bizDate);
						if(bill.getBillPayoffDate()==null){
							bill.setBillPayoffDate(bizDate);
						}
						feedbackBillService.addOrUpdate(bill);
						logger.info("[违约截止前账单结清检查处理]{}",new Object[]{bill});
						zmxyUploadDetailService.doUpload(bill,this.getClass().getSimpleName());
					}
					if(orderDetailVo.getStatus() == OrderEnum.AFTER_HISTORY.getCode()){
						bill.setBillStatus(ZmBillStatus.SETTLED.getCode())
						.setOrderStatus(ZmBillStatus.SETTLED.getCode())
						.setBizDate(bizDate);
						if(bill.getBillPayoffDate()==null){
							bill.setBillPayoffDate(bizDate);
						}
						feedbackBillService.addOrUpdate(bill);
						logger.info("[违约截止前账单结清检查处理]{}",new Object[]{bill});
						zmxyUploadDetailService.doUpload(bill,this.getClass().getSimpleName());
					}
				}
			}
			
		}
	}
}
