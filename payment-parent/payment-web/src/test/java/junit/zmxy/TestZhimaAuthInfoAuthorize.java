package junit.zmxy;

import java.io.UnsupportedEncodingException;

import com.antgroup.zmxy.openplatform.api.DefaultZhimaClient;
import com.antgroup.zmxy.openplatform.api.ZhimaApiException;
import com.antgroup.zmxy.openplatform.api.request.ZhimaCreditWatchlistiiGetRequest;
import com.antgroup.zmxy.openplatform.api.response.ZhimaCreditWatchlistiiGetResponse;
import com.xiaoerzuche.biz.zmxy.config.ZmxyClient;
import com.xiaoerzuche.common.util.JsonUtil;

public class TestZhimaAuthInfoAuthorize {
    //芝麻开放平台地址
    private String gatewayUrl     = "https://zmopenapi.zmxy.com.cn/openapi.do";
    //商户应用 Id
    private String appId          = "1002716";
    //商户 RSA 私钥
    private String privateKey     = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBANgbUVGqtKyKb/V2hM36/7LzsKvoGt/YOd32zREY/uhujnNdnIKfclz+iVkG+nqguNcGudTooKA4/w9Mb5MNtbCM5ZPinM8VNJdXfgGdEKHb2J9uz2SfDv+9Q3wpEPiRAlLBMNAs5srAkrtWgUJYwUbzeF6D1I3Ye+RO7CqO2GCJAgMBAAECgYB085u3kaWDdswOUByhCIQWyXDEJ7FbkL4IVIc4CInzKdmnr3KC8l1G3G1iI7esY4yEudwZz+cVrqLCGcCMGbBII23hBXJtXWK3us4TTawdLxPhKoWoj2v8wYUffltk3WRXFKaqgBaUKDTjjPRBOluFhVfQ5fBeFl/lSVUBeGNj3QJBAPq63h5mUfDbP2A5PzwEqLpE5BBm+BySxflxUGyEchKVmWEf9Qo0EhKlBEggNLU+Rs8DQycW7X/BRjyA5MvKwTcCQQDcpiYMBXGsiYXVFe2jZDMVOgZuzvRjNojoCNFr9gWPh6L0zvvQJxwJUoBd30IfmQfcAhgJW1MUe63c/U0DXcw/AkBH8J4SsuEH7qB8h3BxVBTlfSbC3giKikJrk+uzdz0TwADGMVkpmE8nMlnMKBF3f2rpstHtwVJbuaThb0o2fvJ5AkBlpYjbetqOIGBt9IcgATXjLnLuMxq0ZdgWBJHtSU3wfAye+Idasdmhlcca95GvmsDbIn/ON6sDQaKrrmj/BwPjAkEArNJRRP0WWrRRVylJl6AUr8Uli1+noKwogPhBI4fEYHnmwxiT/O/JPzpAGRHsehhLZeQg0SULE0KTHJHxS98I/Q==";
    //芝麻 RSA 公钥
    private String zhimaPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCWzt2zjsK8yLA4dv0K95srqolOKrm2IJY4gxqY2LVwSsTf7CuHVEmXP88sickeyKFihkdQLeEfhxdbTIwSCgbV7JXIAhOhwfcGrEioq2sw57Ta7zzI9XX+ekzZb8mJ4Xkp0rai9/or3HOX+/gsnFjG9HYi8xgaVxasJz4L4LebCQIDAQAB";

//    public void  testZhimaAuthInfoAuthorize() throws UnsupportedEncodingException {
//    	ZmAuthStateVo authStateVo = new ZmAuthStateVo();
//		authStateVo.setMemberCreditId("1234325246426345")
//		.setFailUrl( "FAIL")
//		.setSuccessUrl("SUCCESS");
//    	String state = JsonUtil.toJson(authStateVo);
//    	state = URLEncoder.encode(state, "UTF-8");
//        ZhimaAuthInfoAuthorizeRequest req = new ZhimaAuthInfoAuthorizeRequest();
//        req.setChannel("apppc");
//        req.setPlatform("zmop");
//        req.setIdentityType("2");// 必要参数 
//        req.setIdentityParam("{\"name\":\"陈彦志\",\"certType\":\"IDENTITY_CARD\",\"certNo\":\"460002198703290513\"}");// 必要参数 
//        req.setBizParams("{\"auth_code\":\"M_H5\",\"channelType\":\"app\",\"state\":\""+state+"\"}");// 
//        DefaultZhimaClient client = new DefaultZhimaClient(gatewayUrl, appId, privateKey, zhimaPublicKey);
//        try {
//            String url = client.generatePageRedirectInvokeUrl(req);
//            System.out.println(url);
//        } catch (ZhimaApiException e) {
//            e.printStackTrace();
//        }
//    }
    public void  testZhimaCreditWatchlistiiGet() {
        ZhimaCreditWatchlistiiGetRequest req = new ZhimaCreditWatchlistiiGetRequest();
        req.setChannel("apppc");
        req.setPlatform("zmop");
        req.setProductCode("w1010100100000000022");// 必要参数 
        req.setTransactionId("20170510D18691954193ZM14943114209557660124");// 必要参数 
        req.setOpenId("268812842580063597647364333");// 必要参数 
        DefaultZhimaClient client = new DefaultZhimaClient(gatewayUrl, appId, privateKey, zhimaPublicKey);
        try {
            ZhimaCreditWatchlistiiGetResponse response =(ZhimaCreditWatchlistiiGetResponse)client.execute(req);
            
            response.getDetails();
//            ZmWatchListDetail zmWatchListDetail = new ZmWatchListDetail();
            
            System.out.println("======="+JsonUtil.toJson(response));
//            System.out.println(response.isSuccess());
//            System.out.println(response.getErrorCode());
//            System.out.println(response.getErrorMessage());
        } catch (ZhimaApiException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        TestZhimaAuthInfoAuthorize result = new  TestZhimaAuthInfoAuthorize();
//        result.testZhimaAuthInfoAuthorize();
        result.testZhimaCreditWatchlistiiGet();
    }
}