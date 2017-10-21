package com.changyi.fi.core.token;

import com.changyi.fi.core.BaseVO;
import com.changyi.fi.core.LogUtil;
import com.changyi.fi.core.Payload;
import com.changyi.fi.core.redis.RedisClient;
import com.changyi.fi.vo.Session;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * The type Token repository.
 */
public class TokenRepository implements ITokenRepository{

    private static final String SESSION_LIST_KEY = "sessionList";

    private ConcurrentHashMap<String, Token> tokens = new ConcurrentHashMap<String, Token>();
    private ScheduledExecutorService tokenRemoverPool = Executors.newSingleThreadScheduledExecutor();

    public void addToken(Token token) {
        removeUnusedTokens(token.getOpenId());
        tokens.put(token.getKey(), token);
        tokenRemoverPool.schedule(new TokenRemover(token.getKey()), token.getDelay(), TimeUnit.MILLISECONDS);
    }

    public Token touchToken(String key) {
        return tokens.get(key);
    }

    public void removeToken(String key) {
        tokens.remove(key);
    }


    private void removeUnusedTokens(String openId) {
        List<String> unusedKeys = new ArrayList<String>();
        if (StringUtils.isNotBlank(openId)) {
            Enumeration<String> keys = tokens.keys();
            while (keys.hasMoreElements()) {
                String key = keys.nextElement();
                if (openId.equals(tokens.get(key).getOpenId())) {
                    unusedKeys.add(key);
                }
            }
        }
        if (unusedKeys != null && unusedKeys.size() > 0) {
            LogUtil.debug(this.getClass(), "Remove unused tokens: ");
            for (String key : unusedKeys) {
                LogUtil.debug(this.getClass(), "key|" + key);
                removeToken(key);
            }
        }
    }

    public void updateToken(Token token) {}

    public void saveToken() {
        RedisClient.del(SESSION_LIST_KEY);
        for (String key : tokens.keySet()) {
            Token token = tokens.get(key);
            LogUtil.info(this.getClass(), "Save token: " + token.getOpenId());
            RedisClient.rpush(SESSION_LIST_KEY, new SessionStorage(key, token.getSession()).toJson());
        }
    }

    public void init() {
        List<String> sessionList = RedisClient.lrange(SESSION_LIST_KEY, 0 , -1);
        if (sessionList != null && sessionList.size() > 0) {
            LogUtil.info(this.getClass(), "Recover session");
            for (String se : sessionList) {
                try {
                    SessionStorage storage = new Payload(se).as(SessionStorage.class);
                    new Token(storage.key, new Session(storage.openId, storage.sessionKey));
                    LogUtil.info(this.getClass(), "Init token: " + storage.openId);
                } catch (Exception e) {
                    LogUtil.error(this.getClass(), "Init token error", e);
                }
            }
        }
    }

    private class TokenRemover implements Runnable {
        private String tokenKey;

        /**
         * Instantiates a new Token remover.
         *
         * @param tokenKey the token key
         */
        public TokenRemover(String tokenKey) {
            this.tokenKey = tokenKey;
        }

        public void run() {
            Token currentToken = tokens.get(tokenKey);
            if (currentToken == null) {
                return;
            }
            Date expiration = currentToken.getExpiration();
            if (expiration.after(new Date())) {
                long checkAfter = expiration.getTime() - System.currentTimeMillis();
                tokenRemoverPool.schedule(this, checkAfter, TimeUnit.MILLISECONDS);
            } else {
                tokens.remove(tokenKey);
            }
        }

    }

    private class SessionStorage extends BaseVO {

        /**
         * Instantiates a new Session storage.
         *
         * @param key     the key
         * @param session the session
         */
        public SessionStorage(String key, Session session) {
            this.key = key;
            this.openId = session.getOpenId();
            this.sessionKey = session.getSessionKey();
        }

        private String key;
        private String openId;
        private String sessionKey;

        /**
         * Gets key.
         *
         * @return the key
         */
        public String getKey() {
            return key;
        }

    }


}
