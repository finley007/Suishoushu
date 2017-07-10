package com.changyi.fi.core;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.util.List;

/**
 * RegexMatches Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Jul 10, 2017</pre>
 */
public class RegexMatchesTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: match(String str, String pattern)
     */
    @Test
    public void testMatch() throws Exception {
//TODO: Test goes here...
        String str = "2007-08-14 至9999-09-09";
        String pattern = "\\d{4}-\\d{2}-\\d{2}";
        List result = RegexMatches.match(str, pattern);
        System.out.println(result.size());
        System.out.println(result.get(0));
        System.out.println(result.get(1));
    }


} 
