package junit.zmxy;

import org.junit.Test;
import org.quartz.JobExecutionException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.xiaoerzuche.biz.zmxy.schedule.ZmBillDefaultJob;

import junit.BaseTest;

public class ScheduleTest extends BaseTest{
	@Autowired
	private ApplicationContext context;
	@Test
	public void zmBillDefaultJob(){
		try {
			ZmBillDefaultJob zmBillDefaultJob = context.getBean(ZmBillDefaultJob.class);
			zmBillDefaultJob.execute(null);
		} catch (BeansException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JobExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void zmUnSettledBillJob(){
		
	}
	@Test
	public void zmBillDefaultToSetteldJob(){
		
	}
}
