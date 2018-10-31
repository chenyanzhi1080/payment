package com.xiaoerzuche.common.core.web;

import java.util.Date;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

public class CustomWebBindingInitializer implements WebBindingInitializer  {
	@Override
	public void initBinder(WebDataBinder binder, WebRequest request) {
		binder.registerCustomEditor(Date.class, new DefaultDateEditor());
		binder.registerCustomEditor(int.class, new IntegerEditor());
		binder.registerCustomEditor(long.class, new LongEditor());
		binder.registerCustomEditor(Integer.class, new IntegerEditor());
		binder.registerCustomEditor(Long.class, new LongEditor());
		binder.registerCustomEditor(boolean.class, new BooleanEditor());
		binder.registerCustomEditor(Boolean.class, new BooleanEditor());
	}
}
