package junit.zmxy;

import com.antgroup.zmxy.openplatform.api.DefaultZhimaClient;
import com.antgroup.zmxy.openplatform.api.ZhimaApiException;
import com.antgroup.zmxy.openplatform.api.request.ZhimaCreditAntifraudVerifyRequest;
import com.antgroup.zmxy.openplatform.api.response.ZhimaCreditAntifraudVerifyResponse;
import com.xiaoerzuche.common.util.JsonUtil;

public class TestZhimaCreditAntifraudVerify {
	//芝麻开放平台地址
    private String gatewayUrl     = "https://zmopenapi.zmxy.com.cn/openapi.do";
    //商户应用 Id
    private String appId          = "1002716";
    //商户 RSA 私钥
    private String privateKey     = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBANgbUVGqtKyKb/V2hM36/7LzsKvoGt/YOd32zREY/uhujnNdnIKfclz+iVkG+nqguNcGudTooKA4/w9Mb5MNtbCM5ZPinM8VNJdXfgGdEKHb2J9uz2SfDv+9Q3wpEPiRAlLBMNAs5srAkrtWgUJYwUbzeF6D1I3Ye+RO7CqO2GCJAgMBAAECgYB085u3kaWDdswOUByhCIQWyXDEJ7FbkL4IVIc4CInzKdmnr3KC8l1G3G1iI7esY4yEudwZz+cVrqLCGcCMGbBII23hBXJtXWK3us4TTawdLxPhKoWoj2v8wYUffltk3WRXFKaqgBaUKDTjjPRBOluFhVfQ5fBeFl/lSVUBeGNj3QJBAPq63h5mUfDbP2A5PzwEqLpE5BBm+BySxflxUGyEchKVmWEf9Qo0EhKlBEggNLU+Rs8DQycW7X/BRjyA5MvKwTcCQQDcpiYMBXGsiYXVFe2jZDMVOgZuzvRjNojoCNFr9gWPh6L0zvvQJxwJUoBd30IfmQfcAhgJW1MUe63c/U0DXcw/AkBH8J4SsuEH7qB8h3BxVBTlfSbC3giKikJrk+uzdz0TwADGMVkpmE8nMlnMKBF3f2rpstHtwVJbuaThb0o2fvJ5AkBlpYjbetqOIGBt9IcgATXjLnLuMxq0ZdgWBJHtSU3wfAye+Idasdmhlcca95GvmsDbIn/ON6sDQaKrrmj/BwPjAkEArNJRRP0WWrRRVylJl6AUr8Uli1+noKwogPhBI4fEYHnmwxiT/O/JPzpAGRHsehhLZeQg0SULE0KTHJHxS98I/Q==";
    //芝麻 RSA 公钥
    private String zhimaPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCWzt2zjsK8yLA4dv0K95srqolOKrm2IJY4gxqY2LVwSsTf7CuHVEmXP88sickeyKFihkdQLeEfhxdbTIwSCgbV7JXIAhOhwfcGrEioq2sw57Ta7zzI9XX+ekzZb8mJ4Xkp0rai9/or3HOX+/gsnFjG9HYi8xgaVxasJz4L4LebCQIDAQAB";

    public void  testZhimaCreditAntifraudVerify() {
        ZhimaCreditAntifraudVerifyRequest req = new ZhimaCreditAntifraudVerifyRequest();
        req.setChannel("apppc");
        req.setPlatform("zmop");
        req.setProductCode("w1010100000000002859");// 必要参数 
        req.setTransactionId(String.valueOf(System.currentTimeMillis()));// 必要参数 
        req.setCertType("IDENTITY_CARD");// 必要参数 
        req.setCertNo("440102199607230613");// 必要参数 
        req.setName("陈彦志");// 必要参数 
        req.setMobile("18565254925");//
        req.setBankCard("6212263602094215779");
        DefaultZhimaClient client = new DefaultZhimaClient(gatewayUrl, appId, privateKey, zhimaPublicKey);
        try {
            ZhimaCreditAntifraudVerifyResponse response =(ZhimaCreditAntifraudVerifyResponse)client.execute(req);
            System.out.println(response.isSuccess());
            System.out.println(response.getErrorCode());
            System.out.println(response.getErrorMessage());
            System.out.println(JsonUtil.toJson(response));
        } catch (ZhimaApiException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        TestZhimaCreditAntifraudVerify result = new  TestZhimaCreditAntifraudVerify();
        result.testZhimaCreditAntifraudVerify();
    }
}
