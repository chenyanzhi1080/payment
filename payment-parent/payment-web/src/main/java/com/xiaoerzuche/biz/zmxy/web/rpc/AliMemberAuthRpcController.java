package com.xiaoerzuche.biz.zmxy.web.rpc;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.spi.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xiaoerzuche.biz.payment.vo.ReturnDataVo;
import com.xiaoerzuche.biz.payment.web.rpc.BaseController;
import com.xiaoerzuche.biz.zmxy.config.AliOpendApiClient;
import com.xiaoerzuche.biz.zmxy.model.AliMember;
import com.xiaoerzuche.biz.zmxy.service.AliMemberAuthService;
import com.xiaoerzuche.common.util.CheckUtil;

/** 
* 蚂蚁金服用户授权
* @author: cyz
* @version: payment-web
* @time: 2017年10月16日
* 
*/
@RestController
@RequestMapping("/rpc")
public class AliMemberAuthRpcController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(AliMemberAuthRpcController.class);
	@Autowired
	private AliMemberAuthService aliMemberAuthService;
	@Autowired
	private AliOpendApiClient aliOpendApiClient;
	
	@RequestMapping(value = "/v1.0/ali/auth/member", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ReturnDataVo authMember(String thirdUserId, String token){
		logger.info("[查询支付宝授权用户信息][thirdUserId={}][token={}]", new Object[]{thirdUserId, token});
		//判断是token是否有效
		AliMember aliMember = aliMemberAuthService.getByPaymentToken(token);
		CheckUtil.assertNotNull(aliMember, ErrorCode.GENERIC_FAILURE, "无效token");
		CheckUtil.assertEquals(aliMember.getAliUserId(), thirdUserId, ErrorCode.GENERIC_FAILURE, "无效thirdUserId");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("thirdUserId", "ALI"+aliMember.getAliUserId());
		map.put("name", aliMember.getName());
		map.put("idCard", aliMember.getIdCard());
		map.put("mobilePhone", aliMember.getMobilePhone());
		map.put("gender", String.valueOf(aliMember.getGender()).equals("f")? 0:1);
		map.put("redirect_uri", aliOpendApiClient.aliAuthRedirectH5Url());
		return responseEntity(map);
	}
}
