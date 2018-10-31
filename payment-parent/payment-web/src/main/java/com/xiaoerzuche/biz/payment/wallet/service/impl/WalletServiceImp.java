package com.xiaoerzuche.biz.payment.wallet.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import com.xiaoerzuche.biz.payment.enu.TranStatus;
import com.xiaoerzuche.biz.payment.enu.TranType;
import com.xiaoerzuche.biz.payment.model.PayOrder;
import com.xiaoerzuche.biz.payment.paymentEnu.PaymentEnum;
import com.xiaoerzuche.biz.payment.service.PayAuthService;
import com.xiaoerzuche.biz.payment.service.PayOrderService;
import com.xiaoerzuche.biz.payment.service.PaySystemConfigService;
import com.xiaoerzuche.biz.payment.util.DESUtil;
import com.xiaoerzuche.biz.payment.util.MD5Util;
import com.xiaoerzuche.biz.payment.util.PaymentRestClien;
import com.xiaoerzuche.biz.payment.vo.CallbackVo;
import com.xiaoerzuche.biz.payment.vo.PayVo;
import com.xiaoerzuche.biz.payment.vo.bcustomer.WalletConsumeFormVo;
import com.xiaoerzuche.biz.payment.vo.rest.RestResponeVo;
import com.xiaoerzuche.biz.payment.wallet.dto.ConsumeDto;
import com.xiaoerzuche.biz.payment.wallet.dto.RefundDto;
import com.xiaoerzuche.biz.payment.wallet.dto.RefundResp;
import com.xiaoerzuche.biz.payment.wallet.dto.WalletEventDto;
import com.xiaoerzuche.biz.payment.wallet.service.WalletService;
import com.xiaoerzuche.common.util.HttpUtils;
import com.xiaoerzuche.common.util.JsonUtil;

import net.sf.json.JSONObject;

@Service
public class WalletServiceImp implements WalletService{
	@Value("${wallet_service_refund}")
	private String walletServiceRefund;
	@Value("${wallet_service_prepay}")
	private String walletServicepRrepay;
	@Value("${wallet_appid}")
	private String walletAppid;
	@Value("${wallet_appkey}")
	private String walletAppkey;
	
	@Autowired
	private PaymentRestClien restClien;
	@Value("${wallet_service}")
	private String walletService;
	
	@Autowired
	private PayOrderService payOrderService;
	@Autowired
	private PaySystemConfigService paySystemConfigService;
	@Autowired
	private PayAuthService payAuthService;
	
	private static final Logger logger = LoggerFactory.getLogger(WalletServiceImp.class);
	@Override
	public String doPayment(PayVo payVo, String queryId, int channel) {
		
		ConsumeDto consumeDto = new ConsumeDto();
		consumeDto.setAccount(payVo.getAccount());
		consumeDto.setChannelId(channel);
		consumeDto.setName(payVo.getBody());
		consumeDto.setPaymentQueryId(queryId);
		consumeDto.setGoodsOrder(payVo.getGoodsOrderId());
		consumeDto.setPrice(payVo.getPrice());
		consumeDto.setTimeExpire(payVo.getTimeExpire());
		consumeDto.setTimeStamp(String.valueOf(payVo.getTimeStamp()));
		//签名
		String authSign = consumeDto.getPaymentQueryId() + consumeDto.getPrice() + consumeDto.getTimeStamp() + consumeDto.getPaymentQueryId();
		authSign = MD5Util.MD5Encode(authSign, "UTF-8").toUpperCase();	
		consumeDto.setSign(authSign);
		logger.info("[电子钱包支付请求][consumeDto={}]",new Object[]{consumeDto.toString()});
		String data = JsonUtil.toJson(consumeDto);
		try {
			data = DESUtil.ENCRYPTMethod(data, walletAppkey);
		} catch (Exception e) {
			logger.error("[电子钱包支付，参数加密异常][{}][{}]{}", new Object[]{data,e.getMessage(),e});
		}
		Map<String, String> paramPam = new HashMap<String, String>();
		paramPam.put("data", data);
		String resultJson = HttpUtils.doPost(walletServicepRrepay, paramPam, -1, -1);
		logger.info("[电子钱包支付请求结果][resultJson={}]",new Object[]{resultJson});
		return resultJson;
		/**
		JSONObject jsonObject = JSONObject.fromObject(resultJson);
		String code = jsonObject.getString("code");
		if("0".equals(code)){
			return jsonObject.getString("data");
		}
		return null;
		*/
	}

