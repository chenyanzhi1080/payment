package com.xiaoerzuche.biz.zmxy.service.imp;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.parser.json.JsonConverter;
import com.antgroup.zmxy.openplatform.api.ZhimaApiException;
import com.antgroup.zmxy.openplatform.api.request.ZhimaAuthInfoAuthorizeRequest;
import com.antgroup.zmxy.openplatform.api.request.ZhimaAuthInfoAuthqueryRequest;
import com.antgroup.zmxy.openplatform.api.request.ZhimaCreditScoreGetRequest;
import com.antgroup.zmxy.openplatform.api.request.ZhimaCreditWatchlistiiGetRequest;
import com.antgroup.zmxy.openplatform.api.response.ZhimaAuthInfoAuthqueryResponse;
import com.antgroup.zmxy.openplatform.api.response.ZhimaCreditScoreGetResponse;
import com.antgroup.zmxy.openplatform.api.response.ZhimaCreditWatchlistiiGetResponse;
import com.google.gson.reflect.TypeToken;
import com.xiaoerzuche.biz.payment.model.PaySystemConfig;
import com.xiaoerzuche.biz.payment.service.PaySystemConfigService;
import com.xiaoerzuche.biz.zmxy.config.ZmxyClient;
import com.xiaoerzuche.biz.zmxy.dao.MemberCreditDao;
import com.xiaoerzuche.biz.zmxy.dao.sqlbuilder.MemberCreditBuilder;
import com.xiaoerzuche.biz.zmxy.enu.AuthStatusEnu;
import com.xiaoerzuche.biz.zmxy.enu.CreditStatus;
import com.xiaoerzuche.biz.zmxy.enu.XiaoerLevelEnu;
import com.xiaoerzuche.biz.zmxy.model.MemberCredit;
import com.xiaoerzuche.biz.zmxy.service.ZhiMaAuthService;
import com.xiaoerzuche.biz.zmxy.util.ZmTableUtil;
import com.xiaoerzuche.biz.zmxy.vo.AuthRecordVo;
import com.xiaoerzuche.biz.zmxy.vo.MemberCreditVo;
import com.xiaoerzuche.biz.zmxy.vo.ZmAuthCallBackVo;
import com.xiaoerzuche.biz.zmxy.vo.ZmAuthStateVo;
import com.xiaoerzuche.common.enu.ErrorCode;
import com.xiaoerzuche.common.exception.ErrorCodeException;
import com.xiaoerzuche.common.util.CheckUtil;
import com.xiaoerzuche.common.util.DateTimeUtil;
import com.xiaoerzuche.common.util.JsonUtil;

@Service
public class ZhiMaAuthServiceImp implements ZhiMaAuthService{
	private static final Logger logger = LoggerFactory.getLogger(ZhiMaAuthServiceImp.class);
	
	@Autowired
	private MemberCreditDao memberCreditDao;
	
	@Autowired
	private PaySystemConfigService paySystemConfigService;
	
