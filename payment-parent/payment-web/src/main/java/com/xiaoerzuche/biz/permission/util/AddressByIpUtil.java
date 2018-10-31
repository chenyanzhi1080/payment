package com.xiaoerzuche.biz.permission.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import com.xiaoerzuche.biz.deploy.util.PinYinHelper;
import com.xiaoerzuche.biz.permission.mode.IpAddress;
import com.xiaoerzuche.common.util.HttpUtils;

import net.sf.json.JSONObject;

/**
 * 根据IP地址获取详细的地域信息
 * 
 * @project:personGocheck
 * @class:AddressUtils.java
 * @author:heguanhua E-mail:37809893@qq.com
 * @date：Nov 14, 2012 6:38:25 PM
 */
public class AddressByIpUtil {

	private static final Logger logger = LoggerFactory.getLogger(AddressByIpUtil.class);
	
	/**
	 * 获取客户端IP
	 * @param request
	 * @return
	 */
	
	
	public static String getIpAddr(HttpServletRequest request) {
		String ipAddress = request.getHeader("x-forwarded-for");
		if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
			if(ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")){
				//根据网卡取本机配置的IP
				InetAddress inet=null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				ipAddress= inet.getHostAddress();
			}
		}
		//对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if(ipAddress!=null && ipAddress.length()>15){ //"***.***.***.***".length() = 15
			if(ipAddress.indexOf(",")>0){
				ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));
			}
		}
		
		logger.info("获取客户端ipAddress="+ipAddress);
		return ipAddress; 
	}
	public static String getIpAddr2(HttpServletRequest request) {
		if (null == request) {
			return null;
		}

		String proxs[] = { "X-Forwarded-For", "Proxy-Client-IP",
				"WL-Proxy-Client-IP", "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR" ,"x-real-ip","X-Cluster-Client-Ip" };

		String ip = null;

		for (String prox : proxs) {
			ip = request.getHeader(prox);
			if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
				continue;
			} else {
				break;
			}
		}

		if (StringUtils.isBlank(ip)) {
			ip = request.getRemoteAddr();
			if(ip.equals("127.0.0.1") || ip.equals("0:0:0:0:0:0:0:1")){
				//根据网卡取本机配置的IP
				InetAddress inet=null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				ip = inet.getHostAddress();
			}
		}
		logger.info("获取客户端ipAddress="+ip);
		//对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if(ip!=null && ip.length()>15){ //"***.***.***.***".length() = 15
			if(ip.indexOf(",")>0){
				ip = ip.substring(0,ip.indexOf(","));
			}
		}
		logger.info("获取客户端真实ipAddress="+ip);
		return ip;
	}
	/**
	 * 
	 * @param content
	 *            请求的参数 格式为：name=xxx&pwd=xxx
	 * @param encoding
	 *            服务器端请求编码。如GBK,UTF-8等
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String getAddresses(String ip, String encodingString) throws UnsupportedEncodingException {
		/**
		// 这里调用pconline的接口
		String urlStr = "http://ip.taobao.com/service/getIpInfo.php?ip="+ip;//淘宝IP地理位置
		// 从http://whois.pconline.com.cn取得IP所在的省市区信息
		String returnStr = this.getResult(urlStr, ip, encodingString);
//		String returnStr = HttpUtils.doGet(urlStr);
		if (returnStr != null) {
			// 处理返回的省市区信息
			logger.info("根据IP返回省市信息:"+returnStr);
			JSONObject jsonObject = JSONObject.fromObject(returnStr);
			int code = (int) jsonObject.get("code");
			Object data = jsonObject.get("data");
			if(0==code){
				IpAddress ipAddress = (IpAddress) JSONObject.toBean(JSONObject.fromObject(data), IpAddress.class);
				logger.info("根据IP返回省市信息:"+ipAddress.toString());
				String city = ipAddress.getCity();
				return city;
			}
			
		}
		*/
		String urlStr = "http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json&ip="+ip;//新浪IP地理位置
		String returnStr = HttpUtils.doGet(urlStr);
		logger.info("根据IP返回地理位置信息:"+returnStr.toString());
		String city = "";
		if(returnStr.length()>3){
			JSONObject jsonObject = JSONObject.fromObject(returnStr);
			String ret = String.valueOf(jsonObject.get("ret"));
			if(StringUtils.isNotBlank(ret) && "1".equals(ret)){
				city = jsonObject.getString("city");
				logger.info("根据IP返回城市信息:"+returnStr.toString());
			}
		}
		return city;
	}

	/**
	 * @param urlStr
	 *            请求的地址
	 * @param content
	 *            请求的参数 格式为：name=xxx&pwd=xxx
	 * @param encoding
	 *            服务器端请求编码。如GBK,UTF-8等
	 * @return
	 */
	private String getResult(String urlStr, String content, String encoding) {
		URL url = null;
		HttpURLConnection connection = null;
		try {
			url = new URL(urlStr);
			connection = (HttpURLConnection) url.openConnection();// 新建连接实例
			connection.setConnectTimeout(10000);// 设置连接超时时间，单位毫秒
			connection.setReadTimeout(10000);// 设置读取数据超时时间，单位毫秒
			connection.setDoOutput(true);// 是否打开输出流 true|false
			connection.setDoInput(true);// 是否打开输入流true|false
//			connection.setRequestMethod("POST");// 提交方法POST|GET
			connection.setRequestMethod("GET");// 提交方法POST|GET
			connection.setUseCaches(false);// 是否缓存true|false
			connection.connect();// 打开连接端口
			DataOutputStream out = new DataOutputStream(connection.getOutputStream());// 打开输出流往对端服务器写数据
			out.writeBytes(content);// 写数据,也就是提交你的表单 name=xxx&pwd=xxx
			out.flush();// 刷新
			out.close();// 关闭输出流
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), encoding));// 往对端写完数据对端服务器返回数据
			// ,以BufferedReader流来读取
			StringBuffer buffer = new StringBuffer();
			String line = "";
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			reader.close();
			return buffer.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.disconnect();// 关闭连接
			}
		}
		return null;
	}

	public static AddressByIpUtil addressByIpUtil = new AddressByIpUtil();

	public static AddressByIpUtil getInstance() {
		if (addressByIpUtil == null) {
			addressByIpUtil = new AddressByIpUtil();
		}
		return addressByIpUtil;
	}

	public static String getAddressByIp(String ip) {
		String address = "";
		try {
			address = AddressByIpUtil.getInstance().getAddresses(ip, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		logger.info(address);
//		if (StringUtils.isNotBlank(address)) {
////			address = address.replace("市", "");
//			address = PinYinHelper.getPingYin(address);
//		}
		return address;
	}

	// 测试
	public static void main(String[] args) {
		
		// 测试ip 219.136.134.157 中国=华南=广东省=广州市=越秀区=电信
//		String ip = "122.49.20.247";
//		String ip = "125.70.11.136";
//		String ip = "120.25.249.248";
//		String ip = "219.137.135.156";//
		String ip = "36.46.46.231";//西安
//		String ip = "127.0.0.1";
//		String ip = "10.21.92.30";
		
//		String returnStr = AddressByIpUtil.getInstance().getResult(url, "219.136.134.157", "utf-8");
//		String address = AddressByIpUtil.getAddressByIp(ip);
//		System.out.println(address);
		
		String url = "http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json&ip="+ip;
		String returnStr = HttpUtils.doGet(url);
		System.out.println(returnStr);
		String city = "";
		if(returnStr.length()>3){
			JSONObject jsonObject = JSONObject.fromObject(returnStr);
			String ret = String.valueOf(jsonObject.get("ret"));
			if(StringUtils.isNotBlank(ret) && "1".equals(ret)){
				city = jsonObject.getString("city");
			}
		}
		System.out.println(city);
	}
}
