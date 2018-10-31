package com.xiaoerzuche.biz.payment.vo.credit;

public abstract class BaseQueryVO<VO> {
	
	private Integer offset;
	private Integer limit;
	public Integer getOffset() {
		return offset;
	}
	public VO setOffset(Integer offset) {
		this.offset = offset;
		return (VO)this;
	}
	public Integer getLimit() {
		return limit;
	}
	public VO setLimit(Integer limit) {
		this.limit = limit;
		return (VO)this;
	}
	@Override
	public String toString() {
		return "BaseQueryVO [offset=" + offset + ", limit=" + limit + "]";
	}
	
	

}
