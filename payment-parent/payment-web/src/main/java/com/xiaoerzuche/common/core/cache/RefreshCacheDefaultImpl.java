package com.xiaoerzuche.common.core.cache;

import java.util.List;

/**
 * 默认的刷新接口实现
 * @author Nick C
 *
 * @param <T>
 */
public abstract class RefreshCacheDefaultImpl<T> implements RefreshInterface<T>
{
	@Override
	public void refresh(List<T> list){
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void refresh(){
	}
}
