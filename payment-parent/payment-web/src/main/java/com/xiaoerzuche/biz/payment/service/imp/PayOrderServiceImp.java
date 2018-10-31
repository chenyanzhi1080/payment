package com.xiaoerzuche.biz.payment.service.imp;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaoerzuche.biz.payment.dao.PayOrderDao;
import com.xiaoerzuche.biz.payment.dao.builder.PayorderBuilder;
import com.xiaoerzuche.biz.payment.enu.Channel;
import com.xiaoerzuche.biz.payment.enu.ExpenseType;
import com.xiaoerzuche.biz.payment.enu.PayType;
import com.xiaoerzuche.biz.payment.enu.TranType;
import com.xiaoerzuche.biz.payment.model.PayOrder;
import com.xiaoerzuche.biz.payment.service.PayAuthService;
import com.xiaoerzuche.biz.payment.service.PayOrderService;
import com.xiaoerzuche.biz.payment.vo.PagerVo;
import com.xiaoerzuche.biz.payment.vo.PayorderQueryVo;
import com.xiaoerzuche.biz.payment.vo.QueryVo;
import com.xiaoerzuche.biz.payment.vo.TranRecordVo;

@Service
public class PayOrderServiceImp implements PayOrderService {
	@Autowired
	private PayAuthService payAuthService;
	@Autowired
	private PayOrderDao payOrderDao; 

	@Override
	public boolean add(PayOrder payOrder) {
		return this.payOrderDao.add(payOrder);
	}

	@Override
	public boolean update(PayOrder payOrder) {
		return this.payOrderDao.update(payOrder);
	}

	@Override
	public PayOrder getPayOrderBy(QueryVo vo) {
		return this.payOrderDao.getByOrder(vo);
	}

	@Override
	public boolean setOrUpdatePay(PayOrder payOrder) {
		
		PayOrder srcPayOrder = this.payOrderDao.get(payOrder.getQueryId());
		if(null == srcPayOrder){
			return this.add(payOrder);
		}else{
			srcPayOrder.setTranNo(payOrder.getTranNo());
			srcPayOrder.setPayType(payOrder.getPayType());
			srcPayOrder.setPayName(payOrder.getPayName());
			srcPayOrder.setTranAmount(payOrder.getTranAmount());
			srcPayOrder.setTranType(payOrder.getTranType());
			srcPayOrder.setTranTime(payOrder.getTranTime());
			srcPayOrder.setTranTime(payOrder.getTranTime());
			srcPayOrder.setTranStatus(payOrder.getTranStatus());
			srcPayOrder.setNotifyFlag(payOrder.getNotifyFlag());
			srcPayOrder.setLastModifyTime(payOrder.getLastModifyTime());
			return this.update(srcPayOrder);
		}
	}

	@Override
	public boolean setOrUpdateRefund(PayOrder payOrder) {
		QueryVo vo = new QueryVo(payOrder.getAppid(), payOrder.getOrderId(), null, TranType.REFUND.getCode(), payOrder.getPayType());
		PayOrder srcPayOrder = this.getPayOrderBy(vo);
		if(null == srcPayOrder){
			payOrder.setTranType(TranType.REFUND.getCode());
			return this.add(payOrder);
		}else{
			srcPayOrder.setTranNo(payOrder.getTranNo());
			srcPayOrder.setOrigTranNo(payOrder.getOrigTranNo());
			srcPayOrder.setPayType(payOrder.getPayType());
			srcPayOrder.setPayName(payOrder.getPayName());
			srcPayOrder.setTranAmount(payOrder.getTranAmount());
			srcPayOrder.setOrigTranAmount(payOrder.getOrigTranAmount());
			srcPayOrder.setTranType(payOrder.getTranType());
			srcPayOrder.setTranTime(payOrder.getTranTime());
			srcPayOrder.setTranTime(payOrder.getTranTime());
			srcPayOrder.setTranStatus(payOrder.getTranStatus());
			srcPayOrder.setNotifyFlag(payOrder.getNotifyFlag());
			srcPayOrder.setLastModifyTime(payOrder.getLastModifyTime());
			return this.update(srcPayOrder);
		}
	}

	@Override
	public boolean updateCallBackStatus(String queryId, String tranNo, int tranStatus) {
		PayOrder payOrder = this.payOrderDao.get(queryId);
		payOrder.setTranNo(tranNo);
		payOrder.setTranStatus(tranStatus);
		payOrder.setNotifyFlag(1);
		payOrder.setLastModifyTime(new Date());
		return this.update(payOrder);
	}

	@Override
	public PayOrder get(String queryId) {
		return this.payOrderDao.get(queryId);
	}

	@Override
	public PayOrder getByOrigTranNo(String origTradeNo, int type){
		return this.payOrderDao.getByOrigTranNo(origTradeNo, type);
	}

	@Override
	public PagerVo<TranRecordVo> getTranRecorders(String type, PayorderQueryVo payorderQueryVo) {
		//交易类型 1：支付 10：退款
		int tranType = type.equals("refund") ? 10:1;
		PayorderBuilder builder = payorderQueryVo.builder();
		builder.setTranType(tranType);
		
		long count = this.payOrderDao.getTranRecordersCount(builder);
		List<PayOrder> list = this.payOrderDao.getTranRecorders(builder);
		List<TranRecordVo> tranRecordVos = new ArrayList<TranRecordVo>();
		
		for(PayOrder payOrder : list){
			BigDecimal amount = new BigDecimal(payOrder.getTranAmount());
			amount = amount.movePointLeft(2);
			TranRecordVo tranRecordVo = new TranRecordVo();
			tranRecordVo.setQueryId(payOrder.getQueryId());
			tranRecordVo.setGoodsNo(payOrder.getGoodsNo());
			tranRecordVo.setTranNo(payOrder.getTranNo());
			tranRecordVo.setAmount(amount.toString());
			tranRecordVo.setPayTypeName(PayType.getByCode(payOrder.getPayType()).getComment());
			if(StringUtils.isNotBlank(payOrder.getChannel())){
				tranRecordVo.setChannelName(Channel.getByValue(payOrder.getChannel()).getName());
			}
			if(StringUtils.isNotBlank(payOrder.getAccount())){
				tranRecordVo.setAccount(payOrder.getAccount());
			}
			if(StringUtils.isNotBlank(payOrder.getExpenseType())){
				tranRecordVo.setExpenseTypeName(ExpenseType.getByValue(payOrder.getExpenseType()).getName());
			}
			tranRecordVo.setComment(payOrder.getComment());
			tranRecordVo.setTime(payOrder.getLastModifyTime().getTime());
			tranRecordVo.setMerchant("开开出行");//TODO saas 服务建设后，将会加入其它商家.届时需要进一步设计
			tranRecordVos.add(tranRecordVo);
		}
		
		PagerVo<TranRecordVo> pagerVo = new PagerVo<TranRecordVo>();
		pagerVo.setTotalRecords(count);
		pagerVo.setLimit(builder.getLimit());
		pagerVo.setOffset(builder.getOffset());
		pagerVo.setList(tranRecordVos);
		return pagerVo;
	}
	
	public static void main(String[] args){
		BigDecimal amount = new BigDecimal(189000);
		amount = amount.movePointLeft(2);
		System.out.println(amount.toString());
		System.out.println(String.format("%.2f", amount));
	}
}
