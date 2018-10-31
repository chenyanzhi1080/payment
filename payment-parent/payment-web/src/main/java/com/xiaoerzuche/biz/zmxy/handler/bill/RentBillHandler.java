package com.xiaoerzuche.biz.zmxy.handler.bill;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaoerzuche.biz.hourlyrate.enu.OrderEnum;
import com.xiaoerzuche.biz.hourlyrate.service.OrderService;
import com.xiaoerzuche.biz.hourlyrate.vo.OrderDetailVo;
import com.xiaoerzuche.biz.hourlyrate.vo.QueryOrder;
import com.xiaoerzuche.biz.payment.service.PaySystemConfigService;
import com.xiaoerzuche.biz.zmxy.config.ZmxyClient;
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

/** 
* 租金账单处理
* @author: cyz
* @version: payment-web
* @time: 2017年8月28日
* 
*/
@Service
public class RentBillHandler implements BillHandler{
    private static final Logger logger = LoggerFactory.getLogger(RentBillHandler.class);
    private final String FEEDBACKBILL_DEFAULT_CYCLE_KEY = "FEEDBACKBILL_DEFAULT_CYCLE";
	@Autowired
	private PaySystemConfigService paySystemConfigService;
	@Autowired
	private ZmxyFeedbackBillService feedbackBillService;
	@Autowired
	private OrderService orderService;
    @Autowired
    private ZmxyUploadDetailService zmxyUploadDetailService;
	/**
	 *	租金待支付处理
	 */
    @SingleEntryLimit(lockKey = "RentBill_bill_${1}")
	@Override
	public ZmxyFeedbackBill bill(ZhimaDataFeedbackRequstVo dataFeedbackRequstVo, String subOrderNo) {
		logger.info("[芝麻信用数据反馈][租金待支付]"+dataFeedbackRequstVo);
		
		ZmxyFeedbackBillBuilder builder = new ZmxyFeedbackBillBuilder();
		builder.setOrderNo(dataFeedbackRequstVo.getOrderNo())
		.setSubOrderNo(dataFeedbackRequstVo.getOrderNo())
		.setBillType(FeedbackTypeEnu.RENT.getCode());
		//查找租金账单和是否存在当前账单
		List<ZmxyFeedbackBill> bills = feedbackBillService.list(builder);
		ZmxyFeedbackBill rentBill = null;
		if(!bills.isEmpty()){
			rentBill = bills.get(0);
		}
		
		Date bizDate = new Date();
		QueryOrder query = new QueryOrder();
		query.setOperator("payment").setOrderNo(dataFeedbackRequstVo.getOrderNo());
		OrderDetailVo orderDetailVo = orderService.getOrderDetail(query);
    	BigDecimal rentFee = new BigDecimal(orderDetailVo.getRentFee());
    	rentFee = rentFee.movePointLeft(2);//金额换成元
    	bizDate = DateUtils.addMinutes(bizDate, -1);
    	String cycleValue = paySystemConfigService.get(FEEDBACKBILL_DEFAULT_CYCLE_KEY).getConfigValue();//逾期周期
    	int cycle = Integer.parseInt(cycleValue);
    	Date orderStartDate = DateTimeUtil.parse(orderDetailVo.getCreateTime(), "yyyy-MM-dd HH:mm:ss");
    	Date billLastDate = DateUtils.addDays(orderStartDate, cycle);
    	Date toUploadDate = DateUtils.addDays(new Date(), 1);
		if(null != rentBill){
			logger.info("[芝麻信用数据反馈][租金账单记录变更][OrderNo={}][SubOrderNo={}]", new Object[]{dataFeedbackRequstVo.getOrderNo(),dataFeedbackRequstVo.getSubOrderNo()});
			rentBill.setBizDate(bizDate)
	    	.setOrderStatus(ZmOderStatusEnu.NORMAL.getCode())
	    	.setRentDesc(ZmxyClient.rentDesc+rentFee+"元")
	    	.setBillStatus(ZmBillStatus.NORMAL.getCode())
	    	.setBillAmt(orderDetailVo.getRentFee())
	    	.setBillLastDate(billLastDate)
	    	.setBillPayoffDate(null)
	    	.setUploadTime(toUploadDate).setIsUpload(0);
			feedbackBillService.addOrUpdate(rentBill);
		}
/*		else{
			logger.info("[芝麻信用数据反馈][租金账单记录新增][OrderNo={}][SubOrderNo={}]", new Object[]{dataFeedbackRequstVo.getOrderNo(),dataFeedbackRequstVo.getSubOrderNo()});
			rentBill =  new ZmxyFeedbackBill();
	    	rentBill.setBizDate(bizDate)
	    	.setUserCredentialsType("0")
	    	.setUserCredentialsNo(dataFeedbackRequstVo.getIdCard())
	    	.setUserName(dataFeedbackRequstVo.getMemberName())
	    	.setOrderNo(dataFeedbackRequstVo.getOrderNo())
	    	.setSubOrderNo(dataFeedbackRequstVo.getOrderNo())
	    	.setOrderStatus(ZmOderStatusEnu.NORMAL.getCode())
	    	.setOrderStartDate(orderStartDate)
	    	.setObjectName(ZmxyClient.objectName)
	    	.setObjectId(dataFeedbackRequstVo.getCarCard())
	    	.setRentDesc(ZmxyClient.rentDesc+rentFee+"元")
	    	.setDepositAmt(orderDetailVo.getDepositFee())
	    	.setBillStatus(ZmBillStatus.NORMAL.getCode())
	    	.setBillType(FeedbackTypeEnu.RENT.getCode())
	    	.setBillAmt(orderDetailVo.getRentFee())
	    	.setBillLastDate(billLastDate)
	    	.setBillPayoffDate(null)
	    	.setUploadTime(toUploadDate).setIsUpload(0);
	    	feedbackBillService.addOrUpdate(rentBill);
		}*/
		
		return rentBill;
	}
	
