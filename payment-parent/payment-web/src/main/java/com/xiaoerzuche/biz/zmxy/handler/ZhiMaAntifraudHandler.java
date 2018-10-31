package com.xiaoerzuche.biz.zmxy.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xiaoerzuche.biz.zmxy.dao.MemberOutsideDao;
import com.xiaoerzuche.biz.zmxy.model.MemberAntifraud;
import com.xiaoerzuche.biz.zmxy.service.MemberAntifraudService;
import com.xiaoerzuche.biz.zmxy.service.ZhiMaAntifraudService;
import com.xiaoerzuche.biz.zmxy.vo.antifraud.MemberOutsideVo;


@Component
public class ZhiMaAntifraudHandler {
	private static final Logger logger = LoggerFactory.getLogger(ZhiMaAntifraudHandler.class);
	
	@Autowired
	private MemberAntifraudService memberAntifraudService;
	@Autowired
	private ZhiMaAntifraudService antifraudService;
	@Autowired
	private MemberOutsideDao memberOutsideDao;
	
	/**
	 * 批量反欺诈认证
	 */
	public void doBatchAntifraud(){
	}
	
	public boolean refresh(String memberId){
		MemberOutsideVo memberOutsideVo = memberOutsideDao.get(memberId);
		MemberAntifraud memberAntifraud = new MemberAntifraud();
		memberAntifraud.setId(memberId);
		memberAntifraud.setAccount(memberOutsideVo.getMobilePhone())
		.setIdCard(memberOutsideVo.getIdCard())
		.setName(memberOutsideVo.getMemberName())
		;
		//芝麻欺诈分查询(产品暂停使用)
//		antifraudService.antifraudScore(memberAntifraud);
//		if(StringUtils.isNotBlank(memberAntifraud.getErrorMessage())){
//			memberAntifraudService.update(memberAntifraud);
//			return false;
//		}
		//芝麻欺诈验证
		antifraudService.antifraudVerify(memberAntifraud);
		//芝麻欺诈关注名单（产品暂停使用）
//		antifraudService.antifraudRiskList(memberAntifraud);
		return memberAntifraudService.update(memberAntifraud);
	}
}
