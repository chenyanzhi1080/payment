package com.xiaoerzuche.biz.payment.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

public class TimeUtil {
	public static final SimpleDateFormat yyyyMMddHHmmss_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");
	public static final SimpleDateFormat yyyy_MM_dd_HH_mm_ss_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static Date genrealDate(Integer a) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = null;
		Date da = new Date();
		if(a == 1)
		{
			date = DateUtils.addDays(da, -7);
		}
		else if(a == 2)
		{
			date = DateUtils.addDays(da, -30);
		}
		else if(a == 3)
		{
			date = DateUtils.addDays(da, -90);
		}
		else if(a == 4)
		{
			date = DateUtils.addDays(da, -180);
		}
		else if(a == 5)
		{
			date = DateUtils.addDays(da, -365);
		}
		else if(a == 6)
		{
			date = DateUtils.addDays(da, -365);
		}
		return date;
	}

	public static long getTimeStampByTimeString(SimpleDateFormat dateFormat,String timeString) throws ParseException{
		Date timeDate = dateFormat.parse(timeString);
		return timeDate.getTime();
	}
	
	public static void main(String[] args) {
		
		Date date = new Date();
		
		Date aa = DateUtils.addDays(date, -1);
		
		System.out.println("aa = "+aa);
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String sDate=sdf.format(date);
		Date da = null;
		try {
			da = sdf.parse(sDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(sDate);
		System.out.println("da = "+da);
	}
}
