package com.xiaoerzuche.common.exception;

public class XiaoErRuntimeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public XiaoErRuntimeException(String msg){
		super(msg);
	}
	
	public XiaoErRuntimeException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
