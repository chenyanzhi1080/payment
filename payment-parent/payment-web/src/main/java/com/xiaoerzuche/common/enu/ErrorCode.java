package com.xiaoerzuche.common.enu;

/**
 * 错误代码枚举类
 * @author Nick C
 *
 */
public enum ErrorCode {
	
    PARAM (501, "参数错误"), NOT_FOUND (404, "找不到对应的记录"), LIMIT (502, "访问受限"), NOENOUGH(504, "条件未满足"), UNKOWN(500, "服务器异常"), NOLOGIN(508, "您还未登陆");

    //错误代码
    private int errorCode ;
    //错误消息
    private String message;

    private ErrorCode(int errorCode, String message) {
		this.errorCode = errorCode;
		this.message = message;
	}
    
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	@Override
    public String toString() {
        return String.valueOf ("errorCode为：" + this.errorCode + "  meaasge为：" + this.message);
    }
}
