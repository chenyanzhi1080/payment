package com.xiaoerzuche.biz.zmxy.service.imp;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.antgroup.zmxy.openplatform.api.ZhimaApiException;
import com.antgroup.zmxy.openplatform.api.request.ZhimaDataSingleFeedbackRequest;
import com.antgroup.zmxy.openplatform.api.response.ZhimaDataSingleFeedbackResponse;
import com.xiaoerzuche.biz.payment.service.PaySystemConfigService;
import com.xiaoerzuche.biz.zmxy.config.ZmxyClient;
import com.xiaoerzuche.biz.zmxy.dao.ZmxyUploadDetailDao;
import com.xiaoerzuche.biz.zmxy.enu.FeedbackTypeEnu;
import com.xiaoerzuche.biz.zmxy.model.ZmxyFeedbackBill;
import com.xiaoerzuche.biz.zmxy.model.ZmxyUploadDetail;
import com.xiaoerzuche.biz.zmxy.service.ZmxyUploadDetailService;
import com.xiaoerzuche.common.core.data.jdbc.Page;
import com.xiaoerzuche.common.util.DateTimeUtil;
import com.xiaoerzuche.common.util.IDCardUtil;
import com.xiaoerzuche.common.util.JsonUtil;
@Service
public class ZmxyUploadDetailServiceImp  implements ZmxyUploadDetailService {
	private static final Logger logger = LoggerFactory.getLogger(ZmxyUploadDetailServiceImp.class);
	
	private static final String ZMXY_BILL_TIME = "ZMXY_BILL_TIME";
	@Autowired
	private PaySystemConfigService paySystemConfigService;
	@Autowired
	private ZmxyUploadDetailDao zmxyUploadDetailDao;

	public boolean insert(ZmxyUploadDetail zmxyUploadDetail){
		return this.zmxyUploadDetailDao.insert(zmxyUploadDetail);
	}

	public boolean update(ZmxyUploadDetail zmxyUploadDetail){
		return this.zmxyUploadDetailDao.update(zmxyUploadDetail);
	}

	public boolean delete(String id){
		return this.zmxyUploadDetailDao.delete(id);
	}

	public ZmxyUploadDetail get(String id){
		return this.zmxyUploadDetailDao.get(id);
	}

	public Page<ZmxyUploadDetail> list(int pageStart, int pageSize){
		Page<ZmxyUploadDetail> page=new  Page<ZmxyUploadDetail>();
		List<ZmxyUploadDetail> data=this.zmxyUploadDetailDao.list(pageStart,pageSize);
		int count=this.zmxyUploadDetailDao.count();
		page.setCount(count);
//		page.setList(data);
		return page;
	}

	@Override
	public boolean uploadFeedbackBillToZmxy(ZmxyUploadDetail zmxyUploadDetail) {
		ZhimaDataSingleFeedbackRequest req=new ZhimaDataSingleFeedbackRequest();
		req.setChannel("apppc");
		req.setPlatform("zmop");
		req.setTypeId(ZmxyClient.typeId);
		req.setIdentity(zmxyUploadDetail.getOrderNo() + "#" + zmxyUploadDetail.getBillNo());
		req.setData(zmxyUploadDetail.getUploadData());
		logger.info("[上传芝麻信用req={}]", new Object[]{JsonUtil.toJson(req)});
		try {
			ZhimaDataSingleFeedbackResponse response=ZmxyClient.zhimaClient.execute(req);
			if(response.isSuccess()) {
				zmxyUploadDetail.setUploadStatus(1);
				return true;
			}
			else {
				zmxyUploadDetail.setUploadStatus(0)
				.setUploadErrCode(response.getErrorCode())
				.setUploadMsg(response.getErrorMessage());
				logger.info("[orderNo={}billNo={}上传芝麻信用失败{}]", new Object[]{zmxyUploadDetail.getOrderNo(),zmxyUploadDetail.getBillNo(),zmxyUploadDetail.getUploadMsg()});
			}
		} catch (ZhimaApiException e) {
			zmxyUploadDetail.setUploadStatus(0).setUploadErrCode(e.getErrCode()).setUploadMsg(e.getErrMsg());
			logger.info("[orderNo={}billNo={}上传芝麻信用失败{}]", new Object[]{zmxyUploadDetail.getOrderNo(),zmxyUploadDetail.getBillNo(),e});
		}
		catch (RuntimeException e){
			zmxyUploadDetail.setUploadStatus(0);
			zmxyUploadDetail.setUploadMsg(e.getMessage());
			logger.info("[orderNo={}billNo={}上传芝麻信用失败{}]", new Object[]{zmxyUploadDetail.getOrderNo(),zmxyUploadDetail.getBillNo(),e});
		}
		return false;
	}

