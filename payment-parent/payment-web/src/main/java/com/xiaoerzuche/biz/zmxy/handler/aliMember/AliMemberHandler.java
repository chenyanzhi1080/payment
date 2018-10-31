package com.xiaoerzuche.biz.zmxy.handler.aliMember;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.xiaoerzuche.biz.zmxy.dao.AliMemberDao;
import com.xiaoerzuche.biz.zmxy.model.AliMember;
import com.xiaoerzuche.biz.zmxy.web.api.AliMemberAuthApiController;

@Component
public class AliMemberHandler {
	private static final Logger logger = LoggerFactory.getLogger(AliMemberAuthApiController.class);
	@Autowired
	private AliMemberDao aliMemberDao;

	@Async
	public void addAndUpdatAliMember(AliMember aliMember){
		AliMember dbAliMember = aliMemberDao.get(aliMember.getAliUserId());
		try {
			if(dbAliMember == null){
				aliMember.setCreateTime(new Date());
				aliMemberDao.insert(aliMember);
				logger.info("[支付宝会员记录新增]"+aliMember);
			}else{
				aliMember.setLastModifyTime(new Date());
				aliMemberDao.update(aliMember);
				logger.info("[支付宝会员记录变更]"+aliMember);
			}
		} catch (Exception e) {
			logger.info("[支付宝会员记录异常]"+e);
		}
	}
}
