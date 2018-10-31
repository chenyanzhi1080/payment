package com.xiaoerzuche.common.core.session;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xiaoerzuche.common.util.EncryptUtil;
import com.xiaoerzuche.common.util.IPUtil;
import com.xiaoerzuche.common.util.JsonUtil;

/**
 * 
 * @author Nick C
 *
 */
class DistributedSessionManager {
	protected static final Logger log = LoggerFactory.getLogger(DistributedSessionManager.class);

	private static DistributedSessionManager instance = null;

	private DistributedBaseInterface distributedBaseInterface;

	private static byte[] lock = new byte[1];

	private DistributedSessionManager(DistributedBaseInterface distributedBaseInterFace) {
		this.distributedBaseInterface = distributedBaseInterFace;
	}

	public static DistributedSessionManager getInstance(DistributedBaseInterface redis) {
		if (instance == null) {
			synchronized (lock) {
				if (instance == null) {
					instance = new DistributedSessionManager(redis);
				}
			}
		}
		return instance;
	}

	public Map<String, Object> getSession(String sid,int second) {
		String json = this.distributedBaseInterface.get(sid,second);
		if (StringUtils.isNotBlank(json)) {
			return JsonUtil.fromJson(json, Map.class);
		}
		return new HashMap<String, Object>(1);
	}

	public void saveSession(DistributedHttpSessionWrapper session) {
		Map<String, Object> map=session.allAttribute;
		if(map == null || map.isEmpty()){
			return;
		}
		String json = JsonUtil.toJson(map);
		this.distributedBaseInterface.set(session.getId(), json, session.getMaxInactiveInterval());
	}

	public void removeSession(DistributedHttpSessionWrapper session) {
		distributedBaseInterface.del(session.getId());
	}
	
	public void expire(DistributedHttpSessionWrapper session) {
		distributedBaseInterface.expire(session.getId(), session.getMaxInactiveInterval());
	}

	/**
	 * 创建cookie的sid
	 */
	public String[] createUserSid(HttpServletRequest request, String key) {
		String sid = java.util.UUID.randomUUID().toString();
		sid = sid.replace("-", "");
		//upcate by cyz 注：如果用户的更换了wifi路由，ip产生变动，就会导致签名验证失败，需要重新登录
//		String ip = IPUtil.getClientIp(request);
		String ip = "";
		String cookieSid = sid + "-" + md5(key, sid, ip);
		String actualSid = sid + "-" + ip;
		String[] result = new String[] { cookieSid, actualSid };
		return result;
	}

	private String md5(String key, String sid, String ip) {
		//upcate by cyz 注：如果用户的更换了wifi路由，ip产生变动，就会导致签名验证失败，需要重新登录
//		return EncryptUtil.md5(sid + key + ip + key);
		return EncryptUtil.md5(sid + key + key);
	}

	/**
	 * sessionid_in_cookie: 随机串+签名(签名中包含了IP、数字、key)<br>
	 * sessionid_in_redis: 随机串+ip
	 */
	public String getActualSid(String userSid, HttpServletRequest request, String key) {
		if (StringUtils.isBlank(userSid)) {
			return null;
		}
		String[] arr = userSid.split("-");
		if (arr.length != 2) {
			return null;
		}
		//upcate by cyz 注：如果用户的更换了wifi路由，ip产生变动，就会导致签名验证失败，需要重新登录
//		String ip = IPUtil.getClientIp(request);
		String ip = "";
		if (!md5(key, arr[0], ip).equals(arr[1])) {
			log.error("userSid[{}]签名验证失败，IP[{}],userCookiePre[{}]", new Object[]{userSid,ip,arr[0]});
			return null;
		}
		return arr[0] + "-" + ip;
	}

}
