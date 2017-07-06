package com.changyi.fi.core.token;

import java.util.concurrent.TimeUnit;

/**
 * Created by finley on 7/6/17.
 */
public interface ITokenRepository {

    public void addToken(Token token);

    public Token touchToken(String key);

    public void removeToken(String key);

}
