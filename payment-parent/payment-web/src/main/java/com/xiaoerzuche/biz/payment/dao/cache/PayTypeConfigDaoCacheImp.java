package com.xiaoerzuche.biz.payment.dao.cache;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import com.xiaoerzuche.biz.payment.dao.PayTypeConfigDao;
import com.xiaoerzuche.biz.payment.dao.sqlBuilder.PayTypeConfigBuilder;
import com.xiaoerzuche.biz.payment.model.PayTypeConfig;
import com.xiaoerzuche.common.core.cache.RefreshCacheDefaultImpl;
import com.xiaoerzuche.common.core.pubsub.ISubscribe;
import com.xiaoerzuche.common.util.JsonUtil;

@Primary
@Repository
public class PayTypeConfigDaoCacheImp extends RefreshCacheDefaultImpl<PayTypeConfig> implements PayTypeConfigDao{
	private static Logger logger = LoggerFactory.getLogger(PayTypeConfigDaoCacheImp.class);
	
	@Resource(name="payTypeConfigDaoMysqlImp")
	private PayTypeConfigDao payTypeConfigDaoMysqlImp;
	
	@Resource(name="payTypeConfigDaoMemoryImp")
	private PayTypeConfigDao payTypeConfigDaoMemoryImp;
	
	@Override
	public boolean update(PayTypeConfig bean) {
		return this.payTypeConfigDaoMysqlImp.update(bean);
	}

	@Override
	public boolean delete(Integer key) {
		return this.payTypeConfigDaoMysqlImp.delete(key);
	}

	@Override
	public PayTypeConfig get(Integer key) {
		return this.payTypeConfigDaoMysqlImp.get(key);
	}

	@Override
	public boolean add(PayTypeConfig bean) {
		return this.payTypeConfigDaoMysqlImp.add(bean);
	}

	@Override
	public void refresh() {
		logger.info("刷新");
		this.payTypeConfigDaoMemoryImp.refresh(this.payTypeConfigDaoMysqlImp.list(new PayTypeConfigBuilder()));
	}
	
	@Override
	public List<PayTypeConfig> list(PayTypeConfigBuilder builder) {
		List<PayTypeConfig> list = payTypeConfigDaoMemoryImp.list(builder);
		
		if(list.isEmpty()){
			this.refresh();
			return this.payTypeConfigDaoMemoryImp.list(builder);
		}else{
			return list;
		}
		
	}

	@Override
	public int count(PayTypeConfigBuilder builder) {
		return this.payTypeConfigDaoMysqlImp.count(builder);
	}

	@Override
	public List<PayTypeConfig> page(PayTypeConfigBuilder builder) {
		return this.payTypeConfigDaoMysqlImp.page(builder);
	}
	
}
