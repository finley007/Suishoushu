package com.changyi.fi.external.enterprise.qcc;

import com.changyi.fi.core.CommonUtil;
import com.changyi.fi.core.LogUtil;
import com.changyi.fi.core.annotation.Timer;
import com.changyi.fi.core.http.HTTPCaller;
import com.changyi.fi.core.http.HTTPParser;
import com.changyi.fi.core.tool.Properties;
import com.changyi.fi.external.enterprise.ExternalEnterpriseAPIAbstractImpl;
import com.changyi.fi.external.enterprise.ExternalEnterpriseAPIService;
import com.changyi.fi.external.enterprise.account.IAccountConfig;
import com.changyi.fi.model.EnterprisePO;
import com.changyi.fi.util.FIConstants;
import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("qiChaChaAPIService")
public class QiChaChaAPIServiceImpl extends ExternalEnterpriseAPIAbstractImpl implements ExternalEnterpriseAPIService {

    private static final String SOURCE_QCC = "qcc";

    private static final String QICHACHA_DOMAIN = ".qichacha.com";

    private static final String QICHACHA_COOKIE_KEY = "qichacha.cookie.key";
    private static final String QICHACHA_COOKIE_VALUE = "qichacha.cookie.value";

    private static final String QICHACHA_SEARCH_TEMPLATE = "qichacha.search.template";
    private static final String QICHACHA_SEARCH_NAME_MATCHER = "qichacha.search.name.matcher";

    //qcc无需登录
    protected String login(String username, String password) throws Exception {
        return null;
    }

    //qcc无需登录
    protected int getTokenExpiredTime() {
        return 0;
    }

    //qcc无需登录
    protected IAccountConfig getAccountConfig() {
        return null;
    }

    @Timer
    public List<Map> matchEnterprise(String key) throws Exception {
        LogUtil.info(this.getClass(), "Execute enterprise search service by calling QiChaCha API, key: " + key);
        String encodedKey = CommonUtil.urlEncode(key, FIConstants.DEFAULT_CHARSET);
        LogUtil.info(this.getClass(),"Encoded key: " + encodedKey);
        String url = HTTPCaller.createUrl(QICHACHA_SEARCH_TEMPLATE, new Object[]{encodedKey});
        LogUtil.info(this.getClass(), "QiChaCha API, url: " + url);
        String html = new HTTPCaller(url).setCookieStore(createCookieStore()).doGet();
        HTTPParser parser = new HTTPParser(html);
        return createEnterpriseList(parser, key);
    }

    private CookieStore createCookieStore() throws Exception {
        CookieStore cookieStore = new BasicCookieStore();
        String key = Properties.get(QICHACHA_COOKIE_KEY);
        String value = Properties.get(QICHACHA_COOKIE_VALUE);
        BasicClientCookie cookie = new BasicClientCookie(key, value);
        cookie.setDomain(QICHACHA_DOMAIN);
        cookie.setPath("/");
        cookieStore.addCookie(cookie);
        return cookieStore;
    }

    private List<Map> createEnterpriseList(HTTPParser parser, String key) {
        List<Map> result = new ArrayList<Map>();
        List<Element> list = parser.select(Properties.get(QICHACHA_SEARCH_NAME_MATCHER));
        for (Element elem : list) {
            String creditCode = elem.attr("href").toString();
            String name = elem.text();
            if (isValidCreditCode(creditCode)
                    && isValidName(name, key)) {
                Map map = new HashMap();
                map.put(FIConstants.FIELD_CREDIT_CODE, creditCode);
                map.put(FIConstants.FIELD_NAME, name);
                map.put(getSourceKey(), getAPIKey());
                result.add(map);
            }
        }
        return result;
    }

    public EnterprisePO getEnterpriseByCode(String code) throws Exception {
        return null;
    }

    public String getAPIKey() {
        return FIConstants.API_QICHACHA;
    }

}
