package com.xiaoerzuche.biz.payment.web.rpc;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.reflect.TypeToken;
import com.xiaoerzuche.biz.payment.enu.ExpenseType;
import com.xiaoerzuche.biz.payment.enu.PayType;
import com.xiaoerzuche.biz.payment.service.PayAuthService;
import com.xiaoerzuche.biz.payment.service.PayOrderService;
import com.xiaoerzuche.biz.payment.service.PaySystemConfigService;
import com.xiaoerzuche.biz.payment.vo.ChannelVo;
import com.xiaoerzuche.biz.payment.vo.EnumVo;
import com.xiaoerzuche.biz.payment.vo.PagerVo;
import com.xiaoerzuche.biz.payment.vo.PayorderQueryVo;
import com.xiaoerzuche.biz.payment.vo.ReturnDataVo;
import com.xiaoerzuche.biz.payment.vo.TranRecordVo;
import com.xiaoerzuche.common.util.CheckUtil;
import com.xiaoerzuche.common.util.DateTimeUtil;
import com.xiaoerzuche.common.util.JsonUtil;

@RestController
@RequestMapping("/rpc")
public class PayOrderAdminController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(PayOrderAdminController.class);

	@Autowired
	private PayOrderService payOrderService;
	@Autowired
	private PayAuthService payAuthService;
	@Autowired
	private PaySystemConfigService paySystemConfigService;
	
	@RequestMapping(value = "/{version:v1\\.[0]?}/payment/channels", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ReturnDataVo channels(@PathVariable(value = "version") String version) {
		String channelJson = paySystemConfigService.get("CHANNEL_NAME").getConfigValue();
		logger.info("[获取支付网关接入业务渠道信息]{}", channelJson);
		List<ChannelVo> channelVos = JsonUtil.fromJson(channelJson, new TypeToken<List<ChannelVo>>(){});
		ReturnDataVo dataVo = new ReturnDataVo(channelVos);
		return dataVo;
	}

	@RequestMapping(value = "/v1.0/payment/enum", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ReturnDataVo enums() {
		String channelJson = paySystemConfigService.get("CHANNEL_NAME").getConfigValue();
		logger.info("[获取支付网关接入业务渠道信息]{}", channelJson);
		List<ChannelVo> channelVos = JsonUtil.fromJson(channelJson, new TypeToken<List<ChannelVo>>(){});
		
		List<EnumVo> expenseTypes = new ArrayList<EnumVo>();
		for(ExpenseType enu : ExpenseType.values()){
			EnumVo vo = new EnumVo().setCode(enu.getCode()).setName(enu.getName());
			expenseTypes.add(vo);
		}
		
		List<EnumVo> payTypes = new ArrayList<EnumVo>();
		for(PayType enu : PayType.values()){
			EnumVo vo = new EnumVo().setCode(enu.getCode()).setName(enu.getComment());
			payTypes.add(vo);
		}
		
		Map<String, Object> enumMap = new HashMap<String, Object>();
		enumMap.put("channels", channelVos);
		enumMap.put("expenseTypes", expenseTypes);
		enumMap.put("payTypes", payTypes);
		ReturnDataVo dataVo = new ReturnDataVo(enumMap);
		return dataVo;
	}
	
	/**
	 * 查询交易记录
	 * @param version
	 * @param type 支持pay(支付记录查询)和refund(退款记录查询)
	 * @param offset
	 * @param limit
	 * @param channel
	 * @param goodsNo 业务订单号
	 * @param timeStart
	 * @param timeEnd
	 * @return
	 */
	@RequestMapping(value = "/{version:v1\\.[0]?}/{type:pay|refund}/records", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ReturnDataVo payCrecorder(@PathVariable(value = "version") String version, @PathVariable(value = "type") String type,
			PayorderQueryVo payorderQueryVo) {
		logger.info("[支付网关交易记录查询][version={}][type={}]{}", new Object[]{version,type, payorderQueryVo});
		CheckUtil.assertNotNull(payorderQueryVo.getOffset(), "offset不能为null");
		CheckUtil.assertNotNull(payorderQueryVo.getLimit(), "limit不能为null");
		CheckUtil.assertTrue( (StringUtils.isNotBlank(payorderQueryVo.getTimeStart())&&StringUtils.isNotBlank(payorderQueryVo.getTimeEnd())) 
				|| (StringUtils.isBlank(payorderQueryVo.getTimeStart())&&StringUtils.isBlank(payorderQueryVo.getTimeEnd()))
				, "timeStart和timeEnd必须同时不为null");
		
		Date timeStartDate = null;
		Date timeEndDate = null;
		if(StringUtils.isNotBlank(payorderQueryVo.getTimeStart()) && StringUtils.isNotBlank(payorderQueryVo.getTimeEnd())){
			CheckUtil.assertTrue(DateTimeUtil.isDate(payorderQueryVo.getTimeStart()), "timeStart不符合yyyy-MM-dd格式");
			CheckUtil.assertTrue(DateTimeUtil.isDate(payorderQueryVo.getTimeEnd()), "timeEnd不符合yyyy-MM-dd格式");
			int dates = DateTimeUtil.getDayCount(payorderQueryVo.getTimeEnd()+" 00:00:00",payorderQueryVo.getTimeStart()+" 00:00:00");
			
			CheckUtil.assertTrue(dates>0, "timeEnd不能小于timeStart");
			CheckUtil.assertTrue(dates<180, "查询时间不能超过六个月");
		}
		
		PagerVo<TranRecordVo> pagerVo = payOrderService.getTranRecorders(type, payorderQueryVo);
		return responseEntity(pagerVo);
	}
	public static void main(String[] args){
		String timeEnd = "2017-12-28 00:00:00";
		String timeStart = "2017-12-01 00:00:00";
		int dates = DateTimeUtil.getDayCount(timeEnd,timeStart);
		System.out.println(dates);
	}
}
