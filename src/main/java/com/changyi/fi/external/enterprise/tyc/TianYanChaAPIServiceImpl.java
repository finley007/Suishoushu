package com.changyi.fi.external.enterprise.tyc;

import com.changyi.fi.core.LogUtil;
import com.changyi.fi.core.Payload;
import com.changyi.fi.core.RegexMatches;
import com.changyi.fi.core.annotation.Timer;
import com.changyi.fi.core.exception.SystemException;
import com.changyi.fi.core.http.HTTPCaller;
import com.changyi.fi.core.http.HTTPParser;
import com.changyi.fi.core.tool.Properties;
import com.changyi.fi.external.enterprise.ExternalEnterpriseAPIAbstractImpl;
import com.changyi.fi.external.enterprise.ExternalEnterpriseAPIService;
import com.changyi.fi.external.enterprise.tyc.request.TYCLoginRequest;
import com.changyi.fi.external.enterprise.tyc.response.TYCLoginResponse;
import com.changyi.fi.external.enterprise.tyc.response.TYCMatchResponse;
import com.changyi.fi.model.EnterprisePO;
import com.changyi.fi.util.FIConstants;
import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by finley on 7/8/17.
 */
@Service("tianYanChaAPIService")
public class TianYanChaAPIServiceImpl extends ExternalEnterpriseAPIAbstractImpl implements ExternalEnterpriseAPIService {

    private static final String SOURCE_TYC = "tyc";

    private static final String TIANYANCHA_DOMAIN = ".tianyancha.com";

    private static final String COOKIE_AUTH_TOKEN = "auth_token";

    private static final String TINAYANCHA_USERNAME = "tianyancha.username";
    private static final String TINAYANCHA_PASSWORD = "tianyancha.password";

    private static final String TIANYANCHA_LOGIN_TEMPLATE = "tianyancha.login.template";
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


    private static final String FIELD_HREF = "href";
    private static final String FIELD_TOKEN = "token";

    private static final int SECONDS_FOR_ONE_WEEK = 604800;

    protected int getTokenExpiredTime() {
        return SECONDS_FOR_ONE_WEEK;
    }

    public String getAPIKey() {
        return SOURCE_TYC;
    }

    @Timer
    public List<Map> matchEnterprise(String key) throws Exception {
        LogUtil.info(this.getClass(), "Execute enterprise search service by calling TianYanCha API, key: " + key);
        String url = HTTPCaller.createUrl(TIANYANCHA_SEARCH_URL_TEMPLATE, new Object[]{key});
        LogUtil.info(this.getClass(), "TianYanCha API, url: " + url);
        String json = new HTTPCaller(url).setCookieStore(createCookieStore()).enableProxy().doGet();
        LogUtil.info(this.getClass(), "Match result: " + json);
        TYCMatchResponse response = new Payload(json).as(TYCMatchResponse.class);
        List result = new ArrayList();
        if (response != null && response.getData() != null && response.getData().size() > 0) {
            for (Map<String, String> m : response.getData()) {
                Map<String, String> map = new HashMap<String, String>();
                String creditCode = m.get(TYCMatchResponse.FIELD_ID);
                if (this.isValidCreditCode(creditCode)) {
                    map.put(FIConstants.FIELD_CREDIT_CODE, creditCode);
                    map.put(FIConstants.FIELD_NAME, handleName(m.get(TYCMatchResponse.FIELD_NAME)));
                    map.put(getSourceKey(), getAPIKey());
                    result.add(map);
                }
            }
        }
        return result;
    }

    private CookieStore createCookieStore() throws Exception {
        String token = this.getToken(REDIS_TYC_SESSION_TOKEN);
        CookieStore cookieStore = new BasicCookieStore();
        BasicClientCookie cookie = new BasicClientCookie(COOKIE_AUTH_TOKEN, token);
        cookie.setDomain(TIANYANCHA_DOMAIN);
        cookie.setPath("/");
        cookieStore.addCookie(cookie);
        return cookieStore;
    }

    private String getCode(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }

    @Timer
    public EnterprisePO getEnterpriseByCode(String code) throws Exception {
        LogUtil.info(this.getClass(), "Execute get enterprise info service by calling TianYanCha API, code: " + code);
        String url = HTTPCaller.createUrl(TIANYANCHA_SEARCH_GET_TEMPLATE, new Object[]{code});
        LogUtil.info(this.getClass(), "TianYanCha API, url: " + url);
        String html = new HTTPCaller(url).setCookieStore(createCookieStore()).enableProxy().doGet();
        HTTPParser parser = new HTTPParser(html);
        EnterprisePO po = createEnterprisePO(parser, url);
        saveEnterpriseInfo(po);
        return po;
    }

    private EnterprisePO createEnterprisePO(HTTPParser parser, String url) {
        Boolean isListed = this.checkIsListed(parser);
        Elements ccodeElems = parser.select(Properties.get(handleListed(isListed, TIANYANCHA_GET_CREDIT_CODE_MATCHER)));
        String creditCode = "";
        if (ccodeElems != null && ccodeElems.size() > 0) {
            creditCode  = ccodeElems.get(0).text();
        }
        List<String> matchs = RegexMatches.match(creditCode, FIConstants.NUMBER_PATTERN);
        if (matchs == null || matchs.size() <= 0) {
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
        po.setSourceUrl(url);
        return po;
    }

    private String handleName(String name) {
        return name.replaceAll("<em>([\\u4e00-\\u9fa5]{1,})</em>", "$1");
    }

    private void setBizPeriod(HTTPParser parser, EnterprisePO po, Boolean isListed) {
        List<String> bizPeriod = RegexMatches.match(parser.select(Properties.get(handleListed(isListed, TIANYANCHA_GET_BIZ_PERIOD_MATCHER))).toString(),
                FIConstants.DATE_PATTERN);
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
        List<String> capital = RegexMatches.match(str, FIConstants.NUMBER_PATTERN);
        if (capital != null && capital.size() > 0) {
            po.setRegCapital(BigDecimal.valueOf(Double.valueOf(capital.get(0))));
        }
    }

    //检查是否是上市公司，如果是需要做处理
    private Boolean checkIsListed(HTTPParser parser) {
//        return parser.setHandler(new HTTPParser.ResultHandler<Boolean>() {
//            public Boolean handleResult(Elements elems) {
//                return elems != null && elems.size() > 0;
//            }
//        }).select(Properties.get(TIANYANCHA_GET_LISTED_SECTION_MATCHER));
        return false;
    }

    private String handleListed(Boolean isListed, String prop) {
        if (isListed) {
            return prop + ".listed";
        } else {
            return prop;
        }
    }

    public String login() throws Exception {
        String url = HTTPCaller.createUrl(TIANYANCHA_LOGIN_TEMPLATE, new Object[]{});
        LogUtil.info(this.getClass(), "Login TianYanCha: " + url);
        String request = createLoginRequest();
        LogUtil.debug(this.getClass(), "Login request: " + request);
        String res = new HTTPCaller(url).enableProxy().setTimeout(10000).doPost(request);
        TYCLoginResponse response = new Payload(res).as(TYCLoginResponse.class);
        if (!FIConstants.OK.equals(response.getState())) {
            throw new SystemException("");
        }
        return response.getData().get(FIELD_TOKEN);
    }

    private String createLoginRequest() {
        String username = Properties.get(TINAYANCHA_USERNAME);
        String password = Properties.get(TINAYANCHA_PASSWORD);
        TYCLoginRequest request = new TYCLoginRequest(username, password);
        return new Payload(request).from(TYCLoginRequest.class);
    }


}