	private String ZMSCORE_LIMIT_KEY = "ZMSCORE_LIMIT";
	@Override
	public String authUrl(String account, String name, String cardId, String successUrl, String failUrl, String memberCreditId) {
		logger.info("[用户{}][芝麻信用授权请求入参][memberCreditId={}][name={}][cardId={}][successUrl={}][failUrl={}]", 
				new Object[]{account,memberCreditId,name,cardId,successUrl,failUrl});
		String authUrl = "";
		try {
			ZmAuthStateVo authStateVo = new ZmAuthStateVo();
			authStateVo.setMemberCreditId(memberCreditId)
			.setCardId(cardId)
			.setName(name)
			.setFailUrl(StringUtils.isNotBlank(failUrl) ? failUrl : "/api/zmxy/auth/callback/result?result=FAIL")
			.setSuccessUrl(StringUtils.isNotBlank(successUrl) ? successUrl : "/api/zmxy/auth/callback/result?result=SUCCESS");
			String state = JsonUtil.toJson(authStateVo);//state是芝麻信用提供的商户保留字段，会原封不动返回，便于在回调时的业务处理。
			state = URLEncoder.encode(state, "UTF-8");
			ZhimaAuthInfoAuthorizeRequest req = new ZhimaAuthInfoAuthorizeRequest();
			req.setChannel("apppc");
			req.setPlatform("zmop");
			req.setIdentityType("2");//必要参数 
			req.setIdentityParam("{\"name\":\""+name+"\",\"certType\":\"IDENTITY_CARD\",\"certNo\":\""+cardId+"\"}");// 必要参数 
			req.setBizParams("{\"auth_code\":\"M_H5\",\"channelType\":\"app\",\"state\":\""+state+"\"}");// 
			logger.info("[用户{}][芝麻信用授权请求入参IdentityParam{}]", new Object[]{account,req.getIdentityParam()});
			logger.info("[用户{}][芝麻信用授权请求入参BizParams{}]", new Object[]{account,req.getBizParams()});
			authUrl = ZmxyClient.zhimaClient.generatePageRedirectInvokeUrl(req);
		} catch (UnsupportedEncodingException e) {
			logger.error("[用户{}][芝麻信用授权请求异常]", new Object[]{account,e});
			throw new ErrorCodeException(ErrorCode.UNKOWN.getErrorCode(), ErrorCode.UNKOWN.getMessage());
		} catch (ZhimaApiException e) {
			logger.error("[用户{}][芝麻信用授权请求异常]", new Object[]{account,e});
			throw new ErrorCodeException(ErrorCode.UNKOWN.getErrorCode(), ErrorCode.UNKOWN.getMessage());
		}
        logger.info("[用户{}][芝麻信用授权请求url{}]", new Object[]{account,authUrl});
		return authUrl;
	}

	
	/**
	 *业务变更：授权成功后，获取芝麻信用分和行业关注名单
	 */
	@Override
	public boolean authCallBack(ZmAuthCallBackVo zmAuthCallBackVo) {
		/*回调结果Success字段为true时，说明授权成功,否则都认为授权失败*/
		Date time = new Date();
		boolean isAuthSuccess = "true".equals(String.valueOf(zmAuthCallBackVo.getSuccess()).toLowerCase());
		Integer authStatus = isAuthSuccess ? AuthStatusEnu.AUTH.getCode() : null;
		String memberCreditId = zmAuthCallBackVo.getAuthStateVo().getMemberCreditId();
		MemberCredit memberCredit = memberCreditDao.get(memberCreditId);

		memberCredit
		.setName(zmAuthCallBackVo.getAuthStateVo().getName())
		.setCardId(zmAuthCallBackVo.getAuthStateVo().getCardId())
		.setZmopenId(zmAuthCallBackVo.getOpenId())
		.setAuthStatus(authStatus)
		.setAuthResult(zmAuthCallBackVo.getDetails())
		.setOperation("SYSTEM")
		.setAuthChangeTime(time);
		
		if(isAuthSuccess){
			memberCredit = this.getZhimaCreditScore(memberCredit);
			//查看行业关注名单,如果得知用户取消授权，则直接返回状态
			memberCredit = this.getZhimaCreditWatchlist(memberCredit);
			//记录用户成功授权的行为到authRecord字段,增量记录
			authRecor(AuthStatusEnu.AUTH, memberCredit);
		}
		logger.info("[用户{}芝麻信用授权结果={}]", new Object[]{memberCredit.getAccount(),memberCredit});
		return memberCreditDao.update(memberCredit);
	}

