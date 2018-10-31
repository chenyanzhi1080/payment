package junit.zmxy;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.antgroup.zmxy.openplatform.api.DefaultZhimaClient;
import com.antgroup.zmxy.openplatform.api.ZhimaApiException;
import com.antgroup.zmxy.openplatform.api.request.ZhimaAuthInfoAuthqueryRequest;
import com.antgroup.zmxy.openplatform.api.request.ZhimaCreditScoreGetRequest;
import com.antgroup.zmxy.openplatform.api.response.ZhimaAuthInfoAuthqueryResponse;
import com.antgroup.zmxy.openplatform.api.response.ZhimaCreditScoreGetResponse;
import com.xiaoerzuche.biz.zmxy.config.ZmxyClient;
import com.xiaoerzuche.biz.zmxy.dao.MemberCreditDao;
import com.xiaoerzuche.biz.zmxy.dao.sqlbuilder.MemberCreditBuilder;
import com.xiaoerzuche.biz.zmxy.model.MemberCredit;
import com.xiaoerzuche.common.util.JsonUtil;

import junit.BaseTest;

/** 
* 芝麻信用分test
* @author: cyz
* @version: payment-web
* @time: 2017年7月17日
* 
*/
public class ZhimaCreditScoreTest extends BaseTest {
	private static final Logger logger = LoggerFactory.getLogger(ZhimaCreditScoreTest.class);
	@Autowired
	private MemberCreditDao memberCreditDao;
	@Test
	public void testZhimaAuthInfoAuthquery(){
		ZhimaAuthInfoAuthqueryRequest req = new ZhimaAuthInfoAuthqueryRequest();
        req.setChannel("apppc");
        req.setPlatform("zmop");
        req.setIdentityType("2");// 必要参数 
        req.setIdentityParam("{\"certNo\":\"152103199201241514\",\"name\":\"王天航\",\"certType\":\"IDENTITY_CARD\"}");// 必要参数 
        req.setAuthCategory("C2B");// 必要参数 
        try {
            ZhimaAuthInfoAuthqueryResponse response =(ZhimaAuthInfoAuthqueryResponse)ZmxyClient.zhimaClient.execute(req);
            System.out.println(response.isSuccess());
            System.out.println(response.getErrorCode());
            System.out.println(response.getErrorMessage());
            System.out.println("Authorized:"+response.getAuthorized());
            System.out.println("OpenId:"+response.getOpenId());
            System.out.println(JsonUtil.toJson(response));
        } catch (ZhimaApiException e) {
            e.printStackTrace();
        }
	}
	
//	@Test
	public void zhimaCreditScore(){
		for(int i=0;i<135;i++){
			MemberCreditBuilder builder= new MemberCreditBuilder();
			builder.setHasOpenid(true);
			builder.limit(100);
			builder.offset(i+1);
			List<MemberCredit> list = memberCreditDao.list(builder);
			MemberCredit memberCredit= null;
			for(MemberCredit credit : list){
				System.out.println("openid"+credit.getZmopenId());
				if(StringUtils.isNotBlank(credit.getZmopenId())){
					int zmScore = this.getZmScore(credit.getZmopenId());
					memberCredit = new MemberCredit();
					memberCredit.setId(credit.getId());
					memberCredit.setZmScore(zmScore);
					memberCredit.setLastModifyTime(new Date());
					memberCredit.setOperation("SYSTEM");
					memberCreditDao.update(memberCredit);
				}
			}
		}
	}
	
	
	/**
	 *      {
                "bizNo": "ZM201707173000000023000425957700",
                "zmScore": "685",
                "success": true,
                "body": "{\"success\":true,\"biz_no\":\"ZM201707173000000023000425957700\",\"zm_score\":\"685\"}",
                "params": {
                    "params": "QHzYufYKm53LUm1WLjYrwziu1UThxJNPD/v6Sc/v3ypQP1AaRGIwXpL4X5kpdWHdmIX0McNVsD24CVz+8NmsR2XvoxvwF3+DIU2jKEQkWu5msMaZvFKYu2OahCWbuEDLYkRKnn09j849MD8Wy+fVn4dLavM4EZLbI2hktR2zOOs="
                }
            }
	 * @param openId
	 * @return
	 */
	public int getZmScore(String openId){
		ZhimaCreditScoreGetRequest req = new ZhimaCreditScoreGetRequest();
        req.setChannel("apppc");
        req.setPlatform("zmop");
//        req.setTransactionId("201512100936588040000000465159");// 必要参数 
        req.setTransactionId(System.currentTimeMillis()+"");// 必要参数 
        req.setProductCode(ZmxyClient.CREDIT_SCORE_PRODUCT_CODE);// 必要参数 
        req.setOpenId(openId);// 必要参数 
        try {
            ZhimaCreditScoreGetResponse response = ZmxyClient.zhimaClient.execute(req);
            if(response.isSuccess()){
            	System.out.println("ZmScore="+response.getZmScore());
            	return Integer.parseInt(response.getZmScore());
            }else{
            	System.out.println("======="+response.getErrorCode());
                System.out.println("======="+response.getErrorMessage());
            }
        } catch (ZhimaApiException e) {
        	logger.error("===========================", e);
        }
        return 0;
	}

