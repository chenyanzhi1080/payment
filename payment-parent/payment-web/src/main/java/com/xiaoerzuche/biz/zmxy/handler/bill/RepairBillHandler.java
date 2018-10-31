package com.xiaoerzuche.biz.zmxy.handler.bill;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaoerzuche.biz.hourlyrate.service.OrderService;
import com.xiaoerzuche.biz.hourlyrate.vo.OrderDetailVo;
import com.xiaoerzuche.biz.hourlyrate.vo.QueryOrder;
import com.xiaoerzuche.biz.invoice.enu.ExpensesTypeEnum;
import com.xiaoerzuche.biz.invoice.service.RecoveryService;
import com.xiaoerzuche.biz.invoice.vo.SesamaCreditDataPushVo;
import com.xiaoerzuche.biz.payment.service.PaySystemConfigService;
import com.xiaoerzuche.biz.zmxy.config.ZmxyClient;
import com.xiaoerzuche.biz.zmxy.dao.ZmxyFeedbackBillDao;
import com.xiaoerzuche.biz.zmxy.dao.sqlbuilder.ZmxyFeedbackBillBuilder;
import com.xiaoerzuche.biz.zmxy.enu.FeedbackTypeEnu;
import com.xiaoerzuche.biz.zmxy.enu.ZmBillStatus;
import com.xiaoerzuche.biz.zmxy.enu.ZmOderStatusEnu;
import com.xiaoerzuche.biz.zmxy.model.ZmxyFeedbackBill;
import com.xiaoerzuche.biz.zmxy.service.ZmxyFeedbackBillService;
import com.xiaoerzuche.biz.zmxy.service.ZmxyUploadDetailService;
import com.xiaoerzuche.biz.zmxy.vo.ZhimaDataFeedbackRequstVo;
import com.xiaoerzuche.biz.zmxy.vo.ZhimaDataSettledRequestVo;
import com.xiaoerzuche.common.core.annotation.SingleEntryLimit;
import com.xiaoerzuche.common.util.DateTimeUtil;

@Service
public class RepairBillHandler implements BillHandler{
    private static final Logger logger = LoggerFactory.getLogger(RepairBillHandler.class);
    private final String FEEDBACKBILL_DEFAULT_CYCLE_KEY = "FEEDBACKBILL_DEFAULT_CYCLE";
	@Autowired
	private PaySystemConfigService paySystemConfigService;
	@Autowired
	private ZmxyFeedbackBillService feedbackBillService;
	@Autowired
	private OrderService orderService;//时租订单查询
	@Autowired
	private RecoveryService recoveryService;//费用追缴查询
    @Autowired
    private ZmxyUploadDetailService zmxyUploadDetailService;
    
