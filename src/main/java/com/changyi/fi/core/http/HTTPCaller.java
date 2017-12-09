package com.changyi.fi.core.http;

import com.changyi.fi.core.LogUtil;
import com.changyi.fi.core.config.ConfigDic;
import com.changyi.fi.core.config.ConfigManager;
import com.changyi.fi.core.job.JobManager;
import com.changyi.fi.core.model.SysOutboundPO;
import com.changyi.fi.core.tool.Properties;
import com.changyi.fi.job.RecordOutboundJob;
import com.changyi.fi.util.FIConstants;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.ws.rs.HttpMethod;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HTTPCaller {

    private static final String PROXY_IP = "proxy.ip";
    private static final String PROXY_PORT = "proxy.port";

    private static int RESPONSE_CODE_200 = 200;
    private static int RESPONSE_CODE_300 = 300;

    private static String COOKIE_KEY = "Cookie";

    public static final String HEADER_USER_AGENT = "User-Agent";
    public static final String HEADER_CONNECTION = "Connection";

    public static final String CONTENT_TYPE_JSON = "application/json";

    private static final String URL_PATTERN = "(http|ftp|https)://(([a-zA-Z0-9\\._-]+\\.[a-zA-Z]{2,6})|([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}))(:([0-9]{1,4}))*(/[a-zA-Z0-9\\&%_\\./-~-]*)?";

    private String url;

    private int proxyPort;

    private String proxyIp;

    private Map<String, String> header;

    private CookieStore cookieStore;

    private int timeout = 30000;

    public int getTimeout() {
        return timeout;
    }

    public HTTPCaller setTimeout(int timeout) {
        this.timeout = timeout;
        return this;
    }

    public int getProxyPort() {
        return proxyPort;
    }

    public HTTPCaller setProxyPort(int proxyPort) {
        this.proxyPort = proxyPort;
        return this;
    }

    public String getProxyIp() {
        return proxyIp;
    }

    public HTTPCaller setProxyIp(String proxyIp) {
        this.proxyIp = proxyIp;
        return this;
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public HTTPCaller setHeader(Map<String, String> header) {
        this.header = header;
        return this;
    }

    public CookieStore getCookieStore() {
        return cookieStore;
    }

    public HTTPCaller setCookieStore(CookieStore cookieStore) {
        this.cookieStore = cookieStore;
        return this;
    }

    public HTTPCaller(String url) {
        this.url = url;
    }

    public HTTPCaller enableProxy() {
        this.proxyIp = Properties.get(PROXY_IP);
        String proxyPort = Properties.get(PROXY_PORT);
        try {
            this.proxyPort = Integer.valueOf(proxyPort);
        } catch (Exception e) {
            LogUtil.error(this.getClass(), "Invalid proxy port in config.properties", e);
        }
        return this;
    }

    public String getUrl() {
        return url;
    }

    public HTTPCaller setUrl(String url) {
        this.url = url;
        return this;
    }

    public static String createUrl(String prop, Object[] args) {
        String template = Properties.get(prop);
        return MessageFormat.format(template, args);
    }

    public String doGet() throws Exception {
        HttpClient httpClient = getHttpClient();
        HttpGet get = new HttpGet(url);
        init(get);
        recordOutbound(HttpMethod.GET);
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
        return httpClient.execute(get, responseHandler);
    }

    private void recordOutbound(String method) {
        SysOutboundPO po = parseURL(method);
        JobManager.addJob(new RecordOutboundJob(po));
    }

    public SysOutboundPO parseURL(String method) {
        Pattern pattern = Pattern.compile(URL_PATTERN);
        Matcher matcher = pattern.matcher(url);
        if (matcher.matches()) {
            SysOutboundPO po = new SysOutboundPO();
            po.setProtocol(matcher.group(1));
            po.setDomain(matcher.group(2));
            po.setPort(matcher.group(6));
            po.setUrl(url);
            po.setProxyIp(this.proxyIp);
            po.setProxyPort(this.proxyPort + "");
            po.setMethod(method);
            po.setCallTime(new Date());
            return po;
        }
        return null;
    }

    public String doPost(Map<String, String> map) throws Exception {
        HttpClient httpClient = getHttpClient();
        HttpPost post = new HttpPost(url);
        init(post);
        if (map != null && map.size() > 0) {
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            for (Iterator<String> itor = map.keySet().iterator(); itor.hasNext(); ) {
                String key = itor.next();
                nvps.add(new BasicNameValuePair(key, map.get(key)));
            }
            post.setEntity(new UrlEncodedFormEntity(nvps));
        }
        recordOutbound(HttpMethod.POST);
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
        return httpClient.execute(post, responseHandler);
    }

    public String doPost(String json) throws Exception {
        HttpClient httpClient = getHttpClient();
        HttpPost post = new HttpPost(url);
        init(post);
        if (StringUtils.isNoneBlank(json)) {
            StringEntity entity = new StringEntity(json, FIConstants.DEFAULT_CHARSET);   // 中文乱码在此解决
            entity.setContentType(CONTENT_TYPE_JSON);
            post.setEntity(entity);
        }
        recordOutbound(HttpMethod.POST);
        ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
            @Override
            public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
                int status = response.getStatusLine().getStatusCode();
//                if (status >= RESPONSE_CODE_200 && status < RESPONSE_CODE_300) {
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity) : null;
//                } else {
//                    throw new ClientProtocolException("Unexpected response status: " + status);
//                }
            }
        };
        return httpClient.execute(post, responseHandler);
    }

    public void init(HttpRequestBase requestBase) {
        initHeader(requestBase);
        initCookie(requestBase);
        initTimeout(requestBase);
    }

    public void downloadPost(String json, String path) throws Exception {
        HttpClient httpclient = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);
        init(post);
        recordOutbound(HttpMethod.POST);
        if (StringUtils.isNoneBlank(json)) {
            StringEntity entity = new StringEntity(json, FIConstants.DEFAULT_CHARSET);   // 中文乱码在此解决
            entity.setContentType(CONTENT_TYPE_JSON);
            post.setEntity(entity);
        }
        httpclient.execute(post, new DownloadHandler(path));
    }

    private void initHeader(HttpRequestBase requestBase) {
        if (this.header == null) {
            this.header = new HashMap<String, String>();
        }
        this.header.put(HEADER_USER_AGENT, "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36");
        this.header.put(HEADER_CONNECTION, "keep-alive");
        for (String key : this.header.keySet()) {
            requestBase.addHeader(key, this.header.get(key));
        }
    }

    private void initCookie(HttpRequestBase requestBase) {
        if (this.cookieStore != null && this.cookieStore.getCookies() != null && this.cookieStore.getCookies().size() > 0) {
            String cookieExp = "";
            for (Cookie cookie : this.cookieStore.getCookies()) {
                cookieExp += cookie.getName() + "=" + cookie.getValue() + ";";
            }
            if (StringUtils.isNotBlank(cookieExp)) {
                requestBase.addHeader(COOKIE_KEY, cookieExp);
            }
        }
    }

    private void initTimeout(HttpRequestBase requestBase) {
        this.timeout = ConfigManager.getIntegerParameter(ConfigDic.HTTP_TIMEOUT, timeout);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(this.timeout).setConnectionRequestTimeout(this.timeout)
                .setSocketTimeout(this.timeout).build();
        requestBase.setConfig(requestConfig);
    }

    private HttpClient getHttpClient() {
        HttpClientBuilder builder = HttpClients.custom();
        if (this.getProxy() != null && ConfigManager.getBooleanParameter(ConfigDic.HTTP_PROXY_TOGGLE, true)) {
            builder.setProxy(this.getProxy());
        }
        if (this.getCookieStore() != null) {
            builder.setDefaultCookieStore(this.getCookieStore());
        }
        return builder.build();
    }

    private HttpHost getProxy() {
        if (StringUtils.isNotBlank(this.proxyIp) && this.proxyPort > 0) {
            return new HttpHost(this.proxyIp, this.proxyPort);
        }
        return null;
    }

    private static class DownloadHandler implements ResponseHandler {

        public DownloadHandler(String path) {
            this.path = path;
        }

        private String path;

        public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
            LogUtil.info(this.getClass(), "Download file to path: " + path);
            File qrcode = new File(path);
            if (!qrcode.getParentFile().exists()) {
                qrcode.getParentFile().mkdir();
            }
            if (!qrcode.exists()) {
                qrcode.createNewFile();
            }
            FileOutputStream outputStream = new FileOutputStream(qrcode);
            InputStream inputStream = response.getEntity().getContent();
            byte buff[] = new byte[4096];
            int counts = 0;
            while ((counts = inputStream.read(buff)) != -1) {
                outputStream.write(buff, 0, counts);
            }
            outputStream.flush();
            outputStream.close();
            return path;
        }
    }

}