	@Override
	public boolean callback(String data) {
		logger.info("[电子钱包回调通知]:{}",data);
		WalletEventDto walletEventDto = null;
		CallbackVo callbackVo = null;
		boolean isSucess = false;
		try {
			walletEventDto = JsonUtil.fromJson(data, WalletEventDto.class);
		} catch (Exception e) {
			logger.info("[电子钱包回调通知][报文解析异常{}]{}",data);
		}

		if(null != walletEventDto){
		
			String tranNo = walletEventDto.getTranId();
			String queryId = walletEventDto.getPaymentQueryId();
			PayOrder origPayOrder = null;
			origPayOrder = payOrderService.get(queryId);
			
			logger.info("[CallbackServiceImp.callback][tranNo={}][queryId={}][origPayOrder={}]",new Object[]{tranNo, queryId, origPayOrder.toString()});
			
			boolean isFailure = false;
			TranType tranType = TranType.getTranType(walletEventDto.getType());
			
			switch (tranType) {
			case CONSUME:
				isFailure = PaymentEnum.PAY_SUCCESS.getCode()==walletEventDto.getCode();
				break;
			case REFUND:
				isFailure = PaymentEnum.REFUND_SUCCESS.getCode()==walletEventDto.getCode();
				break;
			default:
				break;
			}
			
			origPayOrder.setTranNo(tranNo);
			origPayOrder.setNotifyFlag(1);
			origPayOrder.setLastModifyTime(new Date());
			origPayOrder.setTranStatus(isFailure ? TranStatus.SUCCESS.getCode() : TranStatus.FAIL.getCode());
			
			if(TranType.CONSUME.getCode() == origPayOrder.getTranType().intValue()){
				isSucess = payOrderService.setOrUpdatePay(origPayOrder);
			}else if(TranType.REFUND.getCode() == origPayOrder.getTranType().intValue()){
				isSucess = payOrderService.setOrUpdateRefund(origPayOrder);
			}
			String timeStamp = Long.toString(System.currentTimeMillis());
			String appid = origPayOrder.getAppid();
			String apiKey = payAuthService.get(origPayOrder.getAppid()).getApiKey();
			String sign = MD5Util.getAuthSign(timeStamp, origPayOrder.getAppid(), apiKey);
			
			callbackVo = new CallbackVo(200, "交易成功", origPayOrder.getOrderId(), origPayOrder.getGoodsNo(), origPayOrder.getQueryId(),  tranNo, origPayOrder.getTranType(),
					origPayOrder.getPayType(), origPayOrder.getTranAmount(), TranStatus.SUCCESS.getCode(), timeStamp, sign);
			String callbackUrl = paySystemConfigService.getClientcallbackUrl(appid);
			Map<String, String> map = new HashMap<String, String>();
			String dataJson = JsonUtil.toJson(callbackVo);
			map.put("data", dataJson);
			logger.info("[电子钱包-->支付网关-->通知业务系统][callbackUrl={}][data={}]",new Object[]{callbackUrl, walletEventDto});
			String callbackJson = HttpUtils.doPost(callbackUrl, map, -1, 20000);
			logger.info("[电子钱包-->支付网关-->业务系统通知回执][callbackUrl={}][callbackJson={}]",new Object[]{callbackUrl,callbackJson});
			
			try {
				JSONObject object = JSONObject.fromObject(callbackJson);
				String code = object.getString("code");
				if("200".equals(code)){
					isSucess = true;
				}
			} catch (Exception e) {
				logger.error("电子钱包回调通知{}异常{}",new Object[]{walletEventDto.getPaymentQueryId(),e});
			}
		}
		
		return isSucess;
	}

