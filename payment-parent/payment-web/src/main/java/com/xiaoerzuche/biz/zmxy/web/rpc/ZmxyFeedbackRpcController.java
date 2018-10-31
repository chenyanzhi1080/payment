package com.xiaoerzuche.biz.zmxy.web.rpc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xiaoerzuche.biz.payment.vo.ReturnDataVo;
import com.xiaoerzuche.biz.payment.web.rpc.BaseController;
import com.xiaoerzuche.biz.zmxy.enu.ZmOderStatusEnu;
import com.xiaoerzuche.biz.zmxy.handler.bill.BillHandler;
import com.xiaoerzuche.biz.zmxy.handler.bill.CreateOrderHandler;
import com.xiaoerzuche.biz.zmxy.handler.bill.OrderFinishHandler;
import com.xiaoerzuche.biz.zmxy.vo.OrderCreateRequestVo;
import com.xiaoerzuche.biz.zmxy.vo.ZhimaDataFeedbackRequstVo;
import com.xiaoerzuche.biz.zmxy.vo.ZhimaDataSettledRequestVo;

/**
 * 芝麻信用数据反馈
 * Created by lqq on 2017/8/17.
 * update by cyz on 2017/8/21.
 */
@RestController
@RequestMapping("/rpc")
public class ZmxyFeedbackRpcController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(ZmxyFeedbackRpcController.class);
    
	@Autowired
	private ApplicationContext context;
    @Autowired
    private CreateOrderHandler createOrderHandler;
    @Autowired
    private OrderFinishHandler orderFinishHandler;
    
    /**
     * 芝麻信用数据反馈：业务订单开始使用
     * @param createRequestVo
     * @return
     */
    @RequestMapping(value = "/v1.0/zmxy/zhimaDataFeedback/orderCreate", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    private ReturnDataVo creatOrder(@RequestBody OrderCreateRequestVo createRequestVo){
    	logger.info("[业务订单开始使用]"+createRequestVo);
    	long starage = System.currentTimeMillis();
    	try {
			createOrderHandler.createBill(createRequestVo, createRequestVo.getOrderNo());
		} catch (Exception e) {
			logger.info("[业务订单开始使用出现并发orderNo={}]{}",new Object[]{createRequestVo.getOrderNo(),e});
		}
    	long endage = System.currentTimeMillis();
		logger.info("[业务订单开始使用处理耗时]{}",new Object[]{(endage-starage)});
    	return this.responseEntity(null);
    }
    
    /**
     * 业务结清
     * @param settledRequestVo
     * @return
     */
    @RequestMapping(value = "/v1.0/zmxy/zhimaDataFeedback/settled", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    private ReturnDataVo settled(@RequestBody ZhimaDataSettledRequestVo settledRequestVo){
    	//结清
    	logger.info("[业务反馈结清]"+settledRequestVo);
    	long starage = System.currentTimeMillis();
    	BillHandler billHandler = (BillHandler) context.getBean(settledRequestVo.getTypeEnu().getHandleClazz());
    	try {
			billHandler.settled(settledRequestVo, settledRequestVo.getSubOrderNo()==null ? settledRequestVo.getOrderNo():settledRequestVo.getSubOrderNo());
		} catch (Exception e) {
			logger.info("[业务反馈结清出现并发orderNo={}subOrderNo{}]{}",new Object[]{settledRequestVo.getOrderNo(),settledRequestVo.getSubOrderNo(),e});
		}
    	long endage = System.currentTimeMillis();
    	logger.info("[业务反馈结清处理耗时]{}",new Object[]{(endage-starage)});
    	return this.responseEntity(null);
    }
    
    /**
     * 业务反馈上报
     * @param feedbackRequstVo
     * @return
     */
    @RequestMapping(value = "/v1.0/zmxy/zhimaDataFeedback", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    private ReturnDataVo zhimaDataFeedback(@RequestBody ZhimaDataFeedbackRequstVo feedbackRequstVo){
    	System.out.println("============="+this.getClass().getSimpleName());
    	//产生费用事件，同一事件费用发生变化，则覆盖上原先的金额
    	logger.info("[业务反馈上报]"+feedbackRequstVo);
    	long starage = System.currentTimeMillis();
    	try {
			if(feedbackRequstVo.getOderStatusEnu()==ZmOderStatusEnu.SETTLED){
				orderFinishHandler.orderFinish(feedbackRequstVo, feedbackRequstVo.getOrderNo());
			}else{
				BillHandler billHandler = (BillHandler) context.getBean(feedbackRequstVo.getTypeEnu().getHandleClazz());
				billHandler.bill(feedbackRequstVo, feedbackRequstVo.getSubOrderNo()==null ? feedbackRequstVo.getOrderNo():feedbackRequstVo.getSubOrderNo());
			}
		} catch (BeansException e) {
			logger.info("[业务反馈上报出现并发orderNo={}subOrderNo{}]{}",new Object[]{feedbackRequstVo.getOrderNo(),feedbackRequstVo.getSubOrderNo(),e});
		}
    	long endage = System.currentTimeMillis();
    	logger.info("[业务反馈上报理耗时]{}",new Object[]{(endage-starage)});
    	return this.responseEntity(null);
    }
}
