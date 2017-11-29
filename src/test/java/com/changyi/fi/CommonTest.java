package com.changyi.fi;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CommonTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void testRegexReplace() throws Exception {
        //TODO: Test goes here...
        String testStr = "123a你好bm测试121312MN123123\t很好";
        System.out.println(testStr.replaceAll("([A-Za-z])", "$1\n"));
    }
}
