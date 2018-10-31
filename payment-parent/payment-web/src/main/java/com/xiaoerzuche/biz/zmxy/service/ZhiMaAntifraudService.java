package com.xiaoerzuche.biz.zmxy.service;

import java.util.concurrent.Future;

import org.springframework.stereotype.Service;

import com.xiaoerzuche.biz.zmxy.enu.SynthesizeAntifraudEnu;
import com.xiaoerzuche.biz.zmxy.model.MemberAntifraud;
import com.xiaoerzuche.biz.zmxy.vo.antifraud.MemberAntifraudResultVo;

/** 
* 芝麻欺诈
* @author: cyz
* @version: payment-web
* @time: 2017年8月11日
*/
@Service
public interface ZhiMaAntifraudService {
	//芝麻欺诈分（产品暂停使用）
	public Future<Boolean> antifraudScore(MemberAntifraud memberAntifraud);
	//芝麻欺诈信息验证
	public Future<Boolean> antifraudVerify(MemberAntifraud memberAntifraud);
	//芝麻欺诈关注清单（产品暂停使用）
	public Future<Boolean> antifraudRiskList(MemberAntifraud memberAntifraud);
	
	/**
	 * 系统评估反欺诈情况
	 * @param memberAntifraud
	 * @param antifraudEnu 三要素分析维度
	 */
	public void synthesizeAntifraud(MemberAntifraud memberAntifraud, SynthesizeAntifraudEnu antifraudEnu);
	
	/**
	 * 单个用户的欺诈信息
	 * @param memberAntifraud
	 * @return
	 */
	public MemberAntifraudResultVo antifraudUser(MemberAntifraud memberAntifraud);
	
	/**
	 * 用户身份信息校验
	 * @param memberAntifraud
	 * @return
	 */
	public boolean identityAuth(MemberAntifraud memberAntifraud);
}
