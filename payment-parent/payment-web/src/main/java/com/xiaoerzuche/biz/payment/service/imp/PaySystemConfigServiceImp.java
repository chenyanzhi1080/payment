package com.xiaoerzuche.biz.payment.service.imp;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaoerzuche.biz.payment.dao.PaySystemConfigDao;
import com.xiaoerzuche.biz.payment.model.PayAuth;
import com.xiaoerzuche.biz.payment.model.PaySystemConfig;
import com.xiaoerzuche.biz.payment.service.PayAuthService;
import com.xiaoerzuche.biz.payment.service.PaySystemConfigService;
import com.xiaoerzuche.common.util.CheckUtil;

@Service
public class PaySystemConfigServiceImp implements PaySystemConfigService{
	
	@Autowired
	private PaySystemConfigDao paySystemConfigDao;
	@Autowired
	private PayAuthService authService;
	
	@Override
	public List<PaySystemConfig> list() {
		return paySystemConfigDao.list();
	}

	@Override
	public PaySystemConfig get(String configKey) {
		return paySystemConfigDao.get(configKey);
	}

	@Override
	public String getClientcallbackUrl(String appid) {
		PayAuth auth = authService.get(appid);
		String configKey = auth.getServiceName()+"_CALLBACK_URL";
		PaySystemConfig paySystemConfig = this.get(configKey);
		CheckUtil.assertTrue(paySystemConfig!=null && StringUtils.isNotBlank(paySystemConfig.getConfigValue()), "appid="+appid+" 业务端没有配callback地址");
		return paySystemConfig.getConfigValue();
	}

}
