package com.xiaoerzuche.common.exception;

public class ClassNotFoundRuntimeException extends XiaoErRuntimeException {
	private static final long serialVersionUID = 1L;

	public ClassNotFoundRuntimeException(String message) {
		super(message);
	}

	public ClassNotFoundRuntimeException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
