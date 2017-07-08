package com.changyi.fi.core.http;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HTTPCaller {

    private static int RESPONSE_CODE_200 = 200;
    private static int RESPONSE_CODE_300 = 300;

    public static final String HEADER_USER_AGENT = "User-Agent";

    private String url;

    private Map<String, String> header;

    public Map<String, String> getHeader() {
        return header;
    }

    public HTTPCaller setHeader(Map<String, String> header) {
        this.header = header;
        return this;
    }

    public HTTPCaller(String url) {
        this.url = url;
    }

    public String getUrl() {

        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String doGet() throws Exception {
        HttpClient httpclient = HttpClients.createDefault();
        HttpGet get = new HttpGet(url);
        initHeader(get);
        ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
            @Override
            public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
                int status = response.getStatusLine().getStatusCode();
                if (status >= RESPONSE_CODE_200 && status < RESPONSE_CODE_300) {
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity) : null;
                } else {
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
            }
        };
        return httpclient.execute(get, responseHandler);
    }

    private void initHeader(HttpGet get) {
        if (this.header == null) {
            this.header = new HashMap<String, String>();
            this.header.put(HEADER_USER_AGENT, "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36");
        }
        for (String key : this.header.keySet()) {
            get.setHeader(key, this.header.get(key));
        }
    }

}