	@Override
	public RefundResp doRefund(String origTranId, String refundQueryId, String goodsOrderId, int refundPrice) {
		String timeStamp = String.valueOf(System.currentTimeMillis());
		String authSign = refundQueryId + origTranId + refundPrice + timeStamp ;
		authSign = MD5Util.MD5Encode(authSign, "UTF-8").toUpperCase();	
		RefundDto refundDto = new RefundDto();
		refundDto.setOrigTranId(origTranId);
		refundDto.setRefundQueryId(refundQueryId);
		refundDto.setGoodsOrder(goodsOrderId);
		refundDto.setPrice(refundPrice);
		refundDto.setSign(authSign);
		refundDto.setTimeStamp(timeStamp);
		logger.info("[电子钱包退款请求][consumeDto={}]",new Object[]{refundDto.toString()});
		String data = JsonUtil.toJson(refundDto);
		Map<String, String> paramPam = new HashMap<String, String>();
		paramPam.put("data", data);
		String resultJson = HttpUtils.doPost(walletServiceRefund, paramPam, -1, 20000);
		logger.info("[电子钱包退款请求结果][resultJson={}]",new Object[]{resultJson});
		
		RefundResp refundResp = null;
		JSONObject jsonObject = JSONObject.fromObject(resultJson);
		String code = jsonObject.getString("code");
		String msg = jsonObject.getString("msg");
		String resultData = jsonObject.getString("data");
		refundResp = JsonUtil.fromJson(resultData, RefundResp.class);
		refundResp.setCode(new BigDecimal(code).intValue());
		refundResp.setMsg(msg);
		
		return refundResp;
	}
	public static void main(String[] args){
		String resultJson = "{\"code\":2001, \"msg\":\"请求成功\", \"data\":{\"refundTranNo\":\"aaaaaaa\",\"paymentQueryId\":\"bbbbb\"}} ";
		JSONObject jsonObject = JSONObject.fromObject(resultJson);
		String code = jsonObject.getString("code");
		String msg = jsonObject.getString("msg");
		String resultData = jsonObject.getString("data");
		RefundResp refundResp = JsonUtil.fromJson(resultData, RefundResp.class);
		refundResp.setCode(new BigDecimal(code).intValue());
		refundResp.setMsg(msg);
		System.out.println(refundResp.toString());
	}

	@Override
	public String doBigCustomerPay(PayVo payVo, String queryId, int channel) {
		String url = walletService+"/rpc/walletPay/bigcustomerConsume";
		WalletConsumeFormVo formVo = new WalletConsumeFormVo();
		formVo.setAccount(payVo.getAccount());
		formVo.setGoodsOrderId(payVo.getGoodsOrderId());
		formVo.setOrderId(payVo.getOrderId());
		formVo.setPrice(payVo.getPrice());
		formVo.setType("HOURLY");
		formVo.setQueryId(queryId);
		Map<String, String> body = new HashMap<String,String>();;
		try {
			RestResponeVo response = restClien.restClien(url, formVo, MediaType.APPLICATION_JSON, HttpMethod.POST);
			logger.info("请求企业钱包data={} url={} response={}", new Object[]{formVo,url,JsonUtil.toJson(response)});
			
			if(response.getCode()==HttpStatus.OK.value()){
//				body.put(String.valueOf(PaymentEnum.PAY_SUCCESS), PaymentEnum.PAY_SUCCESS.getName());
				body.put("bizCode", String.valueOf(PaymentEnum.PAY_SUCCESS));
				body.put("bizMsg", PaymentEnum.PAY_SUCCESS.getName());
			}else{
				PaymentEnum enu = PaymentEnum.get(response.getCode());
//				body.put(String.valueOf(enu), enu.getName());
				body.put("bizCode", String.valueOf(enu));
				body.put("bizMsg", enu.getName());
			}
			return JsonUtil.toJson(body);
		} catch (Exception e) {
			logger.info("请求企业钱包data={} url={} e {}", new Object[]{formVo,url, e});
			body.put("bizCode", String.valueOf(PaymentEnum.SYSTEMERROR));
			body.put("bizMsg", PaymentEnum.SYSTEMERROR.getName());
			return JsonUtil.toJson(body);
		}
		
	}

	@Override
	public RefundResp doBigCustomerRefund(String origTranId, String refundQueryId, String goodsOrderId,
			int refundPrice) {
		
		return null;
	}
}

