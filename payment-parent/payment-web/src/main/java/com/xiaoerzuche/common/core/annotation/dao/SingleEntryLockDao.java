package com.xiaoerzuche.common.core.annotation.dao;

public interface SingleEntryLockDao {

	/**
	 * 锁定制定的KEY，要是已经被锁定的话就返回false,否则返回true
	 * @param lockKey
	 * @return
	 */
	boolean lock(String lockKey);

	/**
	 * 解除锁定
	 * @param lockKey
	 */
	void release(String lockKey);

}
