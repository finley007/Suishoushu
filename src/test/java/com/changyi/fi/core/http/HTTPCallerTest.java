package com.changyi.fi.core.http;

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
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=wx8910b0b926fa81a9&secret=0d1afed8ecd9077731dacb2e7d13499f&js_code=JSCODE&grant_type=authorization_code";
        String response = new HTTPCaller(url).doGet();
        System.out.println(response);
    }


} 
