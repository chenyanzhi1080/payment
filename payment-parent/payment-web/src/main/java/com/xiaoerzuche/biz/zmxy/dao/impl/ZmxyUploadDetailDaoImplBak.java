package com.xiaoerzuche.biz.zmxy.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.xiaoerzuche.biz.zmxy.dao.ZmxyUploadDetailDaoBak;
import com.xiaoerzuche.biz.zmxy.model.ZmxyUploadDetailBak;
import com.xiaoerzuche.common.core.data.jdbc.Jdbc;
import com.xiaoerzuche.common.core.data.jdbc.StatementParameter;
import com.xiaoerzuche.common.core.data.jdbc.builder.InsertBuilder;
import com.xiaoerzuche.common.core.data.jdbc.builder.UpdateBuilder;

@Repository
public class ZmxyUploadDetailDaoImplBak  implements ZmxyUploadDetailDaoBak {

	private static final String	TABLE	= "zmxy_feedback_bill";
	@Autowired
	private Jdbc jdbc;

	public boolean insert(ZmxyUploadDetailBak zmxyUploadDetail){
		InsertBuilder ib = new InsertBuilder(TABLE);
		ib.setString("id", zmxyUploadDetail.getId());
		ib.setDate("biz_date", zmxyUploadDetail.getBizDate());
		ib.setString("user_credentials_type",zmxyUploadDetail.getUserCredentialsType());
		ib.setString("user_credentials_no",zmxyUploadDetail.getUserCredentialsNo());
		ib.setString("user_name",zmxyUploadDetail.getUserName());
		ib.setString("order_no", zmxyUploadDetail.getOrderNo());
		ib.setInt("order_status", zmxyUploadDetail.getOrderStatus());
		ib.setDate("order_start_date", zmxyUploadDetail.getOrderStartDate());
		ib.setString("object_name",zmxyUploadDetail.getObjectName());
		ib.setString("object_id",zmxyUploadDetail.getObjectId());
		ib.setString("rent_desc", zmxyUploadDetail.getRentDesc());
		ib.setInt("deposit_amt", zmxyUploadDetail.getDepositAmt());
		ib.setString("bill_no", zmxyUploadDetail.getBillNo());
		ib.setInt("bill_status", zmxyUploadDetail.getBillStatus());
		ib.setInt("bill_type", zmxyUploadDetail.getBillType());
		ib.setInt("bill_amt", zmxyUploadDetail.getBillAmt());
		ib.setDate("bill_last_date",zmxyUploadDetail.getBillLastDate());
		if(null!=zmxyUploadDetail.getBillPayoffDate())
		{
			ib.setDate("bill_payoff_date",zmxyUploadDetail.getBillPayoffDate());
		}
		ib.setInt("upload_status",zmxyUploadDetail.getUploadStatus());
		ib.setString("upload_msg",zmxyUploadDetail.getUploadMsg());
		ib.setDate("upload_time",zmxyUploadDetail.getUploadTime());
		return this.jdbc.updateForBoolean(ib);
	}

	public boolean update(ZmxyUploadDetailBak zmxyUploadDetail){
		UpdateBuilder ub = new UpdateBuilder(TABLE);
		ub.setInt("upload_status",zmxyUploadDetail.getUploadStatus());
		ub.setString("upload_msg",zmxyUploadDetail.getUploadMsg());
		ub.setDate("upload_time",zmxyUploadDetail.getUploadTime());
		ub.where.setString("id",zmxyUploadDetail.getId());
		return this.jdbc.updateForBoolean(ub);
	}

	public boolean delete(String id){
		String sql = "delete  from " + TABLE + " where id = ?";
		StatementParameter sp = new StatementParameter();
		sp.setString(id);
		return this.jdbc.updateForBoolean(sql, sp);
	}

	public ZmxyUploadDetailBak get(String id){
		String sql = "select * from " + TABLE + " where id = ?";
		StatementParameter sp = new StatementParameter();
		sp.setString(id);
		return this.jdbc.query(sql, ZmxyUploadDetailBak.class, sp);
	}


}