	@Override
	public String genMemberCredit(String account, String name, String cardId) {
		MemberCredit memberCredit = memberCreditDao.getByAccount(account);
		if(memberCredit == null ){
			String memberCreditId = ZmTableUtil.genMemberCreditId(account);//生成id
			memberCredit = new MemberCredit();
			memberCredit.setId(memberCreditId)
			.setAccount(account)
			.setAuthStatus(AuthStatusEnu.NOT_AUTH.getCode())
			.setIsBlacklist(0)
			.setOperation("SYSTEM")
			.setCreateTime(new Date())
			.setLastModifyTime(new Date())
			.setLevel(XiaoerLevelEnu.LEVEL_1.getCode());
			CheckUtil.assertTrue(memberCreditDao.insert(memberCredit), ErrorCode.UNKOWN.getErrorCode(), ErrorCode.UNKOWN.getMessage());
			return memberCreditId;
		}else{
			return memberCredit.getId();
		}
		
	}
	/**
	 *身份证重复拦截
	 */
	@Override
	public boolean filter(String account, String name, String cardId){
		MemberCreditBuilder builder = new MemberCreditBuilder();
		builder.setCardId(cardId).setAuthStatus(AuthStatusEnu.AUTH.getCode());
		List<MemberCredit> list = memberCreditDao.list(builder);
		if(null != list && !list.isEmpty()){
			boolean isFilter = true;
			for(MemberCredit memberCredit : list){
				if(account.equals(memberCredit.getAccount())){
					continue;
				}else{
					isFilter = false;
					break;
				}
			}
			return isFilter;
		}else{
			return true;
		}
		
	}
	@Override
	public Integer getCreditStatus(String account) {
		/**
		 * memberCredit为空说明用户未授权；ZmopenId为空说明用户未授权;authStatus 为NOT_AUTH，说明未授权；CANCEL_AUTH 说明用户已取消授权
		 * 用户必须在授权状态，才能在第三方查询到用户的行业
		 */
		MemberCredit memberCredit = memberCreditDao.getByAccount(account);
		if(memberCredit == null || StringUtils.isBlank(memberCredit.getZmopenId()) 
				|| memberCredit.getAuthStatus()==AuthStatusEnu.NOT_AUTH.getCode() 
				|| memberCredit.getAuthStatus()==AuthStatusEnu.CANCEL_AUTH.getCode()
				|| memberCredit.getAuthStatus()==AuthStatusEnu.UNBIND.getCode()){
			return CreditStatus.NOT_AUTH.getCode();
		}
		int queryResult = this.zhimaAuthInfoAuthquery(memberCredit);
		if(queryResult>0 && queryResult==AuthStatusEnu.NOT_AUTH.getCode()){
			return CreditStatus.NOT_AUTH.getCode();
		}

		//查看芝麻信用分,如果得知用户取消授权，则直接返回状态
		if(memberCredit.getLevel()>0){
			if(memberCredit.getAuthStatus()==AuthStatusEnu.CANCEL_AUTH.getCode()) return CreditStatus.NOT_AUTH.getCode();
			PaySystemConfig config = paySystemConfigService.get(ZMSCORE_LIMIT_KEY);
			int zmScoreLimit = Integer.parseInt(config.getConfigValue());
			logger.info("[用户{}芝麻信用分={}当前信用分限制{}]", new Object[]{account,memberCredit.getZmScore(),zmScoreLimit});
			if(memberCredit.getZmScore() < zmScoreLimit) return CreditStatus.HAD_BAD_RECORD.getCode();
		}
		
		//查看行业关注名单,如果得知用户取消授权，则直接返回状态
//		memberCredit = this.getZhimaCreditWatchlist(memberCredit);
		if(memberCredit.getAuthStatus()==AuthStatusEnu.CANCEL_AUTH.getCode()) return CreditStatus.NOT_AUTH.getCode();
		
		if(StringUtils.isBlank(memberCredit.getZmDetails()) || memberCredit.getZmDetails().equals("NO_BAD_RECORD")){
			return CreditStatus.NO_BAD_RECORD.getCode();
		}else{
			return CreditStatus.HAD_BAD_RECORD.getCode();
		}
	}

