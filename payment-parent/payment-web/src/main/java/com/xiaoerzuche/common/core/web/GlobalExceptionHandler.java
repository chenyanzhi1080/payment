package com.xiaoerzuche.common.core.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.xiaoerzuche.common.enu.ErrorCode;
import com.xiaoerzuche.common.exception.ErrorCodeException;
import com.xiaoerzuche.common.exception.XiaoErRuntimeException;

/**
 * 统一的异常处理
 * @author Nick C
 *
 */
public class GlobalExceptionHandler implements HandlerExceptionResolver {
	
	private static final Logger logger = Logger.getLogger(GlobalExceptionHandler.class);
   
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,  
            Exception ex) {  
    	logger.error("接口请求异常", ex);
        
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	if(ex instanceof ErrorCodeException) {
        	ErrorCodeException errorCodeEx = (ErrorCodeException)ex;
        	resultMap.put("code", errorCodeEx.getCode());
        	resultMap.put("msg", errorCodeEx.getMessage());
        }else if(ex instanceof XiaoErRuntimeException) {
        	ErrorCodeException errorCodeEx = (ErrorCodeException)ex;
        	resultMap.put("code", ErrorCode.UNKOWN.getErrorCode());
        	resultMap.put("msg", errorCodeEx.getMessage());
        }else{
        	resultMap.put("code", ErrorCode.UNKOWN.getErrorCode());
        	resultMap.put("msg", ErrorCode.UNKOWN.getMessage());
        }
    	
    	resultMap.put("servletPath", request.getServletPath());
    	return new ModelAndView("ajax-error", resultMap);  
    }  
}  