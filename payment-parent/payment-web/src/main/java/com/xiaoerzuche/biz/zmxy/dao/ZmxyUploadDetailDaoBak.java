package com.xiaoerzuche.biz.zmxy.dao;

import com.xiaoerzuche.biz.zmxy.model.ZmxyUploadDetailBak;

/**
 * Created by lqq on 2017/8/17.
 */
public interface ZmxyUploadDetailDaoBak {

    boolean insert(ZmxyUploadDetailBak zmxyUploadDetail);

    boolean update(ZmxyUploadDetailBak zmxyUploadDetail);

    boolean delete(String id);

    ZmxyUploadDetailBak get(String id);
    
}
