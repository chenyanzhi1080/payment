package junit.zmAntifraud;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.antgroup.zmxy.openplatform.api.DefaultZhimaClient;
import com.antgroup.zmxy.openplatform.api.ZhimaApiException;
import com.antgroup.zmxy.openplatform.api.request.ZhimaCreditAntifraudRiskListRequest;
import com.antgroup.zmxy.openplatform.api.request.ZhimaCreditAntifraudScoreGetRequest;
import com.antgroup.zmxy.openplatform.api.request.ZhimaCreditAntifraudVerifyRequest;
import com.antgroup.zmxy.openplatform.api.response.ZhimaCreditAntifraudRiskListResponse;
import com.antgroup.zmxy.openplatform.api.response.ZhimaCreditAntifraudScoreGetResponse;
import com.antgroup.zmxy.openplatform.api.response.ZhimaCreditAntifraudVerifyResponse;
import com.google.gson.reflect.TypeToken;
import com.xiaoerzuche.biz.zmxy.config.ZmxyClient;
import com.xiaoerzuche.biz.zmxy.enu.AntifraudVerifyEnu;
import com.xiaoerzuche.biz.zmxy.model.MemberAntifraud;
import com.xiaoerzuche.biz.zmxy.model.MemberCredit;
import com.xiaoerzuche.biz.zmxy.service.MemberCreditService;
import com.xiaoerzuche.biz.zmxy.service.ZhiMaAntifraudService;
import com.xiaoerzuche.biz.zmxy.vo.antifraud.MemberAntifraudResultVo;
import com.xiaoerzuche.common.util.JsonUtil;

import junit.BaseTest;

public class AntifraudTest extends BaseTest{
	private static final Logger logger = LoggerFactory.getLogger(AntifraudTest.class);
	@Autowired
	private ZhiMaAntifraudService antifraudService;
	@Autowired
	private MemberCreditService memberCreditService;
	@Test
	public void get(){
		MemberCredit credit = memberCreditService.get("");
		System.out.println(credit);
	}
	
