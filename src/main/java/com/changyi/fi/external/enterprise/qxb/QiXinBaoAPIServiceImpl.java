package com.changyi.fi.external.enterprise.qxb;

import com.changyi.fi.core.LogUtil;
import com.changyi.fi.core.Payload;
import com.changyi.fi.core.exception.SystemException;
import com.changyi.fi.core.http.HTTPCaller;
import com.changyi.fi.core.tool.Properties;
import com.changyi.fi.external.enterprise.ExternalEnterpriseAPIAbstractImpl;
import com.changyi.fi.external.enterprise.ExternalEnterpriseAPIService;
import com.changyi.fi.external.enterprise.qxb.request.LoginRequest;
import com.changyi.fi.model.EnterprisePO;
import com.changyi.fi.util.FIConstants;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QiXinBaoAPIServiceImpl extends ExternalEnterpriseAPIAbstractImpl implements ExternalEnterpriseAPIService {

    private static final String QIXINBAO_LOGIN_TEMPLATE = "qixinbao.login.template";
    private static final String QIXINBAO_USERNAME = "qixinbao.username";
    private static final String QIXINBAO_PASSWORD = "qixinbao.password";
    private static final String QIXINBAO_LOGIN_REFERER_URL = "qixinbao.login.referer.url";
    private static final String QIXINBAO_HOST = "qixinbao.host";
    private static final String QIXINBAO_MAINPAGE_URL = "qixinbao.mainpage.url";

    public List<Map> matchEnterprise(String key) throws Exception {
        return null;
    }

    public EnterprisePO getEnterpriseByCode(String code) throws Exception {
        return null;
    }

    protected String login() throws Exception {
        String url = HTTPCaller.createUrl(QIXINBAO_LOGIN_TEMPLATE, new Object[]{});
        LogUtil.info(this.getClass(), "Login QiXinBao: " + url);
        String request = createLoginRequest();
        LogUtil.debug(this.getClass(), "Login request: " + request);
        String res = new HTTPCaller(url).setHeader(this.createLoginHeader()).doPost(request);
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
        LoginRequest request = new LoginRequest(username, password);
        return new Payload(request).from(LoginRequest.class);
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
