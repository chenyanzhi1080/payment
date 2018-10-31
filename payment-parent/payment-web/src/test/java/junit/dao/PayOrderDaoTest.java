package junit.dao;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.xiaoerzuche.biz.payment.dao.PayOrderDao;
import com.xiaoerzuche.biz.payment.model.PayOrder;
import com.xiaoerzuche.common.util.DateTimeUtil;
import com.xiaoerzuche.common.util.JsonUtil;

import junit.BaseTest;

public class PayOrderDaoTest extends BaseTest{
	@Autowired
	private PayOrderDao payOrderDao;
	private Date timeStartDate = DateTimeUtil.parse("2016-11-01", "yyyy-MM-dd");
	private Date timeEndDate = DateTimeUtil.parse("2017-01-02", "yyyy-MM-dd");
//	@Test
	public void getTranRecordersCount(){
		try {
			long count = payOrderDao.getTranRecordersCount(null);
			System.out.println("=====count"+count);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
//	@Test
	public void getTranRecorders(){
		try {
			List<PayOrder> list = payOrderDao.getTranRecorders(null);
			System.out.println(JsonUtil.toJson(list));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void getByOrigTranNo(){
		try {
			PayOrder payOrder = payOrderDao.getByOrigTranNo("2016123121001004900273591020", 8);
			System.out.println(JsonUtil.toJson(payOrder));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
