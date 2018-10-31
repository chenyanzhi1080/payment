package com.xiaoerzuche.common.core.session;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 分布式Session的Filter
 * @author Nick C
 *
 */
public class DistributedSessionFilter implements Filter {
	private static final Logger log = LoggerFactory.getLogger(DistributedSessionFilter.class);

	private String cookieName;

	private DistributedSessionManager distributedSessionManager;

	private String key;
	
	private static String accessControlAllowOrigin = ""; 

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws ServletException, IOException {
		DistributedHttpServletRequestWrapper distReq = null;
		try {
			distReq = createDistributedRequest(servletRequest, servletResponse);
			HttpServletResponse response = (HttpServletResponse) servletResponse;
			response.addHeader("Access-Control-Allow-Credentials", "true");
			response.addHeader("Access-Control-Allow-Origin", accessControlAllowOrigin);
			response.addHeader("Access-Control-Allow-Headers", "Content-Type");
			response.addHeader("Access-Control-Allow-Methods", "POST,GET,PUT,DELETE,OPTIONS");
			filterChain.doFilter(distReq, servletResponse);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new DistributedSessionException(e.getMessage(), e);
		} finally {
			if(distReq!=null) {
				dealSessionAfterRequest(distReq.getSession());
			}
		}
	}

	// request处理完时操作session
	private void dealSessionAfterRequest(DistributedHttpSessionWrapper session) {
		if (session == null) {
			return;
		}
		if (session.changed) {
			distributedSessionManager.saveSession(session);
		} else if (session.invalidated) {
			distributedSessionManager.removeSession(session);
		} else {
			distributedSessionManager.expire(session);
		}
	}

	private DistributedHttpServletRequestWrapper createDistributedRequest(ServletRequest servletRequest,
			ServletResponse servletResponse) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;

		String userSid = CookieUtil.getCookie(cookieName, request);
		String actualSid = distributedSessionManager.getActualSid(userSid, request, key);
		if (StringUtils.isBlank(actualSid)) {
			if (StringUtils.isNotBlank(userSid)) {
				log.info("userSid[{}]验证不通过", userSid);
			}
			// 写cookie
			String[] userSidArr = distributedSessionManager.createUserSid(request, key);
			userSid = userSidArr[0];
			CookieUtil.setCookie(cookieName, userSid, request, response);
			actualSid =userSidArr[1];
		}
		actualSid="sid:" + actualSid;
		Map<String, Object> allAttribute = distributedSessionManager.getSession(actualSid, request.getSession()
				.getMaxInactiveInterval());
		DistributedHttpSessionWrapper distSession = new DistributedHttpSessionWrapper(actualSid, request.getSession(),
				allAttribute);
		DistributedHttpServletRequestWrapper requestWrapper = new DistributedHttpServletRequestWrapper(request,
				distSession);
		return requestWrapper;

	}

	@Override
	public void init(FilterConfig config) throws ServletException {
        InputStream in = DistributedSessionFilter.class.getClassLoader().getResourceAsStream("config/datasource.properties");    
    	try  {    
        	Properties prop =  new  Properties();    
            prop.load(in);    
            accessControlAllowOrigin = prop.getProperty("Access.Control.Allow.Origin").trim();
        }  catch  (IOException e) {    
        	log.warn("加载application.properties出错，可能会影响js的跨域请求", e);  
        }    
		
		WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(config
				.getServletContext());
		String key = config.getInitParameter("key");
		String cookieName = config.getInitParameter("cookieName");
		String cacheBean = config.getInitParameter("cacheBean");
		// 获取bean的名称，配置是"bean:"
		String redisBeanStr = cacheBean.substring(5);
		DistributedBaseInterface distributedCache = (DistributedBaseInterface) wac.getBean(redisBeanStr);

		// 获取key，有2种配置方式,1对应为bean，格式为bean:key。2字符串,格式如:sdfdf
		if (key.startsWith("bean:")) {
			this.key = (String) wac.getBean(key.substring(5));
		} else {
			this.key = key;
		}
		this.cookieName = cookieName;
		this.distributedSessionManager = DistributedSessionManager.getInstance(distributedCache);

		DistributedSessionException.assertNotBlank(key, "key不能为空");
		DistributedSessionException.assertNotBlank(cookieName, "cookieName不能为空");
		DistributedSessionException.assertNotBlank(cacheBean, "cacheBean不能为空");
		DistributedSessionException.isTrue(cacheBean.startsWith("bean:"), "cacheBean不是以[bean:]开头");
		DistributedSessionException.assertNotNull(distributedCache, "distributedCache初始化失败，为空了");
		DistributedSessionException.assertNotBlank(key, "key初始化失败，为空了");
	}

	@Override
	public void destroy() {

	}

}