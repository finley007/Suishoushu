package com.changyi.fi.vo;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

/**
 * Session Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Jul 7, 2017</pre>
 */
public class SessionTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: getCustomer()
     * Ò
     */
    @Test
    public void testGetCustomer() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: setCustomer(Customer customer)
     */
    @Test
    public void testSetCustomer() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: getOpenId()
     */
    @Test
    public void testGetOpenId() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: setOpenId(String openId)
     */
    @Test
    public void testSetOpenId() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: getSessionKey()
     */
    @Test
    public void testGetSessionKey() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: setSessionKey(String sessionKey)
     */
    @Test
    public void testSetSessionKey() throws Exception {
//TODO: Test goes here... 
    }

    @Test
    public void testToJson() throws Exception {
        Session session = new Session();
        session.setOpenId("openId");
        session.setSessionKey("sessionKey");
        Customer customer = new Customer();
        customer.setCity("city");
        customer.setCountry("country");
        customer.setGendar("1");
        customer.setNickName("nickName");
        customer.setProvince("province");
        session.setCustomer(customer);
        System.out.println(session.toJson());
    }


} 
