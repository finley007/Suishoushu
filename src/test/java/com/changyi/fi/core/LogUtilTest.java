package com.changyi.fi.core;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.rmi.runtime.Log;

import java.io.File;
import java.io.FileInputStream;
import java.util.Collection;

/**
 * LogUtil Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Nov 6, 2017</pre>
 */
public class LogUtilTest {

    @Before
    public void before() throws Exception {
        File config = new File("/Users/finley/Finley/workspace/java/Suishoushu/src/test/resources/log4j2.xml");
        ConfigurationSource source = new ConfigurationSource(new FileInputStream(config), config);
        Configurator.initialize(null, source);
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: info(Class clz, String info)
     */
    @Test
    public void testInfoForClzInfo() throws Exception {
//TODO: Test goes here...
//        ThreadContext.put(LogUtil.LOG_ROUTE_KEY, LogUtil.DAEMON_THREAD);
        Logger logger  =  LoggerFactory.getLogger(this.getClass());
        logger.debug("debug");
        logger.info("test");
        Collection<org.apache.logging.log4j.core.Logger> notCurrentLoggerCollection = org.apache.logging.log4j.core.LoggerContext.getContext(false).getLoggers();
        Collection<org.apache.logging.log4j.core.Logger> currentLoggerCollection = org.apache.logging.log4j.core.LoggerContext.getContext().getLoggers();
        Collection<org.apache.logging.log4j.core.Logger> loggerCollection = notCurrentLoggerCollection;
        loggerCollection.addAll(currentLoggerCollection);
        for (org.apache.logging.log4j.core.Logger log :loggerCollection){
            log.setLevel(org.apache.logging.log4j.Level.toLevel("debug"));
        }
        logger.debug("debug");
        logger.info("test");
    }

    /**
     * Method: info(Class clz, String format, Object para)
     */
    @Test
    public void testInfoForClzFormatPara() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: debug(Class clz, String info)
     */
    @Test
    public void testDebugForClzInfo() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: debug(Class clz, String format, Object para)
     */
    @Test
    public void testDebugForClzFormatPara() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: error(Class clz, String info, Throwable t)
     */
    @Test
    public void testError() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: warn(Class clz, String info)
     */
    @Test
    public void testWarn() throws Exception {
//TODO: Test goes here... 
    }


    /**
     * Method: getClassInfo(Class clz, String info)
     */
    @Test
    public void testGetClassInfo() throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = LogUtil.getClass().getMethod("getClassInfo", Class.class, String.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

} 
