package com.xiaoerzuche.biz.zmxy.service.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.reflect.TypeToken;
import com.xiaoerzuche.biz.payment.service.PaySystemConfigService;
import com.xiaoerzuche.biz.payment.vo.PagerVo;
import com.xiaoerzuche.biz.zmxy.bo.ZmWatchListDetailBo;
import com.xiaoerzuche.biz.zmxy.bz.CommeZmxyBz;
import com.xiaoerzuche.biz.zmxy.bz.ZmxyBz;
import com.xiaoerzuche.biz.zmxy.dao.MemberCreditDao;
import com.xiaoerzuche.biz.zmxy.dao.sqlbuilder.MemberCreditBuilder;
import com.xiaoerzuche.biz.zmxy.enu.AuthStatusEnu;
import com.xiaoerzuche.biz.zmxy.enu.ZMRiskEnu;
import com.xiaoerzuche.biz.zmxy.model.MemberCredit;
import com.xiaoerzuche.biz.zmxy.service.MemberCreditService;
import com.xiaoerzuche.biz.zmxy.vo.AuthRecordVo;
import com.xiaoerzuche.biz.zmxy.vo.MemberBlacklistFromVo;
import com.xiaoerzuche.biz.zmxy.vo.MemberCreditFormVo;
import com.xiaoerzuche.biz.zmxy.vo.MemberCreditMgtVo;
import com.xiaoerzuche.biz.zmxy.vo.MemberCreditQueryVo;
import com.xiaoerzuche.biz.zmxy.vo.ZmDetailVo;
import com.xiaoerzuche.biz.zmxy.vo.ZmRiskVo;
import com.xiaoerzuche.common.util.JsonUtil;

@Service
public class MemberCreditServiceImp implements MemberCreditService{
	@Autowired
	private MemberCreditDao memberCreditDao;
	@Autowired
	private PaySystemConfigService paySystemConfigService;

	@Override
	public PagerVo<MemberCreditMgtVo> memberCreditList(MemberCreditQueryVo queryVo) {
		MemberCreditBuilder builder = queryVo.builder();
		List<MemberCredit> list = memberCreditDao.list(builder);
		long count = memberCreditDao.count(builder);
		List<MemberCreditMgtVo> creditMgtVos = new ArrayList<MemberCreditMgtVo>();
		MemberCreditMgtVo creditMgtVo = null;
		List<MemberCreditFormVo> creditFormVos = null;
		List<AuthRecordVo> authRecors = null;
		Map<Integer, AuthStatusEnu> mapByCode = AuthStatusEnu.mapByCode;
		for(MemberCredit credit : list){
			//处理风控备注
			String detailJson = credit.getDetails();
			creditFormVos = new ArrayList<MemberCreditFormVo>();
			if(StringUtils.isNotBlank(detailJson)){
				creditFormVos = JsonUtil.fromJson(detailJson, new TypeToken<List<MemberCreditFormVo>>(){});
			}
			//处理用户授权行为记录
			authRecors = new ArrayList<AuthRecordVo>(); 
			String authRecordJson = credit.getAuthRecord();
			if(StringUtils.isNotBlank(authRecordJson)){
				authRecors = JsonUtil.fromJson(authRecordJson, new TypeToken<List<AuthRecordVo>>(){} );
			}
			List<ZmDetailVo> zmDetails = this.paserZmDetail(credit);
			creditMgtVo = new MemberCreditMgtVo();
			creditMgtVo.setId(credit.getId())
			.setName(credit.getName())
			.setAccount(credit.getAccount())
			.setCardId(credit.getCardId())
			.setBlacklist(credit.getIsBlacklist())
			.setLastModifyTime(credit.getLastModifyTime())
			.setStatus(mapByCode.get(credit.getAuthStatus()).getValue())
			.setZmScore(credit.getZmScore())
			.setDetails(creditFormVos)
			.setAuthRecors(authRecors)
			.setZmDetails(zmDetails)
			.setAuthChangeTime(credit.getAuthChangeTime());
			creditMgtVos.add(creditMgtVo);
		}
		PagerVo<MemberCreditMgtVo> pagerVo = new PagerVo<MemberCreditMgtVo>(builder.getOffset(), builder.getLimit());
		pagerVo.setList(creditMgtVos);
		pagerVo.setTotalRecords(count);
		return pagerVo;
	}
	
	
	@Override
	public boolean sendZhimaCreditWatch(MemberCreditFormVo formVo) {
		MemberCredit memberCredit = memberCreditDao.get(formVo.getId());
		
		List<MemberCreditFormVo> creditFormVos = new ArrayList<MemberCreditFormVo>();
		
		if(StringUtils.isNotBlank(memberCredit.getDetails())){
			creditFormVos = JsonUtil.fromJson(memberCredit.getDetails(), new TypeToken<List<MemberCreditFormVo>>(){} );
		}
		
		creditFormVos.add(formVo);
		
		return memberCreditDao.sendZhimaCreditWatch(formVo.getId(), JsonUtil.toJson(creditFormVos));
	}

