package com.xiaoerzuche.biz.zmxy.service.imp;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaoerzuche.biz.zmxy.dao.ZmxyFeedbackBillDao;
import com.xiaoerzuche.biz.zmxy.dao.sqlbuilder.ZmxyFeedbackBillBuilder;
import com.xiaoerzuche.biz.zmxy.enu.FeedbackTypeEnu;
import com.xiaoerzuche.biz.zmxy.handler.bill.CreateOrderHandler;
import com.xiaoerzuche.biz.zmxy.model.ZmxyFeedbackBill;
import com.xiaoerzuche.biz.zmxy.service.ZmxyFeedbackBillService;
import com.xiaoerzuche.biz.zmxy.util.ZmTableUtil;
@Service
public class ZmxyFeedbackBillServiceImp  implements ZmxyFeedbackBillService {
	private static final Logger logger = LoggerFactory.getLogger(ZmxyFeedbackBillServiceImp.class);
	@Autowired
	private ZmxyFeedbackBillDao zmxyFeedbackBillDao;
	@Override
	public boolean addOrUpdate(ZmxyFeedbackBill zmxyFeedbackBill) {
		Date recordTime = new Date();
		if(StringUtils.isNotBlank(zmxyFeedbackBill.getBillNo())){
			zmxyFeedbackBill.setLastModifyTime(recordTime);
			logger.info("[更新芝麻信用账单记录]"+zmxyFeedbackBill);
			return this.zmxyFeedbackBillDao.update(zmxyFeedbackBill);
		}else{
			String billNo = ZmTableUtil.genZmxyFeedbackBillNo(zmxyFeedbackBill.getOrderNo(), zmxyFeedbackBill.getBillType());
			zmxyFeedbackBill.setBillNo(billNo)
    		.setCreateTime(recordTime).setLastModifyTime(recordTime);
			logger.info("[新增芝麻信用账单记录]"+zmxyFeedbackBill);
			return this.zmxyFeedbackBillDao.insert(zmxyFeedbackBill);
		}
	}
	@Override
	public boolean insert(ZmxyFeedbackBill zmxyFeedbackBill){
		return this.zmxyFeedbackBillDao.insert(zmxyFeedbackBill);
	}
	@Override
	public boolean update(ZmxyFeedbackBill zmxyFeedbackBill){
		return this.zmxyFeedbackBillDao.update(zmxyFeedbackBill);
	}
	@Override
	public boolean delete(String billNo){
		return this.zmxyFeedbackBillDao.delete(billNo);
	}
	@Override
	public ZmxyFeedbackBill get(String billNo){
		return this.zmxyFeedbackBillDao.get(billNo);
	}
	@Override
	public List<ZmxyFeedbackBill> list(ZmxyFeedbackBillBuilder builder) {
		return this.zmxyFeedbackBillDao.list(builder);
	}

}