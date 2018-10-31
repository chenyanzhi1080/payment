package com.xiaoerzuche.common.core.context;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 容器接口默认实现
 * @author Nick C
 *
 */
public class ContextImpl implements Context {
	protected Log logger = LogFactory.getLog(this.getClass());
	protected Log beanLogger = LogFactory.getLog("BEANLOG." + this.getClass().getSimpleName());

	@PostConstruct
	@Override
	public void init() {
		beanLogger.info("init");
	}

	@PreDestroy
	@Override
	public void destroy() {
		beanLogger.info("destroy");
	}
}
