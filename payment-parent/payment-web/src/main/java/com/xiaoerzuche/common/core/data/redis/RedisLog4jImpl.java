package com.xiaoerzuche.common.core.data.redis;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import redis.clients.jedis.BinaryClient.LIST_POSITION;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.SortingParams;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.Tuple;
import redis.clients.jedis.ZParams;

import com.xiaoerzuche.common.core.data.redis.pool.IJedisPool;

/**
 * Redis单机实现(操作Redis服务器之前会写入log4j日志).
 * @author Nick C
 *
 */
public class RedisLog4jImpl extends AbstractRedis implements Redis {
	private final Logger logDb = Logger.getLogger(RedisLog4jImpl.class);

	private Redis redis;

	private String server;

	public void setRedis(Redis redis) {
		this.redis = redis;
	}

	public void setServer(String server) {
		this.server = server;
	}

	@Override
	public void init() {
		super.init();
		if (StringUtils.isNotEmpty(server)) {
			RedisImpl redisImpl = new RedisImpl(server, maxActive, initialPoolSize, timeout);
			redisImpl.init();
			this.redis = redisImpl;
		}
	}

	@Override
	public void destroy() {
		if (redis != null) {
			this.redis.destroy();
		}
		super.destroy();
	}

	private String encode(String content) {
		try {
			return URLEncoder.encode(content, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	protected String format(double score) {
		NumberFormat formatter = NumberFormat.getNumberInstance();
		formatter.setGroupingUsed(false);
		String s = formatter.format(score);
		return s;
	}

	@Override
	public String set(String key, String value) {
		String result = redis.set(key, value);
		this.logDb.info("set " + key + " " + encode(value));
		return result;
	}

	@Override
	public String get(String key) {
		return redis.get(key);
	}

	@Override
	public Boolean exists(String key) {
		return this.redis.exists(key);
	}

	@Override
	public String type(String key) {
		return this.redis.type(key);
	}

	@Override
	public Long expire(String key, int seconds) {
		Long result = this.redis.expire(key, seconds);
		this.logDb.info("expire " + key + " " + seconds);
		return result;
	}

	@Override
	public Long expireAt(String key, long unixTime) {
		Long result = this.redis.expireAt(key, unixTime);
		this.logDb.info("expireAt " + key + " " + unixTime);
		return result;
	}

	@Override
	public Long ttl(String key) {
		return this.redis.ttl(key);
	}

	@Override
	public Boolean setbit(String key, long offset, boolean value) {
		boolean result = this.redis.setbit(key, offset, value);
		this.logDb.info("setbit " + key + " " + offset + " " + value);
		return result;
	}

	@Override
	public Boolean getbit(String key, long offset) {
		return this.redis.getbit(key, offset);
	}

	@Override
	public Long setrange(String key, long offset, String value) {
		long result = this.redis.setrange(key, offset, value);
		this.logDb.info("setrange " + key + " " + offset + " " + encode(value));
		return result;
	}

	@Override
	public String getrange(String key, long startOffset, long endOffset) {
		return this.redis.getrange(key, startOffset, endOffset);
	}

	@Override
	public String getSet(String key, String value) {
		return this.redis.getSet(key, value);
	}

	@Override
	public Long setnx(String key, String value) {
		Long result = this.redis.setnx(key, value);
		this.logDb.info("setnx " + key + " " + encode(value));
		return result;
	}

	@Override
	public String setex(String key, int seconds, String value) {
		String result = this.redis.setex(key, seconds, value);
		this.logDb.info("setex " + key + " " + seconds + " " + encode(value));
		return result;
	}

	@Override
	public Long decrBy(String key, long integer) {
		Long result = this.redis.decrBy(key, integer);
		this.logDb.info("decrBy " + key + " " + integer);
		return result;
	}

	@Override
	public Long decr(String key) {
		Long result = this.redis.decr(key);
		this.logDb.info("decr " + key);
		return result;
	}

	@Override
	public Long incrBy(String key, long integer) {
		Long result = this.redis.incrBy(key, integer);
		this.logDb.info("incrBy " + key + " " + integer);
		return result;
	}

	@Override
	public Long incr(String key) {
		Long result = this.redis.incr(key);
		this.logDb.info("incr " + key);
		return result;
	}

	@Override
	public Long append(String key, String value) {
		Long result = this.redis.append(key, value);
		this.logDb.info("append " + key + " " + encode(value));
		return result;
	}

	@Override
	public String substr(String key, int start, int end) {
		return this.redis.substr(key, start, end);
	}

	@Override
	public Long hset(String key, int field, String value) {
		return this.hset(key, Integer.toString(field), value);
	}

	@Override
	public Long hset(String key, String field, String value) {
		Long result = this.redis.hset(key, field, value);
		this.logDb.info("hset " + key + " " + field + " " + encode(value));
		return result;
	}

	@Override
	public String hget(String key, int field) {
		return this.hget(key, Integer.toString(field));
	}

	@Override
	public String hget(String key, String field) {
		return this.redis.hget(key, field);
	}

	@Override
	public Long hsetnx(String key, String field, String value) {
		Long result = this.redis.hsetnx(key, field, value);
		this.logDb.info("hsetnx " + key + " " + field + " " + encode(value));
		return result;
	}

	@Override
	public String hmset(String key, Map<String, String> hash) {
		String result = this.redis.hmset(key, hash);
		if (hash != null) {
			Iterator<Entry<String, String>> iterator = hash.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<String, String> entry = iterator.next();
				String field = entry.getKey();
				String value = entry.getValue();
				this.logDb.info("hmset " + key + " " + this.encode(field) + " " + this.encode(value));
			}
		}
		return result;
	}

	@Override
	public List<String> hmget(String key, String... fields) {
		return this.redis.hmget(key, fields);
	}

	@Override
	public Long hincrBy(String key, String field, long value) {
		Long result = this.redis.hincrBy(key, field, value);
		this.logDb.info("hincrBy " + key + " " + field + " " + value);
		return result;
	}

	@Override
	public Boolean hexists(String key, String field) {
		return this.redis.hexists(key, field);
	}

	@Override
	public Long hdel(String key, int field) {
		return this.hdel(key, Integer.toString(field));
	}

	@Override
	public Long hdel(String key, String... field) {
		Long result = this.redis.hdel(key, field);
		this.logDb.info("hdel " + key + " " + field);
		return result;
	}

	@Override
	public Long hlen(String key) {
		return this.redis.hlen(key);
	}

	@Override
	public Set<String> hkeys(String key) {
		return this.redis.hkeys(key);
	}

	@Override
	public List<String> hvals(String key) {
		return this.redis.hvals(key);
	}

	@Override
	public Map<String, String> hgetAll(String key) {
		return this.redis.hgetAll(key);
	}

	@Override
	public Long rpush(String key, String... string) {
		Long result = this.redis.rpush(key, string);
		this.logDb.info("rpush " + key + " " + encode(StringUtils.join(string, " ")));
		return result;
	}

	@Override
	public Long lpush(String key, String... string) {
		Long result = this.redis.lpush(key, string);
		this.logDb.info("lpush " + key + " " + encode(StringUtils.join(string, " ")));
		return result;
	}

	@Override
	public Long llen(String key) {
		return this.redis.llen(key);
	}

	@Override
	public List<String> lrange(String key, long start, long end) {
		return this.redis.lrange(key, start, end);
	}

	@Override
	public String ltrim(String key, long start, long end) {
		return this.redis.ltrim(key, start, end);
	}

	@Override
	public String lindex(String key, long index) {
		return this.redis.lindex(key, index);
	}

	@Override
	public String lset(String key, long index, String value) {
		String result = this.redis.lset(key, index, value);
		this.logDb.info("lset " + key + " " + index + " " + encode(value));
		return result;
	}

	@Override
	public Long lrem(String key, long count, String value) {
		return this.redis.lrem(key, count, value);
	}

	@Override
	public String lpop(String key) {
		String result = this.redis.lpop(key);
		this.logDb.info("lpop " + key);
		return result;
	}

	@Override
	public String rpop(String key) {
		String result = this.redis.rpop(key);
		this.logDb.info("rpop " + key);
		return result;
	}

	@Override
	public Long sadd(String key, String... members) {
		Long result = this.redis.sadd(key, members);
		this.logDb.info("sadd " + key + " " + encode(StringUtils.join(members, " ")));
		return result;
	}

	@Override
	public Set<String> smembers(String key) {
		return this.redis.smembers(key);
	}

	@Override
	public Long srem(String key, String... members) {
		Long result = this.redis.srem(key, members);
		this.logDb.info("srem " + key + " " + encode(StringUtils.join(members, " ")));
		return result;
	}

	@Override
	public String spop(String key) {
		String result = this.redis.spop(key);
		this.logDb.info("spop " + key);
		return result;
	}

	@Override
	public Long scard(String key) {
		return this.redis.scard(key);
	}

	@Override
	public Boolean sismember(String key, String member) {
		return this.redis.sismember(key, member);
	}

	@Override
	public String srandmember(String key) {
		return this.redis.srandmember(key);
	}

	@Override
	public Long zadd(String key, double score, long member) {
		return this.zadd(key, score, Long.toString(member));
	}

	@Override
	public Long zadd(String key, double score, String member) {
		Long result = this.redis.zadd(key, score, member);
		this.logDb.info("zadd " + key + " " + format(score) + " " + member);
		return result;
	}

	@Override
	public Set<String> zrange(String key, long start, long end) {
		return this.redis.zrange(key, start, end);
	}

	@Override
	public Long zrem(String key, String... member) {
		Long result = this.redis.zrem(key, member);
		this.logDb.info("zrem " + key + " " + encode(StringUtils.join(member, " ")));
		return result;
	}

	@Override
	public Double zincrby(String key, double score, String member) {
		Double result = this.redis.zincrby(key, score, member);
		this.logDb.info("zincrby " + key + " " + format(score) + " " + encode(member));
		return result;
	}

	@Override
	public Long zrank(String key, String member) {
		return this.redis.zrank(key, member);
	}

	@Override
	public Long zrevrank(String key, String member) {
		return this.redis.zrevrank(key, member);
	}

	@Override
	public Set<String> zrevrange(String key, long start, long end) {
		return this.redis.zrevrange(key, start, end);
	}

	@Override
	public Set<Tuple> zrangeWithScores(String key, long start, long end) {
		return this.redis.zrangeWithScores(key, start, end);
	}

	@Override
	public Set<Tuple> zrevrangeWithScores(String key, long start, long end) {
		return this.redis.zrevrangeWithScores(key, start, end);
	}

	@Override
	public Long zcard(String key) {
		return this.redis.zcard(key);
	}

	@Override
	public Double zscore(String key, String member) {
		return this.redis.zscore(key, member);
	}

	@Override
	public List<String> sort(String key) {
		return this.redis.sort(key);
	}

	@Override
	public List<String> sort(String key, SortingParams sortingParameters) {
		return this.redis.sort(key, sortingParameters);
	}

	@Override
	public Long zcount(String key, double min, double max) {
		return this.redis.zcount(key, min, max);
	}

	@Override
	public Set<String> zrangeByScore(String key, double min, double max) {
		return this.redis.zrangeByScore(key, min, max);
	}

	@Override
	public Set<String> zrevrangeByScore(String key, double max, double min) {
		return this.redis.zrevrangeByScore(key, max, min);
	}

	@Override
	public Set<String> zrangeByScore(String key, double min, double max, int offset, int count) {
		return this.redis.zrangeByScore(key, min, max, offset, count);
	}

	@Override
	public Set<String> zrevrangeByScore(String key, double max, double min, int offset, int count) {
		return this.redis.zrevrangeByScore(key, max, min, offset, count);
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max) {
		return this.redis.zrangeByScoreWithScores(key, min, max);
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min) {
		return this.redis.zrevrangeByScoreWithScores(key, max, min);
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max, int offset, int count) {
		return this.redis.zrangeByScoreWithScores(key, min, max, offset, count);
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min, int offset, int count) {
		return this.redis.zrevrangeByScoreWithScores(key, max, min, offset, count);
	}

	@Override
	public Long zremrangeByRank(String key, long start, long end) {
		Long result = this.redis.zremrangeByRank(key, start, end);
		this.logDb.info("zremrangeByRank " + key + " " + start + " " + end);
		return result;
	}

	@Override
	public Long zremrangeByScore(String key, double start, double end) {
		Long result = this.redis.zremrangeByScore(key, start, end);
		this.logDb.info("zremrangeByScore " + key + " " + start + " " + end);
		return result;
	}

	@Override
	public Long linsert(String key, LIST_POSITION where, String pivot, String value) {
		// if (true) {
		throw new NotImplementedException("linsert方法未启用");
		// }
		// Long result = this.redis.linsert(key, where, pivot, value);
		// //log未启用.
		// return result;
	}

	@Override
	public Jedis getResource() {
		return this.redis.getResource();
	}

	@Override
	public boolean append(String key, String value, int seconds) {
		boolean result = this.redis.append(key, value, seconds);
		this.logDb.info("append " + key + " " + this.encode(value) + " " + seconds);
		return result;
	}

	@Override
	public boolean rename(String oldkey, String newkey) {
		boolean result = this.redis.rename(oldkey, newkey);
		this.logDb.info("rename " + oldkey + " " + newkey);
		return result;
	}

	@Override
	public Transaction multi() {
		return this.redis.multi();
	}

	@Override
	public boolean flushDB() {
		boolean result = this.redis.flushDB();
		this.logDb.info("flushDB");
		return result;
	}

	@Override
	public RedisInfo info() {
		return this.redis.info();
	}

	@Override
	public boolean rename(String oldkey, String newkey, int seconds) {
		boolean result = this.redis.rename(oldkey, newkey, seconds);
		this.logDb.info("rename " + oldkey + " " + newkey + " " + seconds);
		return result;
	}

	@Override
	public long getUsedMemory() {
		return this.redis.getUsedMemory();
	}

	@Override
	public long dbSize() {
		return this.redis.dbSize();
	}

	@Override
	public String set(String key, String value, int seconds) {
		String result = this.redis.set(key, value, seconds);
		this.logDb.info("set " + key + " " + this.encode(value) + " " + seconds);
		return result;
	}

	@Override
	public boolean flushAll() {
		boolean result = this.redis.flushAll();
		this.logDb.info("flushAll");
		return result;
	}

	@Override
	public boolean set(List<String> keyList, List<String> valueList) {
		boolean result = this.redis.set(keyList, valueList);
		for (int i = 0; i < keyList.size(); i++) {
			String key = keyList.get(i);
			String value = valueList.get(i);
			this.logDb.info("set " + key + " " + encode(value));
		}
		return result;
	}

	@Override
	public boolean append(List<String> keyList, List<String> valueList, int seconds) {
		boolean result = this.redis.append(keyList, valueList, seconds);
		for (int i = 0; i < keyList.size(); i++) {
			String key = keyList.get(i);
			String value = valueList.get(i);
			this.logDb.info("append " + key + " " + this.encode(value) + " " + seconds);
		}
		return result;
	}

	@Override
	public Long del(String... keys) {
		Long result = this.redis.del(keys);
		this.logDb.info("del " + StringUtils.join(keys, " "));
		return result;
	}

	@Override
	public Long del(String key) {
		return this.redis.del(key);
	}

	@Override
	public void returnResource(Jedis jedis) {
		this.redis.returnResource(jedis);
	}

	@Override
	public List<String> mget(String... keys) {
		return this.redis.mget(keys);
	}

	@Override
	public Long zinterstore(String dstkey, String... sets) {
		Long result = this.redis.zinterstore(dstkey, sets);
		this.logDb.info("zinterstore " + dstkey + " " + StringUtils.join(sets, " "));
		return result;
	}

	@Override
	public Long zinterstore(String dstkey, ZParams params, String... sets) {
		Long result = this.redis.zinterstore(dstkey, params, sets);
		// TODO 未做params转换
		this.logDb.info("zinterstore params " + dstkey + " " + StringUtils.join(sets, " "));
		return result;
	}

	@Override
	public Set<String> keys(String pattern) {
		return this.redis.keys(pattern);
	}

	@Override
	public Long zunionstore(String dstkey, String... sets) {
		Long result = this.redis.zunionstore(dstkey, sets);
		this.logDb.info("zunionstore " + dstkey + " " + StringUtils.join(sets, " "));
		return result;
	}

	@Override
	public String getServerInfo() {
		return this.server;
	}

	@Override
	public Long zrem(String key, long member) {
		return this.zrem(key, Long.toString(member));
	}

	@Override
	public Set<String> zunionStoreInJava(String... sets) {
		return redis.zunionStoreInJava(sets);
	}

	@Override
	public Set<String> zunionStoreByScoreInJava(double min, double max, String... sets) {
		return redis.zunionStoreByScoreInJava(min, max, sets);
	}

	@Override
	public Long zadd(String key, Map<Double, String> scoreMembers) {
		Long result = this.redis.zadd(key, scoreMembers);

		Iterator<Entry<Double, String>> iterator = scoreMembers.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<Double, String> entry = iterator.next();
			Double score = entry.getKey();
			String member = entry.getValue();
			this.logDb.info("zadd " + key + " " + format(score) + " " + encode(member));
		}

		return result;
	}

	@Override
	public Long zadd2(String key, Map<String, Double> scoreMembers) {
		Long result = this.redis.zadd2(key, scoreMembers);

		Iterator<Entry<String, Double>> iterator = scoreMembers.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, Double> entry = iterator.next();
			Double score = entry.getValue();
			String member = entry.getKey();
			this.logDb.info("zadd " + key + " " + format(score) + " " + encode(member));
		}

		return result;
	}

