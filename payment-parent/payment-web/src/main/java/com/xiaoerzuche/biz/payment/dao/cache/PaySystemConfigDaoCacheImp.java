package com.xiaoerzuche.biz.payment.dao.cache;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import com.xiaoerzuche.biz.payment.dao.PaySystemConfigDao;
import com.xiaoerzuche.biz.payment.model.PaySystemConfig;
import com.xiaoerzuche.common.core.cache.RefreshCacheDefaultImpl;

@Primary
@Repository
public class PaySystemConfigDaoCacheImp extends RefreshCacheDefaultImpl<PaySystemConfig> implements PaySystemConfigDao{
	private static Logger logger = LoggerFactory.getLogger(PaySystemConfigDaoCacheImp.class);
	
	@Resource(name="paySystemConfigDaoMysqlImp")
	private PaySystemConfigDao paySystemConfigDaoMysqlImp;
	
	@Resource(name="paySystemConfigDaoMemoryImp")
	private PaySystemConfigDao paySystemConfigDaoMemoryImp;
	
	@Override
	public void refresh() {
		this.paySystemConfigDaoMemoryImp.refresh(this.paySystemConfigDaoMysqlImp.list());
	}

	@Override
	public void subscribe(String message, boolean isMySelf) {
		logger.info("接收到订阅消息，msg:"+message);
		this.refresh();
	}

	@Override
	public List<PaySystemConfig> list() {
		return this.paySystemConfigDaoMemoryImp.list();
	} 

	@Override
	public PaySystemConfig get(String configKey) {
		PaySystemConfig paySystemConfig = this.paySystemConfigDaoMemoryImp.get(configKey);
		if(null == paySystemConfig){
			paySystemConfig = paySystemConfigDaoMysqlImp.get(configKey);
		}
		return paySystemConfig;
	}

}
