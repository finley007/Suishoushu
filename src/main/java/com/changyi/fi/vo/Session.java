package com.changyi.fi.vo;

import com.changyi.fi.core.BaseVO;

public class Session extends BaseVO {

    public Customer getCustomer() {
        return customer;
    }

    public Session setCustomer(Customer customer) {
        this.customer = customer;
        return this;
    }

    private Customer customer;

    private String openId;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    private String sessionKey;
}
