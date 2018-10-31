package com.xiaoerzuche.biz.zmxy.bo;

import java.util.Date;
import java.util.List;

import com.antgroup.zmxy.openplatform.api.internal.mapping.ApiField;
import com.antgroup.zmxy.openplatform.api.internal.mapping.ApiListField;

/** 
* 芝麻信用行业关注名单不良记录信息业务bo
* @author: cyz
* @version: payment-web
* @time: 2017年7月4日
* 
*/
public class ZmWatchListDetailBo {
	/** 
	 * 风险信息行业编码
	 */
	@ApiField("biz_code")
	private String bizCode;

	/** 
	 * 风险编码
	 */
	@ApiField("code")
	private String code;

/*	@ApiField("extendInfo")
	private String extendInfo;*/
	/** 
	 * 扩展信息
	 
	@ApiListField("extend_info")
	@ApiField("zm_watch_list_extend_info")
	private List<ZmWatchListExtendInfoBo> extendInfo;
*/ 
	
	/** 
	 * 行业名单数据类型，负面信息或者风险信息，取值：1=Negative or 2=Risk
	 */
	@ApiField("level")
	private Long level;

	/** 
	 * 数据刷新时间
	 */
	@ApiField("refresh_time")
	private Date refreshTime;

	/** 
	 * 结清状态
	 */
	@ApiField("settlement")
	private Boolean settlement;

	/** 
	 * 当用户进行异议处理，并核查完毕之后，仍有异议时，给出的声明
	 */
	@ApiField("statement")
	private String statement;

	/** 
	 * 用户本人对该条负面记录有异议时，表示该异议处理流程的状态
	 */
	@ApiField("status")
	private String status;

	/** 
	 * 行业名单风险类型
	 */
	@ApiField("type")
	private String type;

	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Long getLevel() {
		return level;
	}

	public void setLevel(Long level) {
		this.level = level;
	}

	public Date getRefreshTime() {
		return refreshTime;
	}

	public void setRefreshTime(Date refreshTime) {
		this.refreshTime = refreshTime;
	}

	public Boolean getSettlement() {
		return settlement;
	}

	public void setSettlement(Boolean settlement) {
		this.settlement = settlement;
	}

	public String getStatement() {
		return statement;
	}

	public void setStatement(String statement) {
		this.statement = statement;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "ZmWatchListDetailBo [bizCode=" + bizCode + ", code=" + code + ", level=" + level + ", refreshTime="
				+ refreshTime + ", settlement=" + settlement + ", statement=" + statement + ", status=" + status
				+ ", type=" + type + "]";
	}

}
