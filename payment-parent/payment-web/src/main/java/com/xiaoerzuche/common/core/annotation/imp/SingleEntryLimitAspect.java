package com.xiaoerzuche.common.core.annotation.imp;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xiaoerzuche.common.core.annotation.SingleEntryLimit;
import com.xiaoerzuche.common.core.annotation.dao.SingleEntryLockDao;
import com.xiaoerzuche.common.exception.ConcurentOperException;

@Component
@Aspect
public class SingleEntryLimitAspect {
	private static final Logger logger = Logger.getLogger(SingleEntryLimitAspect.class);
	@Autowired
	private SingleEntryLockDao singleEntryLockDao;
	
	@Around("@annotation(singleEntryLimit)")
	public Object dealAspect(ProceedingJoinPoint joinPoint, SingleEntryLimit singleEntryLimit) throws Throwable {
		//取参数
		Object[] args = joinPoint.getArgs();
		String lockKey = getParam(args, singleEntryLimit.lockKey());
		
		if(!singleEntryLockDao.lock(lockKey)){
			throw new ConcurentOperException("并发操作");
		}
		try{
			Object obj = joinPoint.proceed();
			return obj;
		}finally{
			singleEntryLockDao.release(lockKey);
		}
	}
	
    /**
     * 获取参数
     * @param 参数列表
     * @param 参数索引
     */
    private String getParam(Object[] args, String indexKey) {
    	if(args == null || args.length == 0)return null;
        int argLen = args.length;
        for (int i = 0; i <= argLen && i <= 9; i++) {
            if (indexKey.contains("${" + i + "}")) {
                String replaceValue = String.valueOf(args[i]);
                indexKey = StringUtils.replace(indexKey, "${" + i + "}", replaceValue);
            }
        }
        return indexKey;
    }
}
