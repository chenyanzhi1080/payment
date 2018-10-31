package com.xiaoerzuche.biz.zmxy.service;

import com.xiaoerzuche.biz.zmxy.model.AliMember;
import com.xiaoerzuche.common.core.data.jdbc.Page;
public interface AliMemberService  {

	boolean insert(AliMember aliMember);

	boolean update(AliMember aliMember);

	boolean delete(String id);

	AliMember get(String id);

	Page< AliMember>  list(int pageStart, int pageSize);
}