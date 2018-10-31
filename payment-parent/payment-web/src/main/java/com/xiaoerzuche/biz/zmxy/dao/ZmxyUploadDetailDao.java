package com.xiaoerzuche.biz.zmxy.dao;

import java.util.List;

import com.xiaoerzuche.biz.zmxy.model.ZmxyUploadDetail;
public interface ZmxyUploadDetailDao  {

	boolean insert(ZmxyUploadDetail zmxyUploadDetail);

	boolean update(ZmxyUploadDetail zmxyUploadDetail);

	boolean delete(String id);

	ZmxyUploadDetail get(String id);

	List< ZmxyUploadDetail>  list(int pageStart, int pageSize);
	int count();
}