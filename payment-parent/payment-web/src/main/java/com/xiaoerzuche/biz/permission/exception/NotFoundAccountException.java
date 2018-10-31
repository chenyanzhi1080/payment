package com.xiaoerzuche.biz.permission.exception;
/**
 * 账号不存在异常，需要外部去处理
 * @author Nick C
 *
 */
public class NotFoundAccountException extends Exception {
	
	public NotFoundAccountException(String string) {
		super(string);
	}

	private static final long serialVersionUID = 1L;

}