	@SingleEntryLimit(lockKey = "RentBill_settled_${1}")
	@Override
	public ZmxyFeedbackBill settled(ZhimaDataSettledRequestVo settledRequestVo, String subOrderNo) {
		ZmxyFeedbackBillBuilder builder = new ZmxyFeedbackBillBuilder();
		builder.setOrderNo(settledRequestVo.getOrderNo())
		.setSubOrderNo(settledRequestVo.getOrderNo())
		.setBillType(FeedbackTypeEnu.RENT.getCode());
		//检查目前状态为ZmBillStatus.NORMAL的账单是否存在
		List<ZmxyFeedbackBill> bills = feedbackBillService.list(builder);
		ZmxyFeedbackBill rentBill = null;
		if(!bills.isEmpty()){
			rentBill = bills.get(0);
			Date bizDate = new Date();
			rentBill.setBillStatus(ZmBillStatus.SETTLED.getCode())
			.setOrderStatus(ZmBillStatus.NORMAL.getCode())
			.setBizDate(bizDate)
			.setIsUpload(1);
			if(rentBill.getBillPayoffDate()==null){
				rentBill.setBillPayoffDate(bizDate);
				logger.info("租金账单OrderNo={}已完结设置BillPayoffDate={}",new Object[]{rentBill.getOrderNo(),rentBill.getBillPayoffDate()});
			}
			if(null==rentBill.getBillLastDate()){
		    	String cycleValue = paySystemConfigService.get(FEEDBACKBILL_DEFAULT_CYCLE_KEY).getConfigValue();//逾期周期
		    	int cycle = Integer.parseInt(cycleValue);
				Date billLastDate = DateUtils.addDays(rentBill.getOrderStartDate(), cycle);
				rentBill.setBillLastDate(billLastDate);
			}
			feedbackBillService.addOrUpdate(rentBill);
			ZmxyFeedbackBill dbBill = feedbackBillService.get(rentBill.getBillNo());
			logger.info("租金账单OrderNo={}当前账单信息{}", new Object[]{rentBill.getOrderNo(),dbBill});
			zmxyUploadDetailService.doUpload(dbBill,this.getClass().getSimpleName());
			logger.info("[芝麻信用数据反馈][租金账单已完结][OrderNo={}][SubOrderNo={}]", new Object[]{rentBill.getOrderNo(),rentBill.getSubOrderNo()});
		}else{
			logger.info("[芝麻信用数据反馈][租金账单已完结][OrderNo={}][SubOrderNo={}][订单不存在]", new Object[]{rentBill.getOrderNo(),rentBill.getSubOrderNo()});
		}
		
		return rentBill;
	}
    
}
