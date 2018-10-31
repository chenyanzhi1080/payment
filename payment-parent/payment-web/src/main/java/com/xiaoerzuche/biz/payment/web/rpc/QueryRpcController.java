package com.xiaoerzuche.biz.payment.web.rpc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xiaoerzuche.biz.payment.model.PayOrder;
import com.xiaoerzuche.biz.payment.paymentEnu.PaymentEnum;
import com.xiaoerzuche.biz.payment.service.PayOrderService;
import com.xiaoerzuche.biz.payment.service.QueryService;
import com.xiaoerzuche.biz.payment.vo.QueryResultVo;
import com.xiaoerzuche.biz.payment.vo.QueryVo;
import com.xiaoerzuche.common.util.CheckUtil;
import com.xiaoerzuche.common.util.JsonUtil;

@Controller
@RequestMapping("/rpc/payment/")
public class QueryRpcController {
	private static final Logger logger = LoggerFactory.getLogger(QueryRpcController.class);
	@Autowired 
	private QueryService queryService;
	@Autowired
	private PayOrderService payOrderService;
	
	@RequestMapping("query.do")
	private String query(String appid, String queryId, String orderId, HttpServletRequest request, HttpServletResponse response, ModelMap map){
		logger.info("[支付网关交易查询入参][appid={},queryId={},orderId={}]", new Object[]{appid, queryId, orderId});
		QueryVo queryVo = new QueryVo(appid, orderId, queryId, null, null);
		//查找原交易订单
		PayOrder srcPayOrder = payOrderService.getPayOrderBy(queryVo);
		logger.info("支付网关交易本地查询结果{}", new Object[]{srcPayOrder});
		CheckUtil.assertNotNull(srcPayOrder, PaymentEnum.ORDER_NOT_EXIST.getCode(),PaymentEnum.ORDER_NOT_EXIST.getName());
		//根据交易订单获取交易查询结果
		QueryResultVo queryResultVo = queryService.paymentQuery(srcPayOrder);
		map.put("data", JsonUtil.toJson(queryResultVo));
		return "/frontJson";
	}
}
