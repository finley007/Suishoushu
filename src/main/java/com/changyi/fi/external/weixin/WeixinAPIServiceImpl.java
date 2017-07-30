package com.changyi.fi.external.weixin;

import com.changyi.fi.core.LogUtil;
import com.changyi.fi.core.Payload;
import com.changyi.fi.core.exception.SystemException;
import com.changyi.fi.core.http.HTTPCaller;
import com.changyi.fi.core.tool.Properties;
import com.changyi.fi.external.weixin.response.WeixinLoginResponse;
import com.changyi.fi.vo.Session;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Service("weixinAPIService")
public class WeixinAPIServiceImpl implements WeixinAPIService {

    private static String APP_ID = "app.id";
    private static String APP_SECRET = "app.secret";
    private static String APP_HOST= "app.host";
    private static String APP_LOGIN_TEMPLATE = "app.login.template";

    private String getAppId() {
        return Properties.get(APP_ID);
    }

    private String getAppSecret() {
        return Properties.get(APP_SECRET);
    }

    private String getAppHost() {
        return Properties.get(APP_HOST);
    }


    public Session login(String code) throws Exception {
        LogUtil.info(this.getClass(), "Execute login service by calling Weixin API, code: " + code);
        String url = createLoginUrl(code);
        LogUtil.info(this.getClass(), "Call Weixin API, url: " + url);
        String res = new HTTPCaller(url).doGet();
        LogUtil.info(this.getClass(), "Weixin API response: " + res);
        WeixinLoginResponse response = new Payload(res).as(WeixinLoginResponse.class);
        if (!StringUtils.isEmpty(response.getErrcode())) {
            throw new SystemException("Weixin API return error: " + res);
        }
        Session resp = new Session();
        resp.setOpenId(response.getOpenid());
        resp.setSessionKey(response.getSession_key());
        LogUtil.info(this.getClass(),"Execute login service complete");
        LogUtil.debug(this.getClass(), "Response message: " + resp.toJson());
        return resp;
    }

    private String createLoginUrl(String code) {
        String template = Properties.get(APP_LOGIN_TEMPLATE);
        return MessageFormat.format(template, getAppHost(), getAppId(), getAppSecret(), code);
    }
}
