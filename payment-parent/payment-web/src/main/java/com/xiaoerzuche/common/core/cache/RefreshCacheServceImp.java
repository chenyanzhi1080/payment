package com.xiaoerzuche.common.core.cache;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;


@SuppressWarnings("rawtypes")
public class RefreshCacheServceImp implements RefreshCacheService {
	private static final Logger logger = Logger.getLogger(RefreshCacheServceImp.class);
	
	@Autowired(required=false)
	private List<RefreshInterface> refreshInterfaces;

	@Override
	public void refresh() {
		long t1 = System.currentTimeMillis();
        for (RefreshInterface cacheDao : refreshInterfaces) {
            try {
            	System.out.println(cacheDao.getClass());
            	cacheDao.refresh();
            } catch (java.lang.Throwable e) {
            	logger.error("JVM缓存刷新出错", e);
            }
        }
        logger.info("REFRESH ALL,useT:" + (System.currentTimeMillis() - t1) + ",count:" + refreshInterfaces.size());
	}

}
