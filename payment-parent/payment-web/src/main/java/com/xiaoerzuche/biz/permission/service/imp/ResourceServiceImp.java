package com.xiaoerzuche.biz.permission.service.imp;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xiaoerzuche.biz.permission.dao.ResourceDao;
import com.xiaoerzuche.biz.permission.dao.RoleResourceDao;
import com.xiaoerzuche.biz.permission.mode.Resource;
import com.xiaoerzuche.biz.permission.mode.RoleResource;
import com.xiaoerzuche.biz.permission.service.ResourceService;
import com.xiaoerzuche.common.core.data.jdbc.Page;
import com.xiaoerzuche.common.util.CheckUtil;

@Service
public class ResourceServiceImp implements ResourceService {
	private static final Logger logger = Logger.getLogger(ResourceServiceImp.class);
	@Autowired
	private ResourceDao resourceDao;
	@Autowired
	private RoleResourceDao roleResourceDao;

	@Override
	public List<Resource> listAccessableByUserId(int userId) {
		CheckUtil.assertTrue(userId > 0, "非法的用户ID");
		return this.resourceDao.listAccessableByUserId(userId);
	}

	@Override
	public boolean add(Resource resource) {
		return this.resourceDao.add(resource);
	}

	@Override
	public boolean update(Resource resource) {
		return this.resourceDao.update(resource);
	}

	@Override
	public boolean delete(int id) {
		CheckUtil.assertTrue(!resourceDao.hasChild(id), "该资源还存在子资源，请删除子资源之后再删除该资源");
		return this.resourceDao.delete(id);
	}

	@Override
	public Resource get(int id) {
		return this.resourceDao.get(id);
	}

	@Override
	public List<Resource> listAccessableByRoleId(int roleId) {
		return this.resourceDao.listAccessableByRoleId(roleId);
	}

	@Override
	public Page<Resource> list(String name, boolean isAll, int pageStart,
			int pageSize) {
		List<Resource> roles = this.resourceDao.list(name, isAll, pageStart, pageSize);
		long count = this.resourceDao.count(name, isAll);
		Page<Resource> page = new Page<Resource>(count, roles);
		return page;
	}

	@Override
	public List<Resource> listValid() {
		return resourceDao.list(StringUtils.EMPTY, false, 0, Integer.MAX_VALUE);
	}

	@Transactional
	@Override
	public void grant(Integer[] resIds, Integer roleId) {
		roleResourceDao.clear(roleId);
		for(Integer resId : resIds){
			RoleResource rr = new RoleResource();
			rr.setRoleId(roleId);
			rr.setResourceId(resId);
			roleResourceDao.add(rr);
		}
	}
}
