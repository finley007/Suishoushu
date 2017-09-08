package com.changyi.fi.external.enterprise.qxb;

import com.changyi.fi.core.CommonUtil;
import com.changyi.fi.core.LogUtil;
import com.changyi.fi.core.Payload;
import com.changyi.fi.core.http.HTTPCaller;
import com.changyi.fi.core.tool.Properties;
import com.changyi.fi.external.enterprise.ExternalEnterpriseAPIAbstractImpl;
import com.changyi.fi.external.enterprise.ExternalEnterpriseAPIService;
import com.changyi.fi.external.enterprise.qxb.hmac.HmacManager;
import com.changyi.fi.external.enterprise.qxb.hmac.HmacTool;
import com.changyi.fi.external.enterprise.qxb.request.QXBLoginRequest;
import com.changyi.fi.external.enterprise.qxb.response.QXBMatchResponse;
import com.changyi.fi.model.EnterprisePO;
import com.changyi.fi.util.FIConstants;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QiXinBaoAPIServiceImpl extends ExternalEnterpriseAPIAbstractImpl implements ExternalEnterpriseAPIService {

    private static final String REDIS_QXB_SESSION_TOKEN = "qxb_session_token";

    private static final String COOKIE_NAME_SID = "sid";

    private static final String QIXINBAO_LOGIN_TEMPLATE = "qixinbao.login.template";
    private static final String QIXINBAO_SUGGESTION_TEMPLATE = "qixinbao.suggestion.template";
    private static final String QIXINBAO_USERNAME = "qixinbao.username";
    private static final String QIXINBAO_PASSWORD = "qixinbao.password";
    private static final String QIXINBAO_LOGIN_REFERER_URL = "qixinbao.login.referer.url";
    private static final String QIXINBAO_HOST = "qixinbao.host";
    private static final String QIXINBAO_MAINPAGE_URL = "qixinbao.mainpage.url";

    public List<Map> matchEnterprise(String key) throws Exception {
        LogUtil.info(this.getClass(), "Execute enterprise search service by calling QiXinBao API, key: " + key);
        String encodedKey = CommonUtil.urlEncode(key, FIConstants.DEFAULT_CHARSET);
        LogUtil.info(this.getClass(),"Encoded key: " + encodedKey);
        String url = HTTPCaller.createUrl(QIXINBAO_SUGGESTION_TEMPLATE, new Object[]{encodedKey});
        LogUtil.info(this.getClass(), "QiXinBao API, url: " + url);
        String res = new HTTPCaller(url).setCookieStore(createCookieStore()).setHeader(this.createSuggestionHeader(url)).doGet();
        LogUtil.info(this.getClass(), "Match result: " + res);
        QXBMatchResponse response = new Payload("{ \"data\" : " + res + " }").as(QXBMatchResponse.class);
        List result = new ArrayList();
        if (response != null && response.getData() != null && response.getData().size() > 0) {
            for (Map<String, String> m : response.getData()) {
                Map<String, String> map = new HashMap<String, String>();
                map.put(getCreditCodeKey(), m.get(QXBMatchResponse.FIELD_ID));
                map.put(getNameKey(), m.get(QXBMatchResponse.FIELD_NAME));
                result.add(map);
            }
        }
        return result;
    }

    private CookieStore createCookieStore() throws Exception {
        String token = this.getToken(REDIS_QXB_SESSION_TOKEN);
        CookieStore cookieStore = new BasicCookieStore();
        BasicClientCookie cookie = new BasicClientCookie(COOKIE_NAME_SID, token);
        cookie.setDomain(Properties.get(QIXINBAO_MAINPAGE_URL));
        cookie.setPath("/");
        cookieStore.addCookie(cookie);
        return cookieStore;
    }

    private Map<String, String> createSuggestionHeader(String url) throws Exception {
        HmacManager manager = new HmacManager();
        String servicePath = this.getServicePath(url).toLowerCase();
        String key = manager.init(new HmacTool(), this.getKey(servicePath)).finalize(servicePath).stringify().substring(10, 30);
        LogUtil.debug(this.getClass(), "Key: " + key);
        String valInput = servicePath + servicePath + "{}";
        String value = manager.init(new HmacTool(), this.getKey(servicePath)).finalize(valInput).stringify();
        LogUtil.debug(this.getClass(), "Value: " + value);
        Map<String, String> result = new HashMap<String, String>();
        result.put(key, value);
        return result;
    }

    private String getServicePath(String url) {
        String server = Properties.get(QIXINBAO_MAINPAGE_URL);
        if (url.indexOf(server) >= 0) {
            return url.substring(url.indexOf(server) + server.length());
        } else {
            return url;
        }
    }

    public EnterprisePO getEnterpriseByCode(String code) throws Exception {
        return null;
    }

    protected String login() throws Exception {
        String url = HTTPCaller.createUrl(QIXINBAO_LOGIN_TEMPLATE, new Object[]{});
        LogUtil.info(this.getClass(), "Login QiXinBao: " + url);
        String request = createLoginRequest();
        LogUtil.debug(this.getClass(), "Login request: " + request);
        CookieStore cookie = new BasicCookieStore();
        String res = new HTTPCaller(url).setHeader(this.createLoginHeader()).setCookieStore(cookie).doPost(request);
        List<Cookie> cookies = cookie.getCookies();
        if (cookies != null && cookies.size() > 0) {
            for (int i = 0; i < cookies.size(); i++) {
                if (cookies.get(i).getName().equals(COOKIE_NAME_SID)) {
                    return cookies.get(i).getValue();
                }
            }
        }
        return "";
    }

    private Map<String, String> createLoginHeader() {
        Map<String, String> header = new HashMap<String, String>();
        header.put("X-Requested-With", "XMLHttpRequest");
        header.put("Referer", Properties.get(QIXINBAO_LOGIN_REFERER_URL));
        header.put("Host", Properties.get(QIXINBAO_HOST));
        header.put("Origin", Properties.get(QIXINBAO_MAINPAGE_URL));
        header.put("76eac628969e70eab74f", "d3bc4148b1f2da7ec043d0279569f61a91c7b599f56c79b0902fe25af01e689a09a82cddb128bafcf4b798d17771c37e190ed6e3cd77c58a9fcb27d1bddfe9cd");
        return header;
    }

    private String createLoginRequest() {
        String username = Properties.get(QIXINBAO_USERNAME);
        String password = Properties.get(QIXINBAO_PASSWORD);
        QXBLoginRequest request = new QXBLoginRequest(username, password);
        return new Payload(request).from(QXBLoginRequest.class);
    }

    protected int getTokenExpiredTime() {
        return 0;
    }

    public String getKey(String path) {
        String result = "";
        String key = "z|z|q|P|z|B|8|j|s|0|l|c|F|K|v|t|E|G|A|8|";
        String[] keys = key.split("\\|");
        if (StringUtils.isNotBlank(path)) {
            path = path.toLowerCase();
            path = path + path;
            for (int i = 0; i < path.length(); ++i) {
                int a = path.charAt(i) % keys.length;
                result += keys[a];
            }
        }
        return result;
    }



}
