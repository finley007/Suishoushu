package com.changyi.fi.core.redis;

import com.changyi.fi.vo.Session;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.util.List;

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

    @Test
    public void testListOperation() throws Exception {
        Session session = new Session();
        session.setOpenId("openId1");
        session.setSessionKey("sessionKey1");
        RedisClient.rpush("sessionList", session.toJson());
        session.setOpenId("openId2");
        session.setSessionKey("sessionKey2");
        RedisClient.rpush("sessionList", session.toJson());
        session.setOpenId("openId3");
        session.setSessionKey("sessionKey3");
        RedisClient.rpush("sessionList", session.toJson());
        List<String> result = RedisClient.lrange("sessionList", 0, -1);
        for (String str : result) {
            System.out.println(str);
        }
        RedisClient.del("sessionList");
        result = RedisClient.lrange("sessionList", 0, -1);
        for (String str : result) {
            System.out.println(str);
        }
    }


} 
