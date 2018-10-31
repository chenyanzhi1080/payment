package com.xiaoerzuche.biz.zmxy.config;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.antgroup.zmxy.openplatform.api.DefaultZhimaClient;



@Component
public class ZmxyClient{
	private static final Logger logger = LoggerFactory.getLogger(ZmxyClient.class);
	
	//数据反馈
	public final static String objectName = "开开出行";
	public final static String rentDesc = "当前消费:";
	/**
	 * 行业关注名单产品码（productCode）
	 */
	public static final String CREDIT_WATCH_LIST_PRODUCT_CODE = "w1010100100000000022";
	/**
	 * 芝麻信用分产品码（productCode）
	 */
	public static final String CREDIT_SCORE_PRODUCT_CODE = "w1010100100000000001";
	/**
	 * 欺诈评分(productCode)
	 */
	public static final String CREDIT_ANTIFRAUD_SCORE_CODE = "w1010100003000001100";
	/**
	 * 欺诈信息验证(productCode)
	 */
	public static final String CREDIT_ANTIFRAUD_VERIFY_CODE = "w1010100000000002859";
	/**
	 * 欺诈关注清单(productCode)
	 */
	public static final String CREDIT_ANTIFRAUD_RISK_LIST_CODE = "w1010100003000001283";
	
	@Value("${profile}")
	private String profile;
    //芝麻开放平台地址
	private static final String gatewayUrl     = "https://zmopenapi.zmxy.com.cn/openapi.do";
    //商户应用 Id
	private static String appId;
    //商户 RSA 私钥
	private static String privateKey;
    //芝麻 RSA 公钥
	private static String zhimaPublicKey;
	//芝麻信用数据反馈
	public static String typeId;
    
	public static DefaultZhimaClient zhimaClient;
    
    /**dev**/
    //dev商户应用 Id
    private String devAppId          = "1002714";
    //dev商户 RSA 私钥
    private String devPrivateKey     = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALB1K2XeO+8767fUvVbsOasf/pgXBRn+9Ma0dbrbFle49ZWThDOEdiW/vuFybdyvI/nksHB9RtRg8q7o+cXi0Iqy0papV9Yf2YAZkviZ17J3sFlOYgVnMf9dZgJo7lKIbhPlXq1gAfnmX4w5mW1P5B8X2URItw9bnvPaelf6dI5DAgMBAAECgYB/6B7SE3ocWrHtqe6pIEhZC7MPabhP9KhXlJ22GXMjmTWi+7Aba8v8ZvYlEz4hdm68iBGBKL372l5vfP74ewWwH/h5GJvQMQV+OXcvSKyUkPaSzD5lsDAbI7804CNmi3c2QtcaPv4vBcDc8hugI17S6FdMZ5erWWBszcvonEAbOQJBANUyQNnPvqBLD/yGFTnoxVbz8xr+P9JUhLPGV4MwdOeYDAkzZyNaatriAkjM8/HPimGcqN1O8/x61ZWPa8dT+VUCQQDT4qU0eiK2ph2PoIeG6BeDmv6jQcorcW4II0ssJA6pozAWg0T0MAWfap64bBu8Ou1HFCNGRBO2uQopeThNDgk3AkEAireh/pbvf7iXdEWB9iYAkO011vBrcl1P4vFA67lRt4b0/d+WUih4smav+dJxP3s8nPqj3SuMEVGTOzcQd6ep6QJBAKnUYthCAOrnEJCbeaV/B8HbjLfsNpBFlYz+RPV3XS1I+HVC+BTFHOye9+MX+88OaDrtgCyEykxN2kBNwfu5AVcCQCsH0/XdHkL/HWoly+khflIKfr5ekIbmcZrIYUUjmlAF5AsDV3Q9jKxY1Gn0j8PmmAeFJ8FpivBJUXNwIS8FfUY=";
    //dev芝麻 RSA 公钥
    private String devZhimaPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC2EMhB6nNk+X8oNhZ6DfHTV2kJrdQkmFFq5NwCXVrgaiTQgcO27zpR7V8G1Pl16HycYPQah5UsjOzDIpbQ4rDsH/bjPIg1NOxsNY4/7Yml+PxJYRUaWkrlwXJy63mS818gXL11gcvXh4bbVvreiDhrlsADRyi17+JwKMpVHWA8twIDAQAB";
    //dev芝麻信用数据反馈
  	public final static String devTypeId = "1002716-default-test";
    
