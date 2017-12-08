package com.changyi.fi.external.enterprise.qcc.response;

import java.util.Map;

public class QCCBankInfoResponse {

    public static final String FIELD_NAME = "Name";
    public static final String FIELD_BANK = "Bank";
    public static final String FIELD_BANK_ACCOUNT = "Bankaccount";

    private Map<String, String> data;

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }
}
