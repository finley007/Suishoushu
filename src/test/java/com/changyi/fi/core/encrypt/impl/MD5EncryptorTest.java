package com.changyi.fi.core.encrypt.impl;

import com.changyi.fi.core.encrypt.EncryptManager;
import com.changyi.fi.util.FIConstants;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

/**
 * MD5Encryptor Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Oct 22, 2017</pre>
 */
public class MD5EncryptorTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: sign(String content)
     */
    @Test
    public void testSign() throws Exception {
//TODO: Test goes here...
        System.out.println(EncryptManager.getEncryptor(FIConstants.EncryptorAlgorithm.MD5).sign("2343434"));
    }


} 
