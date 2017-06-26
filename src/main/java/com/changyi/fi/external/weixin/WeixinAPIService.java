package com.changyi.fi.external.weixin;

import com.changyi.fi.vo.CustomerInfo;

public interface WeixinAPIService {

    public CustomerInfo login(String code) throws Exception;

}
