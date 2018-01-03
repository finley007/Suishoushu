package com.changyi.fi.core.tool;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Distance Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Jan 3, 2018</pre>
 */
public class DistanceTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: caculate()
     */
    @Test
    public void testCaculate() throws Exception {
//TODO: Test goes here...
        Distance distance = new Distance(34.322049, 34.320585, 108.978996, 108.978845);
        System.out.println(distance.caculate());
    }


    /**
     * Method: degreesToRadians(double degrees)
     */
    @Test
    public void testDegreesToRadians() throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = Distance.getClass().getMethod("degreesToRadians", double.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

    /**
     * Method: HaverSin(double theta)
     */
    @Test
    public void testHaverSin() throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = Distance.getClass().getMethod("HaverSin", double.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

} 
