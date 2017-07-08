package com.changyi.fi.external.tianyancha;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

/**
 * TianYanChaAPIServiceImpl Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Jul 8, 2017</pre>
 */
public class TianYanChaAPIServiceImplTest {

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
        TianYanChaAPIService apiService = new TianYanChaAPIServiceImpl();
        apiService.matchEnterprise("天之健");
    }


    /**
     * Method: createSearchUrl(String key)
     */
    @Test
    public void testCreateSearchUrl() throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = TianYanChaAPIServiceImpl.getClass().getMethod("createSearchUrl", String.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

} 
