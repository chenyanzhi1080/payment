package junit.zmxy;

import com.antgroup.zmxy.openplatform.api.DefaultZhimaClient;
import com.antgroup.zmxy.openplatform.api.ZhimaApiException;
import com.antgroup.zmxy.openplatform.api.request.ZhimaAuthInfoAuthqueryRequest;
import com.antgroup.zmxy.openplatform.api.response.ZhimaAuthInfoAuthqueryResponse;
import com.xiaoerzuche.common.util.JsonUtil;

public class TestZhimaAuthInfoAuthquery {
	//芝麻开放平台地址
    private String gatewayUrl     = "https://zmopenapi.zmxy.com.cn/openapi.do";
    //商户应用 Id
    private String appId          = "1002716";
    //商户 RSA 私钥
    private String privateKey     = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBANgbUVGqtKyKb/V2hM36/7LzsKvoGt/YOd32zREY/uhujnNdnIKfclz+iVkG+nqguNcGudTooKA4/w9Mb5MNtbCM5ZPinM8VNJdXfgGdEKHb2J9uz2SfDv+9Q3wpEPiRAlLBMNAs5srAkrtWgUJYwUbzeF6D1I3Ye+RO7CqO2GCJAgMBAAECgYB085u3kaWDdswOUByhCIQWyXDEJ7FbkL4IVIc4CInzKdmnr3KC8l1G3G1iI7esY4yEudwZz+cVrqLCGcCMGbBII23hBXJtXWK3us4TTawdLxPhKoWoj2v8wYUffltk3WRXFKaqgBaUKDTjjPRBOluFhVfQ5fBeFl/lSVUBeGNj3QJBAPq63h5mUfDbP2A5PzwEqLpE5BBm+BySxflxUGyEchKVmWEf9Qo0EhKlBEggNLU+Rs8DQycW7X/BRjyA5MvKwTcCQQDcpiYMBXGsiYXVFe2jZDMVOgZuzvRjNojoCNFr9gWPh6L0zvvQJxwJUoBd30IfmQfcAhgJW1MUe63c/U0DXcw/AkBH8J4SsuEH7qB8h3BxVBTlfSbC3giKikJrk+uzdz0TwADGMVkpmE8nMlnMKBF3f2rpstHtwVJbuaThb0o2fvJ5AkBlpYjbetqOIGBt9IcgATXjLnLuMxq0ZdgWBJHtSU3wfAye+Idasdmhlcca95GvmsDbIn/ON6sDQaKrrmj/BwPjAkEArNJRRP0WWrRRVylJl6AUr8Uli1+noKwogPhBI4fEYHnmwxiT/O/JPzpAGRHsehhLZeQg0SULE0KTHJHxS98I/Q==";
    //芝麻 RSA 公钥
    private String zhimaPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCWzt2zjsK8yLA4dv0K95srqolOKrm2IJY4gxqY2LVwSsTf7CuHVEmXP88sickeyKFihkdQLeEfhxdbTIwSCgbV7JXIAhOhwfcGrEioq2sw57Ta7zzI9XX+ekzZb8mJ4Xkp0rai9/or3HOX+/gsnFjG9HYi8xgaVxasJz4L4LebCQIDAQAB";

    public void  testZhimaAuthInfoAuthquery() {
        ZhimaAuthInfoAuthqueryRequest req = new ZhimaAuthInfoAuthqueryRequest();
        req.setChannel("apppc");
        req.setPlatform("zmop");
        req.setIdentityType("0");// 必要参数 
        req.setIdentityParam("{\"openId\":\"268812842580063596657973302\"}");// 必要参数 
//      req.setIdentityType("2");// 必要参数 
//      req.setIdentityParam("{\"certNo\":\"460002198703290513\",\"name\":\"陈彦志\",\"certType\":\"IDENTITY_CARD\"}");// 必要参数         
        req.setAuthCategory("C2B");// 必要参数 
        DefaultZhimaClient client = new DefaultZhimaClient(gatewayUrl, appId, privateKey, zhimaPublicKey);
        try {
            ZhimaAuthInfoAuthqueryResponse response =(ZhimaAuthInfoAuthqueryResponse)client.execute(req);
            System.out.println(response.isSuccess());
            System.out.println(response.getErrorCode());
            System.out.println(response.getErrorMessage());
            System.out.println(JsonUtil.toJson(response));
        } catch (ZhimaApiException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        TestZhimaAuthInfoAuthquery result = new  TestZhimaAuthInfoAuthquery();
        result.testZhimaAuthInfoAuthquery();
    }
}
