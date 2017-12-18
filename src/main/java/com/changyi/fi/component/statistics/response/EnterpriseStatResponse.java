package com.changyi.fi.component.statistics.response;

import com.changyi.fi.core.response.NormalResponse;

import java.util.List;
import java.util.Map;

public class EnterpriseStatResponse extends NormalResponse {

    private List<Map> enterpriseStat;

    private List<Map> enterpriseDistri;

    public List<Map> getEnterpriseStat() {
        return enterpriseStat;
    }

    public void setEnterpriseStat(List<Map> enterpriseStat) {
        this.enterpriseStat = enterpriseStat;
    }

    public List<Map> getEnterpriseDistri() {
        return enterpriseDistri;
    }

    public void setEnterpriseDistri(List<Map> enterpriseDistri) {
        this.enterpriseDistri = enterpriseDistri;
    }
}
