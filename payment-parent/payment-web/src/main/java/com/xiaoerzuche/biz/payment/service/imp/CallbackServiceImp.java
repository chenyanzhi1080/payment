package com.xiaoerzuche.biz.payment.service.imp;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.xiaoerzuche.biz.payment.PaymentContext;
import com.xiaoerzuche.biz.payment.common.PaymentConf;
import com.xiaoerzuche.biz.payment.dao.PayOrderDao;
import com.xiaoerzuche.biz.payment.enu.TranStatus;
import com.xiaoerzuche.biz.payment.enu.TranType;
import com.xiaoerzuche.biz.payment.model.PayOrder;
import com.xiaoerzuche.biz.payment.service.CallbackService;
import com.xiaoerzuche.biz.payment.service.MessageService;
import com.xiaoerzuche.biz.payment.service.PayAuthService;
import com.xiaoerzuche.biz.payment.service.PayOrderService;
import com.xiaoerzuche.biz.payment.service.PaySystemConfigService;
import com.xiaoerzuche.biz.payment.util.MD5Util;
import com.xiaoerzuche.biz.payment.vo.CallbackVo;
import com.xiaoerzuche.common.util.HttpUtils;
import com.xiaoerzuche.common.util.JsonUtil;

import net.sf.json.JSONObject;

@Service
public class CallbackServiceImp implements CallbackService{
	
	private static final Logger logger = LoggerFactory.getLogger(CallbackServiceImp.class);
	
	@Autowired
	private PayAuthService payAuthService;
	@Autowired
	private PayOrderService payOrderService;
	@Autowired
	private PaymentContext		paymentContext;
	@Autowired
	private PaySystemConfigService paySystemConfigService;
	@Autowired
	private MessageService messageService;
	@Autowired
	private PayOrderDao payOrderDao;
	
	
	@Override 
	public boolean callback(Map<String, String> valideData, boolean isFailure) {
		//TODO 需要重构
		logger.info("支付网关回调内容valideData:{}",valideData);
		String appid = "";
		CallbackVo callbackVo = null;
		PayOrder origPayOrder = null;
		String openId = "";
		
		boolean isSucess = false;
		// 验证信息为null或empty
		if (null == valideData || valideData.isEmpty()) {
			callbackVo = new CallbackVo(500, "支付回调信息解析异常", "", "", "", "", 0, 0, 0, 500, "", "");
		}else{
			String tranNo = valideData.get(PaymentConf.queryId_pay);
			String queryId = valideData.get(PaymentConf.orderid_name);
			int payType = Integer.parseInt(valideData.get("payType"));
			openId = valideData.get("openid");
			
			if(StringUtils.isNotBlank(queryId)){
				origPayOrder = payOrderService.get(queryId);
				logger.info("[CallbackServiceImp.callback][tranNo={}][queryId={}][origPayOrder={}]",new Object[]{tranNo, queryId, origPayOrder});
			}else{
				String origTranNo = valideData.get(PaymentConf.origTranNo);
				origPayOrder = payOrderService.getByOrigTranNo(origTranNo, payType);
				logger.info("[CallbackServiceImp.callback][tranNo={}][origTranNo={}]]",new Object[]{origTranNo, origPayOrder});
			}
			
			appid = origPayOrder.getAppid();
			
			origPayOrder.setTranNo(tranNo);
			origPayOrder.setNotifyFlag(1);
			origPayOrder.setLastModifyTime(new Date());
			origPayOrder.setTranStatus(isFailure ? TranStatus.SUCCESS.getCode() : TranStatus.FAIL.getCode());
			if(TranType.CONSUME.getCode() == origPayOrder.getTranType().intValue()){
				isSucess = payOrderService.setOrUpdatePay(origPayOrder);
			}else if(TranType.REFUND.getCode() == origPayOrder.getTranType().intValue()){
				isSucess = payOrderService.setOrUpdateRefund(origPayOrder);
			}
//				callbackVo = new CallbackVo(500, "支付回调信息解析异常", "", "", 0, 0, 500, "", "");
			String timeStamp = Long.toString(System.currentTimeMillis());
			String apiKey = payAuthService.get(origPayOrder.getAppid()).getApiKey();
			String sign = MD5Util.getAuthSign(timeStamp, origPayOrder.getAppid(), apiKey);
			int result = isFailure ? TranStatus.SUCCESS.getCode() : TranStatus.FAIL.getCode();
			callbackVo = new CallbackVo(200, "交易成功", origPayOrder.getOrderId(), origPayOrder.getGoodsNo(), origPayOrder.getQueryId(),  tranNo, origPayOrder.getTranType(),
					origPayOrder.getPayType(), origPayOrder.getTranAmount(), result, timeStamp, sign);
		
		}
		//签名异常，非成功状态码，不通知业务系统
		if(isFailure){
			String callbackUrl = paySystemConfigService.getClientcallbackUrl(appid);
			Map<String, String> map = new HashMap<String, String>();
			String data = JsonUtil.toJson(callbackVo);
			map.put("data", data);
			logger.info("[CallbackServiceImp.callback][callbackUrl={}][data={}]",new Object[]{callbackUrl, data});
			String callbackJson = HttpUtils.doPost(callbackUrl, map, -1, 20000);
			logger.info("[CallbackServiceImp.callback][callbackJson={}]",new Object[]{callbackJson});
			JSONObject jsonObject = JSONObject.fromObject(callbackJson);
			isSucess = "200".equals(jsonObject.getString("code"));//如果业务接收不成功也返回失败
			logger.info("[支付网关回调通知结果][是否需要微信通知{}][isSucess={}][openId={}][origPayOrder={}]", new Object[]{(isSucess && StringUtils.isNotBlank(openId) && null != origPayOrder)
					,isSucess, openId, origPayOrder});
			if(isSucess && StringUtils.isNotBlank(openId) && null != origPayOrder){
				messageService.pushWechatMessageQueue(openId, origPayOrder.getGoodsNo(), origPayOrder.getAppid());
			}
		}
		
		return isSucess;
	}
	
