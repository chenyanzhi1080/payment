package junit.zmxy;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.reflect.TypeToken;
import com.xiaoerzuche.biz.payment.service.PaySystemConfigService;
import com.xiaoerzuche.biz.payment.vo.PagerVo;
import com.xiaoerzuche.biz.zmxy.bo.ZmWatchListDetailBo;
import com.xiaoerzuche.biz.zmxy.bz.CommeZmxyBz;
import com.xiaoerzuche.biz.zmxy.bz.ZmxyBz;
import com.xiaoerzuche.biz.zmxy.dao.MemberCreditDao;
import com.xiaoerzuche.biz.zmxy.dao.sqlbuilder.MemberCreditBuilder;
import com.xiaoerzuche.biz.zmxy.enu.ZMRiskEnu;
import com.xiaoerzuche.biz.zmxy.model.MemberCredit;
import com.xiaoerzuche.biz.zmxy.service.ZhiMaAuthService;
import com.xiaoerzuche.biz.zmxy.vo.MemberCreditFormVo;
import com.xiaoerzuche.biz.zmxy.vo.MemberCreditMgtVo;
import com.xiaoerzuche.biz.zmxy.vo.ZmDetailVo;
import com.xiaoerzuche.common.util.JsonUtil;

import junit.BaseTest;

public class TestMemberCreditService extends BaseTest {
	@Autowired
	private PaySystemConfigService paySystemConfigService;
	@Autowired
	private MemberCreditDao memberCreditDao;
//	@Test
	public void getCommeZmxyBz(){
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
		
		System.out.println(JsonUtil.toJson(commeZmxyBz));
	}

//	@Test
	public void memberCreditList(){
		PagerVo<MemberCreditMgtVo> pagerVo = new PagerVo<MemberCreditMgtVo>(0, 2);
		System.out.println("========="+pagerVo.getStart());
		MemberCreditBuilder builder = new MemberCreditBuilder();
		List<MemberCredit> list = memberCreditDao.list(builder);
		long count = memberCreditDao.count(builder);
		List<MemberCreditMgtVo> creditMgtVos = new ArrayList<MemberCreditMgtVo>();
		MemberCreditMgtVo creditMgtVo = null;
		
		for(MemberCredit credit : list){
			creditMgtVo = new MemberCreditMgtVo();
			creditMgtVo.setId(credit.getId())
			.setName(credit.getName())
			.setAccount(credit.getAccount())
			.setCardId(credit.getCardId())
			.setBlacklist(credit.getIsBlacklist())
			.setLastModifyTime(credit.getLastModifyTime());
//			credit.getDetails();
			creditMgtVos.add(creditMgtVo);
		}
		
		pagerVo.setList(creditMgtVos);
		pagerVo.setTotalRecords(count);
		System.out.println(JsonUtil.toJson(pagerVo));
	}
	@Autowired
	private ZhiMaAuthService zhiMaAuthService;
	@Test
	public void getMemberCreditStatus(){
		Integer creditStatus = zhiMaAuthService.getCreditStatus("18289767660");
		System.out.println("测试getMemberCreditStatus="+creditStatus);
	}
	
	public static void main(String[] args){
		List<ZmDetailVo> zmDetails = new ArrayList<ZmDetailVo>();
		ZmDetailVo detailVo = null;
		String json = "[{\"bizCode\":\"AA\",\"code\":\"AA001001\",\"extendInfo\":[{\"description\":\"逾期金额（元）\",\"key\":\"event_max_amt_code\",\"value\":\"M01\"},{\"description\":\"编号\",\"key\":\"id\",\"value\":\"f2ff5e72b4d88c6c547a15531314d035\"},{\"description\":\"最近一次违约时间\",\"key\":\"event_end_time_desc\",\"value\":\"2014-12\"}],\"level\":1,\"refreshTime\":\"2017-07-09 00:00:00\",\"settlement\":true,\"type\":\"AA001\"}]";
		List<ZmWatchListDetailBo> list = JsonUtil.fromJson(json, new TypeToken<List<ZmWatchListDetailBo>>(){});
		
		for(ZmWatchListDetailBo bo : list){
			String bizCode = bo.getBizCode();
			System.out.println(ZMRiskEnu.valueOf(bizCode).getName());
			String zmDetail = ZMRiskEnu.valueOf(bizCode).getName()+bo.getCode();
			detailVo = new ZmDetailVo();
			detailVo.setZmDetail(zmDetail);
			zmDetails.add(detailVo);
		}
		System.out.println(JsonUtil.toJson(zmDetails));
	}
}
