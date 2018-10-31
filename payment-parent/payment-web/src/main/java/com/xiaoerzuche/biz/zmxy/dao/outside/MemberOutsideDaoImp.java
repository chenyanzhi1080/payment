package com.xiaoerzuche.biz.zmxy.dao.outside;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;

import com.google.gson.reflect.TypeToken;
import com.xiaoerzuche.biz.zmxy.dao.MemberOutsideDao;
import com.xiaoerzuche.biz.zmxy.util.RestClien;
import com.xiaoerzuche.biz.zmxy.vo.antifraud.MemberOutsideVo;
import com.xiaoerzuche.common.util.JsonUtil;

import net.sf.json.JSONObject;
@Repository
public class MemberOutsideDaoImp implements MemberOutsideDao{
	private static final Logger logger = LoggerFactory.getLogger(MemberOutsideDaoImp.class);
//	@Value("${SMALLTWO_URL}")
//	private String smalltwoUrl;
	@Value("${message.baseUrl}")
	private String smalltwoUrl;
	@Autowired
	private RestClien restClien;
	
	@Override
	public List<MemberOutsideVo> list(Integer limit, Integer offset) {
		List<MemberOutsideVo> list = new ArrayList<MemberOutsideVo>();
		String url = smalltwoUrl+"/admin/member/qzfk/list.ihtml?limit="+limit+"&offset="+offset;
		logger.info("[MemberOutsideDaoImp.list][url={}]", new Object[]{url});
		try {
			String json = restClien.restClien(String.class,url, null, MediaType.APPLICATION_JSON, HttpMethod.GET);
			logger.info("[MemberOutsideDaoImp.list][url={}][restRespone={}]", new Object[]{url,json});
			JSONObject jsonObject = JSONObject.fromObject(json);
			int code = jsonObject.getInt("code");
			if(code==200){
				JSONObject datas = JSONObject.fromObject(jsonObject.get("datas"));
				String data = datas.getString("data");
				list = JsonUtil.fromJson(data, new TypeToken<List<MemberOutsideVo>>(){});
			}
		} catch (Exception e) {
			logger.error("[MemberOutsideDaoImp.list][url={}]{}", new Object[]{url,e});
		}
		return list;
	}
	@Override
	public boolean setBlack(String memberId) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public MemberOutsideVo get(String memberId) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public MemberOutsideVo getByMobilePhone(String mobilePhone) {
		String url = smalltwoUrl+"/admin/member/getMemberInfo.ihtml?mobilePhone="+mobilePhone;
		try {
			String json = restClien.restClienForJson(url, null, MediaType.APPLICATION_JSON, HttpMethod.GET);
			logger.info("[MemberOutsideDaoImp.getByMobilePhone][url={}][restRespone={}]", new Object[]{url,json});
			JSONObject jsonObject = JSONObject.fromObject(json);
			int code = jsonObject.getInt("code");
			if(code==200){
				JSONObject datas = JSONObject.fromObject(jsonObject.get("datas"));
				String memberName = datas.getString("memberName");
				String idCard = datas.getString("idCard");
				MemberOutsideVo memberOutsideVo = new MemberOutsideVo();
				memberOutsideVo.setIdCard(idCard);
				memberOutsideVo.setMemberName(memberName);
				return memberOutsideVo;
			}
		} catch (Exception e) {
			logger.error("[MemberOutsideDaoImp.getByMobilePhone][url={}]{}", new Object[]{url,e});
		}
		return null;
	}
	
}
