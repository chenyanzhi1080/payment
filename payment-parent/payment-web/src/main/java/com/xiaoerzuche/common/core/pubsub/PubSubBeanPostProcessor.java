package com.xiaoerzuche.common.core.pubsub;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import com.xiaoerzuche.common.core.data.redis.Redis;
import com.xiaoerzuche.common.util.CheckUtil;

/**
 * 自动注册订阅者
 * @author Nick C
 *
 */
public class PubSubBeanPostProcessor implements BeanPostProcessor, BeanFactoryAware {
	private static final Logger logger = Logger.getLogger(PubSubBeanPostProcessor.class);
	
	private ConfigurableListableBeanFactory beanFactory;

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
	}

	protected Redis getRedis() {
		Redis redis = this.findRedis();
		CheckUtil.assertNotNull(redis, "找不到redis连接 ....好可怜，求给我一个redis...");
		return redis;
	}

	protected Redis findRedis() {
		return this.findRedisBySessionRedis();
	}

	protected Redis findRedisBySessionRedis() {
		try {
			return (Redis) beanFactory.getBean("redis");
		}
		catch (NoSuchBeanDefinitionException e) {
			return null;
		}
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		if (bean instanceof ISubscribe && beanName.endsWith("CacheImp")) {//通过这里去注册实现了ISubscribe的实现类作为订阅者by Nick C
			logger.info("订阅消息：beanName:" + beanName + " bean:" + bean);
			Publisher.listen((ISubscribe) bean, this.getRedis());
		}
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

}
