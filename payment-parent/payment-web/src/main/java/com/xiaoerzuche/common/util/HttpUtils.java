package com.xiaoerzuche.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xiaoerzuche.common.enu.ErrorCode;
import com.xiaoerzuche.common.exception.ErrorCodeException;

/**
 * http请求工具类
 * 
 * @author Nick C
 * 
 */
public class HttpUtils {
	private static final Log logger = LogFactory.getLog(HttpUtils.class);

	static {
		HttpUtils.setAllowRestrictedHeaders(true);
	}

	/**
	 * 打开Http Header安全限制</br>
	 * 
	 * @param allowRestrictedHeaders
	 *            true
	 */
	public static void setAllowRestrictedHeaders(boolean allowRestrictedHeaders) {
		System.setProperty("sun.net.http.allowRestrictedHeaders", allowRestrictedHeaders + "");
	}

	/**
	 * 解析url</br> url必须以http://打头,并校验该URL是否设置了host</br>
	 * 
	 * @param url
	 *            需要解析的url
	 * @return 将url与ip返回
	 */
	public static String[] parseUrl(String url) {
		if (!url.startsWith("http://")) {
			throw new IllegalArgumentException("url[" + url + "]只能以http://开头");
		}
		URL oUrl = null;
		try {
			oUrl = new URL(url);
		}
		catch (MalformedURLException e) {
			logger.error("url:" + url + " " + e.getMessage());
			throw new RuntimeException(e.getMessage(), e);
		}
		String host = oUrl.getHost();
		String ip;
		if (isValidIp(host)) {
			ip = host;
		}
		else {
			InetAddress address = null;
			try {
				address = InetAddress.getByName(host);
			} catch (UnknownHostException e) {            
				logger.error("UnknownHostException e"+e);
			}
			ip = address.getHostAddress();

			if (StringUtils.isEmpty(ip)) {
				logger.error("没有设置host[" + host + "] url:" + url);
				throw new RuntimeException("host[" + host + "]没有配置映射关系.");
			}
		}
		url = url.replaceFirst("http://" + host, "http://" + ip);
		return new String[] { url, host };
	}

	/**
	 * 验验是否是有效的ip</br>
	 * 
	 * @param ip
	 *            需要验证的ip
	 * @return
	 */
	public static boolean isValidIp(String ip) {
		if (StringUtils.isEmpty(ip)) {
			return false;
		}
		if (ip.length() < 7) {
			return false;
		}
		String regex = "^[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}$";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(ip);
		return m.find();
	}

	/**
	 * 解析url,将IP转换成域名</br>
	 * 
	 * @param url
	 *            需要转换的url
	 * @param host
	 *            域名
	 * @return 转换后的url
	 */
	public static String getUrl(String url, String host) {
		if (!url.startsWith("http://")) {
			throw new IllegalArgumentException("url[" + url + "]只能以http://开头");
		}
		if (StringUtils.isEmpty(host)) {
			return url;
		}
		URL oUrl = null;
		try {
			oUrl = new URL(url);
		}
		catch (MalformedURLException e) {
			logger.error("url:" + url + " " + e.getMessage());
			throw new RuntimeException(e.getMessage(), e);
		}
		String ip = oUrl.getHost();
		url = url.replaceFirst("http://" + ip, "http://" + host);
		return url;
	}

	/**
	 * 发送get请求</br>
	 * 
	 * @param strurl
	 * @return 网页代码.
	 */
	public static String doGet(String url) {
		return doGet(url, -1, -1);
	}

	/**
	 * 发送get请求</br>
	 * 
	 * @param url
	 *            请求的url
	 * @param checkHost
	 *            是否检查host属性有没有设置?
	 * @return 请求的结果
	 */
	public static String doGet(String url, boolean checkHost) {
		if (checkHost) {
			return doGet(url, -1, -1);
		}
		else {
			return doGet(url, null, -1, -1);
		}
	}

	public static String doGet(String url, long connectTimeout, long readTimeout, boolean checkHost) {
		if (checkHost) {
			return doGet(url, connectTimeout, readTimeout);
		}
		else {
			return doGet(url, null, connectTimeout, readTimeout);
		}
	}

	/**
	 * 发送get 请求</br>
	 * 
	 * @param url
	 *            请求的url
	 * @param host
	 *            域名
	 * @return 请求的结果
	 */
	public static String doGet(String url, String host) {
		if (StringUtils.isEmpty(host)) {
			throw new IllegalArgumentException("参数host不能为空.");
		}
		return doGet(url, host, -1, -1);
	}

	/**
	 * 发送get 请求</br>
	 * 
	 * @param url
	 *            请求的url
	 * @param connectTimeout
	 *            连接超时时间
	 * @param readTimeout
	 *            请求超时时间
	 * @return 请求的结果
	 */
	public static String doGet(String url, long connectTimeout, long readTimeout) {
		String[] strs = parseUrl(url);
		url = strs[0];
		String host = strs[1];
		if (StringUtils.isEmpty(host)) {
			throw new IllegalArgumentException("参数host不能为空.");
		}
		return doGet(url, host, connectTimeout, readTimeout);
	}

