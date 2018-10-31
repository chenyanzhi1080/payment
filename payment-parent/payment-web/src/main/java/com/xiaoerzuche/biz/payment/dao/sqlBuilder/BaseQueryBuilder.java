package com.xiaoerzuche.biz.payment.dao.sqlBuilder;



import com.xiaoerzuche.common.constant.Constants;


@SuppressWarnings("unchecked")
public abstract class BaseQueryBuilder<BEAN> {

	private Integer offset;
	private Integer limit;

	public Integer getOffset() {
		return (offset == null || offset <= 0) ? 1
				: offset;
	}

	public BEAN offset(Integer offset) {
		this.offset = offset;
		return (BEAN) this;
	}

	public Integer getLimit() {
		return (limit == null || limit <= 0) ? Constants.DEFAULT_PAGE_SIZE
				: limit;
	}

	public Integer getStart() {
		return (getOffset() == null || getOffset() < 1) ? 0
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