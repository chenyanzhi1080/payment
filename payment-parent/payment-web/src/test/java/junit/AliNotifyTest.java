package junit;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.reflect.TypeToken;
import com.xiaoerzuche.biz.payment.PaymentContext;
import com.xiaoerzuche.biz.payment.alipayment.AliPaymentStrategy;
import com.xiaoerzuche.biz.payment.alipayment.alipay.AlipayCore;
import com.xiaoerzuche.biz.payment.alipayment.alipay.RSA;
import com.xiaoerzuche.biz.payment.common.PaymentConf;
import com.xiaoerzuche.biz.payment.enu.PayType;
import com.xiaoerzuche.biz.payment.paymentConfig.AliPayConfig;
import com.xiaoerzuche.biz.payment.service.CallbackService;
import com.xiaoerzuche.common.util.JsonUtil;

import net.sf.json.JSONObject;

public class AliNotifyTest extends BaseTest{
	
	private static final Logger logger = LoggerFactory.getLogger(AliNotifyTest.class);
	@Autowired
	private PaymentContext		paymentContext;
	@Autowired
	private CallbackService callbackService;
	
	@Test
	public void appCallBack(){
		try {
			Map<String, String> params = new HashMap<String, String>();
			String jsonStr = "{'gmt_create'='2016-12-06 16:40:13', 'buyer_email'='317754415@qq.com', 'notify_time'='2016-12-06 16:40:14', 'gmt_payment'='2016-12-06 16:40:14', 'seller_email'='xiaoerzuche@hnair.com', 'quantity'='1', 'subject'='开开出行交易商品', 'use_coupon'='N', 'sign'='mCmupIoZWQtNXOdSCP8FU1RnkSb8CKbcm4D6YmnLKf5e6uK/Fu48oZiu8vMnJE2UGF3dKw3+di4NjN42QmjMUxN/SkSq0UlboWYsjZpcaM+mdYRhvNtVwMvKAf4FdexNQueVNI9TOTYuN49CgQZ+QKvSQWCLO6sZhJkhlIChu50=', 'discount'='0.00', 'body'='开开出行交易商品', 'buyer_id'='2088702267447164', 'notify_id'='d847c442fabaf554b3a64fb338a74d7h8i', 'notify_type'='trade_status_sync', 'payment_type'='1', 'out_trade_no'='14810136039981652', 'price'='0.04', 'trade_status'='TRADE_SUCCESS', 'total_fee'='0.04', 'trade_no'='2016120621001004160200902916', 'sign_type'='RSA', 'seller_id'='2088811891419125', 'is_total_fee_adjust'='N'}";
			System.out.println(jsonStr);
			JSONObject jsonObject = JSONObject.fromObject(jsonStr);
			String json = jsonObject.toString();
			System.out.println(json);
			params = JsonUtil.fromJson(json, new TypeToken<Map<String, String>>(){});
			System.out.println(params);
			
			logger.info("[阿里APP支付服务端通知]{}",params);
			Map<String, String> valideData = paymentContext.getPaymentBackNotifaction(PaymentConf.ALI_PAYMENT_SERVICE_NAME,
							params);
			valideData.put("payType", String.valueOf(PayType.ALIAPPPAY.getCode()));
			logger.info("[阿里APP支付服务端通知报文解析valideData=" + valideData.toString());
			String code = valideData.get(PaymentConf.code_pay);
			boolean isFailure = PaymentConf.successcode.equals(code);
			isFailure = true;
//			boolean isSucess = callbackService.aliAppcallback(valideData, isFailure);
//			System.out.println("==============isSucess:"+isSucess);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args){
		try {
			AliPaymentStrategy aliPaymentStrategy = new AliPaymentStrategy();
			Map<String, String> params = new HashMap<String, String>();
			String jsonStr = "{'gmt_create'='2016-12-06 16:40:13', 'buyer_email'='317754415@qq.com', 'notify_time'='2016-12-06 16:40:14', 'gmt_payment'='2016-12-06 16:40:14', 'seller_email'='xiaoerzuche@hnair.com', 'quantity'='1', 'subject'='开开出行交易商品', 'use_coupon'='N', 'sign'='mCmupIoZWQtNXOdSCP8FU1RnkSb8CKbcm4D6YmnLKf5e6uK/Fu48oZiu8vMnJE2UGF3dKw3+di4NjN42QmjMUxN/SkSq0UlboWYsjZpcaM+mdYRhvNtVwMvKAf4FdexNQueVNI9TOTYuN49CgQZ+QKvSQWCLO6sZhJkhlIChu50=', 'discount'='0.00', 'body'='开开出行交易商品', 'buyer_id'='2088702267447164', 'notify_id'='d847c442fabaf554b3a64fb338a74d7h8i', 'notify_type'='trade_status_sync', 'payment_type'='1', 'out_trade_no'='14810136039981652', 'price'='0.04', 'trade_status'='TRADE_SUCCESS', 'total_fee'='0.04', 'trade_no'='2016120621001004160200902916', 'sign_type'='RSA', 'seller_id'='2088811891419125', 'is_total_fee_adjust'='N'}";
			System.out.println(jsonStr);
			JSONObject jsonObject = JSONObject.fromObject(jsonStr);
			String json = jsonObject.toString();
			System.out.println(json);
			params = JsonUtil.fromJson(json, new TypeToken<Map<String, String>>(){});
			System.out.println(params);
			System.out.println(params.get("refund_status"));
//			Map<String, String> valideData = aliPaymentStrategy.getPaymentBackNotifaction(params);
//			System.out.println(JsonUtil.toJson(valideData));
			
			String sign = params.get("sign");
			Map<String, String> sParaNew = AlipayCore.paraFilter(params);
	        //获取待签名字符串
	    	String preSignStr = "";
    		preSignStr = AlipayCore.createLinkString(sParaNew);
	        //获得签名验证结果
	        boolean isSign = false;
        	isSign = RSA.verify(preSignStr, sign, AliPayConfig.ali_public_key, AliPayConfig.input_charset);
			System.out.println(isSign);
        	preSignStr = AlipayCore.createLinkStringNoSort(sParaNew);
        	isSign = RSA.verify(preSignStr, sign, AliPayConfig.ali_public_key, AliPayConfig.input_charset);
        	System.out.println(isSign);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
