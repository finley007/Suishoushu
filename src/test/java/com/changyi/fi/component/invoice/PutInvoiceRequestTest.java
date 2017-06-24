package com.changyi.fi.component.invoice;

import com.changyi.fi.component.invoice.request.PutInvoiceRequest;
import com.changyi.fi.core.Payload;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

/**
 * PutInvoiceRequest Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Jun 24, 2017</pre>
 */
public class PutInvoiceRequestTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }


    @Test
    public void createJson() {
        PutInvoiceRequest req = new PutInvoiceRequest();
        req.setId("11");
        req.setType("0");
        String json = new Payload(req).from(PutInvoiceRequest.class);
        System.out.println(json);

    }

} 
