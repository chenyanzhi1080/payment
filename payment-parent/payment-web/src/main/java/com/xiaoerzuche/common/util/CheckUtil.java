package com.xiaoerzuche.common.util;

import java.util.Collection;

import org.apache.commons.lang.StringUtils;

import com.xiaoerzuche.common.enu.ErrorCode;
import com.xiaoerzuche.common.exception.ErrorCodeException;

/**
 * 
 * @author Nick C
 *
 */
public class CheckUtil {
	
	/**
	 * 断言指定的对象不为null
	 * @param obj
	 * @param code
	 * @param message
	 */
	public static void assertNotNull(Object obj, int code, String message){
		if(obj == null){
			throw new ErrorCodeException(code, message);
		}
	}
	
	/**
	 * 断言指定的条件的为true
	 * @param condition
	 * @param code
	 * @param message
	 */
	public static void assertTrue(boolean condition, int code, String message){
		if(!condition){
			throw new ErrorCodeException(code, message);
		}
	}
	
	/**
	 * 断言a.equals(b)
	 * @param condition
	 * @param code
	 * @param message
	 */
	public static void assertEquals(Object a, Object b, int code, String message){
		if(!a.equals(b)){
			throw new ErrorCodeException(code, message);
		}
	}
	
	/**
	 * 断言指定的对象不为null
	 * @param obj
	 */
	public static void assertNotNull(Object obj, String message){
		if(obj == null){
			throw new ErrorCodeException(ErrorCode.PARAM.getErrorCode(), message);
		}
	}
	
	/**
	 * 断言指定的条件的为true
	 * @param condition
	 */
	public static void assertTrue(boolean condition, String message){
		if(!condition){
			throw new ErrorCodeException(ErrorCode.PARAM.getErrorCode(), message);
		}
	}
	
	/**
	 * 断言a.equals(b)
	 * @param condition
	 * @param code
	 * @param message
	 */
	public static void assertEquals(Object a, Object b, String message){
		if(!a.equals(b)){
			throw new ErrorCodeException(ErrorCode.NOENOUGH.getErrorCode(), message);
		}
	}
	
	
	/**
	 * 断言指定的集合对象不为空
	 * @param obj
	 * @param code
	 * @param message
	 */
	@SuppressWarnings("rawtypes")
	public static void assertNotEmpty(Collection coll, int code, String message){
		if(coll == null || coll.isEmpty()){
			throw new ErrorCodeException(code, message);
		}
	}
	
	/**
	 * 断言指定的集合对象不为空
	 * @param obj
	 * @param message
	 */
	@SuppressWarnings("rawtypes")
	public static void assertNotEmpty(Collection coll, String message){
		if(coll == null || coll.isEmpty()){
			throw new ErrorCodeException(ErrorCode.PARAM.getErrorCode(), message);
		}
	}

	/**
	 * 断言字符串不是空
	 * @param str
	 * @param message
	 */
	public static void assertNotBlank(String str, String message) {
		if(StringUtils.isBlank(str)){
			throw new ErrorCodeException(ErrorCode.PARAM.getErrorCode(), message);
		}
	}

	/**
	 * 断言字符串不是空
	 * @param str
	 * @param errorCode
	 * @param message
	 */
	public static void assertNotBlank(String str, int errorCode, String message) {
		if(StringUtils.isBlank(str)){
			throw new ErrorCodeException(errorCode, message);
		}
	}
}
