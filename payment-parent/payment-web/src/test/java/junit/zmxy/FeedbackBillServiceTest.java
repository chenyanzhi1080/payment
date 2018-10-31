package junit.zmxy;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.xiaoerzuche.biz.zmxy.dao.sqlbuilder.ZmxyFeedbackBillBuilder;
import com.xiaoerzuche.biz.zmxy.enu.FeedbackTypeEnu;
import com.xiaoerzuche.biz.zmxy.model.ZmxyFeedbackBill;
import com.xiaoerzuche.biz.zmxy.service.ZmxyFeedbackBillService;
import com.xiaoerzuche.common.util.JsonUtil;

import junit.BaseTest;

public class FeedbackBillServiceTest extends BaseTest{
	@Autowired
	private ZmxyFeedbackBillService feedbackBillService; 
	@Test
	public void list(){
		ZmxyFeedbackBillBuilder builder = new ZmxyFeedbackBillBuilder();
		builder.setOrderNo("45152049132110072502")
		.setSubOrderNo("421803080018")
		.setBillType(FeedbackTypeEnu.ILLEGAL.getCode());
		//检查目前状态为ZmBillStatus.NORMAL的账单是否存在
		List<ZmxyFeedbackBill> bills = feedbackBillService.list(builder);
		System.out.println(JsonUtil.toJson(bills));
	}
}
