package com.xiaoerzuche.biz.payment.wechatpayment.handle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaoerzuche.biz.payment.paymentConfig.WechatJsPayConfig;

@Service
public class WechatTranQueryHandle {
	private static final Logger logger = LoggerFactory.getLogger(WechatTranQueryHandle.class);

	@Autowired
	private WechatJsPayConfig wechatJsPayConfig;
}