	@Override
	public MemberCreditVo getMemberCreditStatus(String account) {
		/**
		 * memberCredit为空说明用户未授权；ZmopenId为空说明用户未授权;authStatus 为NOT_AUTH，说明未授权；CANCEL_AUTH 说明用户已取消授权
		 * 用户必须在授权状态，才能在第三方查询到用户的行业
		 */
		PaySystemConfig config = paySystemConfigService.get(ZMSCORE_LIMIT_KEY);
		int zmScoreLimit = Integer.parseInt(config.getConfigValue());
		MemberCreditVo creditVo = null;
		MemberCredit memberCredit = memberCreditDao.getByAccount(account);
		//没有过授权记录的用户
		if(memberCredit == null){
			return new MemberCreditVo(null, null, null, 
					CreditStatus.NOT_AUTH.getCode(), "0", config.getConfigValue());
		}
		if(StringUtils.isBlank(memberCredit.getZmopenId()) 
				|| memberCredit.getAuthStatus()==AuthStatusEnu.NOT_AUTH.getCode() 
				|| memberCredit.getAuthStatus()==AuthStatusEnu.CANCEL_AUTH.getCode()
				|| memberCredit.getAuthStatus()==AuthStatusEnu.UNBIND.getCode()){
			return new MemberCreditVo(memberCredit.getId(), null, null, 
					CreditStatus.NOT_AUTH.getCode(), "0", config.getConfigValue());
		}
		//查看芝麻信用分,如果得知用户取消授权，则直接返回状态
//		memberCredit = this.getZhimaCreditScore(memberCredit);
		int queryResult = this.zhimaAuthInfoAuthquery(memberCredit);
		if(queryResult>0 && queryResult!=AuthStatusEnu.AUTH.getCode()){
			return new MemberCreditVo(memberCredit.getId(), null, null, 
					CreditStatus.NOT_AUTH.getCode(), "0", config.getConfigValue());
		}
		if(memberCredit.getLevel()>0){
/*			memberCredit = this.getZhimaCreditScore(memberCredit);
			if(memberCredit.getAuthStatus()==AuthStatusEnu.CANCEL_AUTH.getCode()){
				return new MemberCreditVo(memberCredit.getId(), null, null, 
						CreditStatus.NOT_AUTH.getCode(), String.valueOf(memberCredit.getZmScore()), config.getConfigValue());
			} */
			logger.info("[用户{}芝麻信用分={}当前信用分限制{}]", new Object[]{account,memberCredit.getZmScore(),zmScoreLimit});
			if(memberCredit.getZmScore() < zmScoreLimit) {
				return new MemberCreditVo(memberCredit.getId(), memberCredit.getCardId(), memberCredit.getName(), 
						CreditStatus.ZMSCORE_NOT_ENOUGH.getCode(), String.valueOf(memberCredit.getZmScore()), config.getConfigValue());
			}
		}
		//查看行业关注名单,如果得知用户取消授权，则直接返回状态
//		memberCredit = this.getZhimaCreditWatchlist(memberCredit);
//		if(memberCredit.getAuthStatus()==AuthStatusEnu.CANCEL_AUTH.getCode()){
//			return CreditStatus.NOT_AUTH.getCode();
//		} 
		if(StringUtils.isBlank(memberCredit.getZmDetails()) || memberCredit.getZmDetails().equals("NO_BAD_RECORD")){
			//查看行业关注名单
			creditVo = new MemberCreditVo(memberCredit.getId(), memberCredit.getCardId(), memberCredit.getName(), 
					CreditStatus.NO_BAD_RECORD.getCode(), String.valueOf(memberCredit.getZmScore()), config.getConfigValue());
		}else{
			creditVo = new MemberCreditVo(memberCredit.getId(), memberCredit.getCardId(), memberCredit.getName(), 
					CreditStatus.HAD_BAD_RECORD.getCode(), String.valueOf(memberCredit.getZmScore()), config.getConfigValue());
		}
		return creditVo;
	}
	
