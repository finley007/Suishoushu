package com.changyi.fi.external.enterprise.manager;

import com.changyi.fi.exception.UnknowEnterpriseAPIImpl;
import com.changyi.fi.external.enterprise.ExternalEnterpriseAPIService;
import com.changyi.fi.external.enterprise.qxb.QiXinBaoAPIServiceImpl;
import com.changyi.fi.external.enterprise.tyc.TianYanChaAPIServiceImpl;

public class EnternalEnterpriseAPIManager {

    public static final String API_TIANYANCHA = "tyc";
    public static final String API_QIXINBAO = "qxb";

    public static ExternalEnterpriseAPIService getAPIImpl(String key) throws Exception {
        if (API_TIANYANCHA.equals(key)) {
            return new TianYanChaAPIServiceImpl();
        } if (API_QIXINBAO.equals(key)) {
            return new QiXinBaoAPIServiceImpl();
        } else {
            throw new UnknowEnterpriseAPIImpl("The enterprise service implements: " + key + "is unknown");
        }
    }
}
