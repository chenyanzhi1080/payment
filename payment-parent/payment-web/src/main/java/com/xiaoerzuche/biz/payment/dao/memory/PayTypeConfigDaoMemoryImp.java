package com.xiaoerzuche.biz.payment.dao.memory;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.xiaoerzuche.biz.payment.dao.PayTypeConfigDao;
import com.xiaoerzuche.biz.payment.dao.sqlBuilder.PayTypeConfigBuilder;
import com.xiaoerzuche.biz.payment.model.PayTypeConfig;
import com.xiaoerzuche.common.core.cache.RefreshCacheDefaultImpl;

@Repository("payTypeConfigDaoMemoryImp")
public class PayTypeConfigDaoMemoryImp extends RefreshCacheDefaultImpl<PayTypeConfig> implements PayTypeConfigDao{
	
	private List<PayTypeConfig> payTypeConfigList = new ArrayList<PayTypeConfig>();

	@Override
	public List<PayTypeConfig> list(PayTypeConfigBuilder builder) {
		if(StringUtils.isNotBlank(builder.getBzAppid()) && StringUtils.isNotBlank(builder.getTerminalAppid()) ){
			String key = builder.getBzAppid()+builder.getTerminalAppid();
			List<PayTypeConfig> newlist = new ArrayList<PayTypeConfig>();
			for(PayTypeConfig config : this.payTypeConfigList){
				if(key.equals(config.getBzAppid()+config.getTerminalAppid())){
					newlist.add(config);
				}
			}
			return newlist;
		}else{
			return this.payTypeConfigList;
		}
	}
	
	@Override
	public void refresh(List<PayTypeConfig> list){
		this.payTypeConfigList = list;
	}
	
	@Override
	public int count(PayTypeConfigBuilder builder) {
		throw new UnsupportedOperationException("不支持该操作");
	}

	@Override
	public boolean update(PayTypeConfig bean) {
		throw new UnsupportedOperationException("不支持该操作");
	}

	@Override
	public boolean delete(Integer key) {
		throw new UnsupportedOperationException("不支持该操作");
	}

	@Override
	public PayTypeConfig get(Integer key) {
		throw new UnsupportedOperationException("不支持该操作");
	}

	@Override
	public boolean add(PayTypeConfig bean) {
		throw new UnsupportedOperationException("不支持该操作");
	}

	@Override
	public List<PayTypeConfig> page(PayTypeConfigBuilder builder) {
		throw new UnsupportedOperationException("不支持该操作");
	}

}
