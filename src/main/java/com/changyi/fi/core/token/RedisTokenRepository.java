package com.changyi.fi.core.token;

import com.changyi.fi.core.LogUtil;
import com.changyi.fi.core.Payload;
import com.changyi.fi.core.redis.RedisClient;
import com.changyi.fi.vo.Session;

/**
 * Created by finley on 7/7/17.
 */
public class RedisTokenRepository implements ITokenRepository {

    public void addToken(Token token) {
        RedisClient.set(token.getKey(), token.getSession().toJson());
        RedisClient.expire(token.getKey(), token.getDelaySeconds());
    }

    public Token touchToken(String key) {
        String value = RedisClient.get(key);
        if (value == null) {
            return null;
        }
        try {
            Session session = new Payload(value).as(Session.class);
            Token token = new Token(session);
            token.setKey(key);
            //重新设置过期时间
            RedisClient.expire(key, token.getDelaySeconds());
            return token;
        } catch (Exception e) {
            LogUtil.error(this.getClass(), "Error parse token session: " + value, e);
            return null;
        }
    }

    public void updateToken(Token token) {
        RedisClient.set(token.getKey(), token.getSession().toJson());
    }

    public void removeToken(String key) {
        RedisClient.del(key);
    }

    public void saveToken() {}

    public void init() {}

}
