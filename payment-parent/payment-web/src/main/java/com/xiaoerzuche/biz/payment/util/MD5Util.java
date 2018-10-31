package com.xiaoerzuche.biz.payment.util;




import java.security.MessageDigest;

import org.apache.commons.lang.StringUtils;

import com.xiaoerzuche.common.util.Base64;
import com.xiaoerzuche.common.util.EncryptUtil;


public class MD5Util {

	private static String byteArrayToHexString(byte b[]) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++)
			resultSb.append(byteToHexString(b[i]));

		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n += 256;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	public static String MD5Encode(String origin, String charsetname) {
		String resultString = null;
		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			if (charsetname == null || "".equals(charsetname))
				resultString = byteArrayToHexString(md.digest(resultString
						.getBytes()));
			else
				resultString = byteArrayToHexString(md.digest(resultString
						.getBytes(charsetname)));
		} catch (Exception exception) {
		}
		return resultString;
	}

	private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
	/**
	 * 获取支付系统与业务系统交互的加密验证标识
	 * @param timeStamp
	 * @param appid
	 * @param apiKey
	 * @return
	 */
	public static String getAuthSign(String timeStamp, String appid, String apiKey){
		String authSign = timeStamp + appid + apiKey;
		authSign = MD5Util.MD5Encode(authSign, "UTF-8").toUpperCase();
		return authSign;
	}
	
	public static void main(String[] args){
		String timeStamp = String.valueOf(System.currentTimeMillis());
		System.out.println(DESUtil.getstrByte(timeStamp));
		System.out.println(EncryptUtil.sha1(timeStamp));
		System.out.println(EncryptUtil.md5(timeStamp));
		System.out.println(EncryptUtil.toHexString(timeStamp));
		System.out.println(EncryptUtil.toStringHex(timeStamp));
	}
}
