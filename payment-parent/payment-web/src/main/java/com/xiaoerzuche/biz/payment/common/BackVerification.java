package com.xiaoerzuche.biz.payment.common;

import java.io.StringReader;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.InputSource;

import com.xiaoerzuche.common.util.CheckUtil;

@Component
public class BackVerification {
	private static final Logger logger = LoggerFactory.getLogger(BackVerification.class);
	
//	@Autowired
//	private PayOrderService	payOrderService;

	/**
	 * 解析微信支付的报文
	 * 
	 * @param request
	 * @return
	 */
	public static SortedMap<String, String> getRequestXmlParam(HttpServletRequest request) {
		String inputLine = null;
		StringBuilder notityXml = new StringBuilder();
		Map<String, String> valideData = new HashMap<String, String>();
		valideData.put(PaymentConf.code_pay, PaymentConf.errorcode);
		try {
			while ((inputLine = request.getReader().readLine()) != null) {
				notityXml.append(inputLine);
			}
			request.getReader().close();
		} catch (Exception e) {
			CheckUtil.assertTrue(false, "解析报文失败");
		}
		logger.info("notityXml=" + notityXml.toString());
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		try {
			StringReader read = new StringReader(notityXml.toString());
			// 创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
			InputSource source = new InputSource(read);
			// 创建一个新的SAXBuilder
			SAXBuilder sb = new SAXBuilder();
			// 通过输入源构造一个Document
			Document doc = (Document) sb.build(source);
			Element root = doc.getRootElement();// 指向根节点
			List<Element> es = root.getChildren();
			if (es != null && es.size() != 0) {
				for (Element element : es) {
					packageParams.put(element.getName(), element.getValue());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return packageParams;

	}

	public static SortedMap<String, String> parseXmlToMap(String xml) {
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		try {
			StringReader read = new StringReader(xml.toString());
			// 创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
			InputSource source = new InputSource(read);
			// 创建一个新的SAXBuilder
			SAXBuilder sb = new SAXBuilder();
			// 通过输入源构造一个Document
			Document doc = (Document) sb.build(source);
			Element root = doc.getRootElement();// 指向根节点
			List<Element> es = root.getChildren();
			if (es != null && es.size() != 0) {
				for (Element element : es) {
					packageParams.put(element.getName(), element.getValue());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return packageParams;
	}

	/**
	 * 解析银联支付和阿里支付的报文
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> getRequestMapParam(HttpServletRequest request) throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		Map<?, ?> requestParams = request.getParameterMap();
		for (Iterator<?> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			valueStr = new String(valueStr.getBytes(PaymentConf.quality_certification), PaymentConf.input_charset);
			params.put(name, valueStr);
		}
		
		return params;
	}

	/**
	 * 验证是否支付成功，并变更数据库中账单信息
	 * 
	 * @param valideData
	 *            验证数据
	 * @param isFailure
	 *            验证是否成功标志
	 * @return
	 */
	public Map<String, Object> verifyBack(Map<String, String> valideData, boolean isFailure, Integer payType) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put(PaymentConf.code_pay, PaymentConf.errorcode);
		// 验证信息为null或empty
		if (null == valideData || valideData.isEmpty()) {
			resultMap.put(PaymentConf.datas_pay, PaymentConf.empty_data_meesage);
			return resultMap;
		}
		String queryId = valideData.get(PaymentConf.queryId_pay);
		String orderId = valideData.get(PaymentConf.orderid_name);
		/**
		// 查询订单信息和原支付信息
		PayOrder payBill = new PayOrder();
		payBill.setOrderId(orderId);
		payBill.setPayType(payType);
		payBill.setTranType(PaymentConf.trantype_consumption);
		Page<PayOrder> pages = PayOrderService.queryList(payBill);
		List<PayOrder> payBills = pages.getData();
		if (null == payBills || payBills.isEmpty()) {
			PayLoggerUtil.error("没有找到对应的支付信息");
			resultMap.put(PaymentConf.datas_pay, PaymentConf.notfound_message);
			return resultMap;
		}
		PayOrder prderPayBill = payBills.get(0);
		prderPayBill.setSuccFlag(1);
		prderPayBill.setNotifyFlag(1);
		prderPayBill.setNotifyDate(new Date());
		prderPayBill.setQueryId(queryId);
		if (isFailure) {
			prderPayBill.setSuccFlag(-1);
			PayOrderService.update(prderPayBill);
			PayLoggerUtil.error("返回的支付验证失败!");
			resultMap.put(PaymentConf.datas_pay, PaymentConf.invalidate_message);
			return resultMap;
		}
		CarOrder carOrder = carOrderService.get(orderId);
		CheckUtil.assertNotNull(carOrder, ErrorCode.NOT_FOUND.getErrorCode(), "找不到对应的订单");
		boolean isSucess = PayOrderService.update(prderPayBill);
		if (isSucess) {
			if (null != carOrder.getIsMajor() && null != carOrder.getMajorOrderNo()
							&& Constants.NOT_MAJOR == carOrder.getIsMajor()) {
				carOrder.setStatus(OrderStatus.TAKED_CAR.getCode());
			} else {
				carOrder.setStatus(OrderStatus.ORDER_PAYED.getCode());
			}
			resultMap.put(PaymentConf.orderDetail, carOrder);
			carOrderService.update(carOrder, PaymentConf.system_admin);
			memberOrdersInfoDao.deleteByMemberOrders(carOrder.getBillPerson(), carOrder.getOrderId());
			resultMap.put(PaymentConf.code_pay, PaymentConf.successcode);
			resultMap.put(PaymentConf.datas_pay, PaymentConf.succesPay_message);
		} else {
			resultMap.put(PaymentConf.datas_pay, PaymentConf.failurePay_message);
		}
		**/
		return resultMap;
	}

/**
	@Transactional
	public void setOrUpdatePayBill(String orderId, String tranType, Integer payType, Double price, String payCompany) {
		logger.info("执行setOrUpdatePayBill接口--开始");
		PayOrder selectBillDto = new PayOrder();
		selectBillDto.setOrderId(orderId);
		selectBillDto.setTranType(tranType);
		selectBillDto.setPayType(payType);
		Page<PayOrder> pages = PayOrderService.queryList(selectBillDto);
		List<PayOrder> PayOrders = pages.getData();
		PayOrder PayOrder=null;
		if (null == PayOrders || PayOrders.isEmpty()) {
			 PayOrder = setPayOrder( orderId, price, tranType, payType, payCompany);
			boolean flagOne = PayOrderService.add(PayOrder);
			logger.info("flagOne="+flagOne+"执行PayOrder=" + PayOrder.toString());
			CheckUtil.assertTrue(flagOne, "支付时,插入账单失败");
		} 
		logger.info("执行setOrUpdatePayBill接口--结束");
	}
*/
	/**
	public PayOrder setPayOrder(String orderId, Double price, String tranType, Integer payType,
					String payCompany) {
		PayOrder result = new PayOrder();
		result.setOrderId(orderId);
		result.setTranAmount(price);
		result.setPayType(payType);
		result.setTranType(tranType);// 消费
		result.setPayCompany(payCompany);
		result.setTranTime(new Date());
		result.setSuccFlag(0);
		result.setNotifyFlag(0);
		return result;
		return null;
	}
*/
	/**
	// 更新退款状态和记录退款记录
	public Map<String, Object> updateRefundStatus(String orderId, Integer payType, Date tranTime, String queryId) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put(PaymentConf.code_pay, PaymentConf.errorcode);
		PayOrder payBill = new PayOrder();
		payBill.setOrderId(orderId);
		payBill.setPayType(payType);
		payBill.setTranType(PaymentConf.trantype_refund);
		logger.info("payBill=" + payBill.toString());
		Page<PayOrder> pages = PayOrderService.queryList(payBill);
		List<PayOrder> payBills = pages.getData();
		if (null == payBills || payBills.isEmpty()) {
			logger.error("没有找到对应的支付信息");
			return resultMap;
		}
		PayOrder prderPayBill = payBills.get(0);
		prderPayBill.setSuccFlag(1);
		prderPayBill.setNotifyFlag(1);
		prderPayBill.setNotifyDate(new Date());
		prderPayBill.setQueryId(queryId);
		prderPayBill.setNotifyDate(tranTime);
		CarOrder carOrder = carOrderService.get(orderId);
		carOrder.setStatus(OrderStatus.BACKFEED.getCode());
		if (PayOrderService.update(prderPayBill) && carOrderService.update(carOrder, PaymentConf.system_admin)) {
			resultMap.put(PaymentConf.code_pay, PaymentConf.successcode);
		}
		resultMap.put(PaymentConf.orderDetail, carOrder);
	}
	*/
}
