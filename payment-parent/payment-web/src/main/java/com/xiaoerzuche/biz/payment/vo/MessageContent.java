package com.xiaoerzuche.biz.payment.vo;

/** 
* 异步消息队列消息体
* @author: cyz
* @version: payment
* @time: 2016年11月8日
* 
*/
public class MessageContent {
	/**
	 * 消息类型
	 */
	private int type;
	/**
	 * 推送重试次数，限制重试5次。
	 */
	private int times;
	/**
	 * 消息体
	 */
	private String content;
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getTimes() {
		return times;
	}
	public void setTimes(int times) {
		this.times = times;
	}
	public MessageContent() {
		super();
	}
	public MessageContent(int type, int times, String content) {
		super();
		this.type = type;
		this.times = times;
		this.content = content;
	}
	@Override
	public String toString() {
		return "MessageContent [type=" + type + ", times=" + times + ", content=" + content + "]";
	}
	
}
