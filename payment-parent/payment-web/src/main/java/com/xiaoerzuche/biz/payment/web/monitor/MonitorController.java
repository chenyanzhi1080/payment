package com.xiaoerzuche.biz.payment.web.monitor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/** 
* 拨测URL：${contextpath}/health/dialing/monitor
* @author: cyz
* @version: payment
* @time: 2016年11月25日
* 
*/
@Controller
@RequestMapping("/health")
public class MonitorController{
	
	@RequestMapping(value = "/dialing/monitor")
	public String monitor(HttpServletRequest request, ModelMap map) {
		return "/frontJson";
	}
	
}
