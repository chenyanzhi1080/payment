package com.xiaoerzuche.biz.zmxy.service;

import com.xiaoerzuche.biz.payment.vo.PagerVo;
import com.xiaoerzuche.biz.zmxy.model.MemberAntifraud;
import com.xiaoerzuche.biz.zmxy.vo.antifraud.AntifraudRemarkForm;
import com.xiaoerzuche.biz.zmxy.vo.antifraud.MemberAntifraudMgtVo;
import com.xiaoerzuche.biz.zmxy.vo.antifraud.MemberAntifraudQueryVo;
public interface MemberAntifraudService  {

	boolean insert(MemberAntifraud memberAntifraud);

	boolean update(MemberAntifraud memberAntifraud);

	boolean delete(String id);
	
	boolean insertAndUpdates(MemberAntifraud memberAntifraud);
	
	MemberAntifraud get(String id);

	PagerVo< MemberAntifraudMgtVo>  list(MemberAntifraudQueryVo queryVo);
	
	/**
	 * 添加备注
	 * @param antifraudRemarkForm
	 * @return
	 */
	boolean remark(AntifraudRemarkForm antifraudRemarkForm);
	
}