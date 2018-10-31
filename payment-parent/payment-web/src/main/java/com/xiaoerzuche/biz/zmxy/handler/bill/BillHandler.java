package com.xiaoerzuche.biz.zmxy.handler.bill;

import org.springframework.stereotype.Component;

import com.xiaoerzuche.biz.zmxy.model.ZmxyFeedbackBill;
import com.xiaoerzuche.biz.zmxy.vo.ZhimaDataFeedbackRequstVo;
import com.xiaoerzuche.biz.zmxy.vo.ZhimaDataSettledRequestVo;

@Component
public abstract interface BillHandler {
	public ZmxyFeedbackBill bill(ZhimaDataFeedbackRequstVo dataFeedbackRequstVo, String subOrderNo);
	public ZmxyFeedbackBill settled(ZhimaDataSettledRequestVo settledRequestVo, String subOrderNo);
}
