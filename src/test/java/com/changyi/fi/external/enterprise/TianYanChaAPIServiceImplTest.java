package com.changyi.fi.external.enterprise;

import com.changyi.fi.external.enterprise.tyc.TianYanChaAPIServiceImpl;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.util.List;

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
        ExternalEnterpriseAPIService apiService = new TianYanChaAPIServiceImpl();
        List result = apiService.matchEnterprise("乔丹");
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

    @Test
    public void testLogin() throws Exception {
        ExternalEnterpriseAPIService apiService = new TianYanChaAPIServiceImpl();
        System.out.println(apiService.login());
    }
} 
