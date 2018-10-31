package junit.zmxy;

import java.util.Map;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.parser.json.JsonConverter;
import com.xiaoerzuche.biz.zmxy.bo.ZmWatchListDetailBo;
import com.xiaoerzuche.common.util.JsonUtil;

public class ZmWatchListDetailBoTest {
	public static void main(String[] agrs) throws AlipayApiException{
		String json = "[{\"bizCode\":\"AA\",\"code\":\"AA001001\",\"extendInfo\":[{\"description\":\"逾期金额（元）\",\"key\":\"event_max_amt_code\",\"value\":\"M01\"},{\"description\":\"编号\",\"key\":\"id\",\"value\":\"f2ff5e72b4d88c6c547a15531314d035\"},{\"description\":\"最近一次违约时间\",\"key\":\"event_end_time_desc\",\"value\":\"2014-12\"}],\"level\":0,\"refreshTime\":\"2017-05-24 00:00:00\",\"settlement\":true,\"type\":\"AA001\"}]";
		JsonConverter converter = new JsonConverter();
		Map<String, String> jsonMap = JsonUtil.fromJson(json, Map.class);
		ZmWatchListDetailBo bo = converter.fromJson(jsonMap, ZmWatchListDetailBo.class);
	}
}
