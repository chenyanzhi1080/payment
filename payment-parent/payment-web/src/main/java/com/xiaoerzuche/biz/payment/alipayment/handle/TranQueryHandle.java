package com.xiaoerzuche.biz.payment.alipayment.handle;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alipay.api.response.AlipayTradeQueryResponse;
import com.xiaoerzuche.biz.payment.enu.TranStatus;
import com.xiaoerzuche.biz.payment.enu.TranType;
import com.xiaoerzuche.biz.payment.model.PayOrder;
import com.xiaoerzuche.biz.payment.service.AliTranQueryService;
import com.xiaoerzuche.biz.payment.service.PayOrderService;
@Service
public class TranQueryHandle {
	@Autowired
	private AliTranQueryService aliTranQueryService;
	@Autowired
	private PayOrderService payOrderService;
	public String query(PayOrder srcPayOrder){
		if(srcPayOrder.getTranType()==TranType.CONSUME.getCode()){
			//单笔交易查询
			AlipayTradeQueryResponse queryResponse = aliTranQueryService.tradeQuery(srcPayOrder.getQueryId());
			if(StringUtils.isNotBlank(queryResponse.getTradeNo()) && "TRADE_SUCCESS".equals(queryResponse.getTradeStatus()) ){
				//存在交易流水，则说明该支付单已支付
				srcPayOrder.setTranNo(queryResponse.getTradeNo());
//				srcPayOrder.setNotifyFlag(1); 不是自动通知，所以不变更该状态
				srcPayOrder.setLastModifyTime(new Date());
				srcPayOrder.setTranStatus(TranStatus.SUCCESS.getCode());
				payOrderService.setOrUpdatePay(srcPayOrder);
				return queryResponse.getTradeNo();
			}
		}else if(srcPayOrder.getTranType()==TranType.REFUND.getCode()){
			//TODO 退款查询，因为旧版退款协议不能使用开发api进行退款查询，所以暂时不接入
		}
		return "";
	}
	
}
