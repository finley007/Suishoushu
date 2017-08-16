package com.changyi.fi.external.enterprise.tyc.response;

import java.util.Map;

/**
 * Created by finley on 8/15/17.
 */
public class LoginResponse {

    public static final String SUCCESS = "ok";

    private Map<String, String> data;

    private String state;

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
