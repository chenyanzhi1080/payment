package com.xiaoerzuche.biz.payment.model;

public class PayTypeConfig {

	 private Integer id; //主键

	 private Integer paytypeCode; //交易方式代码

	 private String name; //交易方式名称

	 private String direction; //支付方式描述

	 private String bzAppid; //业务appid

	 private String terminalAppid; //终端appid

	 private Integer status; //状态(开启:1，关闭:0)

	 private Integer orderNo; //排序序号

		public PayTypeConfig setId (Integer id) {
			this.id = id;
			return this;
		}

		public Integer getId () {
			return id;
		}

		public PayTypeConfig setPaytypeCode (Integer paytypeCode) {
			this.paytypeCode = paytypeCode;
			return this;
		}

		public Integer getPaytypeCode () {
			return paytypeCode;
		}

		public PayTypeConfig setName (String name) {
			this.name = name;
			return this;
		}

		public String getName () {
			return name;
		}

		public PayTypeConfig setDirection (String direction) {
			this.direction = direction;
			return this;
		}

		public String getDirection () {
			return direction;
		}

		public PayTypeConfig setBzAppid (String bzAppid) {
			this.bzAppid = bzAppid;
			return this;
		}

		public String getBzAppid () {
			return bzAppid;
		}

		public PayTypeConfig setTerminalAppid (String terminalAppid) {
			this.terminalAppid = terminalAppid;
			return this;
		}

		public String getTerminalAppid () {
			return terminalAppid;
		}

		public PayTypeConfig setStatus (Integer status) {
			this.status = status;
			return this;
		}

		public Integer getStatus () {
			return status;
		}

		public PayTypeConfig setOrderNo (Integer orderNo) {
			this.orderNo = orderNo;
			return this;
		}

		public Integer getOrderNo () {
			return orderNo;
		}

		public PayTypeConfig (){ 
			super();
		}
}
