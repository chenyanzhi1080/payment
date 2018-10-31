package com.xiaoerzuche.biz.zmxy.web.api;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.xiaoerzuche.biz.payment.model.Members;
import com.xiaoerzuche.biz.payment.vo.ReturnDataVo;
import com.xiaoerzuche.biz.payment.web.rpc.BaseController;
import com.xiaoerzuche.biz.permission.util.SessionUtil;
import com.xiaoerzuche.biz.zmxy.dao.MemberOutsideDao;
import com.xiaoerzuche.biz.zmxy.service.ZhiMaAuthService;
import com.xiaoerzuche.biz.zmxy.vo.MemberCreditVo;
import com.xiaoerzuche.biz.zmxy.vo.ZmAuthCallBackVo;
import com.xiaoerzuche.biz.zmxy.vo.antifraud.MemberOutsideVo;
import com.xiaoerzuche.common.util.CheckUtil;


/** 
* 芝麻信用API
* @author: cyz
* @version: payment-web
* @time: 2017年5月5日
* 
*/
@RestController
@RequestMapping("/api")
public class ZmAuthApiController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(ZmAuthApiController.class);
	@Autowired
	private ZhiMaAuthService zhiMaAuthService;
	@Autowired
	private MemberOutsideDao memberOutsideDao;
	
	/**
	 * 获取授权用户信征情况（只支持2017sp17之前的客户端使用）
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/v1.0/zmxy/isAuth", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	private ReturnDataVo isAuth(HttpServletRequest request){
		Members members = SessionUtil.getLoginMember(request);
		String account = members.getPhone();
		long starage = System.currentTimeMillis();
		Integer creditStatus = zhiMaAuthService.getCreditStatus(account);
		long endage = System.currentTimeMillis();
		logger.info("[获取授权用户{}信征情况结果{}][age={}]",new Object[]{account,creditStatus,(endage-starage)});
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("creditStatus", creditStatus);
		
		return responseEntity(map);
	}

	/**
	 * 1、需要查询用户当前芝麻信用信征情况（是否授权、是否有负面记录、是否达到芝麻分标准）
	 * 2、获取用户当前芝麻信用分
	 * 3、当前运营设置的芝麻信用分门槛
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/v2.0/zmxy/creditStatus", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	private ReturnDataVo creditStatus(HttpServletRequest request){
		long starage = System.currentTimeMillis();
		Members members = SessionUtil.getLoginMember(request);
		String account = members.getPhone();
		//1、需要查询用户当前芝麻信用信征情况（是否授权、是否有负面记录、是否达到芝麻分标准）2、获取用户当前芝麻信用分3、当前运营设置的芝麻信用分门槛
		logger.info("[获取授权用户{}信征情况开始]",new Object[]{account});
		MemberCreditVo creditVo = zhiMaAuthService.getMemberCreditStatus(account);
		String idCard = creditVo.getIdCard();
		if(StringUtils.isNotBlank(idCard)){
			char[] array = idCard.toCharArray();
			StringBuffer buffer = new StringBuffer();
			for(int i=0;i<array.length;i++){
				if(i==0 || i==array.length-1){
					buffer.append(array[i]);
				}else{
					buffer.append("*");
				}
			}
			creditVo.setIdCard(buffer.toString());
		}
		
		long endage = System.currentTimeMillis();
		logger.info("[获取授权用户{}信征情况结果{}][age={}]",new Object[]{account,creditVo,(endage-starage)});
		return responseEntity(creditVo);
	}

	/**
	 * 1、需要查询用户当前芝麻信用信征情况（是否授权、是否有负面记录、是否达到芝麻分标准）
	 * 2、获取用户当前芝麻信用分
	 * 3、当前运营设置的芝麻信用分门槛
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/v3.0/zmxy/creditStatus", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	private ReturnDataVo creditStatusV3(HttpServletRequest request){
		long starage = System.currentTimeMillis();
		Members members = SessionUtil.getLoginMember(request);
		String account = members.getPhone();
		//1、需要查询用户当前芝麻信用信征情况（是否授权、是否有负面记录、是否达到芝麻分标准）2、获取用户当前芝麻信用分3、当前运营设置的芝麻信用分门槛
		logger.info("[获取授权用户{}信征情况开始]",new Object[]{account});
		MemberCreditVo creditVo = zhiMaAuthService.getMemberCreditStatus(account);
		long endage = System.currentTimeMillis();
		logger.info("[获取授权用户{}信征情况结果{}][age={}]",new Object[]{account,creditVo,(endage-starage)});
		return responseEntity(creditVo);
	}
	
	/**
	 * 获取授权页面
	 * @param name
	 * @param cardId
	 * @param successUrl
	 * @param failUrl
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/v1.0/zmxy/auth", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	private ReturnDataVo authUlr(String name, String cardId, String successUrl, String failUrl, HttpServletRequest request){
		//获取当前登录用户
		Members members = SessionUtil.getLoginMember(request);
		String account = members.getPhone();
		logger.info("[芝麻信用授权][name={}][cardId={}][account={}][successUrl={}][failUrl={}]",
				new Object[]{name,cardId,account,successUrl,failUrl});
		CheckUtil.assertNotBlank(cardId, "身份证不能为空");
		if(cardId.indexOf("*")>0){
			MemberOutsideVo memberOutsideVo = memberOutsideDao.getByMobilePhone(members.getPhone());
			cardId = memberOutsideVo.getIdCard();
			name = memberOutsideVo.getMemberName();
		}
		String memberCreditId = zhiMaAuthService.genMemberCredit(account, name, cardId);
		String authUrl = zhiMaAuthService.authUrl(account, name, cardId, successUrl, failUrl, memberCreditId);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("authUrl", authUrl);
		return responseEntity(map);
	}
	/**
	 * 获取授权页面,身份证重复者被拦截
	 * @param name
	 * @param cardId
	 * @param successUrl
	 * @param failUrl
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/v2.0/zmxy/auth", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	private ReturnDataVo authUlr2(String name, String cardId, String successUrl, String failUrl, HttpServletRequest request){
		//获取当前登录用户
		Members members = SessionUtil.getLoginMember(request);
		String account = members.getPhone();
		logger.info("[芝麻信用授权][身份唯一拦截][name={}][cardId={}][account={}][successUrl={}][failUrl={}]",
				new Object[]{name,cardId,account,successUrl,failUrl});
		CheckUtil.assertNotBlank(cardId, "身份证不能为空");
		if(cardId.indexOf("*")>0){
			MemberOutsideVo memberOutsideVo = memberOutsideDao.getByMobilePhone(members.getPhone());
			cardId = memberOutsideVo.getIdCard();
			name = memberOutsideVo.getMemberName();
		}
		//身份重复拦截
		CheckUtil.assertTrue(zhiMaAuthService.filter(account, name, cardId),403001, "该身份信息已被其他用户授权过芝麻信用，不能重复授权。请联系在线客服进行申诉。");
		String memberCreditId = zhiMaAuthService.genMemberCredit(account, name, cardId);
		String authUrl = zhiMaAuthService.authUrl(account, name, cardId, successUrl, failUrl, memberCreditId);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("authUrl", authUrl);
		return responseEntity(map);
	}	
	/**
	 * 直接跳转到授权
	 * @param name
	 * @param cardId
	 * @param successUrl
	 * @param failUrl
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1.0/zmxy/auth2", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	private ModelAndView authView(String name, String cardId, String successUrl, String failUrl, HttpServletRequest request, HttpServletResponse response){
		//获取当前登录用户
		Members members = SessionUtil.getLoginMember(request);
		String account = members.getPhone();
		logger.info("[芝麻信用授权直接跳转][name={}][cardId={}][account={}][successUrl={}][failUrl={}]",
				new Object[]{name,cardId,account,successUrl,failUrl});
		CheckUtil.assertNotBlank(cardId, "身份证不能为空");
		if(cardId.indexOf("*")>0){
			MemberOutsideVo memberOutsideVo = memberOutsideDao.getByMobilePhone(members.getPhone());
			cardId = memberOutsideVo.getIdCard();
			name = memberOutsideVo.getMemberName();
		}
		String memberCreditId = zhiMaAuthService.genMemberCredit(account, name, cardId);
		String authUrl = zhiMaAuthService.authUrl(account, name, cardId, successUrl, failUrl, memberCreditId);
		return new ModelAndView("redirect:"+authUrl);
	}
	
	@RequestMapping(value = "/zmxy/auth/callback")
	private ModelAndView authCallBack(String sign, String params, HttpServletRequest request, HttpServletResponse response) throws Exception{
		ZmAuthCallBackVo authCallBackVo = zhiMaAuthService.paseParam(sign, params);
		logger.info("[芝麻信用授权回调结果]"+authCallBackVo);
		boolean result = zhiMaAuthService.authCallBack(authCallBackVo);
		
		String redirect = "";
		if(result && ( "true".equals(String.valueOf(authCallBackVo.getSuccess()).toLowerCase()) )){
			redirect = authCallBackVo.getAuthStateVo().getSuccessUrl();
		}else{
			redirect = authCallBackVo.getAuthStateVo().getFailUrl();
		}
		logger.info("[芝麻信用授权回调跳转]"+redirect);
		return new ModelAndView("redirect:"+redirect);
	}
	
	/**
	 * 芝麻信用授权回调默认跳转终点
	 * @param result
	 */
	@RequestMapping(value = "/zmxy/auth/callback/result")
	public void authCallBackRusult(String result){
		logger.info("[芝麻信用授权回调跳转]"+result);
	}
	
	
}