	@Override
	public MemberCredit getZhimaCreditWatchlist(MemberCredit memberCredit) {
		logger.info("[行业关注名单查询用户][id={}][account={}][zmopenid={}]", new Object[]{memberCredit.getId(), memberCredit.getAccount(), memberCredit.getZmopenId()});
		//同一个用户，在当天只会存在一个transactionId同一个transactionId在芝麻信用那边不会产生费用
		String transactionId = "2"+ZmTableUtil.genTransactionId(memberCredit.getAccount());
		ZhimaCreditWatchlistiiGetRequest req = new ZhimaCreditWatchlistiiGetRequest();
		req.setChannel("apppc");
		req.setPlatform("zmop");
		req.setProductCode(ZmxyClient.CREDIT_WATCH_LIST_PRODUCT_CODE);// 必要参数
		req.setTransactionId(transactionId);// 必要参数
		req.setOpenId(memberCredit.getZmopenId());// 必要参数
		try {
			ZhimaCreditWatchlistiiGetResponse response = ZmxyClient.zhimaClient.execute(req);
			logger.info("[行业名单查询结果]"+JsonUtil.toJson(response));
			//ZMCREDIT.authentication_fail 获取行业名单信息失败，说明用户已经取消授权了
			if(StringUtils.isNotBlank(response.getErrorCode()) && response.getErrorCode().equals("ZMCREDIT.authentication_fail")){
				memberCredit.setAuthStatus(AuthStatusEnu.CANCEL_AUTH.getCode());
				//记录用户授权行为
				authRecor(AuthStatusEnu.CANCEL_AUTH, memberCredit);
				memberCreditDao.update(memberCredit);
				return memberCredit;
			}
			CheckUtil.assertTrue(response.isSuccess(), ErrorCode.UNKOWN.getErrorCode(), "获取芝麻信用失败");
			if(response.getIsMatched()){
				memberCredit.setTransactionId(transactionId).setZmDetails(JsonUtil.toJson(response.getDetails())).setLastModifyTime(new Date());
//				memberCreditDao.update(memberCredit);
			}else{
				memberCredit.setZmDetails("NO_BAD_RECORD");
			}
			memberCreditDao.update(memberCredit);
		} catch (ZhimaApiException e) {
    		logger.info("[芝麻信用行业关注名单][transactionId={}][id={}][account={}][zmopenid={}]{}", new Object[]{transactionId,memberCredit.getId(), memberCredit.getAccount(), memberCredit.getZmopenId(),e});

		}
		return memberCredit;
	}
	
	/**
	 * 记录授权和取消授权的行为到authRecord字段
	 * @param authStatus
	 * @param memberCredit
	 */
	private void authRecor(AuthStatusEnu authStatus,MemberCredit memberCredit){
		logger.info("记录用户授权或取消授权行为{},{}",new Object[]{authStatus,memberCredit});
		List<AuthRecordVo> records = new ArrayList<AuthRecordVo>();
		if(StringUtils.isNotBlank(memberCredit.getAuthRecord())){
			records = JsonUtil.fromJson(memberCredit.getAuthRecord(), new TypeToken<List<AuthRecordVo>>(){} );
		}
		AuthRecordVo authRecordVo ;
		switch (authStatus) {
		case AUTH://如果获知用户当前状态为授权
			authRecordVo = new AuthRecordVo();
			authRecordVo.setAuthStatus(authStatus.getKey())
			.setRecordTime(DateTimeUtil.getTime());
			records.add(authRecordVo);
			memberCredit.setAuthRecord(JsonUtil.toJson(records)).setAuthChangeTime(new Date());
			break;
		case CANCEL_AUTH://如果获知用户当前状态为取消授权,
			if(records.size()>0 && 
					!(AuthStatusEnu.CANCEL_AUTH.getKey() ).equals(records.get(records.size()-1).getAuthStatus()) ){
				authRecordVo = new AuthRecordVo();
				authRecordVo.setAuthStatus(authStatus.getKey())
				.setRecordTime(DateTimeUtil.getTime());
				records.add(authRecordVo);
				memberCredit.setAuthRecord(JsonUtil.toJson(records)).setAuthChangeTime(new Date());
			}
			break;
		default:
			break;
		}
	}
	
