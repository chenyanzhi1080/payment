package com.xiaoerzuche.biz.payment.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaoerzuche.biz.payment.dao.PayAuthDao;
import com.xiaoerzuche.biz.payment.model.PayAuth;
import com.xiaoerzuche.biz.payment.service.PayAuthService;

@Service
public class PayAuthServiceImp implements PayAuthService{
	
	@Autowired
	private PayAuthDao payAuthDao;
	
	@Override
	public PayAuth get(String appid) {
		return payAuthDao.get(appid);
	}

	@Override
	public PayAuth get(int channel) {
		List<PayAuth> payAuths = this.payAuthDao.list();
		for(PayAuth auth: payAuths){
			if(auth.getChannel()==channel){
				return auth;
			}
		}
		return null;
	}

}
