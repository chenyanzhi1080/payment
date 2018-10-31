package com.xiaoerzuche.biz.zmxy.model;

import java.util.Date;

/**
 * @Copy Right 海南开开出行网络科技有限公司
 *创建者：lqq<2856105570@qq.com>
 *创建时间：2017/8/17
 *版本说明：1.0
 * zmxy_upload_detail  表映射实体.此类有模板自动生成！
 */
public class ZmxyUploadDetailBak{


    /**
     * 主键自增
     */
    public final static String ID="id";

    /**
     * 上传状态，0失败1成功
     */
    public final static String UPLOAD_STATUS="uploadStatus";
    /**
     * 上传数据信息，失败才有记录
     */
    public final static String UPLOAD_MSG="uploadMsg";
    /**
     * 上传时间
     */
    public final static String UPLOAD_TIME="uploadTime";

    /**
     * 主键自增
     */
    private String id;

    /**
     * 上传状态，0失败1成功
     */
    private Integer uploadStatus;
    /**
     * 上传数据信息，失败才有记录
     */
    private String uploadMsg;
    /**
     * 上传时间
     */
    private Date uploadTime;
    /**
     * 数据统计日期
     */
    private Date bizDate;
    /**
     * 证件类型,默认0身份证
     */
    private String userCredentialsType;
    /**
     * 证件号码
     */
    private String userCredentialsNo;
    /**
     * 姓名
     */
    private String userName;
    /**
     * 业务号
     */
    private String orderNo;
    /**
     * 当前业务状态，3-开始使用0-正常（有账单待支付）1-违约（有账单逾期未结清费用）2-结清（使用结束并结清费用）4-提前终止8-预约
     */
    private Integer orderStatus;
    /**
     * 业务开始时间
     */
    private Date orderStartDate;
    /**
     * 物品名称
     */
    private String objectName;
    /**
     * 物品标识
     */
    private String objectId;
    /**
     * 租金描述
     */
    private String rentDesc;
    /**
     * 押金
     */
    private Integer depositAmt;
    /**
     * 账单号
     */
    private String billNo;
    /**
     * 账单状态，0-正常1-违约2-完结
     */
    private Integer billStatus;
    /**
     * 账单类型，100-租金未缴,200-车辆未还,508-违规停车(自行车专用),509-违章费用,510-维修费用
     */
    private Integer billType;
    /**
     * 账单应还金额
     */
    private Integer billAmt;
    /**
     * 账单应还款日
     */
    private Date billLastDate;
    /**
     * 账单完结时间
     */
    private Date billPayoffDate;

    /**
     * 数据统计日期
     * 是否允许为空：False
     *
     */
    public  Date  getBizDate() {
        return this.bizDate;
    }
    /**
     * 数据统计日期
     * 是否允许为空：False
     *
     */
    public ZmxyUploadDetailBak setBizDate(  Date  bizDate) {
        this.bizDate = bizDate;
        return this;
    }

    /**
     * 证件类型,默认0身份证
     * 是否允许为空：False
     * 长度：3
     */
    public  String  getUserCredentialsType() {
        return this.userCredentialsType;
    }
    /**
     * 证件类型,默认0身份证
     * 是否允许为空：False
     * 长度：3
     */
    public ZmxyUploadDetailBak setUserCredentialsType(  String  userCredentialsType) {
        this.userCredentialsType = userCredentialsType;
        return this;
    }

    /**
     * 证件号码
     * 是否允许为空：False
     * 长度：180
     */
    public  String  getUserCredentialsNo() {
        return this.userCredentialsNo;
    }
    /**
     * 证件号码
     * 是否允许为空：False
     * 长度：180
     */
    public ZmxyUploadDetailBak setUserCredentialsNo(  String  userCredentialsNo) {
        this.userCredentialsNo = userCredentialsNo;
        return this;
    }

    /**
     * 姓名
     * 是否允许为空：True
     * 长度：90
     */
    public  String  getUserName() {
        return this.userName;
    }
    /**
     * 姓名
     * 是否允许为空：True
     * 长度：90
     */
    public ZmxyUploadDetailBak setUserName(  String  userName) {
        this.userName = userName;
        return this;
    }

