package com.changyi.fi.external.weixin;

import com.changyi.fi.vo.Session;

public interface WeixinAPIService {

    public Session login(String code) throws Exception;

    public String getAccessToken() throws Exception;

    public String createMerchantQRCode() throws Exception;

}
