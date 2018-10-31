package com.xiaoerzuche.biz.zmxy.dao;

import java.util.List;

import com.xiaoerzuche.biz.zmxy.model.AliMember;
public interface AliMemberDao  {

	boolean insert(AliMember aliMember);

	boolean update(AliMember aliMember);

	boolean delete(String aliUserId);

	AliMember get(String aliUserId);

	List< AliMember>  list(int pageStart, int pageSize);
	int count();
}