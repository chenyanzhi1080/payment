package com.xiaoerzuche.biz.payment.vo;

import java.util.ArrayList;
import java.util.List;

import com.xiaoerzuche.common.util.JsonUtil;


public class ChannelVo {
	//业务渠道码
	private int channel;
	//业务名称
	private String channelName;
	public int getChannel() {
		return channel;
	}
	public void setChannel(int channel) {
		this.channel = channel;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public ChannelVo() {
		super();
	}
	public ChannelVo(int channel, String channelName) {
		super();
		this.channel = channel;
		this.channelName = channelName;
	}
	public static void main(String[] ages){
		List<ChannelVo> list = new ArrayList<ChannelVo>();
		ChannelVo vo = new ChannelVo(4, "充电桩");
		list.add(vo);
		vo = new ChannelVo(5, "时租");
		list.add(vo);
		vo = new ChannelVo(5, "日租");
		list.add(vo);
		String json = JsonUtil.toJson(list);
		List<ChannelVo> channelVo = JsonUtil.fromJson(json, List.class);
		System.out.println(JsonUtil.toJson(list));
	}
}
