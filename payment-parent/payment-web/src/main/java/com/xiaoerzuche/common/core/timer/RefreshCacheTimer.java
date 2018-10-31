package com.xiaoerzuche.common.core.timer;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.xiaoerzuche.common.core.cache.CacheLoaderOnServerStart;
import com.xiaoerzuche.common.core.cache.RefreshCacheService;
import com.xiaoerzuche.common.util.ListUtil;

public class RefreshCacheTimer {
	private static final Logger logger = Logger.getLogger(RefreshCacheTimer.class);
	@Autowired(required=false)
	private RefreshCacheService refreshCacheService;
	@Autowired(required=false)
	private List<CacheLoaderOnServerStart> cacheLoaders;

	public void doRefresh(){
		refreshCacheService.refresh();
	}
	
	@PostConstruct
	public void load(){
		long t1 = System.currentTimeMillis();
		if(ListUtil.isNotEmpty(cacheLoaders)){
			for(CacheLoaderOnServerStart loader : cacheLoaders){
				loader.start();
			}
			logger.info("LOAD CACHE ALL,useT:" + (System.currentTimeMillis() - t1) + ",count:" + cacheLoaders.size());
		}
		
	}
	
}
