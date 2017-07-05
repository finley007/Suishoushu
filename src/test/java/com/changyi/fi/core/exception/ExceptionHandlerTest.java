package com.changyi.fi.core.exception;

import com.changyi.fi.exception.NullRequestException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/** 
* ExceptionHandler Tester. 
* 
* @author <Authors name> 
* @since <pre>Feb 22, 2017</pre> 
* @version 1.0 
*/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/applicationContext.xml"})
public class ExceptionHandlerTest { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: handle(Throwable t) 
* 
*/ 
@Test
public void testHandle() throws Exception {
    String res = ExceptionHandler.handle(new TestException("ERROR"));
    res = ExceptionHandler.handle(new NullRequestException("test"));
    System.out.println(res);
}

@Test
public void testMokito() throws Exception {
    TestException te = mock(TestException.class);
    when(te.getCode()).thenReturn("-1");
    when(te.getMessage()).thenReturn("ERROR");
    String res = ExceptionHandler.handle(te);
}

class TestException extends BusinessException {
    public TestException(String msg) {
        super(msg);
    }

    public String getCode() {
        return "-1";
    }
}


} 
