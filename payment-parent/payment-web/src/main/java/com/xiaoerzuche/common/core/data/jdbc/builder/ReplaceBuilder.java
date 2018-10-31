package com.xiaoerzuche.common.core.data.jdbc.builder;

import com.xiaoerzuche.common.util.CheckUtil;

/**
 * insert语句生成器.
 * @author Nick C
 *
 */
public class ReplaceBuilder extends AbstractSqlBuilder implements SqlBuilder {
	private String tableName;

	/**
	 * @param tableName 表名称.
	 */
	public ReplaceBuilder(String tableName) {
		CheckUtil.assertNotBlank(tableName, "参数tableName不能为空.");
		this.tableName = tableName;
	}

	@Override
	/**
	 * 返回replace into语句.
	 */
	public String getSql() {
		if (fieldList.isEmpty()) {
			throw new NullPointerException("还没有设置任何参数.");
		}

		StringBuilder fields = new StringBuilder();
		StringBuilder values = new StringBuilder();

		for (String fieldName : fieldList) {
			if (fields.length() > 0) {
				fields.append(", ");
				values.append(", ");
			}
			fields.append(fieldName);
			values.append("?");
		}

		StringBuilder sb = new StringBuilder("REPLACE INTO ");
		sb.append(tableName).append("(");
		sb.append(fields.toString()).append(") values(");
		sb.append(values.toString()).append(");");

		return sb.toString();
	}
}
