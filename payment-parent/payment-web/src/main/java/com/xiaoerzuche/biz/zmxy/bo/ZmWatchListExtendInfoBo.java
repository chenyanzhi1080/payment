package com.xiaoerzuche.biz.zmxy.bo;

import com.antgroup.zmxy.openplatform.api.internal.mapping.ApiField;

/** 
* 芝麻信用行业关注名单不良记录拓展信息业务bo
* @author: cyz
* @version: payment-web
* @time: 2017年7月4日
* 
*/
public class ZmWatchListExtendInfoBo {
	/** 
	 * 对于这个key的描述
	 */
	@ApiField("description")
	private String description;

	/** 
	 * 对应的字段名
	 */
	@ApiField("key")
	private String key;

	/** 
	 * 对应的值
	 */
	@ApiField("value")
	private String value;

	public void setDescription(String description) {
		this.description = description;
	}
	public String getDescription( ) {
		return this.description;
	}

	public void setKey(String key) {
		this.key = key;
	}
	public String getKey( ) {
		return this.key;
	}

	public void setValue(String value) {
		this.value = value;
	}
	public String getValue( ) {
		return this.value;
	}
}
