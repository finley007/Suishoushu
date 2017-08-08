package com.changyi.fi.core.http;

import com.changyi.fi.core.tool.Properties;
import com.changyi.fi.util.FIConstants;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.*;

public class HTTPCaller {

    private static int RESPONSE_CODE_200 = 200;
    private static int RESPONSE_CODE_300 = 300;

    public static final String HEADER_USER_AGENT = "User-Agent";

    public static final String CONTENT_TYPE_JSON = "application/json";

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

    public String doPost(Map<String, String> map) throws Exception {
        HttpClient httpclient = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);
        initHeader(post);
        if (map != null && map.size() > 0) {
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            for (Iterator<String> itor = map.keySet().iterator(); itor.hasNext(); ) {
                String key = itor.next();
                nvps.add(new BasicNameValuePair(key, map.get(key)));
            }
            post.setEntity(new UrlEncodedFormEntity(nvps));
        }
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
        return httpclient.execute(post, responseHandler);
    }

    public String doPost(String json) throws Exception {
        HttpClient httpclient = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);
        initHeader(post);
        if (StringUtils.isNoneBlank(json)) {
            StringEntity entity = new StringEntity(json, FIConstants.DEFAULT_CHARSET);   // 中文乱码在此解决
            entity.setContentType(CONTENT_TYPE_JSON);
            post.setEntity(entity);
        }
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
        return httpclient.execute(post, responseHandler);
    }

    public String downloadPost(String json) throws Exception {
        HttpClient httpclient = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);
        initHeader(post);
        if (StringUtils.isNoneBlank(json)) {
            StringEntity entity = new StringEntity(json, FIConstants.DEFAULT_CHARSET);   // 中文乱码在此解决
            entity.setContentType(CONTENT_TYPE_JSON);
            post.setEntity(entity);
        }
        ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
            @Override
            public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
                String path = "/Users/finley/Finley/workspace/java/Suishoushu/code.img";
                File xml = new File(path);
                FileOutputStream outputStream = new FileOutputStream(xml);
                InputStream inputStream = response.getEntity().getContent();
                byte buff[] = new byte[4096];
                int counts = 0;
                while ((counts = inputStream.read(buff)) != -1) {
                    System.out.println(".......");
                    outputStream.write(buff, 0, counts);

                }
                outputStream.flush();
                outputStream.close();
                return path;
            }
        };
        return httpclient.execute(post, responseHandler);
    }

    private void initHeader(HttpRequestBase requestBase) {
        if (this.header == null) {
            this.header = new HashMap<String, String>();
            this.header.put(HEADER_USER_AGENT, "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36");
        }
        for (String key : this.header.keySet()) {
            requestBase.setHeader(key, this.header.get(key));
        }
    }

    public static String createUrl(String prop, Object[] args) {
        String template = Properties.get(prop);
        return MessageFormat.format(template, args);
    }

}
