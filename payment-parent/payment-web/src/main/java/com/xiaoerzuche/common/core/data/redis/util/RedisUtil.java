package com.xiaoerzuche.common.core.data.redis.util;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import redis.clients.jedis.Protocol;
import redis.clients.jedis.Tuple;
import redis.clients.jedis.ZParams;

import com.xiaoerzuche.common.core.data.redis.pool.IJedisPool;
import com.xiaoerzuche.common.core.data.redis.pool.JedisPoolApacheImpl;
import com.xiaoerzuche.common.util.CheckUtil;
import com.xiaoerzuche.common.util.NumberUtil;

/**
 * redis工具类
 * @author Nick C
 *
 */
public class RedisUtil {
	/**
	 * 创建连接池.
	 * 
	 * @param server
	 *            服务器
	 * @param timeout
	 *            超时时间
	 * @return
	 */
	public static IJedisPool createJedisPool(String server, int timeout, String password) {
		int maxActive = 32;
		return createJedisPool(server, timeout, maxActive, password);
	}

	/**
	 * 创建连接池.
	 * 
	 * @param server
	 *            服务器
	 * @param timeout
	 *            超时时间
	 * @param maxActive
	 *            最大连接数
	 * @return
	 */
	public static IJedisPool createJedisPool(String server, int timeout, int maxActive, String password) {
		if (maxActive <= 0) {
			maxActive = 128;
		}
		if (timeout <= 0) {
			timeout = 10000;
		}

		String[] serverInfo = server.split(":");
		String host = serverInfo[0].trim();
		int port;
		if (serverInfo.length == 1) {
			port = 6379;
		}else {
			port = Integer.parseInt(serverInfo[1].trim());
		}
		
		int database = Protocol.DEFAULT_DATABASE;
		if(serverInfo.length > 2){
			database = NumberUtil.toInt(serverInfo[2].trim());
		}
		
		return new JedisPoolApacheImpl(host, port, timeout, maxActive, password, database);

	}

	// public static JedisPool createJedisPoolOld(String server, int timeout, int maxActive) {
	// AssertUtil.assertNotEmpty(server, "参数server不能为空.");
	// if (maxActive <= 0) {
	// maxActive = 128;
	// }
	// if (timeout <= 0) {
	// timeout = 3000;
	// }
	//
	//
	// String[] serverInfo = server.split(":");
	// String host = serverInfo[0].trim();
	// int port;
	// if (serverInfo.length == 1) {
	// port = 6379;
	// }
	// else {
	// port = Integer.parseInt(serverInfo[1].trim());
	// }
	//
	// }

	/**
	 * 判断key-value对List是否为空.
	 * 
	 * @param keyList
	 *            key列表
	 * @param valueList
	 *            value列表
	 * @return
	 */
	public static boolean checkList(List<String> keyList, List<String> valueList) {
		CheckUtil.assertNotEmpty(keyList, "参数keyList不能为空.");
		CheckUtil.assertNotEmpty(valueList, "参数valueList不能为空.");
		// if (keyList == null || keyList.isEmpty()) {
		// throw new IllegalArgumentException("参数keyList不能为空.");
		// }
		// if (valueList == null || valueList.isEmpty()) {
		// throw new IllegalArgumentException("参数valueList不能为空.");
		// }
		if (keyList.size() != valueList.size()) {
			throw new IllegalArgumentException("参数keyList和valueList长度不一致.");
		}
		return true;
	}

	/**
	 * 返回AGGREGATE参数选项.
	 * 
	 * @param params
	 *            参数
	 * @return
	 */
	public static ZParams.Aggregate getAggregate(ZParams params) {
		Collection<byte[]> collect = params.getParams();
		Iterator<byte[]> iterator = collect.iterator();
		String key = new String(iterator.next());
		if (!"aggregate".equals(key)) {
			return ZParams.Aggregate.SUM;
		}
		String type = new String(iterator.next());
		return ZParams.Aggregate.valueOf(type);
	}

	/**
	 * 返回WEIGHTS参数列表.
	 * 
	 * @param params
	 *            参数
	 * @return
	 */
	public static List<Double> getWeights(ZParams params) {
		Collection<byte[]> collect = params.getParams();
		Iterator<byte[]> iterator = collect.iterator();
		boolean hasWeights = false;
		while (iterator.hasNext()) {
			String value = new String(iterator.next());
			// System.out.println("value:" + value);
			if ("weights".equals(value)) {
				hasWeights = true;
				break;
			}
		}
		if (!hasWeights) {
			return null;
		}
		List<Double> result = new ArrayList<Double>();
		while (iterator.hasNext()) {
			String weight = new String(iterator.next());
			result.add(Double.parseDouble(weight));
		}
		return result;
	}

	/**
	 * 返回WEIGHTS选项的默认值.
	 * 
	 * @param sets
	 * @return
	 */
	public static int[] getDefaultWeights(String... sets) {
		int[] weights = new int[sets.length];
		for (int i = 0; i < sets.length; i++) {
			weights[i] = 1;
		}
		return weights;
	}

	/**
	 * 获取第一个元素的score.
	 * 
	 * @param set
	 * @return
	 */
	public static Double getFirstScore(Set<Tuple> set) {
		if (set == null || set.isEmpty()) {
			return null;
		}
		Tuple tuple = set.iterator().next();
		Double score = tuple.getScore();
		return score;
	}

	/**
	 * 将有序集转成List.
	 * 
	 * @param set
	 *            有序集
	 * @return
	 */
	public static List<Entry<String, Double>> toEntryList(Set<Tuple> set) {
		if (set == null || set.isEmpty()) {
			return null;
		}
		List<Entry<String, Double>> result = new ArrayList<Entry<String, Double>>();
		for (Tuple tuple : set) {
			String element = tuple.getElement();
			Double score = tuple.getScore();
			Entry<String, Double> entry = new SimpleEntry<String, Double>(element, score);
			result.add(entry);
		}
		return result;
	}

	// /**
	// * 输出redis服务器信息.
	// *
	// * @param redisBase
	// */
	// public static void printServerInfo(RedisBase redisBase) {
	// for (Redis redis : redisBase.getRedisList()) {
	// String serverInfo = redis.getServerInfo();
	// String host = serverInfo.split(":")[0];
	// InetAddress inetAddress;
	// try {
	// inetAddress = InetAddress.getByName(host);
	// }
	// catch (UnknownHostException e) {
	// throw new RuntimeException(e.getMessage(), e);
	// }
	//
	// String ip = inetAddress.getHostAddress();
	// System.out.println("server:" + serverInfo + " ip:" + ip);
	// }
	// }

	/**
	 * 将有序集的元素转成String，保存到集合中.
	 * 
	 * @param set
	 * @return
	 */
	public static Set<String> tupleToString(Set<Tuple> set) {
		Set<String> result = new LinkedHashSet<String>();
		for (Tuple tuple : set) {
			String element = tuple.getElement();
			result.add(element);
		}
		return result;
	}

	/**
	 * 将有序集中元素对应的score转成Double，保存到集合中.
	 * 
	 * @param set
	 * @return
	 */
	public static Set<Double> tupleToScores(Set<Tuple> set) {
		Set<Double> result = new LinkedHashSet<Double>();
		for (Tuple tuple : set) {
			Double score = tuple.getScore();
			result.add(score);
		}
		return result;
	}

}
