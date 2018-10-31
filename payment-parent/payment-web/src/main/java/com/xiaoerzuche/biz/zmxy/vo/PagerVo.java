package com.xiaoerzuche.biz.zmxy.vo;


import java.util.List;

import com.xiaoerzuche.common.constant.Constants;
import com.xiaoerzuche.common.enu.ErrorCode;
import com.xiaoerzuche.common.util.CheckUtil;
import com.xiaoerzuche.common.util.PageUtil;

/**
 * pager组件对应的VO
 * @author Nick C
 *
 */
public class PagerVo<T> {
	//总数据条数
	private long totalRecords;
	private int offset;
	//一页取多少条记录
	private int limit;
	//分页的数据
	private List<T> list;
	
	public PagerVo(){
		this.limit = Constants.DEFAULT_PAGE_SIZE;
	}
	
	public PagerVo(int offset, int limit) {
		super();
		this.offset = offset < 0 ? 0 : offset;
		this.limit = limit > 0 ? limit : Integer.MAX_VALUE;
	}

	public long getTotalRecords() {
		return totalRecords;
	}
	public PagerVo<T> setTotalRecords(long totalRecords) {
		this.totalRecords = totalRecords;
		return this;
	}
	public int getOffset() {
		return offset;
	}
	public PagerVo<T> setOffset(int offset) {
		this.offset = offset;
		return this;
	}
	public int getLimit() {
		return limit;
	}
	public PagerVo<T> setLimit(int limit) {
		this.limit = limit;
		return this;
	}
	public List<T> getList() {
		return list;
	}
	public PagerVo<T> setList(List<T> list) {
		this.list = list;
		return this;
	}
//	
//	public int getStart(){
//		return PageUtil.getStart(offset, limit);
//	}
	
	
	public PagerVo<T> check(){
		CheckUtil.assertTrue(offset >= 0, ErrorCode.PARAM.getErrorCode(), "分页参数offset不正确");
		CheckUtil.assertTrue(limit >= 0, ErrorCode.PARAM.getErrorCode(), "分页参数limit不正确");
		return this;
	}
	
	@Override
	public String toString() {
		return "PagerVo [totalRecords=" + totalRecords + ", offset=" + offset
				+ ", limit=" + limit + ", list=" + list + "]";
	}
}