	@Override
	public MemberCredit getZhimaCreditScore(MemberCredit memberCredit) {
		logger.info("[芝麻信用分查询][id={}][account={}][zmopenid={}]", new Object[]{memberCredit.getId(), memberCredit.getAccount(), memberCredit.getZmopenId()});
		//同一个用户，在当天只会存在一个transactionId。同一个transactionId在芝麻信用那边不会产生费用
		String transactionId = "1"+ZmTableUtil.genTransactionId(memberCredit.getAccount());
		ZhimaCreditScoreGetRequest req = new ZhimaCreditScoreGetRequest();
        req.setChannel("apppc");
        req.setPlatform("zmop");
        req.setTransactionId(transactionId);// 必要参数 
        req.setProductCode(ZmxyClient.CREDIT_SCORE_PRODUCT_CODE);// 必要参数 
        req.setOpenId(memberCredit.getZmopenId());// 必要参数 
        try {
            ZhimaCreditScoreGetResponse response = ZmxyClient.zhimaClient.execute(req);
			logger.info("[芝麻信用分查询结果]"+JsonUtil.toJson(response));
			//ZMCREDIT.authentication_fail 获取行业名单信息失败，说明用户已经取消授权了
			if(StringUtils.isNotBlank(response.getErrorCode()) && response.getErrorCode().equals("ZMCREDIT.authentication_fail")){
				//记录用户授权行为
				authRecor(AuthStatusEnu.CANCEL_AUTH, memberCredit);
				memberCredit.setAuthStatus(AuthStatusEnu.CANCEL_AUTH.getCode());
				memberCreditDao.update(memberCredit);
				return memberCredit;
			}
            if(response.isSuccess()){
            	memberCredit.setZmScore(Integer.parseInt(response.getZmScore())).setLastModifyTime(new Date());
            	memberCreditDao.update(memberCredit);
            } 
        } catch (ZhimaApiException e) {
    		logger.info("[查询芝麻信用分异常][transactionId={}][id={}][account={}][zmopenid={}]{}", new Object[]{transactionId,memberCredit.getId(), memberCredit.getAccount(), memberCredit.getZmopenId(),e});

        }
		return memberCredit;
	}	
	
	@Override
	public ZmAuthCallBackVo paseParam(String sign, String params) {
		String result = "";
		ZmAuthCallBackVo authCallBackVo = null;
			try {
				if(params.indexOf("%") != -1) params = URLDecoder.decode(params, "UTF-8");
				if(sign.indexOf("%") != -1) sign = URLDecoder.decode(sign, "UTF-8");
				result = ZmxyClient.zhimaClient.decryptAndVerifySign(params, sign);
				result = URLDecoder.decode(URLDecoder.decode(result,"UTF-8"),"UTF-8");//decode两次
				String[] resultArray = result.split("&");
				Map<String, String> resultMap = new HashMap<String, String>();
				for(String resultStr : resultArray){
					String[] array = resultStr.split("=");
					resultMap.put(array[0], array[1]);
				}
				JsonConverter converter = new JsonConverter();
				authCallBackVo = converter.fromJson(resultMap, ZmAuthCallBackVo.class);
				String state = authCallBackVo.getState();
				//反序列化得到授权请求时透传给芝麻信用的信息
				ZmAuthStateVo authStateVo = JsonUtil.fromJson(state, ZmAuthStateVo.class);
				authCallBackVo.setAuthStateVo(authStateVo);
				authCallBackVo.setDetails(result);
			} catch (UnsupportedEncodingException e) {
				logger.error("[芝麻信用授权结果回调异常]", e);
				throw new ErrorCodeException(ErrorCode.UNKOWN.getErrorCode(), ErrorCode.UNKOWN.getMessage());
			} catch (ZhimaApiException e) {
				logger.error("[芝麻信用授权结果回调异常]", e);
				throw new ErrorCodeException(ErrorCode.UNKOWN.getErrorCode(), ErrorCode.UNKOWN.getMessage());
			} catch (AlipayApiException e) {
				logger.error("[芝麻信用授权结果回调异常]", e);
				throw new ErrorCodeException(ErrorCode.UNKOWN.getErrorCode(), ErrorCode.UNKOWN.getMessage());
			}
		logger.info("[芝麻信用授权结果]"+result);
		return authCallBackVo;
	}

