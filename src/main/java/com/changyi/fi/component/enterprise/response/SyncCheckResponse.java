package com.changyi.fi.component.enterprise.response;

import com.changyi.fi.core.response.NormalResponse;

import java.util.Map;

public class SyncCheckResponse extends NormalResponse {

    public SyncCheckResponse(Map enterpriseInfo) {
        this.enterpriseInfo = enterpriseInfo;
    }

    private Map enterpriseInfo;

    public Map getEnterpriseInfo() {
        return enterpriseInfo;
    }

    public void setEnterpriseInfo(Map enterpriseInfo) {
        this.enterpriseInfo = enterpriseInfo;
    }
}
