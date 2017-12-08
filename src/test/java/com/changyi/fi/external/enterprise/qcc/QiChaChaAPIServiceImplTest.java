package com.changyi.fi.external.enterprise.qcc;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * QiChaChaAPIServiceImpl Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Dec 8, 2017</pre>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/applicationContext.xml"})
public class QiChaChaAPIServiceImplTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: login(String username, String password)
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
     * Method: getAccountConfig()
     */
    @Test
    public void testGetAccountConfig() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: matchEnterprise(String key)
     */
    @Test
    public void testMatchEnterprise() throws Exception {
//TODO: Test goes here...
        QiChaChaAPIServiceImpl apiService = new QiChaChaAPIServiceImpl();
        System.out.println(apiService.matchEnterprise("万达"));
    }

    /**
     * Method: getEnterpriseByCode(String code)
     */
    @Test
    public void testGetEnterpriseByCode() throws Exception {
//TODO: Test goes here...
        QiChaChaAPIServiceImpl apiService = new QiChaChaAPIServiceImpl();
        System.out.println(apiService.getEnterpriseByCode("/firm_ab1368826c1506fcee1142e8393197f8.html"));
    }

    /**
     * Method: getAPIKey()
     */
    @Test
    public void testGetAPIKey() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: handleResult(Elements elems)
     */
    @Test
    public void testHandleResult() throws Exception {
//TODO: Test goes here... 
    }


} 
