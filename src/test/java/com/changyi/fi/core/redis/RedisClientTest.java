package com.changyi.fi.core.redis;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

/**
 * RedisClient Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Jul 7, 2017</pre>
 */
public class RedisClientTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: getJedis()
     * Ø
     */
    @Test
    public void testGetJedis() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: set(String key, String value)
     */
    @Test
    public void testSet() throws Exception {
//TODO: Test goes here...
//        RedisClient.set("test", "test");
        System.out.println(RedisClient.get("aa"));
    }


} 
