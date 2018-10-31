package com.xiaoerzuche.biz.zmxy.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.xiaoerzuche.biz.zmxy.dao.ZmxyUploadDetailDao;
import com.xiaoerzuche.biz.zmxy.model.ZmxyUploadDetail;
import com.xiaoerzuche.common.core.data.jdbc.Jdbc;
import com.xiaoerzuche.common.core.data.jdbc.StatementParameter;
import com.xiaoerzuche.common.core.data.jdbc.builder.InsertBuilder;
import com.xiaoerzuche.common.core.data.jdbc.builder.UpdateBuilder;
@Repository
public class ZmxyUploadDetailDaoImpl  implements ZmxyUploadDetailDao {

	private static final String	TABLE	= "zmxy_upload_detail";
	@Autowired
	private Jdbc				jdbc;

	public boolean insert(ZmxyUploadDetail zmxyUploadDetail){
		InsertBuilder ib = new InsertBuilder(TABLE);
//		ib.setString("id",zmxyUploadDetail.getId());
		ib.setString("order_no",zmxyUploadDetail.getOrderNo());
		ib.setString("bill_no",zmxyUploadDetail.getBillNo());
		ib.setString("upload_data",zmxyUploadDetail.getUploadData());
		ib.setInt("upload_status",zmxyUploadDetail.getUploadStatus());
		if(StringUtils.isNotBlank(zmxyUploadDetail.getUploadErrCode())){
			ib.setString("upload_err_code", zmxyUploadDetail.getUploadErrCode());
		}
		if(StringUtils.isNotBlank(zmxyUploadDetail.getUploadMsg())){
			ib.setString("upload_msg",zmxyUploadDetail.getUploadMsg());
		}
		ib.setDate("upload_time",zmxyUploadDetail.getUploadTime());
		ib.setString("task", zmxyUploadDetail.getTask());
		return this.jdbc.updateForBoolean(ib);
	}

	public boolean update(ZmxyUploadDetail zmxyUploadDetail){
		UpdateBuilder ub = new UpdateBuilder(TABLE);
		ub.setString("order_no",zmxyUploadDetail.getOrderNo());
		ub.setString("bill_no",zmxyUploadDetail.getBillNo());
		ub.setString("upload_data",zmxyUploadDetail.getUploadData());
		ub.setInt("upload_status",zmxyUploadDetail.getUploadStatus());
		if(StringUtils.isNotBlank(zmxyUploadDetail.getUploadErrCode())){
			ub.setString("upload_err_code", zmxyUploadDetail.getUploadErrCode());
		}
		if(StringUtils.isNotBlank(zmxyUploadDetail.getUploadMsg())){
			ub.setString("upload_msg",zmxyUploadDetail.getUploadMsg());
		}
		ub.setString("task", zmxyUploadDetail.getTask());
		ub.setDate("upload_time",zmxyUploadDetail.getUploadTime());
		ub.where.setString("id",zmxyUploadDetail.getId());
		return this.jdbc.updateForBoolean(ub);
	}

	public boolean delete(String id){
		String sql = "delete  from " + TABLE + " where id = ?";
		StatementParameter sp = new StatementParameter();
		sp.setString( id);
		return this.jdbc.updateForBoolean(sql, sp);
	}

	public ZmxyUploadDetail get(String id){
		String sql = "select * from " + TABLE + " where id = ?";
		StatementParameter sp = new StatementParameter();
		sp.setString( id);
		return this.jdbc.query(sql,ZmxyUploadDetail.class, sp);
	}

	public List<ZmxyUploadDetail> list(int pageStart, int pageSize){
		StringBuilder sb = new StringBuilder();
		sb.append("select * from ");
		sb.append(TABLE);
		sb.append(" where 1=1 ");
		StatementParameter sp = new StatementParameter();
		return this.jdbc.queryForList(sb.toString(), ZmxyUploadDetail.class, sp);
	}

	public int count(){
		StringBuilder sb = new StringBuilder();
		sb.append("select count(1) from ");
		sb.append(TABLE);
		sb.append(" where 1=1 ");
		StatementParameter sp = new StatementParameter();
		return this.jdbc.queryForInt(sb.toString(),sp);
	}

}