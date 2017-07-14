package com.changyi.fi.component.endpoint.response;

import com.changyi.fi.core.response.NormalResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by finley on 7/14/17.
 */
public class InboundDetailResponse extends NormalResponse {

    public HttpServletRequest getReq() {
        return req;
    }

    public void setReq(HttpServletRequest req) {
        this.req = req;
    }

    public InboundDetailResponse(HttpServletRequest req) {
        this.req = req;
    }

    private HttpServletRequest req;
}
