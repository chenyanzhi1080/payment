package com.xiaoerzuche.biz.payment.web.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xiaoerzuche.biz.payment.dao.sqlBuilder.PayTypeConfigBuilder;
import com.xiaoerzuche.biz.payment.service.PayTypeConfigService;
import com.xiaoerzuche.biz.payment.vo.ReturnDataVo;
import com.xiaoerzuche.biz.payment.vo.payType.PayTypeVo;
import com.xiaoerzuche.biz.payment.web.rpc.BaseController;
import com.xiaoerzuche.common.util.CheckUtil;

@RestController
@RequestMapping("/api")
public class PaymentApi extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(PaymentApi.class);
	
	@Autowired
	private PayTypeConfigService payTypeConfigService;
	
	/**
	 * 快钱支持的信用卡代码列表API
	 * @return
	 */
	@RequestMapping(value = "/v2.0/pay/types", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	private ReturnDataVo bankList(String bzAppid, String terminalAppid, HttpServletRequest request){
		CheckUtil.assertNotBlank(bzAppid, "bzAppid is null");
		CheckUtil.assertNotBlank(terminalAppid, "terminalAppid is null");
		PayTypeConfigBuilder builder = new PayTypeConfigBuilder();
		builder.setBzAppid(bzAppid).setTerminalAppid(terminalAppid).setStatus(0);
		List<PayTypeVo> list = payTypeConfigService.list(builder);
		return responseEntity(list);
	}
	
}