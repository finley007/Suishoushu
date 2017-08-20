package com.changyi.fi.component.endpoint.response;

import com.changyi.fi.core.Payload;
import com.changyi.fi.core.response.NormalResponse;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by finley on 7/14/17.
 */
public class InboundDetailResponse extends NormalResponse {

    public InboundDetailResponse(HttpServletRequest req) {
        this.setCookies(req.getCookies());
        this.setRemoteAddr(req.getRemoteAddr());
        this.setRemoteHost(req.getRemoteHost());
        this.setRemotePort(req.getRemotePort());
        this.setRequestURI(req.getRequestURI());
        this.setRequestURL(req.getRequestURL().toString());
        this.setPathInfo(req.getPathInfo());
        Enumeration headerNames = req.getHeaderNames();
        while (headerNames != null && headerNames.hasMoreElements()) {
            String name = (String) headerNames.nextElement();
            header.put(name, req.getHeader(name));
        }
    }

    private String[] cookies;

    private Map<String, String> header = new HashMap<String, String>();

    private String remoteAddr;

    private String remoteHost;

    private int remotePort;

    private String requestURI;

    private String requestURL;

    private String pathInfo;

    public String getPathInfo() {
        return pathInfo;
    }

    public void setPathInfo(String pathInfo) {
        this.pathInfo = pathInfo;
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public void setHeader(Map<String, String> header) {
        this.header = header;
    }

    public String getRequestURI() {
        return requestURI;
    }

    public void setRequestURI(String requestURI) {
        this.requestURI = requestURI;
    }

    public String getRequestURL() {
        return requestURL;
    }

    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    public String getRemoteAddr() { return remoteAddr; }

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

    public String[] getCookies() {
        return cookies;
    }

    public void setCookies(Cookie[] cookies) {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < cookies.length; i++) {
            list.add(new Payload(cookies[i]).from(Cookie.class));
        }
        if (list.size() > 0) {
            this.cookies = list.toArray(new String[list.size()]);
        }
    }
}
