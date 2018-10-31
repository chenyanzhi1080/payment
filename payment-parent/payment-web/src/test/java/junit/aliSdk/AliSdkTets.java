package junit.aliSdk;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeFastpayRefundQueryRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeFastpayRefundQueryResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.xiaoerzuche.biz.payment.alipayment.handle.TranQueryHandle;
import com.xiaoerzuche.biz.payment.enu.TranType;
import com.xiaoerzuche.biz.payment.model.PayOrder;
import com.xiaoerzuche.biz.payment.service.PayOrderService;
import com.xiaoerzuche.biz.payment.vo.QueryVo;
import com.xiaoerzuche.biz.zmxy.config.AliOpendApiClient;
import com.xiaoerzuche.common.util.JsonUtil;

import junit.BaseTest;


public class AliSdkTets extends BaseTest {
	
	@Autowired
	private TranQueryHandle queryHandle;
	@Autowired
	private PayOrderService payOrderService;
	@Test
	public void test(){
		try {
			QueryVo queryVo = new QueryVo("15480282579", "36151496178310582386", "15149617831758123", null, null);
			//查找原交易订单
			PayOrder srcPayOrder = payOrderService.getPayOrderBy(queryVo);
//			PayOrder srcPayOrder = new PayOrder();
//			srcPayOrder.setQueryId("15149590284152106");
//			srcPayOrder.setTranType(TranType.CONSUME.getCode());
			String tranNo = queryHandle.query(srcPayOrder);
			System.out.println("================="+tranNo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
		
	public static void tradeQuery(String queryId){
		try {
			AlipayClient alipayClient = new DefaultAlipayClient(AliOpendApiClient.GATEWAY_URL,AliOpendApiClient.APP_ID,AliOpendApiClient.PRIVATE_KEY,"json","UTF-8",AliOpendApiClient.ALIPAY_PUBLIC_KEY,"RSA");
			AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
			request.setBizContent("{\"out_trade_no\":\""+queryId+"\"}");
			AlipayTradeQueryResponse response = (AlipayTradeQueryResponse) alipayClient.execute(request);
			System.out.println(JsonUtil.toJson(response));
			if(response.isSuccess()){
				System.out.println("调用成功");
			} else {
				System.out.println("调用失败");
			}
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
	}
	
	public static void refundQuery(String queryId,String orig_tran_no){
		try {
			AlipayClient alipayClient = new DefaultAlipayClient(AliOpendApiClient.GATEWAY_URL,AliOpendApiClient.APP_ID,AliOpendApiClient.PRIVATE_KEY,"json","UTF-8",AliOpendApiClient.ALIPAY_PUBLIC_KEY,"RSA");
			AlipayTradeFastpayRefundQueryRequest request = new AlipayTradeFastpayRefundQueryRequest();
//			request.setBizContent("{\"out_request_no\":\""+queryId+"\"}");
			request.setBizContent("{\"out_request_no\":\""+queryId+"\","
					+ "\"trade_no\":\""+orig_tran_no+"\"}");
			AlipayTradeFastpayRefundQueryResponse response = alipayClient.execute(request);
			System.out.println(JsonUtil.toJson(response));
			if(response.isSuccess()){
				System.out.println("调用成功");
			} else {
				System.out.println("调用失败");
			}
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
	}
	
	public static void doRefund(){
		try {
			AlipayClient alipayClient = new DefaultAlipayClient(AliOpendApiClient.GATEWAY_URL,AliOpendApiClient.APP_ID,AliOpendApiClient.PRIVATE_KEY,"json","UTF-8",AliOpendApiClient.ALIPAY_PUBLIC_KEY,"RSA");
			AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
			request.setBizContent("{" +
//			"\"out_trade_no\":\"15136712732469071\"," +
			"\"trade_no\":\"2017122021001004250228908161\"," +
			"\"refund_amount\":0.01," +
			"\"out_request_no\":\"15137539681393098\"," +
			"\"refund_reason\":\"正常退款\"" +
			"  }");
			AlipayTradeRefundResponse response = alipayClient.execute(request);
			System.out.println(JsonUtil.toJson(response));
			if(response.isSuccess()){
				System.out.println("调用成功");
			} else {
				System.out.println("调用失败");
			}
			
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args){
		AliSdkTets.tradeQuery("15149590284152106");
//		AliSdkTets.refundQuery("2017122015137377424257452","2017111121001004500577811214");
//		AliSdkTets.doRefund();
	}
}
