package com.changyi.fi.core.config;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * ConfigManager Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Jul 5, 2017</pre>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/applicationContext.xml"})
public class ConfigManagerTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: getParameter(String name)
     */
    @Test
    public void testGetParameter() throws Exception {
//TODO: Test goes here...
        System.out.println(ConfigManager.getParameter("MERCHANT_VALIDATION_DISTANCE"));
    }


} 