	@Override
	public boolean setBlacklist(MemberBlacklistFromVo formVo) {
		return memberCreditDao.setBlacklist(formVo.getId(), formVo.getBlackReasonCode(), formVo.getRemark(), formVo.getOperator(), new Date());
	}
	
	@Override
	public CommeZmxyBz getCommeZmxyBz(){
		List<ZmxyBz> behaviour = new ArrayList<ZmxyBz>();//失信行为业务码
		List<ZmxyBz> overdue = new ArrayList<ZmxyBz>();//逾期时间范围业务码
		List<ZmxyBz> amount = new ArrayList<ZmxyBz>();//逾期金额范围业务码
		List<ZmxyBz> currentState = new ArrayList<ZmxyBz>();//逾期当前状态业务码
		List<ZmxyBz> blackReason = new ArrayList<ZmxyBz>();//开开出行设置黑名单原因业务码
		
		String behaviourJson = paySystemConfigService.get("ZMXY_BEHAVIOUR").getConfigValue();
		String overdueJson = paySystemConfigService.get("ZMXY_OVERDUE").getConfigValue();
		String amountJson = paySystemConfigService.get("ZMXY_AMOUNT").getConfigValue();
		String currentStateJson = paySystemConfigService.get("XEZC_CURRENTSTATE").getConfigValue();
		String blackReasonJson = paySystemConfigService.get("XEZC_BLACKREASON").getConfigValue();
		
		if(StringUtils.isNotBlank(behaviourJson)){
			behaviour = JsonUtil.fromJson(behaviourJson, new TypeToken<List<ZmxyBz>>(){});
		}
		if(StringUtils.isNotBlank(overdueJson)){
			overdue = JsonUtil.fromJson(overdueJson, new TypeToken<List<ZmxyBz>>(){});
		}
		if(StringUtils.isNotBlank(amountJson)){
			amount = JsonUtil.fromJson(amountJson, new TypeToken<List<ZmxyBz>>(){});
		}
		if(StringUtils.isNotBlank(currentStateJson)){
			currentState = JsonUtil.fromJson(currentStateJson, new TypeToken<List<ZmxyBz>>(){});
		}
		if(StringUtils.isNotBlank(blackReasonJson)){
			blackReason = JsonUtil.fromJson(blackReasonJson, new TypeToken<List<ZmxyBz>>(){});
		}
		
		CommeZmxyBz commeZmxyBz = new CommeZmxyBz(behaviour, amount, overdue, currentState, blackReason);
		return commeZmxyBz;
	}


	@Override
	public List<ZmDetailVo> paserZmDetail(MemberCredit memberCredit) {
		List<ZmDetailVo> zmDetails = new ArrayList<ZmDetailVo>();
		if(StringUtils.isNotBlank(memberCredit.getZmDetails()) && !memberCredit.getZmDetails().equals("NO_BAD_RECORD")){
			List<ZmWatchListDetailBo> list = JsonUtil.fromJson(memberCredit.getZmDetails(), new TypeToken<List<ZmWatchListDetailBo>>(){});
			ZmDetailVo detailVo = null;
			for(ZmWatchListDetailBo bo : list){
				String zmDetail = ZMRiskEnu.valueOf(bo.getBizCode()).getName()+bo.getCode();
				detailVo = new ZmDetailVo();
				detailVo.setZmDetail(zmDetail);
				zmDetails.add(detailVo);
			}
		}
		return zmDetails;
	}


	@Override
	public List<ZmRiskVo> zmRiskList() {
		List<ZmRiskVo> list = new ArrayList<ZmRiskVo>();
		ZmRiskVo riskVo = null;
		for(ZMRiskEnu enu : ZMRiskEnu.values()){
			riskVo = new ZmRiskVo();
			riskVo.setRiskCode(enu.toString()).setRiskName(enu.getName());
			list.add(riskVo);
			riskVo = null;
		}
		return list;
	}


	@Override
	public MemberCredit get(String id) {
		return this.memberCreditDao.get(id);
	}


	@Override
	public boolean unbind(String account, String id,String operation) {
		
		return this.memberCreditDao.unbind(account, id, operation);
	}
	
}
