package com.xiaoerzuche.biz.zmxy.service.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.reflect.TypeToken;
import com.xiaoerzuche.biz.payment.vo.PagerVo;
import com.xiaoerzuche.biz.zmxy.dao.MemberAntifraudDao;
import com.xiaoerzuche.biz.zmxy.dao.sqlbuilder.MemberAntifraudBuilder;
import com.xiaoerzuche.biz.zmxy.model.MemberAntifraud;
import com.xiaoerzuche.biz.zmxy.service.MemberAntifraudService;
import com.xiaoerzuche.biz.zmxy.vo.antifraud.AntifraudRemarkForm;
import com.xiaoerzuche.biz.zmxy.vo.antifraud.AntifraudRemarkVo;
import com.xiaoerzuche.biz.zmxy.vo.antifraud.MemberAntifraudMgtVo;
import com.xiaoerzuche.biz.zmxy.vo.antifraud.MemberAntifraudQueryVo;
import com.xiaoerzuche.common.util.JsonUtil;
@Service
public class MemberAntifraudServiceImp  implements MemberAntifraudService {

	@Autowired
	private MemberAntifraudDao memberAntifraudDao;

	public boolean insert(MemberAntifraud memberAntifraud){
		return this.memberAntifraudDao.insert(memberAntifraud);
	}

	public boolean update(MemberAntifraud memberAntifraud){
		return this.memberAntifraudDao.update(memberAntifraud);
	}

	public boolean delete(String id){
		return this.memberAntifraudDao.delete(id);
	}

	public MemberAntifraud get(String id){
		return this.memberAntifraudDao.get(id);
	}

	public PagerVo<MemberAntifraudMgtVo> list(MemberAntifraudQueryVo queryVo){
		MemberAntifraudBuilder builder = queryVo.builder();
		List<MemberAntifraud> data = this.memberAntifraudDao.list(builder);
		int count = this.memberAntifraudDao.count(builder);
		PagerVo<MemberAntifraudMgtVo> pagerVo = new PagerVo<MemberAntifraudMgtVo>();
		List<MemberAntifraudMgtVo> list = new ArrayList<MemberAntifraudMgtVo>();
		for(MemberAntifraud  antifraud : data){
			MemberAntifraudMgtVo antifraudMgtVo = new MemberAntifraudMgtVo(antifraud);
			list.add(antifraudMgtVo);
		}
		pagerVo.setList(list);
		pagerVo.setTotalRecords(count);
		pagerVo.setLimit(builder.getLimit());
		pagerVo.setOffset(builder.getOffset());
		return pagerVo;
	}

	@Override
	public boolean insertAndUpdates(MemberAntifraud memberAntifraud) {
		Date recordTime = new Date();
		if (StringUtils.isBlank(memberAntifraud.getId())) {
			String memberAntifraudId = System.currentTimeMillis() + memberAntifraud.getAccount();
			memberAntifraud.setId(memberAntifraudId);
			memberAntifraud.setCreateTime(recordTime);
			memberAntifraud.setLastModifyTime(recordTime);
			return this.memberAntifraudDao.insert(memberAntifraud);
		}else{
			memberAntifraud.setLastModifyTime(recordTime);
			return this.memberAntifraudDao.update(memberAntifraud);
		}
	}

	@Override
	public boolean remark(AntifraudRemarkForm antifraudRemarkForm) {
		MemberAntifraud memberAntifraud = this.memberAntifraudDao.get(antifraudRemarkForm.getId());
		LinkedList<AntifraudRemarkVo> remarkVos = null;
		if(StringUtils.isNotBlank(memberAntifraud.getRemark())){
			remarkVos = JsonUtil.fromJson(memberAntifraud.getRemark(), new TypeToken<LinkedList<AntifraudRemarkVo>>(){});
		}else{
			remarkVos = new LinkedList<AntifraudRemarkVo>();
		}
		AntifraudRemarkVo remarkVo = new AntifraudRemarkVo();
		remarkVo.setCreateTime(new Date())
		.setRemark(antifraudRemarkForm.getRemark())
		.setOperator(antifraudRemarkForm.getOperator());
		remarkVos.addFirst(remarkVo);
		memberAntifraud.setRemark(JsonUtil.toJson(remarkVos));
		return this.memberAntifraudDao.update(memberAntifraud);
	}
}