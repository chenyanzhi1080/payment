package junit.wechatApp;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.xiaoerzuche.biz.payment.alipayment.AliPaymentStrategy;
import com.xiaoerzuche.biz.payment.enu.PayType;
import com.xiaoerzuche.biz.payment.util.TableUtil;
import com.xiaoerzuche.biz.payment.wechatpayment.WechatJSPaymentStrategy;
import com.xiaoerzuche.biz.payment.wechatpayment.WechatPaymentStrategy;

import junit.BaseTest;

public class WechatPayTest extends BaseTest{
	@Autowired
	private WechatJSPaymentStrategy wechatJSPaymentStrategy;
	@Autowired
	private AliPaymentStrategy aliPaymentStrategy;
	@Autowired
	private WechatPaymentStrategy wechatPaymentStrategy;
	
	private String backUrl = "/front/paymentNotify/notifyFroBackWechatPay.do";
	@Value("${addresses}")
	private String addresses;
	
	@Test
	public void doPay(){
		
		try {
			String json = wechatPaymentStrategy.doPayment("", "", "", "123456789", 1, PayType.WEIXINAPPPAY.getCode(), "", "http://sandbox.expose.95071222.net/api/ali/callBack");
			System.out.println(json);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
//	@Test
	public void doReFund(){
		int price = 70;
		int refundPrice = 100;
		try {
			wechatJSPaymentStrategy.doRefund("", "4004712001201606227673575660", 1, TableUtil.genId(), 100, null, backUrl);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
//	@Test
	public void query(){
		try {
//			wechatPaymentStrategy.refundquery("14936996985011947", "50000002402017050201030630246");
//			wechatPaymentStrategy.orderQuery("14902602218851677");
			aliPaymentStrategy.query("14936390581743769","");
//			aliPaymentStrategy.query("","2017011721001004610221689907");
//			wechatJSPaymentStrategy.orderquery(null, "4006472001201609295296723996", TranType.CONSUME.getCode());
//			wechatJSPaymentStrategy.orderquery("14794587527926872", "4006422001201611180104015806", TranType.CONSUME.getCode());
//			wechatJSPaymentStrategy.refundquery(null, "2004862001201610110509481915", TranType.REFUND.getCode());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
