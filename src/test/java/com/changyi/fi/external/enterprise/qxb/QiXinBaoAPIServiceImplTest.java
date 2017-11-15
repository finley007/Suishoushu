package com.changyi.fi.external.enterprise.qxb;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.reflect.Method;
import java.util.regex.Pattern;

/**
 * QiXinBaoAPIServiceImpl Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Sep 1, 2017</pre>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/applicationContext.xml"})
public class QiXinBaoAPIServiceImplTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: matchEnterprise(String key)
     */
    @Test
    public void testMatchEnterprise() throws Exception {
//TODO: Test goes here...
        QiXinBaoAPIServiceImpl apiService = new QiXinBaoAPIServiceImpl();
        System.out.println(apiService.matchEnterprise("万达"));
    }

    /**
     * Method: getEnterpriseByCode(String code)
     */
    @Test
    public void testGetEnterpriseByCode() throws Exception {
//TODO: Test goes here...
        QiXinBaoAPIServiceImpl apiService = new QiXinBaoAPIServiceImpl();
        System.out.println(apiService.getEnterpriseByCode("9eda1ceb-4d50-4b02-9ef0-ad1437d24f75"));
    }

    /**
     * Method: login()
     */
    @Test
    public void testLogin() throws Exception {
//TODO: Test goes here...
        QiXinBaoAPIServiceImpl apiService = new QiXinBaoAPIServiceImpl();
        System.out.println(apiService.login("13609248782", "19830310007"));
    }

    /**
     * Method: getTokenExpiredTime()
     */
    @Test
    public void testGetTokenExpiredTime() throws Exception {
//TODO: Test goes here... 
    }


    /**
     * Method: getKey(String path)
     */
    @Test
    public void testGetKey() throws Exception {
//TODO: Test goes here... 
        try {
            QiXinBaoAPIServiceImpl api = new QiXinBaoAPIServiceImpl();
            Method method = api.getClass().getDeclaredMethod("getKey", String.class);
            method.setAccessible(true);
            //"jGFBjtzGv8zjtGPPztEBclPjzzzGGjGFBjtzGv8zjtGPPztEBclPjzzzGG"
            System.out.println(method.invoke(api, "/api/user/login"));
            System.out.println(method.invoke(api, "/api/search/suggestion?key=%E4%B8%87%E8%BE%BE"));
            System.out.println(method.invoke(api, "/api/search/suggestion?key=%e5%a4%a7%e8%bf%9e%e4%b8%87%e8%be%be%e9%9b%86%e5%9b%a2%e8%82%a1%e4%bb%bd%e6%9c%89%e9%99%90%e5%85%ac%e5%8f%b8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testIsValidCreditCode() throws Exception {
        try {
            Pattern pattern = Pattern.compile("-");
            if (pattern.matcher("1231234-12312").matches()) {
                System.out.println("yes");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

} 
