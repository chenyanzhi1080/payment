package com.xiaoerzuche.biz.zmxy.dao;

import java.util.List;

import com.xiaoerzuche.biz.zmxy.dao.sqlbuilder.MemberAntifraudBuilder;
import com.xiaoerzuche.biz.zmxy.model.MemberAntifraud;
public interface MemberAntifraudDao  {

	boolean insert(MemberAntifraud memberAntifraud);

	boolean update(MemberAntifraud memberAntifraud);

	boolean delete(String id);

	MemberAntifraud get(String id);

	List< MemberAntifraud>  list(MemberAntifraudBuilder builder);
	int count(MemberAntifraudBuilder builder);
}