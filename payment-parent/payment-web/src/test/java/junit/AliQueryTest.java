package junit;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeFastpayRefundQueryRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeFastpayRefundQueryResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.xiaoerzuche.biz.payment.alipayment.alipay.AlipaySubmit;
import com.xiaoerzuche.biz.payment.enu.PayType;
import com.xiaoerzuche.biz.payment.model.PayOrder;
import com.xiaoerzuche.biz.payment.paymentConfig.AliPayConfig;
import com.xiaoerzuche.biz.zmxy.config.AliOpendApiClient;
import com.xiaoerzuche.common.util.JsonUtil;
import com.xiaoerzuche.biz.payment.service.PayOrderService;
import com.xiaoerzuche.biz.payment.vo.QueryVo;

public class AliQueryTest extends BaseTest{
	@Autowired
	private PayOrderService payOrderService;
	@Test
	public void getPayOrderBy(){
		String appid = "15480282579";
		String orderId = "30151381924410555989";
		String queryId = "15138192449067297";
		QueryVo queryVo = new QueryVo(appid, orderId, queryId, null, null);
		PayOrder srcPayOrder = payOrderService.getPayOrderBy(queryVo);
		System.out.println(srcPayOrder);
		PayType payType = PayType.getPayType(srcPayOrder.getPayType());
		System.out.println(payType);
	}
	
//	@Test
	public void alipayTradeQuery(){
		String queryId = "";
		String tranNo = "2016110721001004500286496104";

		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", "alipay.trade.query");
		sParaTemp.put("partner", AliPayConfig.partner);
		sParaTemp.put("_input_charset", AliPayConfig.input_charset);
		if(StringUtils.isNotBlank(queryId)){
			sParaTemp.put("out_trade_no", queryId);
		}
		if(StringUtils.isNotBlank(tranNo)){
			sParaTemp.put("trade_no", tranNo);
		}
		
		try {
			String sHtmlText = AlipaySubmit.buildRequest("https://openapi.alipay.com/gateway.do", "", sParaTemp);
			
			System.out.println(sHtmlText);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
//	@Test
	public void singleTradeQuery(){
		String queryId = "14915490370524165";
		String tranNo = "";
		Map<String, String> sParaTemp = new HashMap<String, String>();
		// 设置有密码和无密码字段
		sParaTemp.put("service", "single_trade_query");
		sParaTemp.put("partner", AliPayConfig.partner);
		sParaTemp.put("_input_charset", AliPayConfig.input_charset);
		if(StringUtils.isNotBlank(queryId)){
			sParaTemp.put("out_trade_no", queryId);
		}
		if(StringUtils.isNotBlank(tranNo)){
			sParaTemp.put("trade_no", tranNo);
		}
		
		try {
			String sHtmlText = AlipaySubmit.buildRequest("", "", sParaTemp);
			System.out.println(sHtmlText);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void query(String queryId){
		//我们现在使用的不是开发平台api产品，因此暂时不考虑支付宝的openid sdk
		String app_id = AliOpendApiClient.APP_ID;
		String private_key = AliOpendApiClient.PRIVATE_KEY;
		String alipay_public_key = AliOpendApiClient.ALIPAY_PUBLIC_KEY;
		try {
			AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",app_id,private_key,"json","UTF-8",alipay_public_key,"RSA");
			AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
			request.setBizContent("{\"out_trade_no\":\""+queryId+"\"}");
			AlipayTradeQueryResponse response = (AlipayTradeQueryResponse) alipayClient.execute(request);
			if(response.isSuccess()){
				System.out.println("调用成功");
				System.out.println(JsonUtil.toJson(response));
			} else {
				System.out.println("调用失败");
				System.out.println(JsonUtil.toJson(response));
			}
		} catch (AlipayApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void refundQuery(String queryId){
		String app_id = AliOpendApiClient.APP_ID;
		String private_key = AliOpendApiClient.PRIVATE_KEY;
		String alipay_public_key = AliOpendApiClient.ALIPAY_PUBLIC_KEY;
		try {
			AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",app_id,private_key,"json","UTF-8",alipay_public_key,"RSA");
			AlipayTradeFastpayRefundQueryRequest request = new AlipayTradeFastpayRefundQueryRequest();
			request.setBizContent("{\"out_trade_no\":\""+queryId+"\"}");
			AlipayTradeFastpayRefundQueryResponse response = alipayClient.execute(request);
			if(response.isSuccess()){
				System.out.println("调用成功");
				System.out.println(JsonUtil.toJson(response));
			} else {
				System.out.println("调用失败");
				System.out.println(JsonUtil.toJson(response));
			}
		} catch (AlipayApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args){
//		AliQueryTest.query("14832307136017625");
		AliQueryTest.refundQuery("14832012115065474");
	}
}
