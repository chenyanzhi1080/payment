package com.xiaoerzuche.biz.permission.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaoerzuche.biz.permission.dao.UserDao;
import com.xiaoerzuche.biz.permission.exception.NotFoundAccountException;
import com.xiaoerzuche.biz.permission.exception.NotMatchingPwdException;
import com.xiaoerzuche.biz.permission.mode.User;
import com.xiaoerzuche.biz.permission.service.UserService;
import com.xiaoerzuche.common.core.data.jdbc.Page;
import com.xiaoerzuche.common.util.EncryptUtil;

@Service
public class UserServiceImp implements UserService {
	@Autowired
	private UserDao userDao;
	
	@Override
	public boolean add(User user) {
		String encryptPwd = EncryptUtil.md5(EncryptUtil.sha1(user.getPwd())+EncryptUtil.md5(user.getPwd()));
		user.setPwd(encryptPwd);
		return this.userDao.add(user);
	}

	@Override
	public boolean update(User user) {
		return this.userDao.update(user);
	}

	@Override
	public boolean delete(int id) {
		return this.userDao.delete(id);
	}

	@Override
	public User get(int id) {
		return this.userDao.get(id);
	}

	@Override
	public User loginValidate(String account, String pwd) throws NotFoundAccountException, NotMatchingPwdException {
		User user = this.userDao.getBy(account);
		if(user == null){
			throw new NotFoundAccountException("账号不存在");
		}
		String dbPwd = user.getPwd();
		String encryptPwd = EncryptUtil.md5(EncryptUtil.sha1(pwd)+EncryptUtil.md5(pwd));
		if(!encryptPwd.equals(dbPwd)){
			throw new NotMatchingPwdException("密码不正确");
		}
		return user;
	}

	@Override
	public Page<User> list(String account, String name, String nickName,
			boolean isAll, int pageStart, int pageSize) {
		List<User> roles = this.userDao.list(account, name, nickName, isAll, pageStart, pageSize);
		long count = this.userDao.count(account, name, nickName, isAll);
		Page<User> page = new Page<User>(count, roles);
		return page;	
	}

	@Override
	public boolean updatePwd(User user, String sourcePwd, String newPwd) {
		return userDao.updatePwd(user, sourcePwd, newPwd);
	}
}
