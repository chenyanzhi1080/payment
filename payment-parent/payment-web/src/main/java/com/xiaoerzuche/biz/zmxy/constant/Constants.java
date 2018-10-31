package com.xiaoerzuche.biz.zmxy.constant;

public final class Constants {
	//规则模板中的默认规则
	public static final String DEFAULT = "default"; 
	
	public static final Integer INDEX = 1;
	public static final Integer ZERO = 0;

	public static final int SHARD = 10;
	
	public static final String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	public static final String ACTUALINFOKEY = "actualInfo:";
	
	private Constants(){
		throw new RuntimeException("该类是常量类");
	}

}
