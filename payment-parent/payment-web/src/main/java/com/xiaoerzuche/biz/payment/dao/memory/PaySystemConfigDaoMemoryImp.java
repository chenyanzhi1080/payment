package com.xiaoerzuche.biz.payment.dao.memory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import com.xiaoerzuche.biz.payment.dao.PaySystemConfigDao;
import com.xiaoerzuche.biz.payment.model.PaySystemConfig;
import com.xiaoerzuche.common.core.cache.RefreshCacheDefaultImpl;

@Repository
public class PaySystemConfigDaoMemoryImp extends RefreshCacheDefaultImpl<PaySystemConfig> implements PaySystemConfigDao{
	
	private Map<String, PaySystemConfig> paySystemConfigMap = new ConcurrentHashMap<String, PaySystemConfig>();
	
	private List<PaySystemConfig> paySystemConfigList = new ArrayList<PaySystemConfig>();
	
	@Override
	public void subscribe(String message, boolean isMySelf) {
		throw new UnsupportedOperationException("不支持该操作");
	}

	@Override
	public List<PaySystemConfig> list() {
		return this.paySystemConfigList;
	}

	@Override
	public PaySystemConfig get(String configKey) {
		return this.paySystemConfigMap.get(configKey);
	}

	@Override
	public void refresh(List<PaySystemConfig> list) {
		Map<String, PaySystemConfig> newMap = new ConcurrentHashMap<String, PaySystemConfig>();
		List<PaySystemConfig> newList = new ArrayList<PaySystemConfig>();
		for(PaySystemConfig config : list){
			newMap.put(config.getConfigKey(), config);
			newList.add(config);
		}
		this.paySystemConfigMap = newMap;
		this.paySystemConfigList = newList;
	}
	
}
