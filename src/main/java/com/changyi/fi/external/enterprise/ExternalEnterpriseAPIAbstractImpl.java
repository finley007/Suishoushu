package com.changyi.fi.external.enterprise;

import com.changyi.fi.core.redis.RedisClient;
import org.apache.commons.lang.StringUtils;

public abstract class ExternalEnterpriseAPIAbstractImpl implements ExternalEnterpriseAPIService {

    abstract protected String login() throws Exception;

    abstract protected int getTokenExpiredTime();

    protected String getToken(String tokenName) throws Exception {
        String token = RedisClient.get(tokenName);
        if (StringUtils.isBlank(token)) {
            token = this.login();
            RedisClient.setex(tokenName, getTokenExpiredTime(), token);
        }
        return token;
    }
}
