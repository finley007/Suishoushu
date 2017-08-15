package com.changyi.fi.external.weixin;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

/**
 * WeixinAPIServiceImpl Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Aug 7, 2017</pre>
 */
public class WeixinAPIServiceImplTest {

    private WeixinAPIService api;

    @Before
    public void before() throws Exception {
        api = new WeixinAPIServiceImpl();
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: login(String code)
     */
    @Test
    public void testLogin() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: getAccessToken()
     */
    @Test
    public void testGetAccessToken() throws Exception {
//TODO: Test goes here...
        System.out.println(this.api.getAccessToken());
    }


    /**
     * Method: getAppId()
     */
    @Test
    public void testGetAppId() throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = WeixinAPIServiceImpl.getClass().getMethod("getAppId"); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

    /**
     * Method: getAppSecret()
     */
    @Test
    public void testGetAppSecret() throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = WeixinAPIServiceImpl.getClass().getMethod("getAppSecret"); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

    /**
     * Method: getAppHost()
     */
    @Test
    public void testGetAppHost() throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = WeixinAPIServiceImpl.getClass().getMethod("getAppHost"); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

    /**
     * Method: createLoginUrl(String code)
     */
    @Test
    public void testCreateLoginUrl() throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = WeixinAPIServiceImpl.getClass().getMethod("createLoginUrl", String.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

    /**
     * Method: createAccessTokenUrl()
     */
    @Test
    public void testCreateAccessTokenUrl() throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = WeixinAPIServiceImpl.getClass().getMethod("createAccessTokenUrl"); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

    @Test
    public void testCreateMerchantQRCode() throws Exception {
        this.api.createMerchantQRCode("", "");
    }


} 
