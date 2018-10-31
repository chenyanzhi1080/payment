<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%  
	response.setHeader("Content-Type", "application/json");
	String callback = request.getParameter("callback");
	int code = Integer.valueOf(request.getAttribute("code").toString());
	String servletPath = request.getServletPath();
	
// 	String paramsServletPath = request.getAttribute("servletPath").toString();
// 	if(paramsServletPath.indexOf(".do") == -1){
// 		//该判断是为了后台管理能够正确的弹出提示,但是好像影响了前端接口的调用,先屏蔽。
// 		response.setStatus(code);
// 	}
	
	String message = String.valueOf(request.getAttribute("msg"));
	String data = "{\"code\":"+ code +", \"msg\":\""+ message +"\", \"data\":{}}";

	//假如是Jsonp请求的话
	if(callback != null && callback.length() > 0){
		out.print(callback+"("+ data +");");  
	}else{//假如是普通的Ajax请求的话
		out.print(data);
	}
%> 