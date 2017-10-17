package com.changyi.fi.core.token;

import com.changyi.fi.core.LogUtil;
import com.changyi.fi.core.config.ConfigManager;
import com.changyi.fi.vo.Session;
import org.apache.commons.lang.StringUtils;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Date;

/**
 * Created by finley on 1/30/17.
 */
public class Token {

    private static final int SECOND = 1000;
    private static final String TOKEN_EXPIRATION_SECONDS = "TOKEN_EXPIRATION_SECONDS";
    private static final String TOKEN_REPOSITORY_IMPL_CLZ = "TOKEN_REPOSITORY_IMPL_CLZ";
    public static final String KEY = "auth-token";

    private static ITokenRepository repository;

    public static void init() {
        String implClz = "com.changyi.fi.core.token.TokenRepository";
        if (!StringUtils.isEmpty(ConfigManager.getParameter(TOKEN_REPOSITORY_IMPL_CLZ))) {
            implClz = ConfigManager.getParameter(TOKEN_REPOSITORY_IMPL_CLZ);
        }
        try {
            repository = (ITokenRepository)Token.class.forName(implClz).newInstance();
        } catch (Exception e) {
            LogUtil.error(Token.class, "Init token repository error: ", e);
            repository = new TokenRepository();
        }
        repository.init();
    }

    private String key;

    private Date lastTouched;

    private SecureRandom random = new SecureRandom();

    public Session getSession() {
        return session;
    }

    public String getOpenId() {return this.session == null ? "" : this.session.getOpenId(); }

    private Session session;

    public Token(Session session) {
        this.session = session;
        setKey(createKey(session));
        setLastTouched(new Date());
        repository.addToken(this);
    }

    public Token(String key, Session session) {
        this.session = session;
        this.key = key;
        setLastTouched(new Date());
        repository.addToken(this);
    }

    private String createKey(Session session) {
        String keyBase = session.getOpenId() + System.currentTimeMillis();
        return new BigInteger(130, random).toString(32) + String.valueOf(keyBase.hashCode());
    }

    /**
     * Checks if token is still valid.
     * If it is - refreshes it's timer.
     * @param key
     * @return whether or not the token is present
     */
    public static Token touch(String key) {
        Token token = repository.touchToken(key);
        if (token != null) {
            token.setLastTouched(new Date());
        }
        return token;
    }

    public static void update(Token token) {
        repository.updateToken(token);
    }

    /**
     * Delay in milliseconds from properties file
     * Defaults to 24 hours
     */
    public long getDelay() {
        return getDelaySeconds() * SECOND;
    }

    public Integer getDelaySeconds() {
        Integer delaySeconds;
        try {
            delaySeconds = Integer.parseInt(ConfigManager.getParameter(TOKEN_EXPIRATION_SECONDS));
        } catch(Exception e) {
            delaySeconds = 24 * 60 * 60;
        }
        return delaySeconds;
    }

    public Date getExpiration() {
        return new Date(lastTouched.getTime() + getDelay());
    }

    public String getKey() {
        return key;
    }

    private void setLastTouched(Date lastTouched) {
        this.lastTouched = lastTouched;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public static void save() {
        repository.saveToken();
    }

}
