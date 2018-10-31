package com.xiaoerzuche.biz.payment.dao.cache;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import com.xiaoerzuche.biz.payment.dao.PayAuthDao;
import com.xiaoerzuche.biz.payment.model.PayAuth;
import com.xiaoerzuche.common.core.cache.RefreshCacheDefaultImpl;
import com.xiaoerzuche.common.util.ListUtil;

@Primary
@Repository
public class PayAuthDaoCacheImp extends RefreshCacheDefaultImpl<PayAuth> implements PayAuthDao {
	
	@Resource(name="payAuthDaoMemoryImp")
	private PayAuthDao payAuthDaoMemoryImp;
	@Resource(name="payAuthDaoMysqlImp")
	private PayAuthDao payAuthDaoMysqlImp;
	@Override
	public void refresh() {
		this.payAuthDaoMemoryImp.refresh(this.payAuthDaoMysqlImp.list());
	}
	@Override
	public List<PayAuth> list() {
		List<PayAuth> list = this.payAuthDaoMemoryImp.list();
		if(ListUtil.isEmpty(list)){
			list = this.payAuthDaoMysqlImp.list();
		}
		return list;
	}
	@Override
	public PayAuth get(String appid) {
		PayAuth auth = this.payAuthDaoMemoryImp.get(appid);
		if(null == auth){
			auth = this.payAuthDaoMysqlImp.get(appid);
		}
		return auth;
	}
	

}