	@Override
	public Long zcount(String key, String min, String max) {
		Long result = this.redis.zcount(key, min, max);
		return result;
	}

	@Override
	public Set<String> zrangeByScore(String key, String min, String max) {
		return this.redis.zrangeByScore(key, min, max);
	}

	@Override
	public Set<String> zrevrangeByScore(String key, String max, String min) {
		return this.redis.zrevrangeByScore(key, max, min);

	}

	@Override
	public Set<String> zrangeByScore(String key, String min, String max, int offset, int count) {
		return this.redis.zrangeByScore(key, min, max, offset, count);
	}

	@Override
	public Set<String> zrevrangeByScore(String key, String max, String min, int offset, int count) {
		return this.redis.zrevrangeByScore(key, max, min, offset, count);
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max) {
		return this.redis.zrangeByScoreWithScores(key, min, max);
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min) {
		return this.redis.zrevrangeByScoreWithScores(key, max, min);
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max, int offset, int count) {
		return this.redis.zrangeByScoreWithScores(key, min, max, offset, count);
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min, int offset, int count) {
		return this.zrevrangeByScoreWithScores(key, Double.parseDouble(max), Double.parseDouble(min), offset, count);
	}

	@Override
	public Long zremrangeByScore(String key, String start, String end) {
		Long result = this.redis.zremrangeByScore(key, start, end);
		this.logDb.info("zremrangeByScore " + key + " " + encode(start) + " " + encode(end));
		return result;
	}

