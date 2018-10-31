package com.xiaoerzuche.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 危险字符检查
 * @author Nick C
 *
 */
public class CheckDangerCharacter {
	//关键字过滤
	private static final String pat1="(^(insert|select|delete|update|create|drop)([\\x00-\\xff&&[^a-zA-Z]&&[^_]]))|(([\\x00-\\xff&&[^a-zA-Z]&&[^_]])(insert|select|delete|update|create|drop)([\\x00-\\xff&&[^a-zA-Z]&&[^_]]))";
	private static final String pat2 ="(\\s(and|exec|count|chr|mid|master|or|truncate|char|declare|join)\\s)";
	private static final Pattern pattern1 = Pattern.compile(pat1);
	private static final Pattern pattern2 = Pattern.compile(pat2);
	
	public static boolean checkSqlInjection(String str){
		Matcher matcher = pattern1.matcher(str);
		if(matcher.find()){
			return false;
		}
		matcher = pattern2.matcher(str);
		if(matcher.find()){
			return false;
		}
		return true;
	}
}
