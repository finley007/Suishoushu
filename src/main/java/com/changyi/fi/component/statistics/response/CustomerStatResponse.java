package com.changyi.fi.component.statistics.response;

import com.changyi.fi.core.response.NormalResponse;

import java.util.List;
import java.util.Map;

public class CustomerStatResponse extends NormalResponse {

    private long totalCustomerCount;

    private long inactiveCustomerCount;

    private long customerLoginCount;

    private long customerRegisterCount;

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

    public long getCustomerLoginCount() {
        return customerLoginCount;
    }

    public void setCustomerLoginCount(long customerLoginCount) {
        this.customerLoginCount = customerLoginCount;
    }

    public long getCustomerRegisterCount() {
        return customerRegisterCount;
    }

    public void setCustomerRegisterCount(long customerRegisterCount) {
        this.customerRegisterCount = customerRegisterCount;
    }

    public long getTotalCustomerCount() {
        return totalCustomerCount;
    }

    public void setTotalCustomerCount(long totalCustomerCount) {
        this.totalCustomerCount = totalCustomerCount;
    }

    public long getInactiveCustomerCount() {
        return inactiveCustomerCount;
    }

    public void setInactiveCustomerCount(long inactiveCustomerCount) {
        this.inactiveCustomerCount = inactiveCustomerCount;
    }
}
