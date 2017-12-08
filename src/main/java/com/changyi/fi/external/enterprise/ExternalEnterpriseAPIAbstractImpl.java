package com.changyi.fi.external.enterprise;

import com.changyi.fi.core.LogUtil;
import com.changyi.fi.core.http.HTTPParser;
import com.changyi.fi.core.redis.RedisClient;
import com.changyi.fi.dao.InvoiceDao;
import com.changyi.fi.external.enterprise.account.AccountPicker;
import com.changyi.fi.external.enterprise.account.IAccountConfig;
import com.changyi.fi.external.enterprise.account.QXBAccountConfig;
import com.changyi.fi.model.EnterprisePO;
import com.changyi.fi.vo.AccountPair;
import org.apache.commons.lang.StringUtils;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.regex.Pattern;

public abstract class ExternalEnterpriseAPIAbstractImpl implements ExternalEnterpriseAPIService {

    protected AccountPicker accountPicker = new AccountPicker();

    static {
        LogUtil.debug(ExternalEnterpriseAPIAbstractImpl.class, "Clear enterprise external session token");
        RedisClient.del(RedisClient.REDIS_TYC_SESSION_TOKEN);
        RedisClient.del(RedisClient.REDIS_QXB_SESSION_TOKEN);
    }

    private static final String FIELD_SOURCE = "source";

    private static final String INVALID_CREDITCODE = "-";

    protected InvoiceDao invoiceDao;

    @Autowired(required = true)
    public void setInvoiceDao(InvoiceDao invoiceDao) {
        this.invoiceDao = invoiceDao;
    }

    abstract protected String login(String username, String password) throws Exception;

    abstract protected int getTokenExpiredTime();

    abstract protected IAccountConfig getAccountConfig();

    protected synchronized String getToken(String tokenName) throws Exception {
        String token = RedisClient.get(tokenName);
        LogUtil.debug(this.getClass(),"Use session token: " + tokenName + " and value: " + token);
        AccountPair accountPair = accountPicker.nextAccount(StringUtils.isBlank(token), getAccountConfig());
        if (accountPair != null) {
            LogUtil.debug(this.getClass(), "Will login again");
            token = this.login(accountPair.getAccount(), accountPair.getPassword());
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

    protected boolean isValidName(String name, String key) {
        return name.contains(key);
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
