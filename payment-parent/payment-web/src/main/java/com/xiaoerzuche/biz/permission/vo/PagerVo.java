package com.xiaoerzuche.biz.permission.vo;

import java.util.List;

import com.xiaoerzuche.common.constant.Constants;
import com.xiaoerzuche.common.core.data.jdbc.Page;

/**
 * pager组件对应的VO
 * @author Nick C
 *
 */
public class PagerVo {
	//总数据条数
	private long totalRecords;
	//总页码
	private int total;
	//当前页码
	private int pno;
	//每页多少条记录
	private int pageSize;
	//分页的数据
	private List data;
	
	public PagerVo(){
		this.pageSize = Constants.DEFAULT_PAGE_SIZE;
	}
	
	public PagerVo(Page page){
		this.totalRecords = page.getCount();
		this.data = page.getData();
		this.pageSize = Constants.DEFAULT_PAGE_SIZE;
	}
	
	public PagerVo(long totalRecords, int total, int pno, int pageSize) {
		super();
		this.totalRecords = totalRecords;
		this.total = total;
		this.pno = pno;
		this.pageSize = pageSize;
	}
	
	public void initWith(Page page){
		this.totalRecords = page.getCount();
		this.data = page.getData();
	}
	
	public long getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(long totalRecords) {
		this.totalRecords = totalRecords;
	}
	public int getTotal() {
		return (int)(totalRecords / pageSize) + 1;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getPno() {
		return pno;
	}
	public void setPno(int pno) {
		this.pno = pno;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public List getData() {
		return data;
	}
	public void setData(List data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "KKPagerVo [totalRecords=" + totalRecords + ", total=" + total
				+ ", pno=" + pno + ", pageSize=" + pageSize + "]";
	}
	
}
