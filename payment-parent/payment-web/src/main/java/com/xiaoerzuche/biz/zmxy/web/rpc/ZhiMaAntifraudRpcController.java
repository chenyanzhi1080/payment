package com.xiaoerzuche.biz.zmxy.web.rpc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xiaoerzuche.biz.payment.vo.PagerVo;
import com.xiaoerzuche.biz.payment.vo.ReturnDataVo;
import com.xiaoerzuche.biz.payment.web.rpc.BaseController;
import com.xiaoerzuche.biz.zmxy.enu.AntifraudScoreRiskEnu;
import com.xiaoerzuche.biz.zmxy.enu.SynthesizeAntifraudEnu;
import com.xiaoerzuche.biz.zmxy.model.MemberAntifraud;
import com.xiaoerzuche.biz.zmxy.service.MemberAntifraudService;
import com.xiaoerzuche.biz.zmxy.service.ZhiMaAntifraudService;
import com.xiaoerzuche.biz.zmxy.vo.antifraud.AntifraudRemarkForm;
import com.xiaoerzuche.biz.zmxy.vo.antifraud.AntifraudScoreRiskVo;
import com.xiaoerzuche.biz.zmxy.vo.antifraud.MemberAntifraudMgtVo;
import com.xiaoerzuche.biz.zmxy.vo.antifraud.MemberAntifraudQueryVo;
import com.xiaoerzuche.biz.zmxy.vo.antifraud.MemberAntifraudResultVo;
import com.xiaoerzuche.common.enu.ErrorCode;
import com.xiaoerzuche.common.util.CheckUtil;

/** 
* 芝麻欺诈风控
* @author: cyz
* @version: payment-web
* @time: 2017年10月10日
* 
*/
@RestController
@RequestMapping("/rpc")
public class ZhiMaAntifraudRpcController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(ZhiMaAntifraudRpcController.class);
	@Autowired
	private MemberAntifraudService memberAntifraudService;
	@Autowired
	private ZhiMaAntifraudService zhiMaAntifraudService;
	/**
	 * 欺诈风控列表
	 * @param antifraudQueryVo
	 * @return
	 */
	@RequestMapping(value = "/v1.0/zmxy/antifraud/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	private ReturnDataVo antifraudList(MemberAntifraudQueryVo antifraudQueryVo){
		logger.info("[风控欺诈列表查询]"+antifraudQueryVo);
		PagerVo<MemberAntifraudMgtVo> pagerVo = memberAntifraudService.list(antifraudQueryVo);
		return responseEntity(pagerVo);
	}
	
	/**
	 * 用户欺诈风控信息查询
	 * @param account
	 * @param idCard
	 * @param name
	 * @return
	 */
	@RequestMapping(value = "/v1.0/zmxy/antifraud/user", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	private ReturnDataVo antifraudUser(String account, String idCard, String name){
		logger.info("[用户欺诈风控信息查询][account={}][idCard={}][name={}]",new Object[]{account, idCard, name});
		CheckUtil.assertNotBlank(account, "手机号不能为空");
		CheckUtil.assertNotBlank(idCard, "身份证号不能为空");
		CheckUtil.assertNotBlank(name, "姓名不能为空");
		MemberAntifraud memberAntifraud = new MemberAntifraud();
		memberAntifraud.setAccount(account).setIdCard(idCard).setName(name);
		//分析用户的欺诈风险
		zhiMaAntifraudService.synthesizeAntifraud(memberAntifraud,SynthesizeAntifraudEnu.NAME_PHONE_IDCARD);
		CheckUtil.assertTrue(StringUtils.isBlank(memberAntifraud.getErrorMessage()), ErrorCode.UNKOWN.getErrorCode(), memberAntifraud.getErrorMessage());
		MemberAntifraudResultVo antifraudResultVo = zhiMaAntifraudService.antifraudUser(memberAntifraud);
		return responseEntity(antifraudResultVo);
	}
	
	/**
	 * 用户信息可靠性验证.验证项,要求手机号与姓名匹配、身份证号与姓名匹配
	 * @param account 
	 * @param name
	 * @param cardId
	 * @return
	 */
	@RequestMapping(value = "/v1.0/zmxy/identity/auth", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ReturnDataVo identityAuth(String account, String name, String idCard){
		logger.info("[用户信息可靠性认证][account={}][idCard={}][name={}]",new Object[]{account, idCard, name});
		MemberAntifraud memberAntifraud = new MemberAntifraud();
		memberAntifraud.setAccount(account).setIdCard(idCard).setName(name);
/*		//分析用户的欺诈风险  停用
		zhiMaAntifraudService.synthesizeAntifraud(memberAntifraud,SynthesizeAntifraudEnu.NAME_IDCARD);
		boolean identityAuth = zhiMaAntifraudService.identityAuth(memberAntifraud);
		return responseEntity(identityAuth);*/
		return responseEntity(true);
	}
	/**
	 * 添加备注
	 * @param antifraudRemarkVo
	 * @return
	 */
	@RequestMapping(value = "/v1.0/zmxy/antifraud/remarks", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ReturnDataVo remarks(@RequestBody AntifraudRemarkForm remarkForm){
		logger.info("[欺诈风控信息添加备注][{}]",new Object[]{remarkForm});
		CheckUtil.assertNotBlank(remarkForm.getId(), "id is null");
		CheckUtil.assertNotBlank(remarkForm.getOperator(), "operator is null");
		CheckUtil.assertNotBlank(remarkForm.getRemark(), "remark is null");
		memberAntifraudService.remark(remarkForm);
		return responseEntity(null);
	}
	
	@RequestMapping(value = "/v1.0/zmxy/antifraud/scoreRisks", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ReturnDataVo scoreRisks(){
		List<AntifraudScoreRiskVo> list = new ArrayList<AntifraudScoreRiskVo>();
		for(AntifraudScoreRiskEnu enu : AntifraudScoreRiskEnu.values()){
			AntifraudScoreRiskVo scoreRiskVo = new AntifraudScoreRiskVo(enu.toString(), enu.getLevelName());
			list.add(scoreRiskVo);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("scoreRisks", list);
		return responseEntity(map);
	}
	
}
