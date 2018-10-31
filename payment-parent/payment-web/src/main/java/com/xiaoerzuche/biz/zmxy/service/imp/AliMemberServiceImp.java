package com.xiaoerzuche.biz.zmxy.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaoerzuche.biz.zmxy.dao.AliMemberDao;
import com.xiaoerzuche.biz.zmxy.model.AliMember;
import com.xiaoerzuche.biz.zmxy.service.AliMemberService;
import com.xiaoerzuche.common.core.data.jdbc.Page;
@Service
public class AliMemberServiceImp  implements AliMemberService {

	@Autowired
	private AliMemberDao aliMemberDao;

	public boolean insert(AliMember aliMember){
		return this.aliMemberDao.insert(aliMember);
	}

	public boolean update(AliMember aliMember){
		return this.aliMemberDao.update(aliMember);
	}

	public boolean delete(String id){
		return this.aliMemberDao.delete(id);
	}

	public AliMember get(String id){
		return this.aliMemberDao.get(id);
	}

	public Page<AliMember> list(int pageStart, int pageSize){
		return null;
	}

}