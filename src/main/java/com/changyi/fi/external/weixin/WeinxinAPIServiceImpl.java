package com.changyi.fi.external.weixin;

import com.changyi.fi.core.LogUtil;
import com.changyi.fi.core.http.HTTPCaller;
import com.changyi.fi.core.tool.Properties;
import com.changyi.fi.vo.CustomerInfo;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Service("appService")
public class WeinxinAPIServiceImpl implements WeixinAPIService {

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

    private String getAppUrl() {
        return Properties.get(APP_HOST);
    }


    public CustomerInfo login(String code) throws Exception {
        LogUtil.info(this.getClass(), "Execute login service by calling Weixin API, code: " + code);
        String template = Properties.get(APP_LOGIN_TEMPLATE);
        String url = MessageFormat.format(template, getAppUrl(), getAppId(), getAppSecret(), code);
        LogUtil.info(this.getClass(), "Call Weixin login service, url: " + url);
        String response = HTTPCaller.doGet(url);
        LogUtil.info(this.getClass(), "Service response: " + response);
        CustomerInfo resp = new CustomerInfo();
        resp.setOpenId("19830310007");
        resp.setNickName("liuli");
        resp.setGendar("1");
        LogUtil.info(this.getClass(),"Execute login service complete");
        LogUtil.debug(this.getClass(), "Response message: " + resp.toJson());
        return resp;
    }
}
