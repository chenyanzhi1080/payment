package com.xiaoerzuche.biz.zmxy.model;

import java.util.Date;

public class ZmxyUploadDetail {

	 private String id; //主键

	 private String orderNo; //业务订单id

	 private String billNo; //账单号

	 private String uploadData; //上报数据

	 private Integer uploadStatus; //上传状态，0失败1成功
	 
	 private String uploadErrCode;//接口调用异常码

	 private String uploadMsg; //上传数据信息，失败才有记录

	 private Date uploadTime; //上传时间
	 
	 private String task;

		public ZmxyUploadDetail setId (String id) {
			this.id = id;
			return this;
		}

		public String getId () {
			return id;
		}

		public ZmxyUploadDetail setOrderNo (String orderNo) {
			this.orderNo = orderNo;
			return this;
		}

		public String getOrderNo () {
			return orderNo;
		}

		public ZmxyUploadDetail setBillNo (String billNo) {
			this.billNo = billNo;
			return this;
		}

		public String getBillNo () {
			return billNo;
		}

		public ZmxyUploadDetail setUploadData (String uploadData) {
			this.uploadData = uploadData;
			return this;
		}

		public String getUploadData () {
			return uploadData;
		}

		public ZmxyUploadDetail setUploadStatus (Integer uploadStatus) {
			this.uploadStatus = uploadStatus;
			return this;
		}

		public Integer getUploadStatus () {
			return uploadStatus;
		}

		public ZmxyUploadDetail setUploadMsg (String uploadMsg) {
			this.uploadMsg = uploadMsg;
			return this;
		}

		public String getUploadMsg () {
			return uploadMsg;
		}

		public ZmxyUploadDetail setUploadTime (Date uploadTime) {
			this.uploadTime = uploadTime;
			return this;
		}

		public Date getUploadTime () {
			return uploadTime;
		}

		public String getUploadErrCode() {
			return uploadErrCode;
		}

		public ZmxyUploadDetail setUploadErrCode(String uploadErrCode) {
			this.uploadErrCode = uploadErrCode;
			return this;
		}
		
		public String getTask() {
			return task;
		}

		public ZmxyUploadDetail setTask(String task) {
			this.task = task;
			return this;
		}
		
		public ZmxyUploadDetail (){ 
			super();
		}

		@Override
		public String toString() {
			return "ZmxyUploadDetail [id=" + id + ", orderNo=" + orderNo + ", billNo=" + billNo + ", uploadData="
					+ uploadData + ", uploadStatus=" + uploadStatus + ", uploadErrCode=" + uploadErrCode
					+ ", uploadMsg=" + uploadMsg + ", uploadTime=" + uploadTime + ", task=" + task + "]";
		}

}
