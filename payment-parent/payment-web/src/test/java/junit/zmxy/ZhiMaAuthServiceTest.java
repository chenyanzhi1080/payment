package junit.zmxy;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.xiaoerzuche.biz.zmxy.dao.sqlbuilder.MemberCreditBuilder;
import com.xiaoerzuche.biz.zmxy.enu.AuthStatusEnu;
import com.xiaoerzuche.biz.zmxy.service.ZhiMaAuthService;
import com.xiaoerzuche.common.core.data.jdbc.StatementParameter;

import junit.BaseTest;

public class ZhiMaAuthServiceTest extends BaseTest{
	@Autowired
	private ZhiMaAuthService zhiMaAuthService;
	@Test
	public void filter(){
		boolean filter = zhiMaAuthService.filter("18691527743", "张水旺", "612526199307122297");
		System.out.println(filter);
	}
	public static void main(String[] args){
		MemberCreditBuilder builder = new MemberCreditBuilder();
		builder.setCardId("612526199307122297").setAuthStatus(AuthStatusEnu.AUTH.getCode());
		String sql = "select * from member_credit where 1=1 ";
		StatementParameter sp = new StatementParameter();
		sql = builder.build(sp, sql, true);
		System.out.println(sql);
	}
}
