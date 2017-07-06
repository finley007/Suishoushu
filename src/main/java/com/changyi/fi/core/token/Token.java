package com.changyi.fi.core.token;

import com.changyi.fi.core.tool.Properties;
import com.changyi.fi.vo.Session;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Date;
import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by finley on 1/30/17.
 */
public class Token {

    private static final int SECOND = 1000;
    private static final String TOKEN_EXPIRATION_SECONDS = "token.expiration.seconds";
    public static final String KEY = "auth-token";

    private static ITokenRepository repository = new TokenRepository();

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

    /**
     * Delay in milliseconds from properties file
     * Defaults to 24 hours
     */
    public long getDelay() {
        long delaySeconds;
        try {
            delaySeconds = Long.parseLong(Properties.get(TOKEN_EXPIRATION_SECONDS));
        } catch(Exception e) {
            delaySeconds = 24 * 60 * 60;
        }

        return delaySeconds * SECOND;
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

    private void setKey(String key) {
        this.key = key;
    }

}
