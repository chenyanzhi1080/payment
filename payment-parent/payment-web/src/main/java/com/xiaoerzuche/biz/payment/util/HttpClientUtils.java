package com.xiaoerzuche.biz.payment.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.net.ssl.SSLContext;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;

import com.sinovatech.common.net.http.HttpUtil;
import com.xiaoerzuche.biz.payment.paymentConfig.WechatPayConfig;

/**
 * 
 * @author andy
 * 
 */
public class HttpClientUtils {
	public static final int connTimeout = 10000;
	public static final int readTimeout = 10000;
	public static final int connMaxTotal = 5;
	public static final int connDefaultMaxPerRoute = 5;
	public static final String charset = "UTF-8";
	private static volatile HttpClientUtils instance;
	private ConnectionConfig connConfig;
	private SocketConfig socketConfig;
	private ConnectionSocketFactory plainSF;
	private KeyStore trustStore;
	private SSLContext sslContext;
	private Registry<ConnectionSocketFactory> registry;
	private PoolingHttpClientConnectionManager connManager;
	private volatile HttpClient httpCient;
	private volatile BasicCookieStore cookieStore;
	private SSLConnectionSocketFactory sslsf;
	private HttpClientUtils() {
		// 设置连接参数
		connConfig = ConnectionConfig.custom()
				.setCharset(Charset.forName(charset)).build();
		socketConfig = SocketConfig.custom().setSoTimeout(connTimeout).build();
		RegistryBuilder<ConnectionSocketFactory> registryBuilder = RegistryBuilder
				.<ConnectionSocketFactory> create();
		plainSF = new PlainConnectionSocketFactory();
		registryBuilder.register("http", plainSF);
		// 指定信任密钥存储对象和连接套接字工厂
		try {
			trustStore = KeyStore.getInstance("PKCS12");
//			FileInputStream instream = new FileInputStream(new File(WechatPayConfig.certLocalPath));// 加载本地的证书进行https加密传输
			FileInputStream instream = new FileInputStream(new File(HttpClientUtils.class.getResource(WechatPayConfig.certLocalPath).getFile()));// 加载本地的证书进行https加密传输
			
			trustStore.load(instream,
					WechatPayConfig.certPassword.toCharArray());// 设置证书密码
			sslContext = SSLContexts
					.custom()
					.useTLS()
					.loadKeyMaterial(trustStore,
							WechatPayConfig.certPassword.toCharArray())
					.build();
			// Allow TLSv1 protocol only
			sslsf = new SSLConnectionSocketFactory(
					sslContext,
					new String[] { "TLSv1" },
					null,
					SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
			registryBuilder.register("https", sslsf);
		} catch (KeyStoreException e) {
			throw new RuntimeException(e);
		} catch (KeyManagementException e) {
			throw new RuntimeException(e);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (CertificateException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (UnrecoverableKeyException e) {
			throw new RuntimeException(e);
		}
		registry = registryBuilder.build();
		// 设置连接管理器
		connManager = new PoolingHttpClientConnectionManager(registry);
		connManager.setDefaultConnectionConfig(connConfig);
		connManager.setDefaultSocketConfig(socketConfig);
		connManager.setMaxTotal(connMaxTotal);
		connManager.setDefaultMaxPerRoute(connDefaultMaxPerRoute);
		// 指定cookie存储对象
		cookieStore = new BasicCookieStore();
		// 构建客户端
		httpCient = HttpClientBuilder.create()
				.setDefaultCookieStore(cookieStore)
				.setConnectionManager(connManager).build();
	}

	public static HttpClientUtils getInstance() {
		synchronized (HttpUtil.class) {
			if (instance == null) {
				instance = new HttpClientUtils();
			}
			return instance;
		}
	}

	public String postParameters(String url, String parameterStr)
			throws ConnectTimeoutException, SocketTimeoutException, Exception {
		return post(url, parameterStr, "application/x-www-form-urlencoded",
				charset, connTimeout, readTimeout);
	}

	public String postParameters(String url, String parameterStr,
			String charset, Integer connTimeout, Integer readTimeout)
			throws ConnectTimeoutException, SocketTimeoutException, Exception {
		return post(url, parameterStr, "application/x-www-form-urlencoded",
				charset, connTimeout, readTimeout);
	}

	public String postParameters(String url, Map<String, String> params)
			throws ConnectTimeoutException, SocketTimeoutException, Exception {
		return postForm(url, params, null, connTimeout, readTimeout);
	}

	public String postParameters(String url, Map<String, String> params,
			Integer connTimeout, Integer readTimeout)
			throws ConnectTimeoutException, SocketTimeoutException, Exception {
		return postForm(url, params, null, connTimeout, readTimeout);
	}

	public String get(String url) throws Exception {
		return get(url, charset, null, null);
	}

	public String get(String url, String charset) throws Exception {
		return get(url, charset, connTimeout, readTimeout);
	}

	/**
	 * 发送一个 Post 请求, 使用指定的字符集编码.
	 * 
	 * @param url
	 * @param body
	 *            RequestBody
	 * @param mimeType
	 *            例如 application/xml "application/x-www-form-urlencoded"
	 *            a=1&b=2&c=3
	 * @param charset
	 *            编码
	 * @param connTimeout
	 *            建立链接超时时间,毫秒.
	 * @param readTimeout
	 *            响应超时时间,毫秒.
	 * @return ResponseBody, 使用指定的字符集编码.
	 * @throws ConnectTimeoutException
	 *             建立链接超时异常
	 * @throws SocketTimeoutException
	 *             响应超时
	 * @throws Exception
	 */
	public String post(String url, String body, String mimeType,
			String charset, Integer connTimeout, Integer readTimeout)
			throws ConnectTimeoutException, SocketTimeoutException, Exception {
		HttpPost post = new HttpPost(url);
		String result = "";
		try {
			if (StringUtils.isNotBlank(body)) {
				HttpEntity entity = new StringEntity(body, ContentType.create(
						mimeType, charset));
				post.setEntity(entity);
			}
			// 设置参数
			Builder customReqConf = RequestConfig.custom();
			if (connTimeout != null) {
				customReqConf.setConnectTimeout(connTimeout);
			}
			if (readTimeout != null) {
				customReqConf.setSocketTimeout(readTimeout);
			}
			post.setConfig(customReqConf.build());

			HttpResponse res = httpCient.execute(post);
			result = IOUtils.toString(res.getEntity().getContent(), charset);
		} finally {
			post.releaseConnection();
		}
		return result;
	}
    /**
     * post请求
     * @param url
     * @param postDataXML
     * @return
     * @throws ConnectTimeoutException
     * @throws SocketTimeoutException
     * @throws Exception
     */
	public String sendPost(String url, String postDataXML)
			throws ConnectTimeoutException, SocketTimeoutException, Exception {
		String result = "";
		HttpPost post = new HttpPost(url);
		// 得指明使用UTF-8编码，否则到API服务器XML的中文不能被成功识别
		StringEntity postEntity = new StringEntity(postDataXML, "UTF-8");
		post.addHeader("Content-Type", "text/xml");
		post.setEntity(postEntity);
		try {
			Builder customReqConf = RequestConfig.custom();
			customReqConf.setConnectTimeout(connTimeout);
			customReqConf.setSocketTimeout(readTimeout);
			post.setConfig(customReqConf.build());
			HttpResponse res = httpCient.execute(post);
			result = IOUtils.toString(res.getEntity().getContent(), charset);
		} finally {
			post.releaseConnection();
		}
		return result;
	}

	/**
	 * 提交form表单
	 * 
	 * @param url
	 * @param params
	 * @param connTimeout
	 * @param readTimeout
	 * @return
	 * @throws ConnectTimeoutException
	 * @throws SocketTimeoutException
	 * @throws Exception
	 */
	public String postForm(String url, Map<String, String> params,
			Map<String, String> headers, Integer connTimeout,
			Integer readTimeout) throws ConnectTimeoutException,
			SocketTimeoutException, Exception {
		HttpPost post = new HttpPost(url);
		try {
			if (params != null && !params.isEmpty()) {
				List<NameValuePair> formParams = new ArrayList<org.apache.http.NameValuePair>();
				Set<Entry<String, String>> entrySet = params.entrySet();
				for (Entry<String, String> entry : entrySet) {
					formParams.add(new BasicNameValuePair(entry.getKey(), entry
							.getValue()));
				}
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(
						formParams, Consts.UTF_8);
				post.setEntity(entity);
			}

			if (headers != null && !headers.isEmpty()) {
				for (Entry<String, String> entry : headers.entrySet()) {
					post.addHeader(entry.getKey(), entry.getValue());
				}
			}
			// 设置参数
			Builder customReqConf = RequestConfig.custom();
			if (connTimeout != null) {
				customReqConf.setConnectTimeout(connTimeout);
			}
			if (readTimeout != null) {
				customReqConf.setSocketTimeout(readTimeout);
			}
			post.setConfig(customReqConf.build());
			HttpResponse res = httpCient.execute(post);

			return IOUtils.toString(res.getEntity().getContent(), "UTF-8");
		} finally {
			post.releaseConnection();
		}
	}

	/**
	 * 发送一个 GET 请求
	 * 
	 * @param url
	 * @param charset
	 * @param connTimeout
	 *            建立链接超时时间,毫秒.
	 * @param readTimeout
	 *            响应超时时间,毫秒.
	 * @return
	 * @throws ConnectTimeoutException
	 *             建立链接超时
	 * @throws SocketTimeoutException
	 *             响应超时
	 * @throws Exception
	 */
	public String get(String url, String charset, Integer connTimeout,
			Integer readTimeout) throws ConnectTimeoutException,
			SocketTimeoutException, Exception {
		HttpGet get = new HttpGet(url);
		String result = "";
		try {
			// 设置参数
			Builder customReqConf = RequestConfig.custom();
			if (connTimeout != null) {
				customReqConf.setConnectTimeout(connTimeout);
			}
			if (readTimeout != null) {
				customReqConf.setSocketTimeout(readTimeout);
			}
			get.setConfig(customReqConf.build());

			HttpResponse res = httpCient.execute(get);

			result = IOUtils.toString(res.getEntity().getContent(), charset);
		} finally {
			get.releaseConnection();
		}
		return result;
	}

	/**
	 * 从 response 里获取 charset
	 * 
	 * @param ressponse
	 * @return
	 */
	@SuppressWarnings("unused")
	private static String getCharsetFromResponse(HttpResponse ressponse) {
		// Content-Type:text/html; charset=GBK
		if (ressponse.getEntity() != null
				&& ressponse.getEntity().getContentType() != null
				&& ressponse.getEntity().getContentType().getValue() != null) {
			String contentType = ressponse.getEntity().getContentType()
					.getValue();
			if (contentType.contains("charset=")) {
				return contentType
						.substring(contentType.indexOf("charset=") + 8);
			}
		}
		return null;
	}
}
