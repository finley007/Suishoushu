package com.changyi.fi.external.enterprise.qcc;

import com.changyi.fi.core.CommonUtil;
import com.changyi.fi.core.LogUtil;
import com.changyi.fi.core.Payload;
import com.changyi.fi.core.RegexMatches;
import com.changyi.fi.core.annotation.Timer;
import com.changyi.fi.core.http.HTTPCaller;
import com.changyi.fi.core.http.HTTPParser;
import com.changyi.fi.core.tool.Properties;
import com.changyi.fi.external.enterprise.ExternalEnterpriseAPIAbstractImpl;
import com.changyi.fi.external.enterprise.ExternalEnterpriseAPIService;
import com.changyi.fi.external.enterprise.account.IAccountConfig;
import com.changyi.fi.external.enterprise.qcc.response.QCCBankInfoResponse;
import com.changyi.fi.model.EnterprisePO;
import com.changyi.fi.util.FIConstants;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service("qiChaChaAPIService")
public class QiChaChaAPIServiceImpl extends ExternalEnterpriseAPIAbstractImpl implements ExternalEnterpriseAPIService {

    private static final String SOURCE_QCC = "qcc";

    private static final String QICHACHA_DOMAIN = ".qichacha.com";

    private static final String QICHACHA_COOKIE_KEY = "qichacha.cookie.key";
    private static final String QICHACHA_COOKIE_VALUE = "qichacha.cookie.value";

    private static final String QICHACHA_SEARCH_TEMPLATE = "qichacha.search.template";
    private static final String QICHACHA_DETAIL_TEMPLATE = "qichacha.detail.template";
    private static final String QICHACHA_BANK_TEMPLATE = "qichacha.bank.template";
    private static final String QICHACHA_SEARCH_NAME_MATCHER = "qichacha.search.name.matcher";
    private static final String QICHACHA_GET_NAME_MATCHER = "qichacha.get.name.matcher";
    private static final String QICHACHA_GET_CREDIT_CODE_MATCHER = "qichacha.get.credit.code.matcher";
    private static final String QICHACHA_GET_PHONE_MATCHER = "qichacha.get.phone.matcher";
    private static final String QICHACHA_GET_ADDRESS_MATCHER = "qichacha.get.address.matcher";
    private static final String QICHACHA_GET_BIZ_REG_NUM_MATCHER = "qichacha.get.biz.reg.num.matcher";
    private static final String QICHACHA_GET_ORG_CODE_MATCHER = "qichacha.get.org.code.matcher";
    private static final String QICHACHA_GET_TAXPAYER_CODE_MATCHER = "qichacha.get.taxpayer.code.matcher";
    private static final String QICHACHA_GET_INDUSTRY_MATCHER = "qichacha.get.industry.matcher";
    private static final String QICHACHA_GET_BIZ_PERIOD_MATCHER = "qichacha.get.biz.period.matcher";
    private static final String QICHACHA_GET_LEGAL_PERSON_MATCHER = "qichacha.get.legal.person.matcher";
    private static final String QICHACHA_GET_LISTED_SECTION_MATCHER = "qichacha.get.listed.section.matcher";
    private static final String QICHACHA_GET_CAPITAL_MATCHER = "qichacha.get.capital.matcher";
    private static final String QICHACHA_GET_REG_AUTHORITY_MATCHER = "qichacha.get.reg.authority.matcher";
    private static final String QICHACHA_GET_BANK_MATCHER = "qichacha.get.bank.matcher";
    private static final String QICHACHA_GET_ACCOUNT_MATCHER = "qichacha.get.account.matcher";

    //qcc无需登录
    protected String login(String username, String password) throws Exception {
        return null;
    }

    //qcc无需登录
    protected int getTokenExpiredTime() {
        return 0;
    }

    //qcc无需登录
    protected IAccountConfig getAccountConfig() {
        return null;
    }

    @Timer
    public List<Map> matchEnterprise(String key) throws Exception {
        LogUtil.info(this.getClass(), "Execute enterprise search service by calling QiChaCha API, key: " + key);
        String encodedKey = CommonUtil.urlEncode(key, FIConstants.DEFAULT_CHARSET);
        LogUtil.info(this.getClass(),"Encoded key: " + encodedKey);
        String url = HTTPCaller.createUrl(QICHACHA_SEARCH_TEMPLATE, new Object[]{encodedKey});
        LogUtil.info(this.getClass(), "QiChaCha API, url: " + url);
        String html = new HTTPCaller(url).setCookieStore(createCookieStore()).doGet();
        LogUtil.debug(this.getClass(), "QiChaCha match enterprise response: " + html);
        HTTPParser parser = new HTTPParser(html);
        return createEnterpriseList(parser, key);
    }

    private CookieStore createCookieStore() throws Exception {
        CookieStore cookieStore = new BasicCookieStore();
        String key = Properties.get(QICHACHA_COOKIE_KEY);
        String value = Properties.get(QICHACHA_COOKIE_VALUE);
        LogUtil.info(this.getClass(), "QCC cookie enabled key: " + key + " and value: " + value);
        BasicClientCookie cookie = new BasicClientCookie(key, value);
        cookie.setDomain(QICHACHA_DOMAIN);
        cookie.setPath("/");
        cookieStore.addCookie(cookie);
        return cookieStore;
    }

