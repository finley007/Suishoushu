package com.changyi.fi.external.weixin;

import com.changyi.fi.core.LogUtil;
import com.changyi.fi.vo.CustomerInfo;
import org.springframework.stereotype.Service;

@Service("appService")
public class WeinxinAPIServiceImpl implements WeixinAPIService {

    public CustomerInfo login(String code) throws Exception {
        LogUtil.info(this.getClass(), "Execute login service by calling Weixin API, code: " + code);
        CustomerInfo resp = new CustomerInfo();
        resp.setOpenId("19830310007");
        resp.setNickName("liuli");
        resp.setGendar("1");
        LogUtil.info(this.getClass(),"Execute login service complete");
        LogUtil.debug(this.getClass(), "Response message: " + resp.toJson());
        return resp;
    }
}
