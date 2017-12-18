package com.changyi.fi.component.statistics.response;

import com.changyi.fi.core.response.NormalResponse;

import java.util.List;
import java.util.Map;

public class SystemStatResponse extends NormalResponse {

    private List<Map> externalAPIStat;

    private Long enterpriseSyncNum;

    public Long getEnterpriseSyncNum() {
        return enterpriseSyncNum;
    }

    public void setEnterpriseSyncNum(Long enterpriseSyncNum) {
        this.enterpriseSyncNum = enterpriseSyncNum;
    }

    public List<Map> getExternalAPIStat() {
        return externalAPIStat;
    }

    public void setExternalAPIStat(List<Map> externalAPIStat) {
        this.externalAPIStat = externalAPIStat;
    }
}
