package com.xiaoerzuche.biz.payment;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Repository;

import com.xiaoerzuche.common.core.pubsub.PubSubBeanPostProcessor;
import com.xiaoerzuche.common.enu.ErrorCode;
import com.xiaoerzuche.common.exception.ErrorCodeException;
import com.xiaoerzuche.common.util.CheckUtil;

@Repository
public class PaymentContext implements BeanPostProcessor {
	private static final Logger				logger				= Logger.getLogger(PubSubBeanPostProcessor.class);
	private Map<String, PaymentStrategy>	paymentStrategyMap	= new HashMap<String, PaymentStrategy>();

	/**
	 * @Description: 一句话描述
	 * @author: cyz
	 * @date: 2016-3-29
	 * @param paymentName 支付商家
	 * @param price
	 * @param orderId
	 * @param payType
	 * @param frontNotifyUrl
	 * @param backNotifyUrl
	 * @return
	 */
	public String doPayment(String timeExpire, String account, String openid, String paymentName, int price, String orderId, Integer payType, String frontNotifyUrl,
					String backNotifyUrl) {
		PaymentStrategy payment = paymentStrategyMap.get(paymentName);
		CheckUtil.assertNotNull(payment, "检查支付商" + paymentName + "是否实例化成功");
		try {
			return payment.doPayment(timeExpire, account, openid, orderId, price, payType, frontNotifyUrl, backNotifyUrl);
		} catch (Exception e) {
			logger.error("PaymentContext支付异常 :" + e, e);
			throw new ErrorCodeException(ErrorCode.UNKOWN.getErrorCode(), e.getMessage());
		}
	}

	public Map<String, String> getPaymentFrontNotifaction(String paymentName, Map<String, String> valideData)
					throws Exception {
		PaymentStrategy payment = paymentStrategyMap.get(paymentName);
		CheckUtil.assertNotNull(payment, "检查支付商" + paymentName + "是否实例化成功");
		return payment.getPaymentFrontNotifaction(valideData);
	}

	public Map<String, String> getPaymentBackNotifaction(String paymentName, Map<String, String> valideData)
					throws Exception {
		PaymentStrategy payment = paymentStrategyMap.get(paymentName);
		CheckUtil.assertNotNull(payment, "检查支付商" + paymentName + "是否实例化成功");
		Map<String, String> result = payment.getPaymentBackNotifaction(valideData);
		// 记录支付结果
		return result;
	}

	public Map<String, String> getRefundBackNotifaction(String paymentName, Map<String, String> valideData)
			throws Exception{
		PaymentStrategy payment = paymentStrategyMap.get(paymentName);
		CheckUtil.assertNotNull(payment, "检查支付商" + paymentName + "是否实例化成功");
		Map<String, String> result = payment.getRefundBackNotifaction(valideData);
		// 记录支付结果
		return result;
	}
	public Map<String, String> doRefund(String paymentName, String orderId, String queryId, int price,
					String refundNo, int refundPrice, String frontUrl, String backUrl) throws Exception {
		PaymentStrategy payment = paymentStrategyMap.get(paymentName);
		CheckUtil.assertNotNull(payment, "检查支付商" + paymentName + "是否实例化成功");
		return payment.doRefund(orderId, queryId, price, refundNo, refundPrice, frontUrl, backUrl);
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		if (bean instanceof PaymentStrategy && beanName.endsWith("PaymentStrategy")) {
			logger.info("向支付容器注入的支付商名称:" + beanName + " ,实例化对象bean:" + bean);
			paymentStrategyMap.put(beanName, (PaymentStrategy) bean);
		}
		return bean;
	}
	
}