	@Override
	public boolean queryZmAuth(String name, String cardId, String account) {
		ZhimaAuthInfoAuthqueryRequest req = new ZhimaAuthInfoAuthqueryRequest();
		req.setChannel("apppc");
        req.setPlatform("zmop");
        req.setIdentityType("2");// 必要参数 
        req.setIdentityParam("{\"certNo\":\""+cardId+"\",\"name\":\""+name+"\",\"certType\":\"IDENTITY_CARD\"}");// 必要参数 
        req.setAuthCategory("C2B");// 必要参数 
        try {
        	ZhimaAuthInfoAuthqueryResponse response = ZmxyClient.zhimaClient.execute(req);
        	logger.info("[主动查询芝麻信用授权结果]"+JsonUtil.toJson(response));
        	if(response.getAuthorized()){
        		MemberCredit memberCredit = memberCreditDao.getByAccount(account);
        		CheckUtil.assertNotNull(memberCredit, account+"用户授权信息不存在");
        		authRecor(AuthStatusEnu.AUTH, memberCredit);
        		memberCredit
        		.setName(name)
        		.setCardId(cardId)
        		.setZmopenId(response.getOpenId())
        		.setAuthStatus(AuthStatusEnu.AUTH.getCode())
//        		.setAuthResult(zmAuthCallBackVo.getDetails())
        		.setOperation("SYSTEM")
        		.setAuthChangeTime(new Date());
        		return memberCreditDao.update(memberCredit);
        	}else{
        		MemberCredit memberCredit = memberCreditDao.getByAccount(account);
        		CheckUtil.assertNotNull(memberCredit, account+"用户授权信息不存在");
        		authRecor(AuthStatusEnu.CANCEL_AUTH, memberCredit);
        		memberCredit
        		.setName(name)
        		.setCardId(cardId)
        		.setZmopenId(response.getOpenId())
        		.setAuthStatus(AuthStatusEnu.AUTH.getCode())
//        		.setAuthResult(zmAuthCallBackVo.getDetails())
        		.setOperation("SYSTEM")
        		.setAuthChangeTime(new Date());
        		return memberCreditDao.update(memberCredit);
        	}
        } catch (ZhimaApiException e) {
        	logger.error("[主动查询芝麻信用授权]error"+e);
        }
		return false;
	}

	@Override
	public Integer zhimaAuthInfoAuthquery(MemberCredit memberCredit) {
        ZhimaAuthInfoAuthqueryRequest req = new ZhimaAuthInfoAuthqueryRequest();
        req.setChannel("apppc");
        req.setPlatform("zmop");
        req.setIdentityType("0");// 必要参数 
        req.setIdentityParam("{\"openId\":\""+memberCredit.getZmopenId()+"\"}");// 必要参数 
        req.setAuthCategory("C2B");// 必要参数 
        AuthStatusEnu authStatus = null;
        try {
        	ZhimaAuthInfoAuthqueryResponse response = ZmxyClient.zhimaClient.execute(req);
        	logger.info("[主动查询芝麻信用授权结果]"+JsonUtil.toJson(response));
        	if(response.isSuccess()){
        		authStatus = response.getAuthorized() ? AuthStatusEnu.AUTH : AuthStatusEnu.CANCEL_AUTH;
//        		memberCredit.setAuthStatus(authStatus);
        	}else{
        		logger.error("[主动查询芝麻信用授权]error"+JsonUtil.toJson(response));
        	}
        	if(authStatus.getCode()>0 && authStatus.getCode()!=memberCredit.getAuthStatus()){
        		memberCredit.setAuthStatus(authStatus.getCode());
        		authRecor(authStatus, memberCredit);
        		memberCreditDao.update(memberCredit);
        	}
        } catch (ZhimaApiException e) {
        	logger.error("[主动查询芝麻信用授权]error"+e);
        }
        return authStatus !=null ? authStatus.getCode() : memberCredit.getAuthStatus();
	}

}
