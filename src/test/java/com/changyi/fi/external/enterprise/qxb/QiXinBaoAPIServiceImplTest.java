package com.changyi.fi.external.enterprise.qxb;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * QiXinBaoAPIServiceImpl Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Sep 1, 2017</pre>
 */
public class QiXinBaoAPIServiceImplTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: matchEnterprise(String key)
     */
    @Test
    public void testMatchEnterprise() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: getEnterpriseByCode(String code)
     */
    @Test
    public void testGetEnterpriseByCode() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: login()
     */
    @Test
    public void testLogin() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: getTokenExpiredTime()
     */
    @Test
    public void testGetTokenExpiredTime() throws Exception {
//TODO: Test goes here... 
    }


    /**
     * Method: getKey(String path)
     */
    @Test
    public void testGetKey() throws Exception {
//TODO: Test goes here... 

        try {
            QiXinBaoAPIServiceImpl api = new QiXinBaoAPIServiceImpl();
            Method method = api.getClass().getDeclaredMethod("getKey", String.class);
            method.setAccessible(true);
            //"jGFBjtzGv8zjtGPPztEBclPjzzzGGjGFBjtzGv8zjtGPPztEBclPjzzzGG"
            System.out.println(method.invoke(api, "/api/user/login"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

} 