    /**
     * 业务号
     * 是否允许为空：False
     * 长度：300
     */
    public  String  getOrderNo() {
        return this.orderNo;
    }
    /**
     * 业务号
     * 是否允许为空：False
     * 长度：300
     */
    public ZmxyUploadDetailBak setOrderNo(  String  orderNo) {
        this.orderNo = orderNo;
        return this;
    }

    /**
     * 当前业务状态，3-开始使用0-正常（有账单待支付）1-违约（有账单逾期未结清费用）2-结清（使用结束并结清费用）4-提前终止8-预约
     * 是否允许为空：False
     *
     */
    public  Integer  getOrderStatus() {
        return this.orderStatus;
    }
    /**
     * 当前业务状态，3-开始使用0-正常（有账单待支付）1-违约（有账单逾期未结清费用）2-结清（使用结束并结清费用）4-提前终止8-预约
     * 是否允许为空：False
     *
     */
    public ZmxyUploadDetailBak setOrderStatus(  Integer  orderStatus) {
        this.orderStatus = orderStatus;
        return this;
    }

    /**
     * 业务开始时间
     * 是否允许为空：False
     *
     */
    public  Date  getOrderStartDate() {
        return this.orderStartDate;
    }
    /**
     * 业务开始时间
     * 是否允许为空：False
     *
     */
    public ZmxyUploadDetailBak setOrderStartDate(  Date  orderStartDate) {
        this.orderStartDate = orderStartDate;
        return this;
    }

    /**
     * 物品名称
     * 是否允许为空：False
     * 长度：300
     */
    public  String  getObjectName() {
        return this.objectName;
    }
    /**
     * 物品名称
     * 是否允许为空：False
     * 长度：300
     */
    public ZmxyUploadDetailBak setObjectName(  String  objectName) {
        this.objectName = objectName;
        return this;
    }

    /**
     * 物品标识
     * 是否允许为空：True
     * 长度：300
     */
    public  String  getObjectId() {
        return this.objectId;
    }
    /**
     * 物品标识
     * 是否允许为空：True
     * 长度：300
     */
    public ZmxyUploadDetailBak setObjectId(  String  objectId) {
        this.objectId = objectId;
        return this;
    }

    /**
     * 租金描述
     * 是否允许为空：False
     * 长度：150
     */
    public  String  getRentDesc() {
        return this.rentDesc;
    }
    /**
     * 租金描述
     * 是否允许为空：False
     * 长度：150
     */
    public ZmxyUploadDetailBak setRentDesc(  String  rentDesc) {
        this.rentDesc = rentDesc;
        return this;
    }

    /**
     * 押金
     * 是否允许为空：False
     *
     */
    public  Integer  getDepositAmt() {
        return this.depositAmt;
    }
    /**
     * 押金
     * 是否允许为空：False
     *
     */
    public ZmxyUploadDetailBak setDepositAmt(  Integer  depositAmt) {
        this.depositAmt = depositAmt;
        return this;
    }

    /**
     * 账单号
     * 是否允许为空：False
     * 长度：600
     */
    public  String  getBillNo() {
        return this.billNo;
    }
    /**
     * 账单号
     * 是否允许为空：False
     * 长度：600
     */
    public ZmxyUploadDetailBak setBillNo(  String  billNo) {
        this.billNo = billNo;
        return this;
    }

    /**
     * 账单状态，0-正常1-违约2-完结
     * 是否允许为空：True
     *
     */
    public  Integer  getBillStatus() {
        return this.billStatus;
    }
    /**
     * 账单状态，0-正常1-违约2-完结
     * 是否允许为空：True
     *
     */
    public ZmxyUploadDetailBak setBillStatus(  Integer  billStatus) {
        this.billStatus = billStatus;
        return this;
    }

