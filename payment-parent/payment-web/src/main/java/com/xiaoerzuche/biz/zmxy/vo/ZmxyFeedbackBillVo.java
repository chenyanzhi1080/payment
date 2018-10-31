package com.xiaoerzuche.biz.zmxy.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by lqq on 2017/8/17.
 */
public class ZmxyFeedbackBillVo implements Serializable{

    /**
     * 数据统计日期
     */
    public final static String BIZ_DATE="bizDate";
    /**
     * 证件类型,默认0身份证
     */
    public final static String USER_CREDENTIALS_TYPE="userCredentialsType";
    /**
     * 证件号码
     */
    public final static String USER_CREDENTIALS_NO="userCredentialsNo";
    /**
     * 姓名
     */
    public final static String USER_NAME="userName";
    /**
     * 业务号
     */
    public final static String ORDER_NO="orderNo";
    /**
     * 当前业务状态，3-开始使用0-正常（有账单待支付）1-违约（有账单逾期未结清费用）2-结清（使用结束并结清费用）4-提前终止8-预约
     */
    public final static String ORDER_STATUS="orderStatus";
    /**
     * 业务开始时间
     */
    public final static String ORDER_START_DATE="orderStartDate";
    /**
     * 物品名称
     */
    public final static String OBJECT_NAME="objectName";
    /**
     * 物品标识
     */
    public final static String OBJECT_ID="objectId";
    /**
     * 租金描述
     */
    public final static String RENT_DESC="rentDesc";
    /**
     * 押金
     */
    public final static String DEPOSIT_AMT="depositAmt";
    /**
     * 账单号
     */
    public final static String BILL_NO="billNo";
    /**
     * 账单状态，0-正常1-违约2-完结
     */
    public final static String BILL_STATUS="billStatus";
    /**
     * 账单类型，100-租金未缴,200-车辆未还,508-违规停车(自行车专用),509-违章费用,510-维修费用
     */
    public final static String BILL_TYPE="billType";
    /**
     * 账单应还金额
     */
    public final static String BILL_AMT="billAmt";
    /**
     * 账单应还款日
     */
    public final static String BILL_LAST_DATE="billLastDate";
    /**
     * 账单完结时间
     */
    public final static String BILL_PAYOFF_DATE="billPayoffDate";

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
    private String billType;
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
    public ZmxyFeedbackBillVo setBizDate(  Date  bizDate) {
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
    public ZmxyFeedbackBillVo setUserCredentialsType(  String  userCredentialsType) {
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
    public ZmxyFeedbackBillVo setUserCredentialsNo(  String  userCredentialsNo) {
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
    public ZmxyFeedbackBillVo setUserName(  String  userName) {
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
    public ZmxyFeedbackBillVo setOrderNo(  String  orderNo) {
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
    public ZmxyFeedbackBillVo setOrderStatus(  Integer  orderStatus) {
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
    public ZmxyFeedbackBillVo setOrderStartDate(  Date  orderStartDate) {
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
    public ZmxyFeedbackBillVo setObjectName(  String  objectName) {
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
    public ZmxyFeedbackBillVo setObjectId(  String  objectId) {
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
    public ZmxyFeedbackBillVo setRentDesc(  String  rentDesc) {
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
    public ZmxyFeedbackBillVo setDepositAmt(  Integer  depositAmt) {
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
    public ZmxyFeedbackBillVo setBillNo(  String  billNo) {
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
    public ZmxyFeedbackBillVo setBillStatus(  Integer  billStatus) {
        this.billStatus = billStatus;
        return this;
    }

    /**
     * 账单类型，100-租金未缴,200-车辆未还,508-违规停车(自行车专用),509-违章费用,510-维修费用
     * 是否允许为空：True
     * 长度：300
     */
    public  String  getBillType() {
        return this.billType;
    }
    /**
     * 账单类型，100-租金未缴,200-车辆未还,508-违规停车(自行车专用),509-违章费用,510-维修费用
     * 是否允许为空：True
     * 长度：300
     */
    public ZmxyFeedbackBillVo setBillType(  String  billType) {
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
    public ZmxyFeedbackBillVo setBillAmt(  Integer  billAmt) {
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
    public ZmxyFeedbackBillVo setBillLastDate(  Date  billLastDate) {
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
    public ZmxyFeedbackBillVo setBillPayoffDate(  Date  billPayoffDate) {
        this.billPayoffDate = billPayoffDate;
        return this;
    }
    
	@Override
	public String toString() {
		return "ZmxyFeedbackBillVo [bizDate=" + bizDate + ", userCredentialsType=" + userCredentialsType
				+ ", userCredentialsNo=" + userCredentialsNo + ", userName=" + userName + ", orderNo=" + orderNo
				+ ", orderStatus=" + orderStatus + ", orderStartDate=" + orderStartDate + ", objectName=" + objectName
				+ ", objectId=" + objectId + ", rentDesc=" + rentDesc + ", depositAmt=" + depositAmt + ", billNo="
				+ billNo + ", billStatus=" + billStatus + ", billType=" + billType + ", billAmt=" + billAmt
				+ ", billLastDate=" + billLastDate + ", billPayoffDate=" + billPayoffDate + "]";
	}

}
