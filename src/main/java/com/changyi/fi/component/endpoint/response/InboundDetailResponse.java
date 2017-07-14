package com.changyi.fi.component.endpoint.response;

import com.changyi.fi.core.response.NormalResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by finley on 7/14/17.
 */
public class InboundDetailResponse extends NormalResponse {

    public InboundDetailResponse(HttpServletRequest req) {
        this.setRemoteAddr(req.getRemoteAddr());
        this.setRemoteHost(req.getRemoteHost());
        this.setRemotePort(req.getRemotePort());
        Enumeration headerNames = req.getHeaderNames();
        while (headerNames != null && headerNames.hasMoreElements()) {
            String name = (String) headerNames.nextElement();
            header.put(name, req.getHeader(name));
        }
    }

    private Map<String, String> header = new HashMap<String, String>();

    private String remoteAddr;

    private String remoteHost;

    private int remotePort;

    public Map<String, String> getHeader() {
        return header;
    }

    public void setHeader(Map<String, String> header) {
        this.header = header;
    }

    public String getRemoteAddr() {
        return remoteAddr;

    }

    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }

    public String getRemoteHost() {
        return remoteHost;
    }

    public void setRemoteHost(String remoteHost) {
        this.remoteHost = remoteHost;
    }

    public int getRemotePort() {
        return remotePort;
    }

    public void setRemotePort(int remotePort) {
        this.remotePort = remotePort;
    }
}
