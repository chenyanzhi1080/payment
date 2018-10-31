package com.xiaoerzuche.biz.payment.web.front;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alipay.api.internal.util.AlipaySignature;
import com.xiaoerzuche.biz.payment.PaymentContext;
import com.xiaoerzuche.biz.payment.alipayment.AliPaymentStrategy;
import com.xiaoerzuche.biz.payment.common.BackVerification;
import com.xiaoerzuche.biz.payment.common.PaymentConf;
import com.xiaoerzuche.biz.payment.enu.PayType;
import com.xiaoerzuche.biz.payment.paymentConfig.AliPayConfig;
import com.xiaoerzuche.biz.payment.paymentConfig.DefaultAliOpendApiClient;
import com.xiaoerzuche.biz.payment.service.CallbackService;
import com.xiaoerzuche.biz.payment.service.PamentSessionService;
import com.xiaoerzuche.biz.payment.wallet.service.WalletService;
import com.xiaoerzuche.common.enu.ErrorCode;
import com.xiaoerzuche.common.util.CheckUtil;
import com.xiaoerzuche.common.util.JsonUtil;

/** 
 * 支付退款回调处理
 * 第三方支付服务对外接口，实现银联、阿里wap版支付以及微信扫码支付和app支付
* @author: cyz
* @version: payment
* @time: 2016年5月18日
* 
*/
@Controller
@RequestMapping("/front/paymentNotify")
public class PaymentNotifyController {

	private static final Logger logger = LoggerFactory.getLogger(PaymentNotifyController.class);
	@Autowired
	private PaymentContext		paymentContext;
	@Autowired AliPaymentStrategy aliPaymentStrategy;
/**
	@Autowired
	private BackVerification	backVerification;
	@Autowired
	private PaySystemConfigService paySystemConfigService;
	@Autowired
	private PayOrderService payOrderService;
*/	
	@Autowired
	private CallbackService callbackService;
	@Autowired 
	private PamentSessionService pamentSessionService;
	@Autowired
	private WalletService walletService;
	
	private static final String RETURN_TERMINAL = "RETURN_TERMINAL:";
	
	/**
	 * 电子钱包支付或退款通知
	 * @param request
	 * @param response
	 * @param map
	 * @throws Exception
	 */
	@RequestMapping("walletnotifyPay.do")
	private String notifyForBackWalletPay(String data,  HttpServletResponse response, ModelMap map){
		CheckUtil.assertTrue(walletService.callback(data), ErrorCode.UNKOWN.getErrorCode(), "通知失败");
		map.put("code", "200");
		return "/jsonForRestFul";
	}
	
