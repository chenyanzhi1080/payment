package com.xiaoerzuche.biz.zmxy.vo.antifraud;

import java.util.List;

public class MemberAntifraudResultVo {
	private int hit;
	private String score;
	private List<String> verifyResults;
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public List<String> getVerifyResults() {
		return verifyResults;
	}
	public void setVerifyResults(List<String> verifyResults) {
		this.verifyResults = verifyResults;
	}
	@Override
	public String toString() {
		return "MemberAntifraudResultVo [hit=" + hit + ", score=" + score + ", verifyResults=" + verifyResults + "]";
	}
	
}
