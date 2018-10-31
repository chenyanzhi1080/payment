package com.xiaoerzuche.biz.zmxy.web.rpc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xiaoerzuche.biz.payment.vo.PagerVo;
import com.xiaoerzuche.biz.payment.vo.ReturnDataVo;
import com.xiaoerzuche.biz.payment.web.rpc.BaseController;
import com.xiaoerzuche.biz.zmxy.bz.CommeZmxyBz;
import com.xiaoerzuche.biz.zmxy.model.MemberCredit;
import com.xiaoerzuche.biz.zmxy.service.MemberCreditService;
import com.xiaoerzuche.biz.zmxy.service.ZhiMaAuthService;
import com.xiaoerzuche.biz.zmxy.vo.MemberBlacklistFromVo;
import com.xiaoerzuche.biz.zmxy.vo.MemberCreditFormVo;
import com.xiaoerzuche.biz.zmxy.vo.MemberCreditMgtVo;
import com.xiaoerzuche.biz.zmxy.vo.MemberCreditQueryVo;
import com.xiaoerzuche.biz.zmxy.vo.MemberCreditVo;
import com.xiaoerzuche.biz.zmxy.vo.ZmRiskVo;
import com.xiaoerzuche.common.enu.ErrorCode;
import com.xiaoerzuche.common.util.CheckUtil;
/** 
* 芝麻信用rpc
* @author: cyz
* @version: payment-web
* @time: 2017年5月9日
* 
*/
@RestController
@RequestMapping("/rpc")
public class ZmAuthRpcController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(ZmAuthRpcController.class);
	@Autowired
	private ZhiMaAuthService zhiMaAuthService;
	@Autowired
	private MemberCreditService memberCreditService;
	
	/**
	 * 检查授权情况、信用状态、芝麻分（只支持2017sp17之前的客户端使用）
	 * @param account
	 * @return
	@RequestMapping(value = "/v1.0/zmxy/isAuth/{account}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	private ReturnDataVo isAuth(@PathVariable(value = "account") String account){
		MemberCreditVo creditVo = new MemberCreditVo();
		Integer creditStatus = zhiMaAuthService.getMemberCreditStatus(account);
		creditVo.setCreditStatus(creditStatus);
		logger.info("[获取授权用户{}信征情况结果{}]",new Object[]{account,creditStatus});
		return responseEntity(creditVo);
	}
		 */
	
	/**
	 * 1、需要查询用户当前芝麻信用信征情况（是否授权、是否有负面记录、是否达到芝麻分标准）
	 * 2、获取用户当前芝麻信用分
	 * 3、当前运营设置的芝麻信用分门槛
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/v1.0/zmxy/creditStatus/{account}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	private ReturnDataVo creditStatus(@PathVariable(value = "account") String account){
		//1、需要查询用户当前芝麻信用信征情况（是否授权、是否有负面记录、是否达到芝麻分标准）2、获取用户当前芝麻信用分3、当前运营设置的芝麻信用分门槛
		MemberCreditVo creditVo = zhiMaAuthService.getMemberCreditStatus(account);
		logger.info("[获取授权用户{}信征情况结果{}]",new Object[]{account,creditVo});
		return responseEntity(creditVo);
	}
	
	/**
	 * 用户风控列表
	 * @param account
	 * @param cardId
	 * @param limit 页长
	 * @param offset 页码
	 * @return
	 */
	@RequestMapping(value = "/v1.0/zmxy/member/credit", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	private ReturnDataVo memberCreditList(MemberCreditQueryVo queryVo){
		logger.info("[用户风控列表查询入参]"+queryVo);
		PagerVo<MemberCreditMgtVo> pagerVo = memberCreditService.memberCreditList(queryVo);
		return responseEntity(pagerVo);
	}
	
	/**
	 * 上报行业关注名单
	 * @param operator
	 * @param formVo
	 * @return
	 */
	@RequestMapping(value = "/v1.0/zmxy/member/credit", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	private ReturnDataVo sendZhimaCreditWatch(String operator, @RequestBody MemberCreditFormVo formVo ){
		CheckUtil.assertNotBlank(formVo.getId(), "id is null");
		CheckUtil.assertNotBlank(formVo.getBehaviourCode(), "behaviourCode is null");
		CheckUtil.assertNotBlank(formVo.getAmountCode(), "amountCode is null");
		CheckUtil.assertNotBlank(formVo.getOverdueCode(), "overdueCode is null");
		CheckUtil.assertNotBlank(formVo.getCurrentStateCode(), "currentStateCode is null");
		CheckUtil.assertNotBlank(formVo.getOverdueTime(), "overdueTime is null");
//		CheckUtil.assertNotBlank(formVo.getPasswd(), "passwd is null");
		formVo.setOperator(operator);
		CheckUtil.assertTrue(memberCreditService.sendZhimaCreditWatch(formVo), ErrorCode.UNKOWN.getErrorCode(), "操作失败");
		return responseEntity(null);
	}
	
	
	/**
	 * 开开出行平台黑名单提交
	 * @param operator
	 * @param formVo
	 * @return
	 */
	@RequestMapping(value = "/v1.0/zmxy/member/credit", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	private ReturnDataVo setBlacklist(String operator, @RequestBody MemberBlacklistFromVo formVo ){
		CheckUtil.assertNotBlank(formVo.getId(), "id is null");
		CheckUtil.assertNotBlank(formVo.getBlackReasonCode(), "blackReasonCode is null");
//		CheckUtil.assertNotBlank(formVo.getPasswd(), "passwd is null");
		formVo.setOperator(operator);
		CheckUtil.assertTrue(memberCreditService.setBlacklist(formVo), ErrorCode.UNKOWN.getErrorCode(), "操作失败");
		return responseEntity(null);
	}
	
	@RequestMapping(value = "/v1.0/zmxy/queryZmAuth", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ReturnDataVo queryZmAuth(String name, String cardId, String account){
		logger.info("[主动查询芝麻信用授权][name={}][cardId={}][account={}]", new Object[]{name, cardId, account});
		return responseEntity(zhiMaAuthService.queryZmAuth(name, cardId, account));
	}
	
	/**
	 * 获取风控页面公共数据
	 * @return
	 */
	@RequestMapping(value = "/v1.0/zmxy/member/credit/comme", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ReturnDataVo getCommeZmxyBz(){
		CommeZmxyBz commeZmxyBz = memberCreditService.getCommeZmxyBz();
		return responseEntity(commeZmxyBz);
	}
		
	/**
	 * 负面记录类型列表(行业关注名单风险信息行业编码)
	 * @return
	 */
	@RequestMapping(value = "/v1.0/zmxy/member/credit/zmRisk", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ReturnDataVo zmRiskList(){
		List<ZmRiskVo> zmRisks = memberCreditService.zmRiskList();
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("zmRisks", zmRisks);
		return responseEntity(data);
	}
	
	@RequestMapping(value = "/v1.0/zmxy/member", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ReturnDataVo getZmxyMember(String id){
		logger.info("[查询芝麻信用用户信息]"+id);
		MemberCredit memberCredit = memberCreditService.get(id);
		CheckUtil.assertNotNull(memberCredit, ErrorCode.NOT_FOUND.getErrorCode(), ErrorCode.NOT_FOUND.getMessage());
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("idCard", memberCredit.getCardId());
		data.put("memberName", memberCredit.getName());
		data.put("mobilePhone", memberCredit.getAccount());
		return responseEntity(data);
	}

	@RequestMapping(value = "/v1.0/zmxy/unbind", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ReturnDataVo unbind(String account,String id,String operation){
		CheckUtil.assertNotBlank(account, "account is null");
		CheckUtil.assertNotBlank(operation, "operation is null");
		CheckUtil.assertTrue(memberCreditService.unbind(account, id, operation),ErrorCode.NOENOUGH.getErrorCode(),"解绑失败");
		return responseEntity(null);
	}
}
