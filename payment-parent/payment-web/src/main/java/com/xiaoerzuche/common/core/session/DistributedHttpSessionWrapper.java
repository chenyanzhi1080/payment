package com.xiaoerzuche.common.core.session;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

/**
 * 
 * @author Nick C
 *
 */
@SuppressWarnings("deprecation")
public class DistributedHttpSessionWrapper implements HttpSession {

	private HttpSession orgiSession;

	private String sid;

	boolean changed = false;
	
	boolean invalidated = false;
	
	Map<String, Object> allAttribute;

	public DistributedHttpSessionWrapper(String sid, HttpSession session, Map<String, Object> allAttribute) {
		this.orgiSession = session;
		this.sid = sid;
		this.allAttribute = allAttribute;
	}

	@Override
	public String getId() {
		return this.sid;
	}

	@Override
	public void setAttribute(String name, Object value) {
		changed = true;
		allAttribute.put(name, value);
	}

	@Override
	public Object getAttribute(String name) {
		return allAttribute.get(name);
	}

	@Override
	public Enumeration<String> getAttributeNames() {
		Set<String> set = allAttribute.keySet();
		Iterator<String> iterator = set.iterator();
		return new MyEnumeration<String>(iterator);
	}

	private class MyEnumeration<T> implements Enumeration<T> {
		Iterator<T> iterator;

		public MyEnumeration(Iterator<T> iterator) {
			super();
			this.iterator = iterator;
		}

		@Override
		public boolean hasMoreElements() {
			return iterator.hasNext();
		}

		@Override
		public T nextElement() {
			return iterator.next();
		}

	}

	@Override
	public void invalidate() {
		this.invalidated = true;
	}

	@Override
	public void removeAttribute(String name) {
		changed = true;
		allAttribute.remove(name);
	}

	@Override
	public long getCreationTime() {
		return orgiSession.getCreationTime();
	}

	@Override
	public long getLastAccessedTime() {
		return orgiSession.getLastAccessedTime();
	}

	@Override
	public int getMaxInactiveInterval() {
		return orgiSession.getMaxInactiveInterval();
	}

	@Override
	public ServletContext getServletContext() {
		return orgiSession.getServletContext();
	}

	@Override
	public Object getValue(String arg0) {
		return orgiSession.getValue(arg0);
	}

	@Override
	public String[] getValueNames() {
		return orgiSession.getValueNames();
	}

	@Override
	public boolean isNew() {
		return orgiSession.isNew();
	}

	@Override
	public void putValue(String arg0, Object arg1) {
		orgiSession.putValue(arg0, arg1);
	}

	@Override
	public void removeValue(String arg0) {
		orgiSession.removeValue(arg0);
	}

	@Override
	public void setMaxInactiveInterval(int arg0) {
		orgiSession.setMaxInactiveInterval(arg0);
	}

	@Override
	public HttpSessionContext getSessionContext() {
		return orgiSession.getSessionContext();
	}
}