	/**
	 * 发送 get请求</br>
	 * 
	 * @param url
	 *            请求的url
	 * @param host
	 *            域名
	 * @param connectTimeout
	 *            连接超时时间
	 * @param readTimeout
	 *            请求超时时间
	 * @return 请求的结果
	 */
	private static String doGet(String url, String host, long connectTimeout, long readTimeout) {
		StringBuilder sb = new StringBuilder();
		BufferedReader in = null;
		try {
			URL oUrl = new URL(url);

			HttpURLConnection conn = (HttpURLConnection) oUrl.openConnection();

			if (connectTimeout > 0) {
				conn.setConnectTimeout((int) connectTimeout);
			}
			if (readTimeout > 0) {
				conn.setReadTimeout((int) readTimeout);
			}
			// System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
			conn.setRequestMethod("GET");
			if (StringUtils.isNotEmpty(host)) {
				conn.setRequestProperty("Host", host);
			}

			in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
			String str;
			while ((str = in.readLine()) != null) {
				if (sb.length() > 0) {
					sb.append('\n');
				}
				sb.append(str);
			}
			in.close();
			conn.disconnect();
		}
		catch (IOException e) {
			logger.error("host:" + host + " url:" + getUrl(url, host) + " " + e.getMessage());
			throw new RuntimeException(e.getMessage(), e);
		}
		return sb.toString();
	}

	public static String doPost(String url, Map<String, String> map) {
		return doPost(url, map, -1, -1);
	}

	/**
	 * 发送 post请求</br>
	 * 
	 * @param url
	 *            请求的url
	 * @param map
	 *            参数
	 * @param connectTimeout
	 *            连接超时时间
	 * @param readTimeout
	 *            请求超时时间
	 * @return 请求的结果
	 */
	public static String doPost(String url, Map<String, String> map, long connectTimeout, long readTimeout) {
		String[] strs = parseUrl(url);
//		url = strs[0];
		String host = strs[1];
		return doPost(url, host, map, connectTimeout, readTimeout);
	}

	/**
	 * 发送 post请求</br>
	 * 
	 * @param url
	 *            请求的url
	 * @param map
	 *            参数
	 * @param connectTimeout
	 *            连接超时时间
	 * @param readTimeout
	 *            请求超时时间
	 * @return 请求的结果
	 */
	private static String doPost(String url, String host, Map<String, String> map, long connectTimeout, long readTimeout) {// NOPMD
		if (StringUtils.isEmpty(host)) {
			throw new IllegalArgumentException("参数host不能为空.");
		}
		StringBuilder param = new StringBuilder();
		if (map != null) {
			Iterator<Entry<String, String>> iterator = map.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<String, String> entry = iterator.next();
				String key = entry.getKey();
				String value = entry.getValue();
				if (param.length() > 0) {
					param.append('&');
				}
				if (value == null) {
					continue;
				}
				param.append(key).append('=').append(StringUtil.urlEncode(value));
			}
		}
		// System.out.println(param.toString());
		StringBuilder sb = new StringBuilder();
		BufferedReader in = null;
		try {
			// System.setProperty("sun.net.http.allowRestrictedHeaders", "true");

			URL postURL = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) postURL.openConnection();
		
			if (connectTimeout > 0) {
				conn.setConnectTimeout((int) connectTimeout);
			}
			if (readTimeout > 0) {
				conn.setReadTimeout((int) readTimeout);
			}
			conn.setRequestProperty("Host", host);

			conn.setUseCaches(false); // do not use cache
			conn.setDoOutput(true); // use for output
			conn.setDoInput(true); // use for Input

			conn.setRequestMethod("POST"); // use the POST method to submit the

			PrintWriter out = new PrintWriter(conn.getOutputStream());

			out.print(param.toString()); // send to server
			out.close(); // close outputstream

			in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));

			String aline;
			while (null != (aline = in.readLine())) {
				sb.append(aline).append('\n');
			}
			in.close();
			conn.disconnect();
		}
		catch (IOException e) {
			String ip = ServerUtil.getIp(host);
			logger.error("host:" + host + " ip:" + ip + " url:" + url + " " + e.getMessage());
			logger.info(e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
		return sb.toString();
	}

	/**
	 * 发送 post请求</br>
	 * 
	 * @param url
	 *            请求的url
	 * @param entries
	 *            参数
	 * @return 请求的结果
	 */
	public static String doGet(String url, Entry<String, Object>... entries) {
		url = UrlUtil.appendParams2Url(url, entries);
		logger.debug("do get!" + "[url=" + url + "]");
		String result = HttpUtils.doGet(url);
		return result;
	}

	/**
	 * 发送 post请求</br>
	 * 
	 * @param url
	 *            请求的url
	 * @param entries
	 *            参数
	 * @return 请求的结果
	 */
	public static String doPost(String url, Entry<String, Object>... entries) {
		logger.debug("do post!" + "[url=" + url + "]");
		Map<String, String> map = new HashMap<String, String>();
		if (entries != null) {
			for (int i = 0; i < entries.length; i++) {
				map.put(entries[i].getKey(), entries[i].getValue().toString());
			}
		}
		String result = HttpUtils.doPost(url, map);
		return result;
	}

	
	public static String UtilDecoder(String str){
		try {
			return java.net.URLDecoder.decode(str, "utf-8");
		} catch (UnsupportedEncodingException e) {
			throw new ErrorCodeException(ErrorCode.UNKOWN.getErrorCode(), e.getMessage());
		}
	}
}
