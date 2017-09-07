package com.changyi.fi.core;

import com.changyi.fi.vo.Position;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

/**
 * CommonUtil Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Jul 5, 2017</pre>
 */
public class CommonUtilTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: getDistance(Position p1, Position p2)
     */
    @Test
    public void testGetDistance() throws Exception {
//TODO: Test goes here...
        Position p1 = new Position(4.5d, 5.6d);
        Position p2 = new Position(5.1d, 3.4d);
        System.out.println(CommonUtil.getDistance(p1, p2));
    }

    @Test
    public void testURLEncoder() throws Exception {
        System.out.println(CommonUtil.urlEncode("万达", "UTF-8"));
    }


} 
