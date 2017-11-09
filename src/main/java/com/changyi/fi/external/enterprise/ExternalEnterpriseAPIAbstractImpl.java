package com.changyi.fi.external.enterprise;

import com.changyi.fi.core.LogUtil;
import com.changyi.fi.core.http.HTTPParser;
import com.changyi.fi.core.redis.RedisClient;
import com.changyi.fi.dao.InvoiceDao;
import com.changyi.fi.model.EnterprisePO;
import org.apache.commons.lang.StringUtils;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.regex.Pattern;

public abstract class ExternalEnterpriseAPIAbstractImpl implements ExternalEnterpriseAPIService {

    public static final String REDIS_TYC_SESSION_TOKEN = "tyc_session_token";
    public static final String REDIS_QXB_SESSION_TOKEN = "qxb_session_token";

    static {
        LogUtil.debug(ExternalEnterpriseAPIAbstractImpl.class, "Clear enterprise external session token");
        RedisClient.del(REDIS_TYC_SESSION_TOKEN);
        RedisClient.del(REDIS_QXB_SESSION_TOKEN);
    }

    private static final String FIELD_SOURCE = "source";

    private static final String INVALID_CREDITCODE = "-";

    protected InvoiceDao invoiceDao;

    @Autowired(required = true)
    public void setInvoiceDao(InvoiceDao invoiceDao) {
        this.invoiceDao = invoiceDao;
    }

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

    protected String getSourceKey() {
        return FIELD_SOURCE;
    }

    protected void saveEnterpriseInfo(EnterprisePO po) {
        EnterprisePO existedPo = invoiceDao.getEnterpriseById(po.getCreditCode());
        if (existedPo == null) {
            this.invoiceDao.insertEnterprise(po);
        } else {
            this.invoiceDao.updateEnterpriseSelective(po);
        }
    }

    protected boolean isValidCreditCode(String creditCode) {
        if (StringUtils.isBlank(creditCode)) {
            return false;
        }
        Pattern pattern = Pattern.compile(INVALID_CREDITCODE);
        if (pattern.matcher(creditCode).matches()) {
            return false;
        }
        return true;
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