	public static void main(String[] args){
		String gatewayUrl = "https://zmopenapi.zmxy.com.cn/openapi.do";
		String appId = "1002714";
		String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALB1K2XeO+8767fUvVbsOasf/pgXBRn+9Ma0dbrbFle49ZWThDOEdiW/vuFybdyvI/nksHB9RtRg8q7o+cXi0Iqy0papV9Yf2YAZkviZ17J3sFlOYgVnMf9dZgJo7lKIbhPlXq1gAfnmX4w5mW1P5B8X2URItw9bnvPaelf6dI5DAgMBAAECgYB/6B7SE3ocWrHtqe6pIEhZC7MPabhP9KhXlJ22GXMjmTWi+7Aba8v8ZvYlEz4hdm68iBGBKL372l5vfP74ewWwH/h5GJvQMQV+OXcvSKyUkPaSzD5lsDAbI7804CNmi3c2QtcaPv4vBcDc8hugI17S6FdMZ5erWWBszcvonEAbOQJBANUyQNnPvqBLD/yGFTnoxVbz8xr+P9JUhLPGV4MwdOeYDAkzZyNaatriAkjM8/HPimGcqN1O8/x61ZWPa8dT+VUCQQDT4qU0eiK2ph2PoIeG6BeDmv6jQcorcW4II0ssJA6pozAWg0T0MAWfap64bBu8Ou1HFCNGRBO2uQopeThNDgk3AkEAireh/pbvf7iXdEWB9iYAkO011vBrcl1P4vFA67lRt4b0/d+WUih4smav+dJxP3s8nPqj3SuMEVGTOzcQd6ep6QJBAKnUYthCAOrnEJCbeaV/B8HbjLfsNpBFlYz+RPV3XS1I+HVC+BTFHOye9+MX+88OaDrtgCyEykxN2kBNwfu5AVcCQCsH0/XdHkL/HWoly+khflIKfr5ekIbmcZrIYUUjmlAF5AsDV3Q9jKxY1Gn0j8PmmAeFJ8FpivBJUXNwIS8FfUY=";
		String zhimaPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC2EMhB6nNk+X8oNhZ6DfHTV2kJrdQkmFFq5NwCXVrgaiTQgcO27zpR7V8G1Pl16HycYPQah5UsjOzDIpbQ4rDsH/bjPIg1NOxsNY4/7Yml+PxJYRUaWkrlwXJy63mS818gXL11gcvXh4bbVvreiDhrlsADRyi17+JwKMpVHWA8twIDAQAB";
		DefaultZhimaClient zhimaClient = new DefaultZhimaClient(gatewayUrl, appId, privateKey, zhimaPublicKey);
		
		MemberAntifraud memberAntifraud = new MemberAntifraud();
//		memberAntifraud.setAccount("18888888888")
		memberAntifraud
//		.setAccount("18191550824")
		.setName("钟焕亮")
		.setIdCard("460036198707204129");
		//申请欺诈分
		String asTransactionId = "as" +System.currentTimeMillis();
		ZhimaCreditAntifraudScoreGetRequest req = new ZhimaCreditAntifraudScoreGetRequest();
        req.setChannel("apppc");
        req.setPlatform("zmop");
        req.setProductCode(ZmxyClient.CREDIT_ANTIFRAUD_SCORE_CODE);
        req.setTransactionId(String.valueOf(System.currentTimeMillis()));
        req.setCertType("IDENTITY_CARD");
        req.setCertNo(memberAntifraud.getIdCard());
        req.setName(memberAntifraud.getName());
        req.setMobile(memberAntifraud.getAccount());
        try {
			ZhimaCreditAntifraudScoreGetResponse response = zhimaClient.execute(req);
			if(response.isSuccess()){
				System.out.println(response.getBody());
				memberAntifraud.setAntifraudScore(response.getScore().intValue());
			}else{
				memberAntifraud.setErrorMessage(response.getErrorMessage());
			}
        } catch (ZhimaApiException e) {
        	logger.error("[反欺诈分查询][req={}][error]{}",new Object[]{JsonUtil.toJson(req),e});
        	memberAntifraud.setErrorMessage("系统异常");
		}
		//欺诈信息校验
        ZhimaCreditAntifraudVerifyRequest reqVerify = new ZhimaCreditAntifraudVerifyRequest();
        reqVerify.setChannel("apppc");
        reqVerify.setPlatform("zmop");
        reqVerify.setProductCode("w1010100000000002859");
        reqVerify.setTransactionId(String.valueOf(System.currentTimeMillis()));
        reqVerify.setCertType("IDENTITY_CARD");
        reqVerify.setCertNo(memberAntifraud.getIdCard());
        reqVerify.setName(memberAntifraud.getName());
        reqVerify.setMobile(memberAntifraud.getAccount());
        try {
            ZhimaCreditAntifraudVerifyResponse response = zhimaClient.execute(reqVerify);
            if(response.isSuccess()){
            	System.out.println(response.getBody());
            	memberAntifraud.setAntifraudVerify(JsonUtil.toJson(response.getVerifyCode()));
			}else{
				memberAntifraud.setErrorMessage(response.getErrorMessage());
			}
        } catch (ZhimaApiException e) {
        	logger.error("[反欺诈信息校验][req={}][error]{}",new Object[]{JsonUtil.toJson(req),e});
        	memberAntifraud.setErrorMessage("系统异常");
        }
        //欺诈关注清单
        ZhimaCreditAntifraudRiskListRequest reqRiskLis = new ZhimaCreditAntifraudRiskListRequest();
        reqRiskLis.setChannel("apppc");
        reqRiskLis.setPlatform("zmop");
        reqRiskLis.setProductCode("w1010100003000001283");
        reqRiskLis.setTransactionId(String.valueOf(System.currentTimeMillis()));//TODO
        reqRiskLis.setCertType("IDENTITY_CARD");
        reqRiskLis.setCertNo(memberAntifraud.getIdCard());
        reqRiskLis.setName(memberAntifraud.getName());
        reqRiskLis.setMobile(memberAntifraud.getAccount());
        try {
            ZhimaCreditAntifraudRiskListResponse response = zhimaClient.execute(reqRiskLis);
            if(response.isSuccess()){
            	System.out.println(response.getBody());
            	memberAntifraud.setHit("yes".equals(response.getHit())? 1:2)
            	.setAntifraudRisk(JsonUtil.toJson(response.getRiskCode()));
            }else{
				memberAntifraud.setErrorMessage(response.getErrorCode()+" "+response.getErrorMessage());
			}
        } catch (ZhimaApiException e) {
        	logger.error("[反欺诈信息校验][req={}][error]{}",new Object[]{JsonUtil.toJson(req),e});
        	memberAntifraud.setErrorMessage("系统异常");
        }
        System.out.println(JsonUtil.toJson(memberAntifraud));
        

        MemberAntifraudResultVo antifraudResultVo = new MemberAntifraudResultVo();
		String antifraudScoreRisk = "";
		int score = memberAntifraud.getAntifraudScore();
		if(0==score){
			antifraudScoreRisk = score+"分；无法识别";
		}
		if(0<score && score<=40){
			antifraudScoreRisk = score+"分；极高风险";
		}
		if(40<score && score<80){
			antifraudScoreRisk = score+"分；中等风险";
		}
		if(80<=score && score<=100){
			antifraudScoreRisk = score+"分；信用良好";
		}
		antifraudResultVo.setHit(memberAntifraud.getHit());
		antifraudResultVo.setScore(antifraudScoreRisk);
		System.out.println(memberAntifraud.getAntifraudVerify());
		List<String> verifyResults = new ArrayList<String>();
		//解读Verify Code
		List<String> verifyCodeList = JsonUtil.fromJson(memberAntifraud.getAntifraudVerify(), new TypeToken<List<String>>(){});
		for(String code : verifyCodeList){
			String verifyResult = AntifraudVerifyEnu.valueOf(code).getVerifyResult();
			verifyResults.add(verifyResult);
		}
		antifraudResultVo.setVerifyResults(verifyResults);
		System.out.println(JsonUtil.toJson(antifraudResultVo));
	}
}
