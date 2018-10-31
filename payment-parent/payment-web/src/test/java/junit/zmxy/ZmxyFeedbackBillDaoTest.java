package junit.zmxy;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.xiaoerzuche.biz.hourlyrate.service.OrderService;
import com.xiaoerzuche.biz.hourlyrate.vo.OrderDetailVo;
import com.xiaoerzuche.biz.hourlyrate.vo.QueryOrder;
import com.xiaoerzuche.biz.zmxy.dao.ZmxyFeedbackBillDao;
import com.xiaoerzuche.biz.zmxy.model.ZmxyFeedbackBill;
import com.xiaoerzuche.common.util.JsonUtil;

import junit.BaseTest;

public class ZmxyFeedbackBillDaoTest extends BaseTest {
	@Autowired
	private ZmxyFeedbackBillDao zmxyFeedbackBillDao;
	@Test
	public void unSettledBills(){
		List<ZmxyFeedbackBill> bills = zmxyFeedbackBillDao.unSettledBills("1", new Date());
	}
	
	@Test
	public void billsToDefault(){
		List<ZmxyFeedbackBill> bills = zmxyFeedbackBillDao.billsToDefault();
	}
	@Test
	public void defaultBillsToSettled(){
		List<ZmxyFeedbackBill> bills = zmxyFeedbackBillDao.defaultBillsToSettled();
	}
	
	public static void main(String[] args){
		int a = 0;
		int b = 1;
		int c = 2;
		int d = 3;
		
		System.out.println(a/2);
		System.out.println(b/2);
		System.out.println(c/2);
		System.out.println(d/2);
	}
	
	@Autowired
	private OrderService orderService;
	@Test
	public void queryTest(){
		try {
			QueryOrder query = new QueryOrder();
			query.setOrderNo("15151474479210041910").setOperator("payment");
			OrderDetailVo orderDetailVo = orderService.getOrderDetail(query);
			System.out.println(JsonUtil.toJson(orderDetailVo));
			System.out.println(orderDetailVo.getCode()==HttpStatus.OK.value());
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
