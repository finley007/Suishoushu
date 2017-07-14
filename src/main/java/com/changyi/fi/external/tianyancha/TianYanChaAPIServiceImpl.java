package com.changyi.fi.external.tianyancha;

import com.changyi.fi.core.LogUtil;
import com.changyi.fi.core.RegexMatches;
import com.changyi.fi.core.http.HTTPCaller;
import com.changyi.fi.core.http.HTTPParser;
import com.changyi.fi.core.tool.Properties;
import com.changyi.fi.model.EnterprisePO;
import com.changyi.fi.util.FIConstants;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
    private static final String TIANYANCHA_GET_BIZ_REG_NUM_MATCHER = "tianyancha.get.biz.reg.num.matcher";
    private static final String TIANYANCHA_GET_ORG_CODE_MATCHER = "tianyancha.get.org.code.matcher";
    private static final String TIANYANCHA_GET_TAXPAYER_CODE_MATCHER = "tianyancha.get.taxpayer.code.matcher";
    private static final String TIANYANCHA_GET_INDUSTRY_MATCHER = "tianyancha.get.industry.matcher";
    private static final String TIANYANCHA_GET_BIZ_PERIOD_MATCHER = "tianyancha.get.biz.period.matcher";
    private static final String TIANYANCHA_GET_LEGAL_PERSON_MATCHER = "tianyancha.get.legal.person.matcher";
    private static final String TIANYANCHA_GET_LISTED_SECTION_MATCHER = "tianyancha.get.listed.section.matcher";
    private static final String TIANYANCHA_GET_CAPITAL_MATCHER = "tianyancha.get.capital.matcher";
    private static final String TIANYANCHA_GET_REG_AUTHORITY_MATCHER = "tianyancha.get.reg.authority.matcher";

    private static final String FIELD_CREDIT_CODE = "creditCode";
    private static final String FIELD_NAME = "name";
    private static final String FIELD_HREF = "href";

    private static final String DATE_PATTERN = "\\d{4}-\\d{2}-\\d{2}";
    private static final String NUMBER_PATTERN = "\\d+\\.?\\d+";

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
        HTTPParser parser = new HTTPParser(html);
        return createEnterprisePO(parser);
    }

    private EnterprisePO createEnterprisePO(HTTPParser parser) {
        Boolean isListed = this.checkIsListed(parser);
        String creditCode = parser.select(Properties.get(handleListed(isListed, TIANYANCHA_GET_CREDIT_CODE_MATCHER))).toString();
        List<String> matchs = RegexMatches.match(creditCode, NUMBER_PATTERN);
        if (matchs == null ||    matchs.size() < 0) {
            return null;
        }
        EnterprisePO po = new EnterprisePO();
        parser.setHandler(new StringResultHandler());
        po.setName(parser.select(Properties.get(TIANYANCHA_GET_NAME_MATCHER)).toString());
        po.setPhone(parser.select(Properties.get(TIANYANCHA_GET_PHONE_MATCHER)).toString());
        po.setCreditCode(creditCode);
        po.setAddress(parser.select(Properties.get(handleListed(isListed, TIANYANCHA_GET_ADDRESS_MATCHER))).toString());
        po.setBizRegNum(parser.select(Properties.get(handleListed(isListed, TIANYANCHA_GET_BIZ_REG_NUM_MATCHER))).toString());
        po.setOrgCode(parser.select(Properties.get(handleListed(isListed, TIANYANCHA_GET_ORG_CODE_MATCHER))).toString());
        po.setIndustry(parser.select(Properties.get(handleListed(isListed, TIANYANCHA_GET_INDUSTRY_MATCHER))).toString());
        po.setTaxpayerCode(parser.select(Properties.get(handleListed(isListed, TIANYANCHA_GET_TAXPAYER_CODE_MATCHER))).toString());
        po.setLegalPerson(parser.select(Properties.get(handleListed(isListed, TIANYANCHA_GET_LEGAL_PERSON_MATCHER))).toString());
        po.setRegAuthority(parser.select(Properties.get(handleListed(isListed, TIANYANCHA_GET_REG_AUTHORITY_MATCHER))).toString());
        po.setCreateBy(FIConstants.SYSTEM);
        po.setCreateTime(new Date());
        po.setModifyBy(FIConstants.SYSTEM);
        po.setModifyTime(new Date());
        setRegCapital(parser, po, isListed);
        setBizPeriod(parser, po, isListed);
        return po;
    }

    private void setBizPeriod(HTTPParser parser, EnterprisePO po, Boolean isListed) {
        List<String> bizPeriod = RegexMatches.match(parser.select(Properties.get(handleListed(isListed, TIANYANCHA_GET_BIZ_PERIOD_MATCHER))).toString(),
                DATE_PATTERN);
        if (bizPeriod != null && bizPeriod.size() > 0) {
            try {
                po.setBizPeriodStart(FIConstants.sdf.parse(bizPeriod.get(0)));
                po.setEstablishDate(FIConstants.sdf.parse(bizPeriod.get(0)));
                if (bizPeriod.size() > 1) {
                    po.setBizPeriodEnd(FIConstants.sdf.parse(bizPeriod.get(1)));
                }
            } catch (Exception e) {
                LogUtil.error(this.getClass(), "Parse bizPeriod field for EnterprisePO error: ", e);
            }
        }
    }

    private void setRegCapital(HTTPParser parser, EnterprisePO po, Boolean isListed) {
        String str = parser.select(Properties.get(handleListed(isListed, TIANYANCHA_GET_CAPITAL_MATCHER))).toString();
        List<String> capital = RegexMatches.match(str, NUMBER_PATTERN);
        if (capital != null && capital.size() > 0) {
            po.setRegCapital(BigDecimal.valueOf(Double.valueOf(capital.get(0))));
        }
    }

    //检查是否是上市公司，如果是需要做处理
    private Boolean checkIsListed(HTTPParser parser) {
        return parser.setHandler(new HTTPParser.ResultHandler<Boolean>() {
            public Boolean handleResult(Elements elems) {
                return elems != null && elems.size() > 0;
            }
        }).select(Properties.get(TIANYANCHA_GET_LISTED_SECTION_MATCHER));
    }

    private String handleListed(Boolean isListed, String prop) {
        if (isListed) {
            return prop + ".listed";
        } else {
            return prop;
        }
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