	@SingleEntryLimit(lockKey = "RepairBill_bill_${1}")
	@Override
	public ZmxyFeedbackBill bill(ZhimaDataFeedbackRequstVo dataFeedbackRequstVo, String subOrderNo) {
		ZmxyFeedbackBill rentBill = null;
		ZmxyFeedbackBill repairBill = null;

		ZmxyFeedbackBillBuilder rentBuilder = new ZmxyFeedbackBillBuilder();
		rentBuilder.setOrderNo(dataFeedbackRequstVo.getOrderNo())
		.setSubOrderNo(dataFeedbackRequstVo.getOrderNo())
		.setBillType(FeedbackTypeEnu.RENT.getCode());
		//查找租金账单和是否存在当前账单
		List<ZmxyFeedbackBill> rentBills = feedbackBillService.list(rentBuilder);
		
		ZmxyFeedbackBillBuilder repairBuilder = new ZmxyFeedbackBillBuilder();
		repairBuilder.setOrderNo(dataFeedbackRequstVo.getOrderNo())
		.setSubOrderNo(dataFeedbackRequstVo.getSubOrderNo())
		.setBillType(FeedbackTypeEnu.REPAIR.getCode());
		//查找违章账单和是否存在当前账单
		List<ZmxyFeedbackBill> repairBills = feedbackBillService.list(repairBuilder);
		
		if(! rentBills.isEmpty()){
			rentBill = rentBills.get(0);
		}
		if(! repairBills.isEmpty()){
			repairBill = repairBills.get(0);
		}
		
//		Date bizDate = DateTimeUtil.parse(dataFeedbackRequstVo.getBizDate(), "yyyy-MM-dd HH:mm:ss");
		Date bizDate = new Date();
		QueryOrder query = new QueryOrder();
		query.setOperator("payment").setOrderNo(dataFeedbackRequstVo.getOrderNo());
		OrderDetailVo orderDetailVo = orderService.getOrderDetail(query);
    	BigDecimal rentFee = new BigDecimal(orderDetailVo.getRentFee());
    	rentFee = rentFee.movePointLeft(2);//金额换成元
    	String cycleValue = paySystemConfigService.get(FEEDBACKBILL_DEFAULT_CYCLE_KEY).getConfigValue();//逾期周期
    	int cycle = Integer.parseInt(cycleValue);
    	Date orderStartDate = DateTimeUtil.parse(orderDetailVo.getCreateTime(), "yyyy-MM-dd HH:mm:ss");
    	Date billLastDate = DateUtils.addDays(bizDate, cycle);
    	Date toUploadDate = DateUtils.addDays(new Date(), 1);
		SesamaCreditDataPushVo dataPushVo = recoveryService.queryRecoveryFeeDetail(dataFeedbackRequstVo.getOrderNo(),dataFeedbackRequstVo.getSubOrderNo(), ExpensesTypeEnum.EXPENSES);
		
		//已有违章账单记录
    	if(null != repairBill){
			logger.info("[芝麻信用数据反馈][维修账单记录变更][OrderNo={}][SubOrderNo={}]", new Object[]{dataFeedbackRequstVo.getOrderNo(),dataFeedbackRequstVo.getSubOrderNo()});
    		repairBill.setBizDate(bizDate)
    		.setBillAmt(dataPushVo.getPayFee())
    		.setBillStatus(ZmBillStatus.NORMAL.getCode())
    		.setOrderStatus(ZmBillStatus.NORMAL.getCode())
    		.setBillLastDate(billLastDate)
    		.setBillPayoffDate(null)
    		.setUploadTime(toUploadDate).setIsUpload(0);
    		feedbackBillService.addOrUpdate(repairBill);
//    		zmxyUploadDetailService.doUpload(repairBill);
    		return repairBill;
    	}
    	//没有违章账单记录，但有租金账单记录
		if(null != rentBill){
			logger.info("[芝麻信用数据反馈][维修账单记录新增][OrderNo={}][SubOrderNo={}]", new Object[]{dataFeedbackRequstVo.getOrderNo(),dataFeedbackRequstVo.getSubOrderNo()});
			repairBill = new ZmxyFeedbackBill();
			repairBill.setBizDate(bizDate)
	    	.setUserCredentialsType("0")
	    	.setUserCredentialsNo(rentBill.getUserCredentialsNo())
	    	.setUserName(rentBill.getUserName())
	    	.setOrderNo(dataFeedbackRequstVo.getOrderNo())
	    	.setSubOrderNo(dataFeedbackRequstVo.getSubOrderNo())
	    	.setOrderStatus(ZmOderStatusEnu.NORMAL.getCode())
	    	.setOrderStartDate(rentBill.getOrderStartDate())
	    	.setObjectName(ZmxyClient.objectName)
	    	.setObjectId(rentBill.getObjectId())
	    	.setRentDesc(ZmxyClient.rentDesc+rentFee+"元")
	    	.setDepositAmt(rentBill.getDepositAmt())
	    	.setBillStatus(ZmBillStatus.NORMAL.getCode())
	    	.setBillType(FeedbackTypeEnu.REPAIR.getCode())
	    	.setBillAmt(dataPushVo.getPayFee())
	    	.setBillLastDate(billLastDate)
	    	.setBillPayoffDate(null)
	    	.setUploadTime(toUploadDate).setIsUpload(0);
			
			feedbackBillService.addOrUpdate(repairBill);
//    		zmxyUploadDetailService.doUpload(repairBill);
    		return repairBill;
		}
		return null;
	}
	@SingleEntryLimit(lockKey = "RepairBill_settled_${1}")
	@Override
	public ZmxyFeedbackBill settled(ZhimaDataSettledRequestVo settledRequestVo, String subOrderNo) {
		logger.info("[芝麻信用数据反馈][维修账单完结处理][OrderNo={}][SubOrderNo={}]", new Object[]{settledRequestVo.getOrderNo(),settledRequestVo.getSubOrderNo()});
		ZmxyFeedbackBillBuilder builder = new ZmxyFeedbackBillBuilder();
		builder.setOrderNo(settledRequestVo.getOrderNo())
		.setSubOrderNo(settledRequestVo.getSubOrderNo())
		.setBillType(FeedbackTypeEnu.REPAIR.getCode());
		//检查目前状态为ZmBillStatus.NORMAL的账单是否存在
		List<ZmxyFeedbackBill> bills = feedbackBillService.list(builder);
		ZmxyFeedbackBill repairBill = null;
		
		if(!bills.isEmpty()){
			Date bizDate = new Date();
			repairBill = bills.get(0);
			SesamaCreditDataPushVo dataPushVo = recoveryService.queryRecoveryFeeDetail(settledRequestVo.getOrderNo(),settledRequestVo.getSubOrderNo(), ExpensesTypeEnum.EXPENSES);
			if(dataPushVo.getPayFee()==0){
				repairBill.setBillStatus(ZmBillStatus.SETTLED.getCode())
				.setBizDate(bizDate)
				.setOrderStatus(ZmBillStatus.NORMAL.getCode());
				if(repairBill.getBillPayoffDate()==null){
					repairBill.setBillPayoffDate(bizDate);
				}
				if(null==repairBill.getBillLastDate()){
			    	String cycleValue = paySystemConfigService.get(FEEDBACKBILL_DEFAULT_CYCLE_KEY).getConfigValue();//逾期周期
			    	int cycle = Integer.parseInt(cycleValue);
					Date billLastDate = DateUtils.addDays(repairBill.getOrderStartDate(), cycle);
					repairBill.setBillLastDate(billLastDate);
				}
				feedbackBillService.addOrUpdate(repairBill);
				ZmxyFeedbackBill dbBill = feedbackBillService.get(repairBill.getBillNo());
				logger.info("维修账单OrderNo={}当前账单信息{}", new Object[]{repairBill.getOrderNo(),dbBill});
				zmxyUploadDetailService.doUpload(dbBill,this.getClass().getSimpleName());
				logger.info("[芝麻信用数据反馈][维修账单已完结][OrderNo={}][SubOrderNo={}]", new Object[]{settledRequestVo.getOrderNo(),settledRequestVo.getSubOrderNo()});
			}
		}else{
			logger.info("[芝麻信用数据反馈][维修账单已完结][OrderNo={}][SubOrderNo={}][订单不存在]", new Object[]{settledRequestVo.getOrderNo(),settledRequestVo.getSubOrderNo()});
		}
		return repairBill;
	}


}