	/**
	 * 阿里支付后台通知
	 * 
	 * @param orderId
	 * @param request
	 * @param response
	 * @param map
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	@RequestMapping("notifyForBackAliPay.do")
	private void notifyForBackAliPay(HttpServletRequest request, HttpServletResponse response, ModelMap map)
					throws Exception {
		Map<String, String> params = BackVerification.getRequestMapParam(request);
		logger.info("[阿里WAP支付服务端通知]{}",params);
		Map<String, String> valideData = new HashMap<String, String>();
		aliPaymentStrategy.parseWapPayNotify(params,valideData);
		valideData.put("payType", String.valueOf(PayType.ALIWAPPAY.getCode()));
		logger.info("[阿里WAP支付服务端通知校验结果]{}",valideData);
		
		String code = valideData.get(PaymentConf.code_pay);
		boolean isFailure = PaymentConf.successcode.equals(code);
		boolean isSucess = callbackService.callback(valideData, isFailure);
		if (isSucess) {
			response.setContentType("text/html;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write("success");// 反馈给支付宝
			response.getWriter().close();
		}
	}

	@RequestMapping("notifyForBackAliAppPay.do")
	private void notifyForBackAliAppPay(HttpServletRequest request, HttpServletResponse response, ModelMap map)
					throws Exception {
		Map<String, String> params = BackVerification.getRequestMapParam(request);
		logger.info("[阿里APP支付服务端通知]{}",params);
		//refund_status有值时说明发生了退款操作，这个接口不做处理
		if(StringUtils.isNotBlank(params.get("refund_status"))){
			response.setContentType("text/html;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write("success");// 反馈给支付宝
			response.getWriter().close();
		}else{
			//订单已完结
			boolean isSucess = false;
			Map<String, String> valideData = paymentContext.getPaymentBackNotifaction(PaymentConf.ALI_PAYMENT_SERVICE_NAME,
							params);
			valideData.put("payType", String.valueOf(PayType.ALIAPPPAY.getCode()));
			logger.info("[阿里APP支付服务端通知报文解析valideData=" + valideData.toString());
			String tradeStatus = valideData.get(AliPayConfig.trade_status).toUpperCase();
			
			if("TRADE_SUCCESS".equals(tradeStatus)){
				String code = valideData.get(PaymentConf.code_pay);
				boolean isFailure = PaymentConf.successcode.equals(code);
				isSucess = callbackService.callback(valideData, isFailure);
				if (isSucess) {
					response.setContentType("text/html;charset=UTF-8");
					response.setCharacterEncoding("UTF-8");
					response.getWriter().write("success");// 反馈给支付宝
					response.getWriter().close();
				}
			}else{
				response.setContentType("text/html;charset=UTF-8");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write("success");// 反馈给支付宝
				response.getWriter().close();
			}
		}
	}

	@RequestMapping("notifyForBackAliOpenAppPay.do")
	private void notifyForBackAliOpenAppPay(HttpServletRequest request, HttpServletResponse response, ModelMap map)
					throws Exception {
		Map<String, String> params = BackVerification.getRequestMapParam(request);
		logger.info("[AliOpenAppPay支付服务端通知]{}",params);
		//refund_status有值时说明发生了退款操作，这个接口不做处理
		if(StringUtils.isNotBlank(params.get("refund_status"))){
			response.setContentType("text/html;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write("success");// 反馈给支付宝
			response.getWriter().close();
		}else{
			//订单已完结
			boolean isSucess = false;
			Map<String, String> valideData = new HashMap<String, String>();
			aliPaymentStrategy.parseOpenAppPayNotify(params,valideData);
			valideData.put("payType", String.valueOf(PayType.ALIOPENAPPPAY.getCode()));
			logger.info("[AliOpenAppPay支付服务端通知报文解析valideData=" + valideData.toString());
			String tradeStatus = valideData.get(AliPayConfig.trade_status).toUpperCase();
			
			if("TRADE_SUCCESS".equals(tradeStatus)){
				String code = valideData.get(PaymentConf.code_pay);
				boolean isFailure = PaymentConf.successcode.equals(code);
				isSucess = callbackService.callback(valideData, isFailure);
				if (isSucess) {
					response.setContentType("text/html;charset=UTF-8");
					response.setCharacterEncoding("UTF-8");
					response.getWriter().write("success");// 反馈给支付宝
					response.getWriter().close();
				}
			}else{
				response.setContentType("text/html;charset=UTF-8");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write("success");// 反馈给支付宝
				response.getWriter().close();
			}
		}
	}	
	/**
	 * 支付宝退款回调通知
	 * 
	 * @param orderId
	 * @param request
	 * @param response
	 * @param map
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	@RequestMapping("notifyForBackAliRefund.do")
	private void notifyForBackAliRefund(HttpServletRequest request, HttpServletResponse response, ModelMap map)
					throws Exception {
		Map<String, String> params = BackVerification.getRequestMapParam(request);
		logger.info("[阿里退款服务端通知报文]{}", params);
		Map<String, String> valideData = paymentContext.getRefundBackNotifaction(PaymentConf.ALI_PAYMENT_SERVICE_NAME,
						params);
		valideData.put("payType", String.valueOf(PayType.ALIWAPPAY.getCode()));
		
		logger.info("接受notifyForBackAliRefund.do后台通知结果,valideData=" + valideData.toString());
		logger.info("notifyForBackAliRefund.valideData"+JsonUtil.toJson(valideData));
		String code = valideData.get(PaymentConf.code_pay);
		boolean isFailure = PaymentConf.successcode.equals(code);
		boolean isSucess = callbackService.callback(valideData, true);
		if (isSucess) {
			response.setContentType("text/html;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write("success");//反馈给支付宝
			response.getWriter().close();
		}
	}
	
	
	/**
	 * 微信支付后台通知
	 * 
	 * @param orderId
	 * @param request
	 * @param response
	 * @param map
	 * @throws Exception
	 */
	@RequestMapping("notifyForBackWechatPay.do")
	private void notifyForBackWechatPay(HttpServletRequest request, HttpServletResponse response, ModelMap map)
					throws Exception {
		SortedMap<String, String> sortedMap = BackVerification.getRequestXmlParam(request);
		logger.info("[微信app交易服务端通知报文]{}",sortedMap);
		Map<String, String> valideData = paymentContext.getPaymentBackNotifaction(
						PaymentConf.WECHAT_PAYMENT_SERVICE_NAME, sortedMap);
		valideData.put("payType", String.valueOf(PayType.WEIXINAPPPAY.getCode()));
		logger.info("notifyForBackWechatPay.valideData"+JsonUtil.toJson(valideData));
		logger.info("接受notifyForBackWechatPay.do后台通知结果,valideData=" + valideData.toString());
		String resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
						+ "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
		String code = valideData.get(PaymentConf.code_pay);
		String queryId = valideData.get(PaymentConf.queryId_pay);
		String orderId = valideData.get(PaymentConf.orderid_name);
		boolean isFailure = PaymentConf.successcode.equals(code);
		boolean isSucess = callbackService.callback(valideData, isFailure);
		//放进微信公众号通知队列
		
		logger.info("接受notifyForBackWechatPay.do后台通知结果[code={}][queryId={}][orderId={}]",code,queryId,orderId);
//		Map<String, Object> payCall = backVerification.verifyBack(valideData, isFailure, PaymentConf.paytype_tenpay);
		if (isSucess) {
			// 支付成功
			resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
							+ "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
		}
		// 处理业务结束
		logger.info("微信支付回调数据结束");
		BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
		out.write(resXml.getBytes());
		out.flush();
		out.close();

	}
	
