package com.xiaoerzuche.biz.permission.exception;

/**
 * 密码不正确异常，这个异常需要外部去处理
 * @author Nick C
 *
 */
public class NotMatchingPwdException extends Exception{

	private static final long serialVersionUID = 1L;

	public NotMatchingPwdException(String string) {
		super(string);
	}

}
