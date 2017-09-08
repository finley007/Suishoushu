package com.changyi.fi.external.enterprise;

import com.changyi.fi.core.http.HTTPParser;
import com.changyi.fi.core.redis.RedisClient;
import org.apache.commons.lang.StringUtils;
import org.jsoup.select.Elements;

public abstract class ExternalEnterpriseAPIAbstractImpl implements ExternalEnterpriseAPIService {

    private static final String FIELD_CREDIT_CODE = "creditCode";
    private static final String FIELD_NAME = "name";

    abstract protected String login() throws Exception;

    abstract protected int getTokenExpiredTime();

    protected String getToken(String tokenName) throws Exception {
        String token = RedisClient.get(tokenName);
        if (StringUtils.isBlank(token)) {
            token = this.login();
            if (getTokenExpiredTime() > 0) {
                RedisClient.setex(tokenName, getTokenExpiredTime(), token);
            } else {
                RedisClient.set(tokenName, token);
            }
        }
        return token;
    }

    protected String getCreditCodeKey() {
        return FIELD_CREDIT_CODE;
    }

    protected String getNameKey() {
        return FIELD_NAME;
    }

    public class StringResultHandler implements HTTPParser.ResultHandler<String> {
        public String handleResult(Elements elems) {
            if (elems != null && elems.size() > 0) {
                return elems.get(0).text();
            } else {
                return "";
            }
        }
    }
}
