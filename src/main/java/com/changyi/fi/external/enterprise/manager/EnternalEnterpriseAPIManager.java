package com.changyi.fi.external.enterprise.manager;

import com.changyi.fi.exception.UnknowEnterpriseAPIImpl;
import com.changyi.fi.external.enterprise.ExternalEnterpriseAPIService;
import com.changyi.fi.external.enterprise.tyc.TianYanChaAPIServiceImpl;

public class EnternalEnterpriseAPIManager {

    public static final String API_TIANYANCHA = "tyc";

    public static ExternalEnterpriseAPIService getAPIImpl(String key) throws Exception {
        if (API_TIANYANCHA.equals(API_TIANYANCHA)) {
            return new TianYanChaAPIServiceImpl();
        } else {
            throw new UnknowEnterpriseAPIImpl("The enterprise service implements: " + key + "is unknown");
        }
    }
}
