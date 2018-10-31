package com.xiaoerzuche.biz.zmxy.service.imp;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import com.antgroup.zmxy.openplatform.api.ZhimaApiException;
import com.antgroup.zmxy.openplatform.api.request.ZhimaCreditAntifraudRiskListRequest;
import com.antgroup.zmxy.openplatform.api.request.ZhimaCreditAntifraudScoreGetRequest;
import com.antgroup.zmxy.openplatform.api.request.ZhimaCreditAntifraudVerifyRequest;
import com.antgroup.zmxy.openplatform.api.response.ZhimaCreditAntifraudRiskListResponse;
import com.antgroup.zmxy.openplatform.api.response.ZhimaCreditAntifraudScoreGetResponse;
import com.antgroup.zmxy.openplatform.api.response.ZhimaCreditAntifraudVerifyResponse;
import com.google.gson.reflect.TypeToken;
import com.xiaoerzuche.biz.zmxy.config.ZmxyClient;
import com.xiaoerzuche.biz.zmxy.dao.MemberAntifraudDao;
import com.xiaoerzuche.biz.zmxy.dao.sqlbuilder.MemberAntifraudBuilder;
import com.xiaoerzuche.biz.zmxy.enu.AntifraudVerifyEnu;
import com.xiaoerzuche.biz.zmxy.enu.SynthesizeAntifraudEnu;
import com.xiaoerzuche.biz.zmxy.model.MemberAntifraud;
import com.xiaoerzuche.biz.zmxy.service.MemberAntifraudService;
import com.xiaoerzuche.biz.zmxy.service.ZhiMaAntifraudService;
import com.xiaoerzuche.biz.zmxy.vo.antifraud.MemberAntifraudResultVo;
import com.xiaoerzuche.common.util.JsonUtil;

@Service
public class ZhiMaAntifraudServiceImp implements ZhiMaAntifraudService{
	private static final Logger logger = LoggerFactory.getLogger(ZhiMaAntifraudServiceImp.class);
	
	@Autowired
	private MemberAntifraudDao memberAntifraudDao;
	@Autowired
	private MemberAntifraudService memberAntifraudService;
	
	@Override
	@Async
	public Future<Boolean> antifraudScore(MemberAntifraud memberAntifraud) {
		ZhimaCreditAntifraudScoreGetRequest req = new ZhimaCreditAntifraudScoreGetRequest();
        req.setChannel("apppc");
        req.setPlatform("zmop");
        req.setProductCode(ZmxyClient.CREDIT_ANTIFRAUD_SCORE_CODE);
        req.setTransactionId("AS"+String.valueOf(System.currentTimeMillis())+memberAntifraud.getAccount());//TODO
        req.setCertType("IDENTITY_CARD");
        if(StringUtils.isNotBlank(memberAntifraud.getIdCard())) req.setCertNo(memberAntifraud.getIdCard());
        if(StringUtils.isNotBlank(memberAntifraud.getAccount())) req.setMobile(memberAntifraud.getAccount());
        req.setName(memberAntifraud.getName());

        try {
			ZhimaCreditAntifraudScoreGetResponse response = ZmxyClient.zhimaClient.execute(req);
			if(response.isSuccess()){
				memberAntifraud.setAntifraudScore(response.getScore().intValue());
			}else{
				memberAntifraud.setErrorMessage(response.getErrorMessage()).setAntifraudScore(0);
				logger.error("[反欺诈分查询][req={}][fail]{}",new Object[]{JsonUtil.toJson(req),response.getErrorMessage()});
				return new AsyncResult<Boolean>(false);
			}
        } catch (ZhimaApiException e) {
        	logger.error("[反欺诈分查询][req={}][error]{}",new Object[]{JsonUtil.toJson(req),e});
        	memberAntifraud.setErrorMessage("系统异常");
        	return new AsyncResult<Boolean>(false);
		}
        return new AsyncResult<Boolean>(true);
	}

	@Override
	@Async
	public Future<Boolean> antifraudVerify(MemberAntifraud memberAntifraud) {
		ZhimaCreditAntifraudVerifyRequest req = new ZhimaCreditAntifraudVerifyRequest();
        req.setChannel("apppc");
        req.setPlatform("zmop");
        req.setProductCode("w1010100000000002859");
        req.setTransactionId("AV"+String.valueOf(System.currentTimeMillis())+memberAntifraud.getAccount());//TODO
        req.setCertType("IDENTITY_CARD");
        if(StringUtils.isNotBlank(memberAntifraud.getIdCard())) req.setCertNo(memberAntifraud.getIdCard());
        if(StringUtils.isNotBlank(memberAntifraud.getAccount())) req.setMobile(memberAntifraud.getAccount());
        req.setName(memberAntifraud.getName());
        
        try {
            ZhimaCreditAntifraudVerifyResponse response = ZmxyClient.zhimaClient.execute(req);
            if(response.isSuccess()){
            	memberAntifraud.setAntifraudVerify(JsonUtil.toJson(response.getVerifyCode()));
			}else{
				memberAntifraud.setErrorMessage(response.getErrorMessage());
				logger.error("[反欺诈信息校验][req={}][fail]{}",new Object[]{JsonUtil.toJson(req),response.getErrorMessage()});
				return new AsyncResult<Boolean>(false);
			}
        } catch (ZhimaApiException e) {
        	logger.error("[反欺诈信息校验][req={}][error]{}",new Object[]{JsonUtil.toJson(req),e});
        	memberAntifraud.setErrorMessage("系统异常");
        	return new AsyncResult<Boolean>(false);
        }
        return new AsyncResult<Boolean>(true);
	}

