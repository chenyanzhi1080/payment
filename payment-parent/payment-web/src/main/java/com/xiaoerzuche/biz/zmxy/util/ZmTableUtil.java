package com.xiaoerzuche.biz.zmxy.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.xiaoerzuche.biz.zmxy.enu.FeedbackTypeEnu;

/**
 * 分表工具类
 * @author Nick C
 *
 */
public class ZmTableUtil {
	/**
	 * 产生信用卡绑定用户ID
	 * @param account
	 * @return
	 */
	public static String genMemberCreditId(String account){
		StringBuilder sb = new StringBuilder();
		return sb.append("ZM").append(System.currentTimeMillis()).append(account.substring(7, 11)).toString();
	}
	
	public static String genZmxyFeedbackBillNo(String orderNo, int billType){
		return orderNo+"zl"+String.valueOf(System.currentTimeMillis()).substring(8,11)+billType;
	}
	
	public static String genTransactionId(String memberCreditId){
		//yyyyMMddHHmmssSSS+memberCreditId
		Date date=new Date();
		DateFormat df=new SimpleDateFormat("yyyyMMddhhmmssSSS");
		
		return df.format(date)+"D"+memberCreditId;
	}
	
	public static void main(String[] args){
//		String id = ZmTableUtil.genMemberCreditId("18289767660");
//		String id = ZmTableUtil.genTransactionId("ZM14943114209457660");
//		System.out.println(id);
//		System.out.println(id.length());
//		id = ZmTableUtil.genTransactionId("ZM14943114209457660");
//		System.out.println(id);
//		System.out.println(id.length());
//		id = ZmTableUtil.genTransactionId("ZM14943114209457660");
//		System.out.println(id);
//		System.out.println(id.length());
		String billNo = ZmTableUtil.genZmxyFeedbackBillNo("00146439986654410093", 509);
		System.out.println(billNo);
		
		String memberCreditId = "18289767660";
		String transactionId = "2"+ZmTableUtil.genTransactionId(memberCreditId);
		System.out.println(transactionId);
		System.out.println(transactionId.length());
	}
	
}
