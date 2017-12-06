package com.changyi.fi.core.token;

import com.changyi.fi.core.LogUtil;
import com.changyi.fi.core.Payload;
import com.changyi.fi.core.redis.RedisClient;
import com.changyi.fi.vo.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by finley on 7/7/17.
 */
public class RedisTokenRepository implements ITokenRepository {

    public void addToken(Token token) {
        removeUnusedTokens(token.getOpenId());
        RedisClient.hset(RedisClient.REDIS_SESSION_POOL, token.getKey(), token.getSession().toJson());
        RedisClient.sadd(createSessionMapKey(token.getOpenId()), token.getKey());
        //暂时不支持设置超时
//        RedisClient.expire(token.getKey(), token.getDelaySeconds());
    }

    private String createSessionMapKey(String openId) {
        return RedisClient.REDIS_SESSION_MAP + "_" + openId;
    }

    private void removeUnusedTokens(String openId) {
        Set<String> set = RedisClient.smembers(createSessionMapKey(openId));
        if (set != null && set.size() > 0) {
            LogUtil.debug(this.getClass(), "Remove unused tokens: ");
            for (String key : set) {
                LogUtil.debug(this.getClass(), "key|" + key);
                RedisClient.hdel(RedisClient.REDIS_SESSION_POOL, key);
                RedisClient.srem(createSessionMapKey(openId), key);
            }
        }
    }

    public Token touchToken(String key) {
        String value = RedisClient.hget(RedisClient.REDIS_SESSION_POOL, key);
        if (value == null) {
            return null;
        }
        try {
            Session session = new Payload(value).as(Session.class);
            Token token = new Token(session, false).setKey(key);
            //重新设置过期时间
//            RedisClient.expire(key, token.getDelaySeconds());
            return token;
        } catch (Exception e) {
            LogUtil.error(this.getClass(), "Error parse token session: " + value, e);
            return null;
        }
    }

    public void updateToken(Token token) {
        RedisClient.hset(RedisClient.REDIS_SESSION_POOL, token.getKey(), token.getSession().toJson());
    }

    public void removeToken(String key) {
        Token token = this.touchToken(key);
        RedisClient.srem(createSessionMapKey(token.getOpenId()), key);
        RedisClient.hdel(RedisClient.REDIS_SESSION_POOL, key);
    }

    public void saveToken() {}

    public void init() {}

    public List<Token> listTokens() {
        List<Token> result = new ArrayList<Token>();
        Map<String, String> tokens = RedisClient.hgetAll(RedisClient.REDIS_SESSION_POOL);
        if (tokens != null && tokens.keySet() != null && tokens.keySet().size() > 0) {
            for (String key : tokens.keySet()) {
                String payload = tokens.get(key);
                try {
                    Session session = new Payload(payload).as(Session.class);
                    result.add(new Token(session, false).setKey(key));
                } catch (Exception e) {
                    LogUtil.error(this.getClass(), "Error parse token session: " + payload, e);
                }
            }
        }
        return result;
    }
}
