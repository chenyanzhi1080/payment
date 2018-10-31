package com.xiaoerzuche.biz.payment.util;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.parser.json.JsonConverter;

public class XmlUtil {
	public static SortedMap<String, String> parseXmlToMap(String xml) {
		SortedMap<String, String> map = new TreeMap<String, String>();
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
					map.put(element.getName(), element.getValue());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 将xml转为json
	 * @param xml
	 * @param t
	 * @return
	 * @throws AlipayApiException
	 * @throws JDOMException
	 * @throws IOException
	 */
	public static <T> T parseXmlToBean(String xml, Class<T> t) throws AlipayApiException, JDOMException, IOException{
		Map<String, String> map = new HashMap<String, String>();
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
				map.put(element.getName(), element.getValue());
			}
		}
		if(!map.isEmpty()){
			JsonConverter converter = new JsonConverter();
				return converter.fromJson(map, t);
		}
		return null;
	}
	
}
