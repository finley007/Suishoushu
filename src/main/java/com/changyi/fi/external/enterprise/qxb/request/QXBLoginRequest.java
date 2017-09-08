package com.changyi.fi.external.enterprise.qxb.request;

import java.util.HashMap;
import java.util.Map;

public class QXBLoginRequest {

    public QXBLoginRequest(String acc, String pass) {
        this.acc = acc;
        this.pass = pass;
        this.captcha.put("isTrusted", true);
    }

    private String acc;

    private String pass;

    private Map captcha = new HashMap();
}
