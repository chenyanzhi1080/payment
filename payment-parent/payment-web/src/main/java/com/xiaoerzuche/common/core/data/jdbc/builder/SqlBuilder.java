package com.xiaoerzuche.common.core.data.jdbc.builder;

import com.xiaoerzuche.common.core.data.jdbc.StatementParameter;

/**
 * SQL构建生成器接口
 * @author Nick C
 *
 */
public interface SqlBuilder {

	/**
	 * 获取SQL语句.
	 * @return
	 */
	String getSql();

	/**
	 * 获取参数
	 * @return
	 */
	StatementParameter getParam();
}
