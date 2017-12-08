package com.changyi.fi.core.redis;

import com.changyi.fi.core.tool.Properties;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by finley on 7/7/17.
 */
public class RedisClient {

    public static final String REDIS_TYC_SESSION_TOKEN = "tyc_session_token";
    public static final String REDIS_QXB_SESSION_TOKEN = "qxb_session_token";
        public static final String REDIS_SESSION_POOL = "session_pool";
    public static final String REDIS_SESSION_MAP = "session_map";

    private static final String REDIS_HOST = "redis.host";
    private static final String REDIS_PORT = "redis.port";

    private static JedisPool pool = new JedisPool(new JedisPoolConfig(), Properties.get(REDIS_HOST),
            Integer.valueOf(Properties.get(REDIS_PORT)));

    public static Jedis getJedis() {
        return pool.getResource();
    }

    public static void close() {
        pool.close();
    }

    public static String set(String key, String value) {
        Jedis jedis = getJedis();
        String ret = jedis.set(key, value);
        jedis.close();
        return ret;
    }

    public static String setex(String key, int seconds, String value) {
        Jedis jedis = getJedis();
        String ret = jedis.setex(key, seconds, value);
        jedis.close();
        return ret;
    }

    public static Long append(String key, String value) {
        Jedis jedis = getJedis();
        Long ret = jedis.append(key, value);
        jedis.close();
        return ret;
    }

    public static String get(String key) {
        Jedis jedis = getJedis();
        String ret = jedis.get(key);
        jedis.close();
        return ret;
    }

    public static Long hdel(String key, String... fields) {
        Jedis jedis = getJedis();
        Long ret = jedis.hdel(key, fields);
        jedis.close();
        return ret;
    }

    public static Boolean hexists(String key, String field) {
        Jedis jedis = getJedis();
        Boolean ret = jedis.hexists(key, field);
        jedis.close();
        return ret;
    }

    public static String hget(String key, String field) {
        Jedis jedis = getJedis();
        String ret = jedis.hget(key, field);
        jedis.close();
        return ret;
    }

    public static Long hset(String key, String field, String value) {
        Jedis jedis = getJedis();
        Long ret = jedis.hset(key, field, value);
        jedis.close();
        return ret;
    }

    public static Map<String, String> hgetAll(String key) {
        Jedis jedis = getJedis();
        Map<String, String> ret = jedis.hgetAll(key);
        jedis.close();
        return ret;
    }

    public static Set<String> hkeys(String key) {
        Jedis jedis = getJedis();
        Set<String> ret = jedis.hkeys(key);
        jedis.close();
        return ret;
    }

    public static Long hlen(String key) {
        Jedis jedis = getJedis();
        Long ret = jedis.hlen(key);
        jedis.close();
        return ret;
    }

    public static List<String> hmget(String key, String... fields) {
        Jedis jedis = getJedis();
        List<String> ret = jedis.hmget(key, fields);
        jedis.close();
        return ret;
    }

    public static String hmset(String key, Map<String,String> hash) {
        Jedis jedis = getJedis();
        String ret = jedis.hmset(key, hash);
        jedis.close();
        return ret;
    }

    public static Long expire(String key, int seconds) {
        Jedis jedis = getJedis();
        Long ret = jedis.expire(key, seconds);
        jedis.close();
        return ret;
    }

    public static Long expireAt(String key, long unixTime) {
        Jedis jedis = getJedis();
        Long ret = jedis.expireAt(key, unixTime);
        jedis.close();
        return ret;
    }

    public static Long ttl(String key) {
        Jedis jedis = getJedis();
        Long ret = jedis.ttl(key);
        jedis.close();
        return ret;
    }

    public static Long del(String key) {
        Jedis jedis = getJedis();
        Long ret = jedis.del(key);
        jedis.close();
        return ret;
    }

    public static Long rpush(String key, String string) {
        Jedis jedis = getJedis();
        Long ret = jedis.rpush(key, string);
        jedis.close();
        return ret;
    }

    public static List<String> lrange(String key, int start, int end) {
        Jedis jedis = getJedis();
        List<String> ret = jedis.lrange(key, start, end);
        jedis.close();
        return ret;
    }

    public static Long sadd(String key, String... strings) {
        Jedis jedis = getJedis();
        Long ret = jedis.sadd(key, strings);
        jedis.close();
        return ret;
    }

    public static Set<String> smembers(String key) {
        Jedis jedis = getJedis();
        Set<String> ret = jedis.smembers(key);
        jedis.close();
        return ret;
    }

    public static Long srem(String key, String... members) {
        Jedis jedis = getJedis();
        Long ret = jedis.srem(key, members);
        jedis.close();
        return ret;
    }
}
