package com.changyi.fi.external.enterprise.tyc.request;

/**
 * Created by finley on 8/15/17.
 */
public class LoginRequest {

    public LoginRequest(String mobile, String cdpassword) {
        this.mobile = mobile;
        this.cdpassword = cdpassword;
    }

    private Boolean autoLogin = true;

    private String cdpassword = "";

    private String loginway = "PL";

    private String mobile = "";

    public Boolean getAutoLogin() {
        return autoLogin;
    }

    public void setAutoLogin(Boolean autoLogin) {
        this.autoLogin = autoLogin;
    }

    public String getCdpassword() {
        return cdpassword;
    }

    public void setCdpassword(String cdpassword) {
        this.cdpassword = cdpassword;
    }

    public String getLoginway() {
        return loginway;
    }

    public void setLoginway(String loginway) {
        this.loginway = loginway;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
