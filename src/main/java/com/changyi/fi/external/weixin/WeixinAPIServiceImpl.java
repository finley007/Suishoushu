package com.changyi.fi.external.weixin;

import com.changyi.fi.core.LogUtil;
import com.changyi.fi.core.Payload;
import com.changyi.fi.core.exception.SystemException;
import com.changyi.fi.core.http.HTTPCaller;
import com.changyi.fi.core.tool.Properties;
import com.changyi.fi.external.weixin.request.MerchantQRCodeRequest;
import com.changyi.fi.external.weixin.response.AccessTokenResponse;
import com.changyi.fi.external.weixin.response.WeixinLoginResponse;
import com.changyi.fi.vo.Session;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

@Service("weixinAPIService")
public class WeixinAPIServiceImpl implements WeixinAPIService {

    private static final String APP_ID = "app.id";
    private static final String APP_SECRET = "app.secret";
    private static final String APP_HOST= "app.host";

    private static final String APP_LOGIN_TEMPLATE = "app.login.template";
    private static final String APP_TOKEN_OBTAIN_TEMPLATE = "app.token.obtain.template";
    private static final String APP_QRCODE_CREATE_TEMPLATE = "app.qrcode.create.template";

    private static final String PARAMETER_PAGE = "pages/index/index";
    private static final String PARAMETER_WIDTH = "430";

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
        LogUtil.debug(this.getClass(), "Weixin API response: " + res);
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

    public String getAccessToken() throws Exception {
        LogUtil.info(this.getClass(), "Get access token by calling Weixin API");
        String url = createAccessTokenUrl();
        LogUtil.info(this.getClass(), "Call Weixin API, url: " + url);
        String res = new HTTPCaller(url).doGet();
        LogUtil.debug(this.getClass(), "Weixin API response: " + res);
        AccessTokenResponse response = new Payload(res).as(AccessTokenResponse.class);
        if (!StringUtils.isEmpty(response.getErrcode())) {
            throw new SystemException("Weixin API return error: " + res);
        }
        LogUtil.info(this.getClass(),"Get access token complete and token: " + response.getAccess_token());
        return response.getAccess_token();
    }

    private String createAccessTokenUrl() {
        String template = Properties.get(APP_TOKEN_OBTAIN_TEMPLATE);
        return MessageFormat.format(template, getAppHost(), getAppId(), getAppSecret());
    }

    public void createMerchantQRCode(String merchantId, String downloadPath) throws Exception {
        LogUtil.info(this.getClass(), "Create QR code by calling Weixin API");
        String url = createMerchantQRCodeUrl();
        LogUtil.info(this.getClass(), "Call Weixin API, url: " + url);
        String req = createQRCodeRequest(merchantId);
        LogUtil.debug(this.getClass(), "Request message: " + req);
        new HTTPCaller(url).downloadPost(req, downloadPath);
        LogUtil.info(this.getClass(),"Complete creating QR code and save to: " + downloadPath);
    }

    public String createQRCodeRequest(String merchantId) {
        String sence = "id=" + merchantId;
//        String req = new Payload(new MerchantQRCodeRequest(sence, page)).from(MerchantQRCodeRequest.class);
        return "{\"scene\":\"" + sence + "\",\"page\":\"" + PARAMETER_PAGE + "\",\"width\":430}";
    }

    private String createMerchantQRCodeUrl() throws Exception {
        String template = Properties.get(APP_QRCODE_CREATE_TEMPLATE);
        String accessToken = this.getAccessToken();
        return MessageFormat.format(template, getAppHost(), accessToken);
    }

}
