package com.xiaoerzuche.biz.zmxy.web;

import org.springframework.web.bind.annotation.ResponseBody;

import com.xiaoerzuche.biz.payment.vo.ReturnDataVo;

public class BaseController {

	public @ResponseBody ReturnDataVo responseEntity(Object data) {
		return new ReturnDataVo(data);
	}


}
