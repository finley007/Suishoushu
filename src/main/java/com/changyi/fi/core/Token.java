package com.changyi.fi.core;

import com.changyi.fi.core.tool.Properties;
import org.apache.shiro.subject.Subject;

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

    private static ConcurrentHashMap<String, Token> tokens = new ConcurrentHashMap<String, Token>();
    private static ScheduledExecutorService tokenRemoverPool = Executors.newSingleThreadScheduledExecutor();

    private static SecureRandom random = new SecureRandom();

    private static final int SECOND = 1000;
    private static final String TOKEN_EXPIRATION_SECONDS = "token.expiration.seconds";

    private String key;
    private Date lastTouched;
    private Subject subject;

    public Token(String username, Subject subject) {
        String keyBase = username + System.currentTimeMillis();
        setKey(new BigInteger(130, random).toString(32) + String.valueOf(keyBase.hashCode()));
        setLastTouched(new Date());
        setSubject(subject);
        tokens.put(key, this);
        tokenRemoverPool.schedule(new TokenRemover(key), getDelay() + SECOND, TimeUnit.MILLISECONDS);
        traceTokens();
    }

    private static void traceTokens() {
        Enumeration<String> en = tokens.keys();
        while (en.hasMoreElements()) {
            System.out.println(en.nextElement());
        }
    }
    /**
     * Checks if token is still valid.
     * If it is - refreshes it's timer.
     * @param key
     * @return whether or not the token is present
     */
    public static Token touch(String key) {
        traceTokens();
        Token token = tokens.get(key);
        if (token != null) {
            token.setLastTouched(new Date());
        }
        return token;
    }

    /**
     * Delay in milliseconds from properties file
     * Defaults to 24 hours
     */
    private static long getDelay() {
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

    private void setKey(String body) {
        this.key = body;
    }

    public Subject getSubject() {
        return subject;
    }

    private void setSubject(Subject subject) {
        this.subject = subject;
    }

    private class TokenRemover implements Runnable {
        private String tokenKey;

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
                long checkAfter = expiration.getTime() - System.currentTimeMillis() + SECOND;
                tokenRemoverPool.schedule(this, checkAfter, TimeUnit.MILLISECONDS);
            } else {
                tokens.remove(key);
            }
        }

    }

    public static void remove(String key) {
        tokens.remove(key);
    }

}
