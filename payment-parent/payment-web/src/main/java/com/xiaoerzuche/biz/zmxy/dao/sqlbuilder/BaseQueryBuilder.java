package com.xiaoerzuche.biz.zmxy.dao.sqlbuilder;

import static com.xiaoerzuche.biz.zmxy.constant.Constants.ZERO;

import com.xiaoerzuche.common.constant.Constants;

import static com.xiaoerzuche.biz.zmxy.constant.Constants.INDEX;

@SuppressWarnings("unchecked")
public abstract class BaseQueryBuilder<BEAN> {

	private Integer offset;
	private Integer limit;

	public Integer getOffset() {
		return (offset == null || offset <= ZERO) ? INDEX
				: offset;
	}

	public BEAN offset(Integer offset) {
		this.offset = offset;
		return (BEAN) this;
	}

	public Integer getLimit() {
		return (limit == null || limit <= ZERO) ? Constants.DEFAULT_PAGE_SIZE
				: limit;
	}

	public Integer getStart() {
		return (getOffset() == null || getOffset() < INDEX) ? ZERO
				: (getOffset() - 1) * getLimit();

	}

	public BEAN limit(Integer limit) {
		this.limit = limit;
		return (BEAN) this;
	}
	

	@Override
	public String toString() {
		return "BaseQueryBuilder [offset=" + offset + ", limit=" + limit + "]";
	}
}