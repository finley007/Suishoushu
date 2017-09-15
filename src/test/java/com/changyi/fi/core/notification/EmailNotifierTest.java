package com.changyi.fi.core.notification;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

/**
 * EmailNotifier Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Sep 15, 2017</pre>
 */
public class EmailNotifierTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: getTgtEmail()
     */
    @Test
    public void testGetTgtEmail() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: setTgtEmail(String tgtEmail)
     */
    @Test
    public void testSetTgtEmail() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: Notify(String title, String message)
     */
    @Test
    public void testNotify() throws Exception {
//TODO: Test goes here...
        INotifier notifier = new EmailNotifier("finley007@163.com");
        notifier.Notify("测试", "这是一封测试邮件");
    }


} 
