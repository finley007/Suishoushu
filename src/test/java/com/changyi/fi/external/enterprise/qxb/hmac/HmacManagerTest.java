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
//        HmacData result = manager.init(new HmacTool(), "jGFBjtzGv8zjtGPPztEBclPjzzz8zAzjGFBjtzGv8zjtGPPztEBclPjzzz8zAz").finalize("/api/search/suggestion?key=cebe/api/search/suggestion?key=cebe{}");
//        HmacData result = manager.init(new HmacTool(), "jGFBjtzGv8zjtGPPztEBclPjzzzGzvGA0GGqGzEGEAGEqGztGGKGEKGztGGtGAAjGFBjtzGv8zjtGPPztEBclPjzzzGzvGA0GGqGzEGEAGEqGztGGKGEKGztGGtGAA").finalize("/api/search/suggestion?key=%e6%b1%9f%e8%8b%8f%e7%95%85%e7%a7%bb");

        /*
        jGFBjtzGv8zjtGPPztEBclPjzzzGzKGGFGGtGzEGAqGGzGzFGAEGEtGzEGAzGAzGzGGGAGEvGzKGGAGGlGzEGElGG0GzFGAAGAzGzvGG8GEGGzGGGGGGsGzKGEKGG8GzKGEqGAEjGFBjtzGv8zjtGPPztEBclPjzzzGzKGGFGGtGzEGAqGGzGzFGAEGEtGzEGAzGAzGzGGGAGEvGzKGGAGGlGzEGElGG0GzFGAAGAzGzvGG8GEGGzGGGGGGsGzKGEKGG8GzKGEqGAE
        /api/search/suggestion?key=%e5%a4%a7%e8%bf%9e%e4%b8%87%e8%be%be%e9%9b%86%e5%9b%a2%e8%82%a1%e4%bb%bd%e6%9c%89%e9%99%90%e5%85%ac%e5%8f%b8
        大连万达集团股份有限公司
        */
        HmacData result = manager.init(new HmacTool(), "jGFBjtzGv8zjtGPPztEBclPjzzzGzKGGFGGtGzEGAqGGzGzFGAEGEtGzEGAzGAzGzGGGAGEvGzKGGAGGlGzEGElGG0GzFGAAGAzGzvGG8GEGGzGGGGGGsGzKGEKGG8GzKGEqGAEjGFBjtzGv8zjtGPPztEBclPjzzzGzKGGFGGtGzEGAqGGzGzFGAEGEtGzEGAzGAzGzGGGAGEvGzKGGAGGlGzEGElGG0GzFGAAGAzGzvGG8GEGGzGGGGGGsGzKGEKGG8GzKGEqGAE").finalize("/api/search/suggestion?key=%e5%a4%a7%e8%bf%9e%e4%b8%87%e8%be%be%e9%9b%86%e5%9b%a2%e8%82%a1%e4%bb%bd%e6%9c%89%e9%99%90%e5%85%ac%e5%8f%b8");
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
