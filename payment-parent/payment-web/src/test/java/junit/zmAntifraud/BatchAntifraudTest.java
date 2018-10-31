package junit.zmAntifraud;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.xiaoerzuche.biz.zmxy.dao.MemberOutsideDao;
import com.xiaoerzuche.biz.zmxy.model.MemberAntifraud;
import com.xiaoerzuche.biz.zmxy.service.ZhiMaAntifraudService;
import com.xiaoerzuche.biz.zmxy.vo.antifraud.MemberOutsideVo;
import com.xiaoerzuche.common.util.JsonUtil;

import junit.BaseTest;

public class BatchAntifraudTest extends BaseTest{
	@Autowired
	private MemberOutsideDao memberOutsideDao;
	@Autowired
	private ZhiMaAntifraudService zhiMaAntifraudService;
	
	@Test
	public void batchAntifraud(){
		int limit = 100;
		int offset = 0;
		boolean recyclable = true;
		while(recyclable){
			System.out.println("limit="+limit);
			System.out.println("offset="+offset);
			List<MemberOutsideVo> list = memberOutsideDao.list(limit, offset);
//			for(MemberOutsideVo memberOutsideVo : list){
//				MemberAntifraud memberAntifraud = new MemberAntifraud();
//				zhiMaAntifraudService.synthesizeAntifraud(memberAntifraud);
//			}
			offset = offset+limit;
			recyclable = (offset <= 800);
//			recyclable = list.size() >= limit;
		}
	}
	
}
