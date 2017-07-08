package com.changyi.fi.core.redis;

import com.changyi.fi.core.tool.Properties;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by finley on 7/7/17.
 */
public class RedisClient {

    private static final String REDIS_HOST = "redis.host";

    private static Jedis jedis;

    public static Jedis getJedis() {
        if (jedis == null) {
            jedis = new Jedis(Properties.get(REDIS_HOST));
        }
        return jedis;
    }

    public static String set(String key, String value) {
        return getJedis().set(key, value);
    }

    public static String setex(String key, int seconds, String value) {
        return getJedis().setex(key, seconds, value);
    }

    public static Long append(String key, String value) {
        return getJedis().append(key, value);
    }

    public static String get(String key) {
        return getJedis().get(key);
    }

    public static Long hdel(String key, String... fields) {
        return getJedis().hdel(key, fields);
    }

    public static Boolean hexists(String key, String field) {
        return getJedis().hexists(key, field);
    }

    public static String hget(String key, String field) {
        return getJedis().hget(key, field);
    }

    public static Long hset(String key, String field, String value) {
        return getJedis().hset(key, field, value);
    }

    public static Map<String, String> hgetAll(String key) {
        return getJedis().hgetAll(key);
    }

    public static Set<String> hkeys(String key) {
        return getJedis().hkeys(key);
    }

    public static Long hlen(String key) {
        return getJedis().hlen(key);
    }

    public static List<String> hmget(String key, String... fields) {
        return getJedis().hmget(key, fields);
    }

    public static String hmset(String key, Map<String,String> hash) {
        return getJedis().hmset(key, hash);
    }

    public static Long expire(String key, int seconds) {
        return getJedis().expire(key, seconds);
    }

    public static Long expireAt(String key, long unixTime) {
        return getJedis().expireAt(key, unixTime);
    }

    public static Long ttl(String key) {
        return getJedis().ttl(key);
    }

    public static Long del(String key) {
        return getJedis().del(key);
    }
}
