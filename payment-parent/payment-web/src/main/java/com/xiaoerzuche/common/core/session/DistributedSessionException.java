package com.xiaoerzuche.common.core.session;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author Nick C
 *
 */
public class DistributedSessionException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5680287893422435319L;

	public static void assertNotNull(Object obj, String msg) {
		if (obj == null) {
			throw new DistributedSessionException(msg);
		}
	}

	public static void assertNotBlank(String str, String msg) {
		if (StringUtils.isBlank(str)) {
			throw new DistributedSessionException(msg);
		}
	}

	public static void isTrue(boolean bool, String msg) {
		if (!bool) {
			throw new DistributedSessionException(msg);
		}
	}

	public DistributedSessionException() {
		super();
	}

	public DistributedSessionException(String message, Throwable cause) {
		super(message, cause);
	}

	public DistributedSessionException(String message) {
		super(message);
	}

	public DistributedSessionException(Throwable cause) {
		super(cause);
	}

}
