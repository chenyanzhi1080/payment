package com.xiaoerzuche.biz.payment.service.imp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaoerzuche.biz.payment.dao.PaymentSessionDao;
import com.xiaoerzuche.biz.payment.service.PamentSessionService;

@Service
public class PamentSessionServiceImp implements PamentSessionService{
	private static final Logger logger = LoggerFactory.getLogger(PamentSessionService.class);
	@Autowired
	private PaymentSessionDao paymentSessionDao;
	@Override
	public boolean setSession(String key, int seconds, String value) {
		String result = paymentSessionDao.setSession(key, seconds, value);
		logger.info("支付缓存set操作：[key={}][seconds={}][value={}][{}]",key,seconds,value,"OK".equals(result.toUpperCase()));
		return "OK".equals(result.toUpperCase());
	}

	@Override
	public String getSession(String key) {
		String value = paymentSessionDao.getSession(key); 
		value = "nil".equals(value)?"":value;
		logger.info("支付缓存get操作：[key={}][value={}]",key,value);
		return value;
	}

	@Override
	public boolean removeSession(String key) {
		long del = paymentSessionDao.removeSession(key);
		logger.info("支付缓存remove操作：[key={}][del={}]",key,del);
		return del>=0;
	}

	@Override
	public String updateSession(String key, String value) {
		String preValue = paymentSessionDao.updateSession(key, value);
		preValue = "nil".equals(preValue)?"":preValue;
		logger.info("支付缓存update操作：[key={}][value={}][preValue={}]",key,value,preValue);
		return preValue;
	}

}
