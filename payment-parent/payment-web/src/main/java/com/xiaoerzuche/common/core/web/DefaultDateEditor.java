package com.xiaoerzuche.common.core.web;

import java.beans.PropertyEditorSupport;

import org.apache.commons.lang.StringUtils;

import com.xiaoerzuche.common.util.DateTimeUtil;

public class DefaultDateEditor extends PropertyEditorSupport {
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		// System.out.println("DefaultDateEditor setAsText:" + text);
		if (StringUtils.isEmpty(text)) {
			super.setValue(null);
		}
		else if (DateTimeUtil.isDateTime(text)) {
			super.setValue(DateTimeUtil.toDate(text));
		}
		else if (DateTimeUtil.isDate(text)) {
			super.setValue(DateTimeUtil.toDate(text + " 00:00:00"));
		}
		else {
			throw new IllegalArgumentException("未知时间格式[" + text + "].");
		}
	}
}
