package com.xiaoerzuche.common.util;

import com.xiaoerzuche.common.constant.Constants;

/**
 * 分页处理工具类
 * @author Nick C
 *
 */
public class PageUtil {

	/**
	 * 计算分页的起始位置
	 * @param pno
	 * @param pageSize
	 * @return
	 */
	public static int getStart(int pno, int pageSize){
		CheckUtil.assertTrue(pageSize >= 0, "页面大小不正确, pageSize="+pageSize);
		pno = pno < 1 ? 1 : pno;
		return (pno - 1) * pageSize;
	}
	
	/**
	 * 计算分页的起始位置
	 * @param pno
	 * @param pageSize
	 * @return
	 */
	public static int getStart(int pno){
		pno = pno < 1 ? 1 : pno;
		return (pno - 1) * Constants.DEFAULT_PAGE_SIZE;
	}
}
