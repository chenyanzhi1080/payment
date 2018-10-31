package com.xiaoerzuche.biz.payment.service.imp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaoerzuche.biz.payment.dao.PayTypeConfigDao;
import com.xiaoerzuche.biz.payment.dao.sqlBuilder.PayTypeConfigBuilder;
import com.xiaoerzuche.biz.payment.model.PayTypeConfig;
import com.xiaoerzuche.biz.payment.service.PayTypeConfigService;
import com.xiaoerzuche.biz.payment.vo.PagerVo;
import com.xiaoerzuche.biz.payment.vo.payType.PayTypeVo;
@Service
public class PayTypeConfigServiceImp  implements PayTypeConfigService {

	@Autowired
	private PayTypeConfigDao payTypeConfigDao;

	public boolean insert(PayTypeConfig payTypeConfig){
		return this.payTypeConfigDao.add(payTypeConfig);
	}

	public boolean update(PayTypeConfig payTypeConfig){
		return this.payTypeConfigDao.update(payTypeConfig);
	}

	public boolean delete(Integer id){
		return this.payTypeConfigDao.delete(id);
	}

	public PayTypeConfig get(Integer id){
		return this.payTypeConfigDao.get(id);
	}

	public PagerVo<PayTypeConfig> page(PayTypeConfigBuilder builder){
		PagerVo<PayTypeConfig> page=new  PagerVo<PayTypeConfig>();
		List<PayTypeConfig> data=this.payTypeConfigDao.page(builder);
		int count=this.payTypeConfigDao.count(builder);
		
		page.setList(data);
		page.setTotalRecords(count);
		
		return page;
	}


	@Override
	public List<PayTypeVo> list(PayTypeConfigBuilder builder) {
		List<PayTypeConfig> data = this.payTypeConfigDao.list(builder);
		List<PayTypeVo> list = new ArrayList<PayTypeVo>();
		PayTypeVo payTypeVo = null;
		for(PayTypeConfig config : data){
			payTypeVo = new PayTypeVo();
			payTypeVo.setPaytypeCode(config.getPaytypeCode())
			.setName(config.getName())
			.setDirection(config.getDirection());
			list.add(payTypeVo);
		}
		
		return list;
	}
}