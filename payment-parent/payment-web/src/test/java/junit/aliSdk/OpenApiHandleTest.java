package junit.aliSdk;

import java.math.BigDecimal;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alipay.api.AlipayApiException;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.xiaoerzuche.biz.payment.alipayment.handle.OpenApiHandle;
import com.xiaoerzuche.biz.payment.model.PayOrder;
import com.xiaoerzuche.biz.payment.paymentConfig.DefaultAliOpendApiClient;
import com.xiaoerzuche.biz.payment.service.imp.AliTranQueryServiceImp;
import com.xiaoerzuche.biz.payment.util.TableUtil;
import com.xiaoerzuche.common.util.JsonUtil;

import junit.BaseTest;

public class OpenApiHandleTest extends BaseTest {
	private static final Logger logger = LoggerFactory.getLogger(OpenApiHandleTest.class);
	@Autowired
	private OpenApiHandle openApiHandle;
	@Autowired
	private DefaultAliOpendApiClient aliClient;
	@Test
	public void refund(){
		try {
			String refundQueryId = TableUtil.genId();
//		String origQueryId = "15240347129775028";
//		String origTranNo = "2018041821001004250576709861";
			String origQueryId = "15241885094171519";
			String origTranNo = "2018042021001004000581075449";
			int money = 1;
			AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
			String refundAmount = new BigDecimal(money).movePointLeft(2).toString();// 付款金额
			StringBuffer bizContent = new StringBuffer();
			bizContent.append("{")
			.append("\"out_trade_no\":\"").append(origQueryId).append("\",")
			.append("\"trade_no\":\"").append(origTranNo).append("\",")
			.append("\"refund_amount\":\"").append(refundAmount).append("\",")
			.append("\"refund_reason\":\"").append("开开出行退款").append("\",")
			.append("\"out_request_no\":\"").append(refundQueryId).append("\"")
			.append("}");
			AlipayTradeRefundResponse response = aliClient.alipayClient.execute(request);
			logger.info("支付宝开放平台退款origQueryId={} response", new Object[]{origQueryId,JsonUtil.toJson(response)});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void query(){
		String queryId = "15241198009913384";
		AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
		request.setBizContent("{\"out_trade_no\":\""+queryId+"\"}");
		AlipayTradeQueryResponse response = null;
		try {
			response = (AlipayTradeQueryResponse) aliClient.alipayClient.execute(request);
		} catch (AlipayApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("[支付宝支付订单查询]"+JsonUtil.toJson(response));
	}
}
