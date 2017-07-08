package com.changyi.fi.external.tianyancha;

import com.changyi.fi.core.LogUtil;
import com.changyi.fi.core.http.HTTPCaller;
import com.changyi.fi.core.http.HTTPParser;
import com.changyi.fi.core.tool.Properties;
import com.changyi.fi.model.EnterprisePO;
import com.changyi.fi.util.FIConstants;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.*;

/**
 * Created by finley on 7/8/17.
 */
@Service("tianYanChaAPIService")
public class TianYanChaAPIServiceImpl implements TianYanChaAPIService {

    private static final String TIANYANCHA_SEARCH_URL_TEMPLATE = "tianyancha.search.url.template";
    private static final String TIANYANCHA_SEARCH_GET_TEMPLATE = "tianyancha.get.url.template";
    private static final String TIANYANCHA_SEARCH_MATCHER = "tianyancha.search.matcher";
    private static final String TIANYANCHA_GET_NAME_MATCHER = "tianyancha.get.name.matcher";
    private static final String TIANYANCHA_GET_CREDIT_CODE_MATCHER = "tianyancha.get.credit.code.matcher";
    private static final String TIANYANCHA_GET_PHONE_MATCHER = "tianyancha.get.phone.matcher";
    private static final String TIANYANCHA_GET_ADDRESS_MATCHER = "tianyancha.get.address.matcher";

    private static final String FIELD_CREDIT_CODE = "creditCode";
    private static final String FIELD_NAME = "name";
    private static final String FIELD_HREF = "href";

    public List<Map> matchEnterprise(String key) throws Exception {
        LogUtil.info(this.getClass(), "Execute enterprise search service by calling TianYanCha API, key: " + key);
        String url = HTTPCaller.createUrl(TIANYANCHA_SEARCH_URL_TEMPLATE, new Object[]{key});
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

    public EnterprisePO getEnterpriseByCode(String code) throws Exception {
        LogUtil.info(this.getClass(), "Execute get enterprise info service by calling TianYanCha API, code: " + code);
        String url = HTTPCaller.createUrl(TIANYANCHA_SEARCH_GET_TEMPLATE, new Object[]{code});
        LogUtil.info(this.getClass(), "TianYanCha API, url: " + url);
        String html = new HTTPCaller(url).doGet();
        HTTPParser parser = new HTTPParser(html).setHandler(new StringResultHandler());
        return createEnterprisePO(parser);
    }

    private EnterprisePO createEnterprisePO(HTTPParser parser) {
        EnterprisePO po = new EnterprisePO();
        po.setName(parser.select(Properties.get(TIANYANCHA_GET_NAME_MATCHER)).toString());
        po.setCreditCode(parser.select(Properties.get(TIANYANCHA_GET_CREDIT_CODE_MATCHER)).toString());
        po.setPhone(parser.select(Properties.get(TIANYANCHA_GET_PHONE_MATCHER)).toString());
        po.setAddress(parser.select(Properties.get(TIANYANCHA_GET_ADDRESS_MATCHER)).toString());
        po.setCreateBy(FIConstants.SYSTEM);
        po.setCreateTime(new Date());
        po.setModifyBy(FIConstants.SYSTEM);
        po.setModifyTime(new Date());
        return po;
    }

    private class StringResultHandler implements HTTPParser.ResultHandler<String> {
        public String handleResult(Elements elems) {
            if (elems != null && elems.size() > 0) {
                return elems.get(0).text();
            } else {
                return "";
            }
        }
    }

}