    /**
     * 账单类型，100-租金未缴,200-车辆未还,508-违规停车(自行车专用),509-违章费用,510-维修费用
     * 是否允许为空：True
     * 长度：300
     */
    public  Integer  getBillType() {
        return this.billType;
    }
    /**
     * 账单类型，100-租金未缴,200-车辆未还,508-违规停车(自行车专用),509-违章费用,510-维修费用
     * 是否允许为空：True
     * 长度：300
     */
    public ZmxyUploadDetailBak setBillType(  Integer  billType) {
        this.billType = billType;
        return this;
    }

    /**
     * 账单应还金额
     * 是否允许为空：True
     *
     */
    public  Integer  getBillAmt() {
        return this.billAmt;
    }
    /**
     * 账单应还金额
     * 是否允许为空：True
     *
     */
    public ZmxyUploadDetailBak setBillAmt(  Integer  billAmt) {
        this.billAmt = billAmt;
        return this;
    }

    /**
     * 账单应还款日
     * 是否允许为空：True
     *
     */
    public  Date  getBillLastDate() {
        return this.billLastDate;
    }
    /**
     * 账单应还款日
     * 是否允许为空：True
     *
     */
    public ZmxyUploadDetailBak setBillLastDate(  Date  billLastDate) {
        this.billLastDate = billLastDate;
        return this;
    }

    /**
     * 账单完结时间
     * 是否允许为空：True
     *
     */
    public  Date  getBillPayoffDate() {
        return this.billPayoffDate;
    }
    /**
     * 账单完结时间
     * 是否允许为空：True
     *
     */
    public ZmxyUploadDetailBak setBillPayoffDate(  Date  billPayoffDate) {
        this.billPayoffDate = billPayoffDate;
        return this;
    }
    /**
     * 主键
     * 是否允许为空：False
     *
     */
    public  String  getId() {
        return this.id;
    }
    /**
     * 主键
     * 是否允许为空：False
     *
     */
    public ZmxyUploadDetailBak setId(  String  id) {
        this.id = id;
        return this;
    }


    /**
     * 上传状态，0失败1成功
     * 是否允许为空：True
     *
     */
    public  Integer  getUploadStatus() {
        return this.uploadStatus;
    }
    /**
     * 上传状态，0失败1成功
     * 是否允许为空：True
     *
     */
    public ZmxyUploadDetailBak setUploadStatus(  Integer  uploadStatus) {
        this.uploadStatus = uploadStatus;
        return this;
    }

    /**
     * 上传数据信息，失败才有记录
     * 是否允许为空：True
     * 长度：1200
     */
    public  String  getUploadMsg() {
        return this.uploadMsg;
    }
    /**
     * 上传数据信息，失败才有记录
     * 是否允许为空：True
     * 长度：1200
     */
    public ZmxyUploadDetailBak setUploadMsg(  String  uploadMsg) {
        this.uploadMsg = uploadMsg;
        return this;
    }

    /**
     * 上传时间
     * 是否允许为空：True
     *
     */
    public  Date  getUploadTime() {
        return this.uploadTime;
    }
    /**
     * 上传时间
     * 是否允许为空：True
     *
     */
    public ZmxyUploadDetailBak setUploadTime(  Date uploadTime) {
        this.uploadTime = uploadTime;
        return this;
    }
	@Override
	public String toString() {
		return "ZmxyUploadDetail [id=" + id + ", uploadStatus=" + uploadStatus + ", uploadMsg=" + uploadMsg
				+ ", uploadTime=" + uploadTime + ", bizDate=" + bizDate + ", userCredentialsType=" + userCredentialsType
				+ ", userCredentialsNo=" + userCredentialsNo + ", userName=" + userName + ", orderNo=" + orderNo
				+ ", orderStatus=" + orderStatus + ", orderStartDate=" + orderStartDate + ", objectName=" + objectName
				+ ", objectId=" + objectId + ", rentDesc=" + rentDesc + ", depositAmt=" + depositAmt + ", billNo="
				+ billNo + ", billStatus=" + billStatus + ", billType=" + billType + ", billAmt=" + billAmt
				+ ", billLastDate=" + billLastDate + ", billPayoffDate=" + billPayoffDate + "]";
	}

}
