package com.changyi.fi.external.enterprise.tyc;

import com.changyi.fi.external.enterprise.ExternalEnterpriseAPIService;
import com.changyi.fi.model.EnterprisePO;
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
     * Method: getEnterpriseByCode(String creditCode)
     */
    @Test
    public void testGetEnterpriseByCode() throws Exception {
//TODO: Test goes here... 
        ExternalEnterpriseAPIService apiService = new TianYanChaAPIServiceImpl();
        EnterprisePO po = apiService.getEnterpriseByCode("264417394");
    }

    @Test
    public void testLogin() throws Exception {
        TianYanChaAPIServiceImpl apiService = new TianYanChaAPIServiceImpl();
        System.out.println(apiService.login());
    }
} 