	@Override
	@Async
	public Future<Boolean> antifraudRiskList(MemberAntifraud memberAntifraud) {
        ZhimaCreditAntifraudRiskListRequest req = new ZhimaCreditAntifraudRiskListRequest();
        req.setChannel("apppc");
        req.setPlatform("zmop");
        req.setProductCode("w1010100003000001283");
        req.setTransactionId(String.valueOf("AR"+System.currentTimeMillis())+memberAntifraud.getAccount());//TODO
        req.setCertType("IDENTITY_CARD");
        if(StringUtils.isNotBlank(memberAntifraud.getIdCard())) req.setCertNo(memberAntifraud.getIdCard());
        if(StringUtils.isNotBlank(memberAntifraud.getAccount())) req.setMobile(memberAntifraud.getAccount());
        req.setName(memberAntifraud.getName());
        
        try {
            ZhimaCreditAntifraudRiskListResponse response = ZmxyClient.zhimaClient.execute(req);
            if(response.isSuccess()){
            	memberAntifraud.setHit("yes".equals(response.getHit())? 1:2);
            	if(null != response.getRiskCode()){
            		memberAntifraud.setAntifraudRisk(JsonUtil.toJson(response.getRiskCode()));
            	} 
            }else{
				memberAntifraud.setErrorMessage(response.getErrorCode()+" "+response.getErrorMessage());
				logger.error("[欺诈关注清单][req={}][fail]{}",new Object[]{JsonUtil.toJson(req),response.getErrorMessage()});
				return new AsyncResult<Boolean>(false);
			}
        } catch (ZhimaApiException e) {
        	logger.error("[欺诈关注清单][req={}][error]{}",new Object[]{JsonUtil.toJson(req),e});
        	memberAntifraud.setErrorMessage("系统异常");
        	return new AsyncResult<Boolean>(false);
        }
        return new AsyncResult<Boolean>(true);
	}

	@Override
	public void synthesizeAntifraud(MemberAntifraud memberAntifraud, SynthesizeAntifraudEnu antifraudEnu){
		long startTime = System.currentTimeMillis();
		MemberAntifraudBuilder builder = new MemberAntifraudBuilder();
		builder.setAccount(memberAntifraud.getAccount()).setIdCard(memberAntifraud.getIdCard());
		List<MemberAntifraud> list = this.memberAntifraudDao.list(builder);
		if(! list.isEmpty()){
			memberAntifraud.setId(list.get(0).getId());
		}
		String phone = memberAntifraud.getAccount();
		String idCard = memberAntifraud.getIdCard();
		switch (antifraudEnu) {
		case NAME_IDCARD:
			memberAntifraud.setAccount(null);break;
		case NAME_PHONE:
			memberAntifraud.setIdCard(null);break;	
		default:
			break;
		}
		//申请欺诈评分
//		Future<Boolean> antifraudScoreFuture = this.antifraudScore(memberAntifraud);
		//申请欺诈信息验证
		Future<Boolean> antifraudVerifyFuture = this.antifraudVerify(memberAntifraud);
		//申请欺诈关注名单
//		Future<Boolean> antifraudRiskListFuture = this.antifraudRiskList(memberAntifraud);
//		while(true){
//			if(antifraudScoreFuture.isDone() && antifraudVerifyFuture.isDone() && antifraudRiskListFuture.isDone()){
//				break;
//			}
//		}
		while(true){
			if(antifraudVerifyFuture.isDone()){
				break;
			}
		}
		//新增或更新记录
		memberAntifraud.setAccount(phone).setIdCard(idCard);
		this.memberAntifraudService.insertAndUpdates(memberAntifraud);
		long endTime = System.currentTimeMillis();
		logger.info("[反欺诈分析age={}]",new Object[]{endTime-startTime});
	}

	@Override
	public MemberAntifraudResultVo antifraudUser(MemberAntifraud memberAntifraud) {
		MemberAntifraudResultVo antifraudResultVo = new MemberAntifraudResultVo();
		String antifraudScoreRisk = "";
		int score = memberAntifraud.getAntifraudScore()==null? 0 : memberAntifraud.getAntifraudScore();
		if(0==score){
			antifraudScoreRisk = score+"分；无法识别";
		}
		if(0<score && score<=40){
			antifraudScoreRisk = score+"分；极高风险|人工核查";
		}
		if(40<score && score<80){
			antifraudScoreRisk = score+"分；中等风险|持续关注";
		}
		if(80<=score && score<=100){
			antifraudScoreRisk = score+"分；信用良好";
		}
		antifraudResultVo.setHit(memberAntifraud.getHit()==null? 2:memberAntifraud.getHit());
		antifraudResultVo.setScore(antifraudScoreRisk);
		List<String> verifyResults = new ArrayList<String>();
		
		//解读Verify Code
		List<String> verifyCodeList = JsonUtil.fromJson(memberAntifraud.getAntifraudVerify(), new TypeToken<List<String>>(){});
		for(String code : verifyCodeList){
			String verifyResult = AntifraudVerifyEnu.getVerifyResult(code);
			verifyResults.add(verifyResult);
		}
		antifraudResultVo.setVerifyResults(verifyResults);
		return antifraudResultVo;
	}
	
	@Override
	public boolean identityAuth(MemberAntifraud memberAntifraud) {
		if(StringUtils.isNotBlank(memberAntifraud.getErrorMessage())){
			return false;
		}
		String antifraudVerifyCode = memberAntifraud.getAntifraudVerify();
		logger.info("[用户{}欺诈验证结果{}]",new Object[]{memberAntifraud.getAccount(),antifraudVerifyCode});
		return StringUtils.isNotBlank(antifraudVerifyCode) && antifraudVerifyCode.lastIndexOf("UM")<0;
	}
}
