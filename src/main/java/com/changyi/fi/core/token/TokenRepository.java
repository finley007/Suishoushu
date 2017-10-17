package com.changyi.fi.core.token;

import com.changyi.fi.core.LogUtil;

import java.util.Date;
import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TokenRepository implements ITokenRepository{

    private ConcurrentHashMap<String, Token> tokens = new ConcurrentHashMap<String, Token>();
    private ScheduledExecutorService tokenRemoverPool = Executors.newSingleThreadScheduledExecutor();

    public void addToken(Token token) {
        tokens.put(token.getKey(), token);
        tokenRemoverPool.schedule(new TokenRemover(token.getKey()), token.getDelay(), TimeUnit.MILLISECONDS);
        traceTokens();
    }

    public Token touchToken(String key) {
        traceTokens();
        return tokens.get(key);
    }

    public void removeToken(String key) {
        tokens.remove(key);
    }

    private void traceTokens() {
        Enumeration<String> en = tokens.keys();
        while (en.hasMoreElements()) {
            System.out.println(en.nextElement());
        }
    }

    public void updateToken(Token token) {};

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
                long checkAfter = expiration.getTime() - System.currentTimeMillis();
                tokenRemoverPool.schedule(this, checkAfter, TimeUnit.MILLISECONDS);
            } else {
                tokens.remove(tokenKey);
            }
        }

    }

    protected void finalize(){
        LogUtil.warn(this.getClass(), "Token repository finalize");
    }

}
