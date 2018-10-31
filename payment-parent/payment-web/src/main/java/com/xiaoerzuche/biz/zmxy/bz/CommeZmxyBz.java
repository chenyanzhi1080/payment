package com.xiaoerzuche.biz.zmxy.bz;

import java.util.List;

public class CommeZmxyBz {
	
	private List<ZmxyBz> behaviour;//失信行为业务码
	
	private List<ZmxyBz> amount;//逾期金额范围业务码
	
	private List<ZmxyBz> overdue;//逾期时间范围业务码
	
	private List<ZmxyBz> currentState;//逾期当前状态业务码
	
	private List<ZmxyBz> blackReason;//开开出行设置黑名单原因业务码

	public List<ZmxyBz> getBehaviour() {
		return behaviour;
	}

	public void setBehaviour(List<ZmxyBz> behaviour) {
		this.behaviour = behaviour;
	}

	public List<ZmxyBz> getAmount() {
		return amount;
	}

	public void setAmount(List<ZmxyBz> amount) {
		this.amount = amount;
	}

	public List<ZmxyBz> getOverdue() {
		return overdue;
	}

	public void setOverdue(List<ZmxyBz> overdue) {
		this.overdue = overdue;
	}

	public List<ZmxyBz> getCurrentState() {
		return currentState;
	}

	public void setCurrentState(List<ZmxyBz> currentState) {
		this.currentState = currentState;
	}

	public List<ZmxyBz> getBlackReason() {
		return blackReason;
	}

	public void setBlackReason(List<ZmxyBz> blackReason) {
		this.blackReason = blackReason;
	}

	@Override
	public String toString() {
		return "CommeZmxyBz [behaviour=" + behaviour + ", amount=" + amount + ", overdue=" + overdue + ", currentState="
				+ currentState + ", blackReason=" + blackReason + "]";
	}

	public CommeZmxyBz() {
		super();
	}

	public CommeZmxyBz(List<ZmxyBz> behaviour, List<ZmxyBz> amount, List<ZmxyBz> overdue, List<ZmxyBz> currentState,
			List<ZmxyBz> blackReason) {
		super();
		this.behaviour = behaviour;
		this.amount = amount;
		this.overdue = overdue;
		this.currentState = currentState;
		this.blackReason = blackReason;
	}
	
}
