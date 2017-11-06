package com.changyi.fi.external.enterprise.qxb;

import com.changyi.fi.core.CommonUtil;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.reflect.InvocationTargetException;
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
        System.out.println(apiService.matchEnterprise("苏州畅移"));
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
        System.out.println(apiService.login());
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
