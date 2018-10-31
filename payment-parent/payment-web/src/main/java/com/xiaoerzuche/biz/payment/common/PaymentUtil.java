package com.xiaoerzuche.biz.payment.common;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

import com.xiaoerzuche.biz.payment.util.TenpayUtil;
import com.xiaoerzuche.common.util.CheckUtil;

public class PaymentUtil {
	public static String genOrderId() {
		StringBuilder sb = new StringBuilder();
		Random rd = new Random();
		return sb.append(System.currentTimeMillis()).append(rd.nextInt(9000) + 1000).toString();
	}

	public static String changeCentToYuan(String moneyOfCent) {

		String result = "0.00";
		if (null == moneyOfCent) {
			return result;
		}
		try {
			int a = Integer.parseInt(moneyOfCent);
			result = String.valueOf((a + 0.0) / 100);
		} catch (NumberFormatException e) {

		}
		return result;
	}

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
	public static String getNonceStr() {
		// 随机数
		String currTime = TenpayUtil.getCurrTime();
		// 6位日期
		String strTime = currTime.substring(8, currTime.length());
		System.out.println(strTime);
		// 四位随机数
		String strRandom = String.valueOf(TenpayUtil.buildRandom(4));
		System.out.println(strRandom);
		// 10位序列号,可以自行调整。
		return strTime + strRandom;
	}
}