	public static void main(String[] args){
		String json = "";
		JSONObject jsonObject = JSONObject.fromObject(json);
		String code = jsonObject.getString("code");
		System.out.println(code);
	}

	@Override
	public boolean tradeFinished(String queryId) {
		return payOrderDao.tradeFinished(queryId);
	}
	
	@Async
	@Override
	public void callback(PayOrder payOrder) {
		boolean isSucess = false;
		try {
			Thread.sleep(5000l);
			String callbackUrl = paySystemConfigService.getClientcallbackUrl(payOrder.getAppid());
			String timeStamp = Long.toString(System.currentTimeMillis());
			String apiKey = payAuthService.get(payOrder.getAppid()).getApiKey();
			String sign = MD5Util.getAuthSign(timeStamp, payOrder.getAppid(), apiKey);
			CallbackVo callbackVo = new CallbackVo(200, "交易成功", 
					payOrder.getOrderId(), 
					payOrder.getGoodsNo(),
					payOrder.getQueryId(),
					payOrder.getTranNo(), 
					payOrder.getTranType(),
					payOrder.getPayType(), payOrder.getTranAmount(), payOrder.getTranStatus(), timeStamp, sign);
			Map<String, String> map = new HashMap<String, String>();
			String data = JsonUtil.toJson(callbackVo);
			map.put("data", data);
			logger.info("[CallbackServiceImp.callback][callbackUrl={}][data={}]",new Object[]{callbackUrl, data});
			String callbackJson = HttpUtils.doPost(callbackUrl, map, -1, 20000);
			logger.info("[CallbackServiceImp.callback][callbackJson={}]",new Object[]{callbackJson});
			JSONObject jsonObject = JSONObject.fromObject(callbackJson);
			isSucess = "200".equals(jsonObject.getString("code"));//如果业务接收不成功也返回失败
			logger.info("[支付网关回调通知结果][isSucess={}][origPayOrder={}]", new Object[]{isSucess, payOrder});
		} catch (Exception e) {
			logger.info("支付网关回调通知{}erro{}",new Object[]{payOrder,e});
		}
//		return isSucess;
	}
}
