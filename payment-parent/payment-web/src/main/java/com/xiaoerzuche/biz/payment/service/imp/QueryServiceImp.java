package com.xiaoerzuche.biz.payment.service.imp;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaoerzuche.biz.payment.PaymentStrategy;
import com.xiaoerzuche.biz.payment.alipayment.handle.TranQueryHandle;
import com.xiaoerzuche.biz.payment.enu.PayType;
import com.xiaoerzuche.biz.payment.enu.TranStatus;
import com.xiaoerzuche.biz.payment.model.PayOrder;
import com.xiaoerzuche.biz.payment.service.QueryService;
import com.xiaoerzuche.biz.payment.vo.QueryResultVo;

@Service
public class QueryServiceImp implements QueryService{
	@Autowired
	private TranQueryHandle tranQueryHandle;
	@Override
	public QueryResultVo paymentQuery(PayOrder srcPayOrder) {
		QueryResultVo queryResultVo = new QueryResultVo();
		queryResultVo.setCode(srcPayOrder.getTranStatus());
		queryResultVo.setMsg(TranStatus.mapByCode.get(srcPayOrder.getTranStatus()).getName());
		queryResultVo.setOrderId(srcPayOrder.getOrderId());
		queryResultVo.setGoodsOrderId(srcPayOrder.getGoodsNo());
		queryResultVo.setQueryId(srcPayOrder.getQueryId());
		queryResultVo.setTranNo(srcPayOrder.getTranNo());
		queryResultVo.setPayType(srcPayOrder.getPayType());
		queryResultVo.setTranType(srcPayOrder.getTranType());
		queryResultVo.setAmount(srcPayOrder.getTranAmount());
		// 交易平台、交易类型
		//判断交易是否完成
		if(StringUtils.isBlank(srcPayOrder.getTranNo())){
			String tranNo = this.query(srcPayOrder);
			if (!StringUtils.isBlank(tranNo)) {
				queryResultVo.setTranNo(tranNo);
				queryResultVo.setCode(TranStatus.SUCCESS.getCode());
				queryResultVo.setMsg(TranStatus.SUCCESS.getName());
			}
		}
		return queryResultVo;
	}
	
	public String query(PayOrder srcPayOrder){
		PayType payType = PayType.getPayType(srcPayOrder.getPayType());
		String tranNo = "";
		switch (payType) {
		case ALIWAPPAY:
			tranNo = tranQueryHandle.query(srcPayOrder);
			break;
		case ALIAPPPAY:
			tranNo = tranQueryHandle.query(srcPayOrder);
			break;
		case WEIXINJSPAY:
			break;
		case WEIXINAPPPAY:
			break;
		default:
			break;
		}
		return tranNo;
	}
	
}
