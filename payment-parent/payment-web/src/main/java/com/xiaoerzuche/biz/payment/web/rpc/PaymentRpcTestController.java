package com.xiaoerzuche.biz.payment.web.rpc;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xiaoerzuche.biz.payment.PaymentContext;
import com.xiaoerzuche.biz.payment.common.BackVerification;
import com.xiaoerzuche.biz.payment.common.PaymentConf;
import com.xiaoerzuche.biz.payment.enu.PayType;
import com.xiaoerzuche.biz.payment.enu.TranStatus;
import com.xiaoerzuche.biz.payment.enu.TranType;
import com.xiaoerzuche.biz.payment.model.PayAuth;
import com.xiaoerzuche.biz.payment.model.PayOrder;
import com.xiaoerzuche.biz.payment.service.PayAuthService;
import com.xiaoerzuche.biz.payment.service.PaySystemConfigService;
import com.xiaoerzuche.biz.payment.util.MD5Util;
import com.xiaoerzuche.biz.payment.util.TableUtil;
import com.xiaoerzuche.biz.payment.vo.PayResponeVo;
import com.xiaoerzuche.biz.payment.vo.PayVo;
import com.xiaoerzuche.common.enu.ErrorCode;
import com.xiaoerzuche.common.exception.ErrorCodeException;
import com.xiaoerzuche.common.util.CheckUtil;
import com.xiaoerzuche.common.util.JsonUtil;

/** 
* 第三方支付服务对外接口，实现银联、阿里wap版支付以及微信扫码支付和app支付及退款
* @author: cyz
* @version: payment
* @time: 2016-3-30
* 
*/
@Controller
@RequestMapping("rpc/testServer/")
public class PaymentRpcTestController {

	private static final Logger logger = LoggerFactory.getLogger(PaymentRpcTestController.class);
	@Autowired
	private PaymentContext		paymentContext;
//	@Autowired
//	private BackVerification	backVerification;
	@Autowired
	private PayAuthService payAuthService;
	
	@Value("${addresses}")
	private String addresses;
	
	
	@SuppressWarnings("unused")
	@RequestMapping("test.do")
	private String test(String sign, String appid, Integer payType, String orderId, Integer price, HttpServletRequest request, HttpServletResponse response, ModelMap map){
		map.put("data", "服务已启动");
		return "/frontJson";
	}
	
}