	@Override
	public ZmxyUploadDetail build(ZmxyFeedbackBill zmxyFeedbackBill) {
		String userName = "";
		if(StringUtils.isNotBlank(zmxyFeedbackBill.getUserName())){
			userName = zmxyFeedbackBill.getUserName();
			userName = (userName.replace("\n", "")).trim();
		}
		StringBuilder stringBuilder=new StringBuilder();
		stringBuilder.append("{\"biz_date\":\""+ DateTimeUtil.getTime(zmxyFeedbackBill.getBizDate())+"\",");
		stringBuilder.append("\"user_credentials_type\":\""+zmxyFeedbackBill.getUserCredentialsType()+"\",");
		stringBuilder.append("\"user_credentials_no\":\""+zmxyFeedbackBill.getUserCredentialsNo().toUpperCase()+"\",");
		stringBuilder.append("\"user_name\":\""+userName+"\",");
		stringBuilder.append("\"order_no\":\""+zmxyFeedbackBill.getOrderNo()+"\",");
		stringBuilder.append("\"order_status\":\""+zmxyFeedbackBill.getOrderStatus()+"\",");
		stringBuilder.append("\"order_start_date\":\""+DateTimeUtil.getTime(zmxyFeedbackBill.getOrderStartDate())+"\",");
		stringBuilder.append("\"object_name\":\""+zmxyFeedbackBill.getObjectName()+"\",");
		stringBuilder.append("\"object_id\":\""+zmxyFeedbackBill.getObjectId()+"\",");
		stringBuilder.append("\"rent_desc\":\""+zmxyFeedbackBill.getRentDesc()+"\",");
		stringBuilder.append("\"deposit_amt\":"+ new BigDecimal(zmxyFeedbackBill.getDepositAmt()).movePointLeft(2)+",");
		stringBuilder.append("\"bill_no\":\""+zmxyFeedbackBill.getBillNo()+"\",");
		stringBuilder.append("\"bill_status\":\""+zmxyFeedbackBill.getBillStatus()+"\",");
		stringBuilder.append("\"bill_type\":\""+zmxyFeedbackBill.getBillType()+"\",");
		stringBuilder.append("\"bill_amt\":\""+ new BigDecimal(zmxyFeedbackBill.getBillAmt()).movePointLeft(2)+"\",");
		stringBuilder.append("\"bill_last_date\":\""+(zmxyFeedbackBill.getBillLastDate()==null? "" :DateTimeUtil.getFormatDate(zmxyFeedbackBill.getBillLastDate()))+"\",");
		stringBuilder.append("\"bill_payoff_date\":\""+(zmxyFeedbackBill.getBillPayoffDate()==null? "":DateTimeUtil.getTime(zmxyFeedbackBill.getBillPayoffDate()))+"\"}");
		logger.info("[上传芝麻信用数据json]"+stringBuilder.toString());
		ZmxyUploadDetail zmxyUploadDetail = new ZmxyUploadDetail();
		zmxyUploadDetail
		.setOrderNo(zmxyFeedbackBill.getOrderNo())
		.setBillNo(zmxyFeedbackBill.getBillNo())
		.setUploadTime(new Date())
		.setUploadStatus(0)
		.setUploadData(stringBuilder.toString());
		return zmxyUploadDetail;
	}
	
	@Override
	public void doUpload(ZmxyFeedbackBill zmxyFeedbackBill,  String taskClassName) {
		ZmxyUploadDetail zmxyUploadDetail = this.build(zmxyFeedbackBill);
		zmxyUploadDetail.setTask(taskClassName);
		//过滤拦截 ，命中规则的不做上传
		Boolean beable = true;
		
		if(beable && !StringUtils.isNotBlank(zmxyFeedbackBill.getUserName())){
			logger.info("[芝麻信用账单上报拦截][username={}]",new Object[]{zmxyFeedbackBill.getUserName()});
			beable = false;
		}
		Pattern pattern = Pattern.compile(".*\\d+.*");
		Matcher isNum = pattern.matcher(zmxyFeedbackBill.getUserName());
		if(beable && isNum.matches()){//判断姓名是否含数字
			logger.info("[芝麻信用账单上报拦截][username={}]",new Object[]{zmxyFeedbackBill.getUserName()});
			beable = false;
		}
		
		if(beable && !IDCardUtil.isIDCard(zmxyFeedbackBill.getUserCredentialsNo())){
			logger.info("[芝麻信用账单上报拦截][UserCredentialsNo={}]",new Object[]{zmxyFeedbackBill.getUserCredentialsNo()});
			beable = false;
		}
/*		if(beable && (zmxyFeedbackBill.getBillType()==FeedbackTypeEnu.ILLEGAL.getCode() ||
				zmxyFeedbackBill.getBillType()==FeedbackTypeEnu.REPAIR.getCode())){
			beable = false;
		}*/
		if(beable){
			String zmxyBillTimeLimit = paySystemConfigService.get(ZMXY_BILL_TIME).getConfigValue();//
			Date zmxyBillLimitDate = DateTimeUtil.parse(zmxyBillTimeLimit, "yyyy-MM-dd HH:mm:ss");
			beable = DateTimeUtil.afterOrEqua(zmxyFeedbackBill.getOrderStartDate(), zmxyBillLimitDate);
			if(beable){//限定日期之前的数据不上报到芝麻信用。用来做限制开关
				this.uploadFeedbackBillToZmxy(zmxyUploadDetail);
			}
			//记录上传情况
			this.insert(zmxyUploadDetail);
		}
		
	}

}