package junit.zmxy;

import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import com.google.gson.reflect.TypeToken;
import com.xiaoerzuche.biz.zmxy.dao.MemberOutsideDao;
import com.xiaoerzuche.biz.zmxy.dao.outside.MemberOutsideDaoImp;
import com.xiaoerzuche.biz.zmxy.util.RestClien;
import com.xiaoerzuche.biz.zmxy.vo.antifraud.MemberOutsideVo;
import com.xiaoerzuche.common.util.JsonUtil;

import junit.BaseTest;
import net.sf.json.JSONObject;

public class MemerInfoTest extends BaseTest{
	private static final Logger logger = LoggerFactory.getLogger(MemerInfoTest.class);
	@Autowired
	private MemberOutsideDao memberOutsideDao;
	@Autowired
	private RestClien restClien;
	
	@Test
	public void list(){
		int limit = 10;
		int offset = 0;
		boolean recyclable = true;
		while(true){
			System.out.println("limit="+limit);
			System.out.println("offset="+offset);
			List<MemberOutsideVo> list = memberOutsideDao.list(limit, offset);
			System.out.println(list.size());
			System.out.println(JsonUtil.toJson(list.get(0)));
			offset = offset+limit;
			recyclable = list.size() >= limit;
			System.out.println(recyclable);
			break;
			/*if(list.size()<limit){
				break;
			}*/
		}
	}
	
	@Test
	public void getMemberInfo(){
//		MemberOutsideVo memberOutsideVo = memberOutsideDao.getByMobilePhone("18289767660");
//		System.out.println(memberOutsideVo);
		String url = "https://www-test.95071222.net/admin/member/getMemberInfo.ihtml?mobilePhone=13096907987";
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
				System.out.println(memberOutsideVo.toString());
			}
		} catch (Exception e) {
			logger.error("[MemberOutsideDaoImp.getByMobilePhone][url={}]{}", new Object[]{url,e});
		}
	}
	
}
