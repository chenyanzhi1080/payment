package com.xiaoerzuche.biz.payment.dao.memory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import com.xiaoerzuche.biz.payment.dao.PayAuthDao;
import com.xiaoerzuche.biz.payment.model.PayAuth;
import com.xiaoerzuche.common.core.cache.RefreshCacheDefaultImpl;

@Repository
public class PayAuthDaoMemoryImp extends RefreshCacheDefaultImpl<PayAuth> implements PayAuthDao{
	private Map<String, PayAuth> payAuthMap = new ConcurrentHashMap<String, PayAuth>();
	private List<PayAuth> payAuthList = new ArrayList<PayAuth>();
	
	@Override
	public List<PayAuth> list() {
		return this.payAuthList;
	}

	@Override
	public PayAuth get(String appid) {
		return this.payAuthMap.get(appid);
	}
	
	@Override
	public void refresh(List<PayAuth> list){
		Map<String, PayAuth> newMap = new ConcurrentHashMap<String, PayAuth>();
		List<PayAuth> newList = new ArrayList<PayAuth>();
		for(PayAuth auth : list){
			newMap.put(auth.getAppid(), auth);
			newList.add(auth);
		}
		this.payAuthMap = newMap;
		this.payAuthList = newList;
	}
}
