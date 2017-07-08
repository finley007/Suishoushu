package com.changyi.fi.core.http;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * HTTPParser Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Jul 8, 2017</pre>
 */
public class HTTPParserTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: select(String selector)
     */
    @Test
    public void testSelect() throws Exception {
//TODO: Test goes here...
        String url = "http://www.tianyancha.com/search?key=%E6%99%BA%E5%9C%A8%E5%BF%85%E4%BA%AB&checkFrom=searchBox";
        String response = new HTTPCaller(url).doGet();
        List<Map<String, String>> result = new HTTPParser(response).setHandler(new HTTPParser.ResultHandler<List<Map<String, String>>>() {
            public List<Map<String, String>> handleResult(Elements elems) {
                List result = new ArrayList();
                for (Element elem : elems) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("url", elem.attr("href"));
                    map.put("name", elem.child(0).text());
                    result.add(map);
                }
                return result;
            }
        }).select("body > div:nth-child(2) > div.top_container_new.b-c-gray > div > div > div.col-9.search-2017-2.pr10.pl0 > div.b-c-white.search_result_container > div > div.search_right_item > div.row.pb10 > div.col-xs-10.search_repadding2.f18 > a");
        for (Map map : result) {
            System.out.println(map);
        }
//        Elements elems = new HTTPParser(response).select("body > div:nth-child(2) > div.top_container_new.b-c-gray > div > div > div.col-9.search-2017-2.pr10.pl0 > div.b-c-white.search_result_container > div > div.search_right_item > div.row.pb10 > div.col-xs-10.search_repadding2.f18 > a");
//        System.out.println(elems.size());
//        for (Element elem : elems) {
//            System.out.println(elem);
//        }
    }


} 