	/**
	 * 微信支付后台通知
	 * 
	 * @param orderId
	 * @param request
	 * @param response
	 * @param map
	 * @throws Exception
	 */
	@RequestMapping("notifyForBackWechatJsPay.do")
	private void notifyForBackWechatJsPay(HttpServletRequest request, HttpServletResponse response, ModelMap map)
					throws Exception {
		SortedMap<String, String> sortedMap = BackVerification.getRequestXmlParam(request);
		logger.info("[微信公众号交易服务端通知报文]{}",sortedMap);
		Map<String, String> valideData = paymentContext.getPaymentBackNotifaction(
						PaymentConf.WECHAT_JSPAYMENT_SERVICE_NAME, sortedMap);
		valideData.put("payType", String.valueOf(PayType.WEIXINJSPAY.getCode()));
		
		logger.info("notifyForBackWechatJsPay.valideData"+JsonUtil.toJson(valideData));
		logger.info("接受notifyForBackWechatJsPay.do后台通知结果,valideData=" + valideData);
		String resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
						+ "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
		String code = valideData.get(PaymentConf.code_pay);
		String queryId = valideData.get(PaymentConf.queryId_pay);
		String orderId = valideData.get(PaymentConf.orderid_name);
		boolean isFailure = PaymentConf.successcode.equals(code);
		boolean isSucess = callbackService.callback(valideData, isFailure);
		logger.info("接受notifyForBackWechatPay.do后台通知结果[code={}][queryId={}][orderId={}]",code,queryId,orderId);
//		Map<String, Object> payCall = backVerification.verifyBack(valideData, isFailure, PaymentConf.paytype_tenpay);
		if (isSucess) {
			// 支付成功
			resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
							+ "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
		}
		// 处理业务结束
		logger.info("微信支付回调数据结束");
		BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
		out.write(resXml.getBytes());
		out.flush();
		out.close();

	}
	
	/**
	 * 阿里支付前台通知
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping("notifyFrontAliPay.do")
	private ModelAndView notifyFrontAliPay(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String toPage = "/front/paymentNotify/changeUrl.do";
		Map<String, String> result = new HashMap<String, String>();
		Map<String, String> valideData = BackVerification.getRequestMapParam(request);
		logger.info("支付宝前端通知valideData"+JsonUtil.toJson(valideData));
		String respCode = StringUtils.isNotBlank(valideData.get("trade_no")) ? "200":"201";
		String queryId = StringUtils.isNotBlank(valideData.get("out_trade_no")) ? valideData.get("out_trade_no"):"";
		result.put(PaymentConf.orderid_name, queryId);
		result.put(PaymentConf.code_pay, respCode);
		return new ModelAndView("redirect:" + toPage, result);
	}
	
	@RequestMapping(value = "changeUrl.do")
	private ModelAndView changeUrl(HttpServletRequest request, HttpServletResponse response, ModelMap map){
		String queryId = request.getParameter(PaymentConf.orderid_name);
		String successToken = request.getParameter("code");
		String terminalServer = (String)pamentSessionService.getSession(RETURN_TERMINAL+queryId);
		boolean resultType = "200".equals(successToken);
		logger.info("[PaymentNotifyController.changeUrl][terminalServer={}][queryId={}][successToken={}]",new Object[]{terminalServer,queryId,successToken});
		
		String toPage = "";
		if(StringUtils.isNotBlank(terminalServer)){
			String[] terminalServers =terminalServer.split("\\|split\\|") ;
			if(terminalServers.length>1){
				toPage = resultType? terminalServers[0] : terminalServers[1];
			}
			logger.info("[PaymentNotifyController.changeUrl][redirect:{}]",toPage);
			return new ModelAndView("redirect:"+ toPage, map);
		}else{
//			toPage= resultType? "SUCCESS" : "FAIL";
//			return new ModelAndView("/jsonForRestFul");
			return new ModelAndView("redirect:/front/paymentNotify/notifyOk.do");
		}
	    
	}
	
	@RequestMapping(value = "notifyOk.do")
	private void success(HttpServletResponse response) throws IOException{
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
//		response.getWriter().write("success");// S
		response.getWriter().close();
	}
}
