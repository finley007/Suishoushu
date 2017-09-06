package com.changyi.fi.external.enterprise.qxb.hmac;

import com.changyi.fi.external.enterprise.qxb.hmac.HmacManager;
import com.changyi.fi.external.enterprise.qxb.hmac.HmacTool;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

/**
 * HmacManager Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Sep 3, 2017</pre>
 */
public class HmacManagerTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: init(HmacTool tool, String key)
     */
    @Test
    public void testInit() throws Exception {
//TODO: Test goes here...
        HmacManager manager = new HmacManager();
        //HmacData result = manager.init(new HmacTool(), "jGFBjtzGv8zjtGPPztEBclPjzzz8zAzjGFBjtzGv8zjtGPPztEBclPjzzz8zAz").finalize("/api/search/suggestion?key=cebe");
        HmacData result = manager.init(new HmacTool(), "jGFBjtzGv8zjtGPPztEBclPjzzz8zAzjGFBjtzGv8zjtGPPztEBclPjzzz8zAz").finalize("/api/search/suggestion?key=cebe/api/search/suggestion?key=cebe{}");
        System.out.println(result.stringify());
    }

    /**
     * Method: finalize(String data)
     */
    @Test
    public void testFinalize() throws Exception {
//TODO: Test goes here... 
    }


    /**
     * Method: reset()
     */
    @Test
    public void testReset() throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = HmacManager.getClass().getMethod("reset"); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

} 