	//芝麻开放平台地址
    private String gatewayUrl     = "https://zmopenapi.zmxy.com.cn/openapi.do";
    //商户应用 Id
    private String appId          = "1002716";
    //商户 RSA 私钥
    private String privateKey     = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBANgbUVGqtKyKb/V2hM36/7LzsKvoGt/YOd32zREY/uhujnNdnIKfclz+iVkG+nqguNcGudTooKA4/w9Mb5MNtbCM5ZPinM8VNJdXfgGdEKHb2J9uz2SfDv+9Q3wpEPiRAlLBMNAs5srAkrtWgUJYwUbzeF6D1I3Ye+RO7CqO2GCJAgMBAAECgYB085u3kaWDdswOUByhCIQWyXDEJ7FbkL4IVIc4CInzKdmnr3KC8l1G3G1iI7esY4yEudwZz+cVrqLCGcCMGbBII23hBXJtXWK3us4TTawdLxPhKoWoj2v8wYUffltk3WRXFKaqgBaUKDTjjPRBOluFhVfQ5fBeFl/lSVUBeGNj3QJBAPq63h5mUfDbP2A5PzwEqLpE5BBm+BySxflxUGyEchKVmWEf9Qo0EhKlBEggNLU+Rs8DQycW7X/BRjyA5MvKwTcCQQDcpiYMBXGsiYXVFe2jZDMVOgZuzvRjNojoCNFr9gWPh6L0zvvQJxwJUoBd30IfmQfcAhgJW1MUe63c/U0DXcw/AkBH8J4SsuEH7qB8h3BxVBTlfSbC3giKikJrk+uzdz0TwADGMVkpmE8nMlnMKBF3f2rpstHtwVJbuaThb0o2fvJ5AkBlpYjbetqOIGBt9IcgATXjLnLuMxq0ZdgWBJHtSU3wfAye+Idasdmhlcca95GvmsDbIn/ON6sDQaKrrmj/BwPjAkEArNJRRP0WWrRRVylJl6AUr8Uli1+noKwogPhBI4fEYHnmwxiT/O/JPzpAGRHsehhLZeQg0SULE0KTHJHxS98I/Q==";
    //芝麻 RSA 公钥
    private String zhimaPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCWzt2zjsK8yLA4dv0K95srqolOKrm2IJY4gxqY2LVwSsTf7CuHVEmXP88sickeyKFihkdQLeEfhxdbTIwSCgbV7JXIAhOhwfcGrEioq2sw57Ta7zzI9XX+ekzZb8mJ4Xkp0rai9/or3HOX+/gsnFjG9HYi8xgaVxasJz4L4LebCQIDAQAB";

	 public void  testZhimaCreditScoreGet() {
	        ZhimaCreditScoreGetRequest req = new ZhimaCreditScoreGetRequest();
	        req.setChannel("apppc");
	        req.setPlatform("zmop");
	        req.setTransactionId(System.currentTimeMillis()+"18289767660");// 必要参数 
	        req.setProductCode("w1010100100000000001");// 必要参数 
	        req.setOpenId("268812842580063596657973302");// 必要参数 
	        DefaultZhimaClient client = new DefaultZhimaClient(gatewayUrl, appId, privateKey, zhimaPublicKey);
	        try {
	            ZhimaCreditScoreGetResponse response =(ZhimaCreditScoreGetResponse)client.execute(req);
	            System.out.println(response.isSuccess());
	            System.out.println(response.getErrorCode());
	            System.out.println(response.getErrorMessage());
	            System.out.println(JsonUtil.toJson(response));
	        } catch (ZhimaApiException e) {
	            e.printStackTrace();
	        }
	    }

	public static void main(String[] args){
		ZhimaCreditScoreTest result = new  ZhimaCreditScoreTest();
        result.testZhimaCreditScoreGet();
	}
}
