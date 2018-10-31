package junit.zmxy;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.antgroup.zmxy.openplatform.api.ZhimaApiException;
import com.antgroup.zmxy.openplatform.api.request.ZhimaDataSingleFeedbackRequest;
import com.antgroup.zmxy.openplatform.api.response.ZhimaDataSingleFeedbackResponse;
import com.xiaoerzuche.biz.hourlyrate.service.OrderService;
import com.xiaoerzuche.biz.hourlyrate.vo.OrderDetailVo;
import com.xiaoerzuche.biz.hourlyrate.vo.QueryOrder;
import com.xiaoerzuche.biz.zmxy.config.ZmxyClient;
import com.xiaoerzuche.biz.zmxy.dao.ZmxyUploadDetailDao;
import com.xiaoerzuche.biz.zmxy.model.ZmxyUploadDetail;
import com.xiaoerzuche.common.util.JsonUtil;

import junit.BaseTest;

public class ZhimaDataSingleFeedbackTest extends BaseTest{
	@Test
	public void zhimaDataSingleFeedback(){
		String json = "{\"biz_date\":\"2017-08-30 16:56:03\",\"user_credentials_type\":\"0\",\"user_credentials_no\":\"4600000128931132423\",\"user_name\":\"李少转\",\"order_no\":\"622322199003180216\",\"order_status\":\"0\",\"order_start_date\":\"2017-08-30 16:56:03\",\"object_name\":\"开开出行\",\"object_id\":\"琼A1234354\",\"rent_desc\":\"当前消费:60.80元\",\"deposit_amt\":50000.0,\"bill_no\":\"622322199003180215zl100139\",\"bill_status\":\"0\",\"bill_type\":\"100\",\"bill_amt\":\"6080.0\",\"bill_last_date\":\"2017-09-06\",\"bill_payoff_date\":\"\"}";
		ZhimaDataSingleFeedbackRequest req=new ZhimaDataSingleFeedbackRequest();
		req.setChannel("apppc");
		req.setPlatform("zmop");
		req.setTypeId("1002716-default-test");
		req.setIdentity("622322199003180216#622322199003180215zl100139");
		req.setData(json);
		try {
			ZhimaDataSingleFeedbackResponse response=ZmxyClient.zhimaClient.execute(req);
			System.out.println(JsonUtil.toJson(response));
			System.out.println(response.getBody());
			System.out.println(response.getErrorCode());
			System.out.println(response.getErrorMessage());
		} catch (ZhimaApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Autowired
	private OrderService orderService;
	@Test
	public void hourlyrate(){
		String orderNo = "47150406072710000025";
		QueryOrder query = new QueryOrder();
		query.setOrderNo(orderNo);
		query.setOperator("test");
		OrderDetailVo orderDetailVo = orderService.getOrderDetail(query);
		System.out.println(JsonUtil.toJson(orderDetailVo));
		if(orderDetailVo.getCode()==2){
			System.out.println(orderDetailVo.getFeedbackType());
		}else{
			System.out.println(orderDetailVo.getMsg());
		}
	}
	
	private static final Logger logger = LoggerFactory.getLogger(ZhimaDataSingleFeedbackTest.class);
	@Autowired
	private ZmxyUploadDetailDao zmxyUploadDetailDao;
	@Test
	public void uploadFeedbackBillToZmxy(){
		ZmxyUploadDetail zmxyUploadDetail = new ZmxyUploadDetail();
		zmxyUploadDetail.setOrderNo("03150617639110269915")
		.setBillNo("03150617639110269915zl035100")
		.setUploadData("{\"biz_date\":\"2017-09-27 01:59:00\",\"user_credentials_type\":\"0\",\"user_credentials_no\":\"460103198909203018\",\"user_name\":\"吴效培\",\"order_no\":\"03150617639110269915\",\"order_status\":\"2\",\"order_start_date\":\"2017-09-24 22:58:00\",\"object_name\":\"开开出行\",\"object_id\":\"琼A0393X\",\"rent_desc\":\"当前消费:48.70元\",\"deposit_amt\":500.00,\"bill_no\":\"03150617639110269915zl035100\",\"bill_status\":\"2\",\"bill_type\":\"100\",\"bill_amt\":\"0.00\",\"bill_last_date\":\"2017-09-30\",\"bill_payoff_date\":\"2017-09-26 09:03:55\"}")
		;
		ZhimaDataSingleFeedbackRequest req=new ZhimaDataSingleFeedbackRequest();
		req.setChannel("apppc");
		req.setPlatform("zmop");
		req.setTypeId(ZmxyClient.typeId);
		req.setIdentity(zmxyUploadDetail.getOrderNo() + "#" + zmxyUploadDetail.getBillNo());
		req.setData(zmxyUploadDetail.getUploadData());
		logger.info("[上传芝麻信用req={}]", new Object[]{JsonUtil.toJson(req)});
		try {
			ZhimaDataSingleFeedbackResponse response=ZmxyClient.zhimaClient.execute(req);
			if(response.isSuccess()) {
				zmxyUploadDetail.setUploadStatus(1);
//				return true;
			}
			else {
				zmxyUploadDetail.setUploadStatus(0)
				.setUploadErrCode(response.getErrorCode())
				.setUploadMsg(response.getErrorMessage());
				logger.info("[上传芝麻信用失败{}]", zmxyUploadDetail.getUploadMsg());
			}
		} catch (ZhimaApiException e) {
			zmxyUploadDetail.setUploadStatus(0).setUploadErrCode(e.getErrCode()).setUploadMsg(e.getErrMsg());
			logger.info("[上传芝麻信用失败{}]", zmxyUploadDetail.getUploadMsg());
		}
		catch (RuntimeException e){
			zmxyUploadDetail.setUploadStatus(0);
			zmxyUploadDetail.setUploadMsg(e.getMessage());
			logger.info("[上传芝麻信用失败{}]",zmxyUploadDetail.getUploadMsg());
		}
		System.out.println("===================="+JsonUtil.toJson(zmxyUploadDetail));
	}
}
