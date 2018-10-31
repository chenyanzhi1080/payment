package com.xiaoerzuche.common.core.data.jdbc;

import java.util.List;

/**
 * 分页数据.
 * @author Nick C
 *
 * @param <BEAN>
 */
public class Page<BEAN> {

	/**
	 * 记录总量
	 */
	private long count;
	/**
	 * 数据
	 */
	private List<BEAN> data;

	public Page(){}
	
	public Page(long count, List<BEAN> data) {
		super();
		this.count = count;
		this.data = data;
	}

	/**
	 * 记录总量
	 * 
	 * @return
	 */
	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	/**
	 * 数据.
	 * 
	 * @return
	 */
	public List<BEAN> getData() {
		return data;
	}

	public void setData(List<BEAN> data) {
		this.data = data;
	}

	public static long getCount(Page<?> page) {
		if (page == null) {
			return 0;
		}
		else {
			return page.getCount();
		}
	}

	public static <BEAN> List<BEAN> getData(Page<BEAN> page) {
		if (page == null) {
			return null;
		}
		else {
			return page.getData();
		}
	}
}
