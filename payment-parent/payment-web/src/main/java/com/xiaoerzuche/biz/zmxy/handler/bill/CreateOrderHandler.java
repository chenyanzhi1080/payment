package com.xiaoerzuche.biz.zmxy.handler.bill;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xiaoerzuche.biz.hourlyrate.service.OrderService;
import com.xiaoerzuche.biz.hourlyrate.vo.OrderDetailVo;
import com.xiaoerzuche.biz.hourlyrate.vo.QueryOrder;
import com.xiaoerzuche.biz.zmxy.config.ZmxyClient;
import com.xiaoerzuche.biz.zmxy.dao.sqlbuilder.ZmxyFeedbackBillBuilder;
import com.xiaoerzuche.biz.zmxy.enu.FeedbackTypeEnu;
import com.xiaoerzuche.biz.zmxy.enu.ZmBillStatus;
import com.xiaoerzuche.biz.zmxy.enu.ZmOderStatusEnu;
import com.xiaoerzuche.biz.zmxy.model.ZmxyFeedbackBill;
import com.xiaoerzuche.biz.zmxy.service.ZmxyFeedbackBillService;
import com.xiaoerzuche.biz.zmxy.service.ZmxyUploadDetailService;
import com.xiaoerzuche.biz.zmxy.vo.OrderCreateRequestVo;
import com.xiaoerzuche.common.core.annotation.SingleEntryLimit;
import com.xiaoerzuche.common.util.DateTimeUtil;

@Component
public class CreateOrderHandler {
	private static final Logger logger = LoggerFactory.getLogger(CreateOrderHandler.class);
	@Autowired
	private ZmxyFeedbackBillService feedbackBillService;
	@Autowired
	private OrderService orderService;
    @Autowired
    private ZmxyUploadDetailService zmxyUploadDetailService;
 
	@SingleEntryLimit(lockKey = "CreateOrder_${1}")
	public void createBill(OrderCreateRequestVo createRequestVo, String orderNo) {
		logger.info("[芝麻信用][{}订单开始使用]",new Object[]{createRequestVo.getOrderNo()});
		ZmxyFeedbackBillBuilder builder = new ZmxyFeedbackBillBuilder();
		builder.setOrderNo(createRequestVo.getOrderNo());
		List<ZmxyFeedbackBill> bills = feedbackBillService.list(builder);

		if (bills.isEmpty()) {
			QueryOrder query = new QueryOrder();
			query.setOperator("payment").setOrderNo(createRequestVo.getOrderNo());
			OrderDetailVo orderDetailVo = orderService.getOrderDetail(query);
	    	BigDecimal rentFee = new BigDecimal(orderDetailVo.getRentFee());
	    	rentFee = rentFee.movePointLeft(2);//金额换成元
	    	Date bizDate = DateTimeUtil.parse(createRequestVo.getBizDate(), "yyyy-MM-dd HH:mm:ss");
	    	Date orderStartDate = DateTimeUtil.parse(orderDetailVo.getCreateTime(), "yyyy-MM-dd HH:mm:ss");
	    	Date toUploadDate = DateUtils.addDays(new Date(), 1);

			ZmxyFeedbackBill bill = new ZmxyFeedbackBill();
	    	bill.setBizDate(bizDate)
	    	.setUserCredentialsType("0")
	    	.setUserCredentialsNo(createRequestVo.getIdCard())
	    	.setUserName(createRequestVo.getMemberName())
	    	.setOrderNo(createRequestVo.getOrderNo())
	    	.setSubOrderNo(createRequestVo.getOrderNo())
	    	.setOrderStatus(ZmOderStatusEnu.START.getCode())
	    	.setOrderStartDate(orderStartDate)
	    	.setObjectName(ZmxyClient.objectName)
	    	.setObjectId(createRequestVo.getCarCard())
	    	.setRentDesc(ZmxyClient.rentDesc+rentFee+"元")
	    	.setDepositAmt(orderDetailVo.getDepositFee())
	    	.setBillStatus(ZmBillStatus.NORMAL.getCode())
	    	.setBillType(FeedbackTypeEnu.RENT.getCode())
	    	.setBillAmt(orderDetailVo.getRentFee())
	    	.setUploadTime(toUploadDate).setIsUpload(0)
	    	;
	    	feedbackBillService.addOrUpdate(bill);
	    	zmxyUploadDetailService.doUpload(bill,this.getClass().getSimpleName());
	    	logger.info("[芝麻信用][订单开始使用租金账单信息处理记录成功][当前账单{}]",new Object[]{bill});
		}

	}
}
