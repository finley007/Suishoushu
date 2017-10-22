package com.changyi.fi.core.tool;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.io.File;

/**
 * QRCodeUtils Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Oct 21, 2017</pre>
 */
public class QRCodeUtilsTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: createQRCode(String content)
     */
    @Test
    public void testCreateQRCode() throws Exception {
//TODO: Test goes here...
        Properties.setPropertiesConfiguration(new PropertiesConfiguration("/Users/finley/Finley/workspace/java/Suishoushu/src/test/resources/config.properties"));
        File file = QRCodeUtils.createQRCode("测试\n你好", "test");
        System.out.println(file.getAbsolutePath());
    }


} 
