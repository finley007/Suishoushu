package com.changyi.fi.core.http;

import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

/**
 * HTTPCaller Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Jun 28, 2017</pre>
 */
public class HTTPCallerTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: doGet()
     */
    @Test
    public void testDoGet() throws Exception {
//TODO: Test goes here...
        CookieStore cookieStore = new BasicCookieStore();
        BasicClientCookie cookie = new BasicClientCookie("auth_token", "test");
        cookie.setDomain("");
        cookie.setPath("/");
        cookieStore.addCookie(cookie);
        String url = "http://localhost:8080/suishoushu/inbound/detail";
        String response = new HTTPCaller(url).setCookieStore(cookieStore).doGet();
        System.out.println(response);
    }


} 