	@Override
	public Long lpushx(String key, String string) {
		Long result = redis.lpushx(key, string);
		this.logDb.info("lpushx " + key + " " + encode(string));
		return result;
	}

	@Override
	public Long rpushx(String key, String string) {
		Long result = this.redis.rpushx(key, string);
		this.logDb.info("rpushx " + key + " " + encode(string));
		return result;
	}

	@Override
	public Long setrange(String key, int offset, String value) {
		Long temp = (long) offset;
		return this.setrange(key, temp, value);
	}

	@Override
	public Object evalsha(String script) {
		return this.redis.evalsha(script);
	}

	@Override
	public Object eval(String script) {
		return this.redis.eval(script);
	}

	@Override
	public Object evalsha(String sha1, List<String> keys, List<String> args) {
		return this.redis.evalsha(sha1, keys, args);
	}

	@Override
	public Object evalsha(String sha1, int keyCount, String... params) {
		return this.redis.evalsha(sha1, keyCount, params);
	}

	// @Override
	// public Ludis getLudis() {
	// return this.redis.getLudis();
	// }

	@Override
	public String evalReturnSha(String script) {
		return this.redis.evalReturnSha(script);
	}

	@Override
	public Object evalAssertSha(String sha, String script) {
		return this.redis.evalAssertSha(sha, script);
	}

