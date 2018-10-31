package com.xiaoerzuche.biz.permission.service.imp;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xiaoerzuche.biz.permission.dao.RoleDao;
import com.xiaoerzuche.biz.permission.dao.UserRoleDao;
import com.xiaoerzuche.biz.permission.mode.Role;
import com.xiaoerzuche.biz.permission.mode.UserRole;
import com.xiaoerzuche.biz.permission.service.RoleService;
import com.xiaoerzuche.common.core.data.jdbc.Page;

@Service
public class RoleServiceImp implements RoleService {
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private UserRoleDao userRoleDao;

	@Override
	public boolean add(Role role) {
		return this.roleDao.add(role);
	}

	@Override
	public boolean update(Role role) {
		return this.roleDao.update(role);
	}

	@Override
	public boolean delete(int id) {
		return this.roleDao.delete(id);
	}

	@Override
	public Role get(int id) {
		return this.roleDao.get(id);
	}

	@Override
	public Page<Role> list(String name, boolean isAll, int pageStart, int pageSize) {
		List<Role> roles = this.roleDao.list(name, isAll, pageStart, pageSize);
		long count = this.roleDao.count(name, isAll);
		Page<Role> page = new Page<Role>(count, roles);
		return page;		
	}

	@Override
	public List<Role> listAll() {
		return this.roleDao.list(StringUtils.EMPTY, false, 0, Integer.MAX_VALUE);
	}

	@Override
	public List<Role> listByUser(Integer id) {
		return this.roleDao.listByUser(id);
	}

	@Transactional
	@Override
	public void grant(Integer uid, Integer[] roleIds) {
		userRoleDao.clear(uid);
		for(Integer roleId : roleIds){
			UserRole ur = new UserRole();
			ur.setRoleId(roleId);
			ur.setUserId(uid);
			userRoleDao.add(ur);
		}
	}

}
