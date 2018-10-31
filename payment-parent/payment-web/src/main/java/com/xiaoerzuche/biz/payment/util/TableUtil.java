package com.xiaoerzuche.biz.payment.util;

import java.util.Date;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateFormatUtils;

import com.xiaoerzuche.biz.payment.enu.PayType;
import com.xiaoerzuche.common.util.CheckUtil;

/**
 * 分表工具类
 * @author Nick C
 *
 */
public class TableUtil {
	private final static int ALIWAPPAY = 1;
	private final static int ALIAPPPAY = 8;
	private final static int UNIONWAPPAY = 2;
	private final static int WEIXINAPPPAY = 6;
	private final static int WEIXINJSPAY = 4;
	/**
	 * 产生信用卡绑定用户ID
	 * @param mobilePhone
	 * @return
	 */
	public static String genCreditUserId(String mobilePhone){
		StringBuilder sb = new StringBuilder();
		return sb.append("CU").append(System.currentTimeMillis()).append(mobilePhone.substring(7, 11)).toString();
	}
	
	/**
	 * 根据支付订单订单号获取对应的年份
	 * @param orderId
	 * @param tableName	表名前缀
	 * @return
	 */
	public static String getTableNameByYear(String tableName){
		return tableName+DateFormatUtils.format(new Date(), "yyyy");
	}
	
	/**
	 * 根据订单号获取对应的年份分表
	 * @param orderId
	 * @param tableName	表名前缀
	 * @return
	 */
	public static String getYearByOrderId(String orderId, String tableName){
//		CheckUtil.assertNotBlank(orderId, "时间戳格式不正确");
		String timestampStr = orderId.substring(0, 13);
		long timestamp = NumberUtils.toLong(timestampStr);
		return tableName+DateFormatUtils.format(new Date(timestamp), "yyyy");
	}
	
	/**
	 * 根据第三方支付流水号获取对应的年份分表
	 * @param tranNo
	 * @param payType
	 * @param tableName
	 * @return
	 */
	public static String getYearByTranNo(String tranNo, int payType, String tableName){
		switch (payType) {
			case ALIAPPPAY :
				return tableName = tableName+tranNo.substring(0,4);
			case ALIWAPPAY :
				return tableName = tableName+tranNo.substring(0,4);
			case UNIONWAPPAY :
				return tableName = tableName+tranNo.substring(0,4);
			case WEIXINAPPPAY : 
				return tableName = tableName+tranNo.substring(10,14);
			case WEIXINJSPAY :
				return tableName = tableName+tranNo.substring(10,14);
			default:
				break;
		}
		
		return tableName+DateFormatUtils.format(new Date(), "yyyy") ;
	}
	/**
	 * 产生订单号
	 * @return
	 */
	public static String genId(){
		StringBuilder sb = new StringBuilder();
		Random rd = new Random();
		return sb.append(System.currentTimeMillis()).append(rd.nextInt(9000)+1000).toString();
	}
	
	/**
	 * 产生业务系统服务端appid
	 * @return
	 */
	public static String genAppid(){
		return Long.toHexString(System.currentTimeMillis());
	}
	
	/**
	 * 产生业务系统服务端ApiKey
	 * @return
	 */
	public static String genApiKey(String appid){
		return MD5Util.MD5Encode(appid, "UTF-8");
	}
	/**
	 * 获取MasTxnRecord的id
	 * @return
	 */
	public static String genMasTxnRecordId(){
		StringBuilder sb = new StringBuilder();
		Random rd = new Random();
		return sb.append("MTR").append(System.currentTimeMillis()).append(rd.nextInt(9000)+1000).toString();
	}
	/**
	 * 根据masTxnRecordId 获取mas_txn_record分表
	 * @param masTxnRecordId
	 * @param tableName
	 * @return
	 */
	public static String getTableByMasTxnRecordId(String masTxnRecordId, String tableName){
		String timestampStr = masTxnRecordId.substring(3, 16);
		long timestamp = NumberUtils.toLong(timestampStr);
		return tableName+DateFormatUtils.format(new Date(timestamp), "yyyy");
	}
	
	public static void main(String[] args){
		/**
		String appid = TableUtil.genAppid();
		System.out.println("appid:"+appid);
		String apiKey = TableUtil.genApiKey(appid);
		System.out.println("apiKey:"+apiKey);
		*/
//		System.out.println(TableUtil.getYearByOrderId("14647472565257943", "pay_order_"));
//		System.out.println(TableUtil.getYearByTranNo("201605291025001881088", 0, "pay_order_"));
	}
	
}
