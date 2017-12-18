package com.changyi.fi.component.statistics.response;

import com.changyi.fi.core.response.NormalResponse;

import java.util.List;
import java.util.Map;

public class CustomerStatResponse extends NormalResponse {

    private long customerLoginTime;

    private long customerRegisterTime;

    private List<Map> custormerStat;

    private List<Map> customerDistri;

    public List<Map> getCustomerDistri() {
        return customerDistri;
    }

    public void setCustomerDistri(List<Map> customerDistri) {
        this.customerDistri = customerDistri;
    }

    public List<Map> getCustormerStat() {
        return custormerStat;
    }

    public void setCustormerStat(List<Map> custormerStat) {
        this.custormerStat = custormerStat;
    }

    public long getCustomerLoginTime() {
        return customerLoginTime;
    }

    public void setCustomerLoginTime(long customerLoginTime) {
        this.customerLoginTime = customerLoginTime;
    }

    public long getCustomerRegisterTime() {
        return customerRegisterTime;
    }

    public void setCustomerRegisterTime(long customerRegisterTime) {
        this.customerRegisterTime = customerRegisterTime;
    }
}
