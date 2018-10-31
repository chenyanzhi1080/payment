package com.xiaoerzuche.biz.zmxy.service;

import com.xiaoerzuche.biz.zmxy.model.MemberCredit;
import com.xiaoerzuche.biz.zmxy.vo.MemberCreditVo;
import com.xiaoerzuche.biz.zmxy.vo.ZmAuthCallBackVo;

/** 
* 芝麻信用授权
* @author: cyz
* @version: payment-web
* @time: 2017年5月5日
* 
*/
public interface ZhiMaAuthService {
	/**
	 * 获取芝麻授权H5页面Url
	 * @param name
	 * @param cardId
	 * @return
	 */
	String authUrl(String account, String name, String cardId, String successUrl, String failUrl, String memberCreditId);
	/**
	 * 芝麻信用用户
	 * @param account
	 * @param name
	 * @param cardId
	 * @return
	 */
	String genMemberCredit(String account, String name, String cardId);
	/**
	 * 获取或生产芝麻信用用户，如果身份相同则拦截
	 * @param account
	 * @param name
	 * @param cardId
	 * @return
	 */
	boolean filter(String account, String name, String cardId);
	/**
	 * 签名及解密
	 * @param sign
	 * @param params
	 * @return
	 */
	ZmAuthCallBackVo paseParam(String sign, String params);
	
	/**
	 * 授权回调处理
	 * @param authCallBackVo
	 * @return
	 */
	boolean authCallBack(ZmAuthCallBackVo authCallBackVo);
	
	/**
	 * 获取用户当前状态：NOT_AUTH(1,"未授权"),NO_BAD_RECORD(2,"无不良记录"),HAD_BAD_RECORD(3,"有不良记录");
	 * 参考CreditStatus
	 * @param account
	 * @return
	 */
	Integer getCreditStatus(String account);
	
	/**
	 * 芝麻信用行业关注名单获取
	 * @param account
	 * @return
	 */
	MemberCredit getZhimaCreditWatchlist(MemberCredit memberCredit); 
	
	/**
	 * 获取芝麻信用分
	 * @param memberCredit
	 * @return
	 */
	MemberCredit getZhimaCreditScore(MemberCredit memberCredit); 
	
	/**
	 * 主动查询芝麻信用查询情况
	 * @param name
	 * @param cardId
	 * @param account
	 * @return
	 */
	boolean queryZmAuth(String name, String cardId, String account);
	
	MemberCreditVo getMemberCreditStatus(String account);
	
	Integer zhimaAuthInfoAuthquery(MemberCredit memberCredit);
}
