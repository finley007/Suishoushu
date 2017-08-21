package com.changyi.fi.component.enterprise;

import com.changyi.fi.component.enterprise.response.MatchEnterpriseResponse;
import com.changyi.fi.component.enterprise.service.EnterpriseService;
import com.changyi.fi.core.CtxProvider;
import com.changyi.fi.core.Payload;
import com.changyi.fi.external.enterprise.ExternalEnterpriseAPIService;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * EnterpriseServiceImpl Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Aug 21, 2017</pre>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/applicationContext.xml"})
public class EnterpriseServiceImplTest {

    private EnterpriseService service;

    @Before
    public void before() throws Exception {
        service = (EnterpriseService)CtxProvider.getContext().getBean("enterpriseService");
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: setInvoiceDao(InvoiceDao invoiceDao)
     */
    @Test
    public void testSetInvoiceDao() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: matchEnterprise(String key)
     */
    @Test
    public void testMatchEnterprise() throws Exception {
//TODO: Test goes here...
        MatchEnterpriseResponse response = this.service.matchEnterprise("畅移");
        System.out.println(new Payload(response).from(MatchEnterpriseResponse.class));
    }

    /**
     * Method: getEnterprise(GetEnterpriseRequest req)
     */
    @Test
    public void testGetEnterprise() throws Exception {
//TODO: Test goes here... 
    }


    /**
     * Method: combineResult(List<Map> internal, List<Map> external)
     */
    @Test
    public void testCombineResult() throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = EnterpriseServiceImpl.getClass().getMethod("combineResult", List<Map>.class, List<Map>.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

} 
