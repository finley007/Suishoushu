package com.changyi.fi.vo;

public class AccountPair {

    private String account;
    private String password;

    public String getAccount() {
        return account;
    }

    public String getPassword() {
        return password;
    }

    public void setAccount(String account) {

        this.account = account;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AccountPair(String account, String password) {

        this.account = account;
        this.password = password;
    }

}
