package com.changyi.fi.external.enterprise;

import com.changyi.fi.core.LogUtil;
import com.changyi.fi.core.http.HTTPParser;
import com.changyi.fi.core.redis.RedisClient;
import org.apache.commons.lang.StringUtils;
import org.jsoup.select.Elements;

public abstract class ExternalEnterpriseAPIAbstractImpl implements ExternalEnterpriseAPIService {

    public static final String REDIS_TYC_SESSION_TOKEN = "tyc_session_token";
    public static final String REDIS_QXB_SESSION_TOKEN = "qxb_session_token";

    static {
        LogUtil.debug(ExternalEnterpriseAPIAbstractImpl.class, "Clear enterprise external session token");
        RedisClient.del(REDIS_TYC_SESSION_TOKEN);
        RedisClient.del(REDIS_QXB_SESSION_TOKEN);
    }

    private static final String FIELD_CREDIT_CODE = "creditCode";
    private static final String FIELD_NAME = "name";
    private static final String FIELD_SOURCE = "source";

    abstract protected String login() throws Exception;

    abstract protected int getTokenExpiredTime();

    protected String getToken(String tokenName) throws Exception {
        String token = RedisClient.get(tokenName);
        if (StringUtils.isBlank(token)) {
            LogUtil.debug(this.getClass(), "Session token: " + tokenName + " does not exist and will login again");
            token = this.login();
            LogUtil.debug(this.getClass(), "Obtain token: " + token);
            if (getTokenExpiredTime() > 0) {
                RedisClient.setex(tokenName, getTokenExpiredTime(), token);
            } else {
                RedisClient.set(tokenName, token);
            }
        } else {
            LogUtil.debug(this.getClass(),"Use session token: " + tokenName + " and value: " + token);
        }
        return token;
    }

    protected String getCreditCodeKey() {
        return FIELD_CREDIT_CODE;
    }

    protected String getNameKey() {
        return FIELD_NAME;
    }

    protected String getSourceKey() {
        return FIELD_SOURCE;
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
