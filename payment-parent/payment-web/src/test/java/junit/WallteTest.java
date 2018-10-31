package junit;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xiaoerzuche.biz.payment.enu.TranType;
import com.xiaoerzuche.biz.payment.model.PayOrder;
import com.xiaoerzuche.biz.payment.service.PayOrderService;
import com.xiaoerzuche.biz.payment.util.TableUtil;
import com.xiaoerzuche.biz.payment.vo.QueryVo;
import com.xiaoerzuche.biz.payment.wallet.service.WalletService;
import com.xiaoerzuche.biz.payment.web.rpc.PaymentRpcController;

public class WallteTest extends BaseTest{
	private static final Logger logger = LoggerFactory.getLogger(WallteTest.class);
	@Autowired
	private WalletService walletService;
	@Autowired
	private PayOrderService payOrderService;
	
//	@Test
	public void doRefund(){
		walletService.doRefund("14802406603857378", "14802406578532255", "018976610678161127121616", 3960);
		PayOrder origPayOrder = null;
		logger.info("[CallbackServiceImp.callback][origPayOrder={}]",new Object[]{origPayOrder});
		
	}
	
//	@Test
	public void walletDoPay(){
		QueryVo queryVo = new QueryVo("d7cfdda6a57", "018976610678161116153801", null, TranType.CONSUME.getCode(), 7);
		PayOrder srcPayOrder = payOrderService.getPayOrderBy(queryVo);
		logger.info("[支付请求查找是否存在][queryVo={}][srcPayOrder={}]", new Object[]{queryVo.toString(), srcPayOrder});
		String queryId = (null != srcPayOrder && StringUtils.isNotBlank(srcPayOrder.getQueryId())) ? srcPayOrder.getQueryId() : TableUtil.genId();//生成预支付单号
		logger.info("================"+queryId);
	}
}