    private List<Map> createEnterpriseList(HTTPParser parser, String key) {
        List<Map> result = new ArrayList<Map>();
        List<Element> list = parser.select(Properties.get(QICHACHA_SEARCH_NAME_MATCHER));
        for (Element elem : list) {
            String creditCode = elem.attr("href").toString();
            String name = elem.text();
            if (isValidCreditCode(creditCode)
                    && isValidName(name, key)) {
                Map map = new HashMap();
                map.put(FIConstants.FIELD_CREDIT_CODE, creditCode);
                map.put(FIConstants.FIELD_NAME, name);
                map.put(getSourceKey(), getAPIKey());
                result.add(map);
            }
        }
        return result;
    }

    @Timer
    public EnterprisePO getEnterpriseByCode(String code) throws Exception {
        LogUtil.info(this.getClass(), "Execute get enterprise info service by calling QiChaCha API, code: " + code);
        String url = HTTPCaller.createUrl(QICHACHA_DETAIL_TEMPLATE, new Object[]{code});
        LogUtil.info(this.getClass(), "QiChaCha API, url: " + url);
        String html = new HTTPCaller(url).setCookieStore(this.createCookieStore()).doGet();
        HTTPParser parser = new HTTPParser(html);
        EnterprisePO po = createEnterprisePO(parser, url);
        updateEnterpriseBankInfo(po, code);
        saveEnterpriseInfo(po);
        return po;
    }

    private void updateEnterpriseBankInfo(EnterprisePO po, String code) throws Exception {
        LogUtil.info(this.getClass(), "Execute get enterprise bank info service by calling QiChaCha API, code: " + code);
        code = code.replaceAll("/firm_", "").replaceAll("\\.html", "");
        String url = HTTPCaller.createUrl(QICHACHA_BANK_TEMPLATE, new Object[]{code});
        LogUtil.info(this.getClass(), "QiChaCha API, url: " + url);
        String res = new HTTPCaller(url).setCookieStore(this.createCookieStore()).doGet();
        LogUtil.info(this.getClass(), "Match result: " + res);
        QCCBankInfoResponse response = new Payload(res).as(QCCBankInfoResponse.class);
        if (response != null && response.getData() != null) {
            if (StringUtils.isNotBlank(response.getData().get(QCCBankInfoResponse.FIELD_NAME))) {
                po.setName(response.getData().get(QCCBankInfoResponse.FIELD_NAME));
            }
            po.setBank(response.getData().get(QCCBankInfoResponse.FIELD_BANK));
            po.setBankAcct(response.getData().get(QCCBankInfoResponse.FIELD_BANK_ACCOUNT));
        }
    }

    private EnterprisePO createEnterprisePO(HTTPParser parser, String url) {
        EnterprisePO po = new EnterprisePO();
        parser.setHandler(new StringResultHandler());
        po.setName(parser.select(Properties.get(QICHACHA_GET_NAME_MATCHER)).toString());
        po.setPhone(parser.select(Properties.get(QICHACHA_GET_PHONE_MATCHER)).toString());
        po.setCreditCode(parser.select(Properties.get(QICHACHA_GET_CREDIT_CODE_MATCHER)).toString());
        po.setAddress(parser.select(Properties.get(QICHACHA_GET_ADDRESS_MATCHER)).toString());
        po.setBizRegNum(parser.select(Properties.get(QICHACHA_GET_BIZ_REG_NUM_MATCHER)).toString());
        po.setOrgCode(parser.select(Properties.get(QICHACHA_GET_ORG_CODE_MATCHER)).toString());
        po.setIndustry(parser.select(Properties.get(QICHACHA_GET_INDUSTRY_MATCHER)).toString());
//        po.setTaxpayerCode(parser.select(Properties.get(QICHACHA_GET_TAXPAYER_CODE_MATCHER)).toString());
        po.setLegalPerson(parser.select(Properties.get(QICHACHA_GET_LEGAL_PERSON_MATCHER)).toString());
        po.setRegAuthority(parser.select(Properties.get(QICHACHA_GET_REG_AUTHORITY_MATCHER)).toString());
        po.setCreateBy(FIConstants.SYSTEM);
        po.setCreateTime(new Date());
        po.setModifyBy(FIConstants.SYSTEM);
        po.setModifyTime(new Date());
        setRegCapital(parser, po);
        setBizPeriod(parser, po);
        po.setSourceUrl(url);
        return po;
    }

    private void setBizPeriod(HTTPParser parser, EnterprisePO po) {
        List<String> bizPeriod = RegexMatches.match(parser.select(Properties.get(QICHACHA_GET_BIZ_PERIOD_MATCHER)).toString(),
                FIConstants.DATE_PATTERN);
        if (bizPeriod != null && bizPeriod.size() > 0) {
            try {
                String strBizPeriod = bizPeriod.get(0);
                po.setBizPeriodStart(FIConstants.sdf.parse(strBizPeriod));
                po.setEstablishDate(FIConstants.sdf.parse(strBizPeriod));
                if (bizPeriod.size() > 1) {
                    po.setBizPeriodEnd(FIConstants.sdf.parse(bizPeriod.get(1)));
                }
            } catch (Exception e) {
                LogUtil.error(this.getClass(), "Parse bizPeriod field for EnterprisePO error: ", e);
            }
        }
    }

    private void setRegCapital(HTTPParser parser, EnterprisePO po) {
        String str = parser.select(Properties.get(QICHACHA_GET_CAPITAL_MATCHER).toString());
        List<String> capital = RegexMatches.match(str, FIConstants.NUMBER_PATTERN);
        if (capital != null && capital.size() > 0) {
            po.setRegCapital(BigDecimal.valueOf(Double.valueOf(capital.get(0))));
        }
    }

    public String getAPIKey() {
        return FIConstants.API_QICHACHA;
    }

}
