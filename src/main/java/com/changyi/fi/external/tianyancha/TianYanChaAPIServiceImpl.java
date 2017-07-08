package com.changyi.fi.external.tianyancha;

import com.changyi.fi.core.LogUtil;
import com.changyi.fi.core.http.HTTPCaller;
import com.changyi.fi.core.http.HTTPParser;
import com.changyi.fi.core.tool.Properties;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by finley on 7/8/17.
 */
@Service("tianYanChaAPIService")
public class TianYanChaAPIServiceImpl implements TianYanChaAPIService {

    private static final String TIANYANCHA_SEARCH_URL_TEMPLATE = "tianyancha.search.url.template";
    private static final String TIANYANCHA_SEARCH_MATCHER = "tianyancha.search.matcher";

    private static final String FIELD_CREDIT_CODE = "creditCode";
    private static final String FIELD_NAME = "name";
    private static final String FIELD_HREF = "href";

    public List<Map> matchEnterprise(String key) throws Exception {
        LogUtil.info(this.getClass(), "Execute enterprise search service by calling TianYanCha API, key: " + key);
        String url = createSearchUrl(key);
        LogUtil.info(this.getClass(), "TianYanCha API, url: " + url);
        String html = new HTTPCaller(url).doGet();
        String matcher = Properties.get(TIANYANCHA_SEARCH_MATCHER);
        LogUtil.info(this.getClass(), "Parser matcher: " + matcher);
        return new HTTPParser(html).setHandler(new HTTPParser.ResultHandler<List<Map<String, String>>>() {
            public List<Map<String, String>> handleResult(Elements elems) {
                List result = new ArrayList();
                for (Element elem : elems) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put(FIELD_CREDIT_CODE, getCode(elem.attr(FIELD_HREF)));
                    map.put(FIELD_NAME, elem.child(0).text());
                    result.add(map);
                }
                return result;
            }
        }).select(matcher);
    }

    private String getCode(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }

    private String createSearchUrl(String key) {
        String template = Properties.get(TIANYANCHA_SEARCH_URL_TEMPLATE);
        return MessageFormat.format(template, key);
    }

}
