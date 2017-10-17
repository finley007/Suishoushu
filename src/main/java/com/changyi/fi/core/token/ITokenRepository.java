package com.changyi.fi.core.token;

/**
 * Created by finley on 7/6/17.
 */
public interface ITokenRepository {

    public void init();

    public void addToken(Token token);

    public Token touchToken(String key);

    public void updateToken(Token token);

    public void removeToken(String key);

    public void saveToken();

}