	@Override
	public Object eval(String script, int keyCount, String... params) {
		return this.redis.eval(script, keyCount, params);
	}

	@Override
	public String bgrewriteaof() {
		return this.redis.bgrewriteaof();
	}

	@Override
	public String bgsave() {
		return this.redis.bgsave();
	}

	@Override
	public String save() {
		return this.redis.save();
	}

	@Override
	public IJedisPool getJedisPool() {
		return this.redis.getJedisPool();
	}

	@Override
	public Long publish(String channel, String message) {
		return this.redis.publish(channel, message);
	}

	@Override
	public void psubscribe(JedisPubSub jedisPubSub, String... patterns) {
		this.redis.psubscribe(jedisPubSub, patterns);
	}

	@Override
	public void subscribe(JedisPubSub jedisPubSub, String... channels) {
		this.redis.subscribe(jedisPubSub, channels);
	}

	@Override
	public Set<String> sdiff(String... keys) {
		return this.redis.sdiff(keys);
	}

	@Override
	public Long zunionstore(String dstkey, ZParams params, String... sets) {
		Long result = this.redis.zunionstore(dstkey, params, sets);
		// TODO 未做params转换
		this.logDb.info("zunionstore " + dstkey + " " + StringUtils.join(sets, " "));
		return result;
	}

	@Override
	public String randomKey() {
		return this.redis.randomKey();
	}

}
