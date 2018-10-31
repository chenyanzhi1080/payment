package com.xiaoerzuche.common.core.data.spi;


/**
 * 删除接口.
 * 
 * 注意:remove接口和delete接口的区别，remove只是移除缓存中的数据(没有mysql实现)，delete是删除记录(包含删除mysql中的记录);
 * 
 * @author Nick C
 * 
 * @param <BEAN> IGet接口返回值类型:如User、Article、Comment等
 * 
 * @param <KEYTYPE>
 *            主键类型:如Integer、Long、String等
 * 
 */
public interface IDelete<BEAN, KEYTYPE> extends IGet<BEAN, KEYTYPE> {

	/**
	 * 删除记录.(如记录有del字段，则使用标记删除)
	 * 
	 * @param key 主键
	 */
	boolean delete(KEYTYPE key);

}
