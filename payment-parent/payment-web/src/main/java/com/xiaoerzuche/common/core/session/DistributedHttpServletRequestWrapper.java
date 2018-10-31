package com.xiaoerzuche.common.core.session;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @author Nick C
 *
 */
public class DistributedHttpServletRequestWrapper extends javax.servlet.http.HttpServletRequestWrapper {
	private HttpServletRequest orgiRequest;
	private DistributedHttpSessionWrapper session;

	public DistributedHttpServletRequestWrapper(HttpServletRequest request, DistributedHttpSessionWrapper session) {
		super(request);
		if (session == null)
			throw new DistributedSessionException("session实例不能为空");
		if (request == null)
			throw new DistributedSessionException("request实例不能为空");
		this.orgiRequest = request;
		this.session = session;
	}

	public DistributedHttpSessionWrapper getSession(boolean create) {
		orgiRequest.getSession(create);
		return session;
	}

	public DistributedHttpSessionWrapper getSession() {
		return session;
	}

}