    /**test**/
    //商户应用 Id
    private String testAppId          = "300002521";
    //商户 RSA 私钥
    private String testPrivateKey     = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAN8iP/uBAZRmutyckH231J0YD/n1z5w0nw/WXsN97/Agr+LdpblpxAD3hwha3I+wQ9R9WWJ4FuUaQTvAI/zp+axdnUhSzH/yC5IGm/ILeIBWNZB9cBf7dJwOxxxSCTfq3oqOE15GIhaoFCrlS+xyt/v4t/GbfvBksKjSUe/0Dba1AgMBAAECgYEAp5RNgVIOIapVxMJBMmoR4DnmJdCbL7HFlFtnqGK5BRtHfZcBlBL/BcrhO0EiTTgQeVH7A501dLMXMwzG+neu7oGLpBMCVH5mAQNn7VR0VLoGFjSdBQGFGRHOzeWzHKqJjQNxj+gTzGwxBu06DuZeEmxSN79OAg4DkXep1WGwxjECQQD8Mj9T9X9W9g7Aka1mfwT/854B8+4a0XEwWyremj7dSlxfWyckbehS8ErE8Fu6ZmdQCrPaxu/kSvJZayvD/OunAkEA4n/KJuWcGGB5gkkCxnYS+BTiIwHdsuXlHer03DiD2ji/KgvFEpSY2hgwG7E84BzbnFtqpB/J7lyaY98lfjzmQwJAGrPLRr14lSGKYFNMU8zhH6SQt043iLliTadpe5ZDT7Te4O3K0C2e6qEsmXuFis/q4muvrVtg7QAOdu9E/ARcgwJASXORlf2eGlWm8fZFXSDLVAJJt3qSb8KYA5S/ZW86axk9/R+mADmKPCfd6g0XJcKWAWWv9puMe60yMDMcUIzlSwJBANA4lzBOb08Bx85xiOrvryffnlGLf5hq1s4ozawYSr3RQl4UJRHYpU+Pcepgz1ZJruE2o4TaHFrdn4d/Amw6EYU=";
    //芝麻 RSA 公钥
    private String testZhimaPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCYLt9gjH6y1vXGfLdgmzMYXmLYGuGl4itFuggs9+KaHCZQ1ZRkieY3D2pJkTmT095DNjQrKA6S1pAaHPJ5zPt8rUnmKmEcMEctTwJlvaI83ltusIjBaaizNtKtBDuwqjkn0PEam2xaqD/Q6goIS3zNdrR+YgPY5akujP96OBlk9QIDAQAB";
    //test芝麻信用数据反馈
  	public final static String testTypeId = "300002521-default-test";
  	
    /**prod**/
    //商户应用 Id
    private String prodAppId          = "300002555";
    //商户 RSA 私钥
    private String prodPrivateKey     = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBANGOHeP5OjV2gtyHC83+odflKF5vF4mv59UmQiefcwRQ6tWlXBGvDmnKMSTXV8iZeVtdgNRngAV6ZOxlTZtI0z1+MWyY4oOnD5Z5Nr5yAMqBkwkHcouj1Y3biwTu31x7rCSNMLLUCpCWA5vOGGePW2U79DjYXhU2r5UpjLXu/o1lAgMBAAECgYEAtqAvZo2+b/dvhndfD1B5hajdrKcOkV0vIvCruNJaL4nBjx4EhhjU69ddSuktRNKUE88CRppI1P1c1TRYFRu7k4gfpxq64jzFzsPMf5qVX+jz61YIHcjVHBKudDqIO2WZc2nwx18we/qpk32z2c6y+eSJuwGVBZGr+VYtwptUAwECQQD4hITxWl78XztPKZRY+fdr6HYt+KMBXHICRYRF2Ib9ljBioRolGcQSWqyw0UY7LFExGsT7RRnFJedeJiyMkixBAkEA191KADUY8od2dULlI5Pr6PUP0CRru/x0hU3qgdLM80J9G9O9/kMy2K4TDSCDja/TjzJT0IwesVpMNAcaWfQoJQJADC4ShfGz7hKMyZh0GNhHdBZ0lFQ3SP3K6KmXjgTKuOr/fBYBPghBP9UJ2bCoDIZjhFCXjukYFj2BVW/xhwIzAQJAP1D6PrtUWPcg6N45RclCni8bd0rnitlO+DbVfUTutr+e4MWn6eeeB25vyh7qV2nmXQ8/BGHFMvIwfeZDdXUnPQJAep2xfYfhBq2xaTaCBFwiqKZbdyNyVvHuc1tRbHLi7XkPEO5VqG1LWCkFonMc+nlMob7Xfl/T9lAPGixDvyCjFQ==";
    //芝麻 RSA 公钥
    private String prodZhimaPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCQvgZBuE3qaaYOraY44HbpfCNz/7gUVtCVYU9Y5HPgXBXgf8zuRf2832KCOLe3eCU6NoFKQVmo4UeZZU9MvdeXtI04BRMd50gqhKrqPDY5ogDZJftcTNR3uU8wnPSaXYfjW/+FRXQL9bAS+HcFkZuq3UQT+AerXuQVjOrkCKMYiQIDAQAB";
    //test芝麻信用数据反馈
  	public final static String prodTypeId = "300002555-default-order";
//    public final static String prodTypeId = "1002716-default-test";
    
    @PostConstruct
	private void init(){
    	if("test".equals(profile)){
    		this.appId = this.testAppId;
    		this.privateKey = this.testPrivateKey;
    		this.zhimaPublicKey = this.testZhimaPublicKey;
    		this.typeId = this.testTypeId;
    	}else if("prod".equals(profile)){
    		this.appId = this.prodAppId;
    		this.privateKey = this.prodPrivateKey;
    		this.zhimaPublicKey = this.prodZhimaPublicKey;
    		this.typeId = this.prodTypeId;
    	}else{
    		this.appId = this.devAppId;
    		this.privateKey = this.devPrivateKey;
    		this.zhimaPublicKey = this.devZhimaPublicKey;
    		this.typeId = this.devTypeId;
    	}
    	logger.info("[芝麻信用初始化][运行环境][profile={}][appId={}][privateKey={}][zhimaPublicKey={}][typeId={}]",new Object[]{profile,appId,privateKey,zhimaPublicKey,typeId});
    	zhimaClient = new DefaultZhimaClient(this.gatewayUrl, this.appId, this.privateKey, this.zhimaPublicKey);
    	logger.info("[芝麻信用初始化结束]");
    }
    
    
}
