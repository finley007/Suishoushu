package com.changyi.fi.core.request;

/**
 * Created by finley on 6/21/17.
 */
public class SecurityRequest {

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    private String sessionId;
}
