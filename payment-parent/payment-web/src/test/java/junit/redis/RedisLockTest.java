package junit.redis;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.xiaoerzuche.common.core.data.redis.Redis;

import junit.BaseTest;

public class RedisLockTest extends BaseTest{
	@Autowired
	private Redis redis;
	@Test
	public void test(){
		this.lock("test", 3000);
		System.out.println(redis.ttl("test"));
		this.lock("test", 3000);
		System.out.println(redis.ttl("test"));
	}
	
	public boolean lock(String key, long timeStampSecond){
		long result = redis.setnx(key, key);
		System.out.println("result"+result);
		long expireAt = redis.expireAt(key, timeStampSecond);
		System.out.println("expireAt"+expireAt);
		return false;
	}
	
	public static void main(String[] args){
		System.out.println(1&0);
		System.out.println(1|0);
	}
}
