package com.xiaoerzuche.biz.zmxy.service;

import java.util.List;

import com.xiaoerzuche.biz.payment.vo.PagerVo;
import com.xiaoerzuche.biz.zmxy.bz.CommeZmxyBz;
import com.xiaoerzuche.biz.zmxy.model.MemberCredit;
import com.xiaoerzuche.biz.zmxy.vo.MemberBlacklistFromVo;
import com.xiaoerzuche.biz.zmxy.vo.MemberCreditFormVo;
import com.xiaoerzuche.biz.zmxy.vo.MemberCreditMgtVo;
import com.xiaoerzuche.biz.zmxy.vo.MemberCreditQueryVo;
import com.xiaoerzuche.biz.zmxy.vo.ZmDetailVo;
import com.xiaoerzuche.biz.zmxy.vo.ZmRiskVo;

public interface MemberCreditService {
	MemberCredit get(String id);
	PagerVo<MemberCreditMgtVo> memberCreditList(MemberCreditQueryVo queryVo);
	boolean sendZhimaCreditWatch(MemberCreditFormVo formVo);
	/**
	 * 设置黑名单
	 * @param formVo
	 * @return
	 */
	boolean setBlacklist(MemberBlacklistFromVo formVo);
	public CommeZmxyBz getCommeZmxyBz();
	/**
	 * 负面记录类型列表(行业关注名单风险信息行业编码)
	 * @return
	 */
	public List<ZmRiskVo> zmRiskList();
	/**
	 * 解析芝麻信用行业负面记录
	 * @param memberCredit
	 * @return
	 */
	public List<ZmDetailVo> paserZmDetail(MemberCredit memberCredit);
	/**
	 * 解绑
	 * @param account
	 * @param id （保留字段，暂时不使用）
	 * @return
	 */
	public boolean unbind(String account,String id ,String operation);
}
