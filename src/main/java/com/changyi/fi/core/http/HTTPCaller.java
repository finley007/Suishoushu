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

public class HTTPCaller {

    private static int RESPONSE_CODE_200 = 200;
    private static int RESPONSE_CODE_300 = 300;

    public static String doGet(String url) throws Exception {
        HttpClient httpclient = HttpClients.createDefault();
        HttpGet get = new HttpGet(url);
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
}
