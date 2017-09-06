package com.changyi.fi.external.enterprise.qxb.hmac;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

/**
 * HmacKey Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Sep 2, 2017</pre>
 */
public class HmacDataTest {

    private HmacData hmacData;
    @Before
    public void before() throws Exception {
        hmacData = new HmacData("jGFBjtzGv8zjtGPPztEBclPjzzz8zKjGFBjtzGv8zjtGPPztEBclPjzzz8zK");
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: parse(String key)
     */
    @Test
    public void testParse() throws Exception {
//TODO: Test goes here...
        String key = "jGFBjtzGv8zjtGPPztEBclPjzzz8zKjGFBjtzGv8zjtGPPztEBclPjzzz8zK";
        long[] result = hmacData.parse(key);
        for (int i = 0; i < result.length; i++) {
            System.out.println(result[i]);
        }
    }


} 
