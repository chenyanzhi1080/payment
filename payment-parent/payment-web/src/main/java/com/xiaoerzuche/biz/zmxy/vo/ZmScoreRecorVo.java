package com.xiaoerzuche.biz.zmxy.vo;

public class ZmScoreRecorVo {
	private int zmScore;//芝麻信用分
	private String recordTime; //变更记录时间
	public int getZmScore() {
		return zmScore;
	}
	public void setZmScore(int zmScore) {
		this.zmScore = zmScore;
	}
	public String getRecordTime() {
		return recordTime;
	}
	public void setRecordTime(String recordTime) {
		this.recordTime = recordTime;
	}
	@Override
	public String toString() {
		return "ZmScoreRecorVo [zmScore=" + zmScore + ", recordTime=" + recordTime + "]";
	}
	
}
