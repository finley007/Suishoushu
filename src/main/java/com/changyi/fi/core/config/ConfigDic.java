package com.changyi.fi.core.config;

import com.changyi.fi.core.ConfigService;
import com.changyi.fi.core.CtxProvider;
import com.changyi.fi.core.LogUtil;
import com.changyi.fi.core.tool.Dictionary;
import com.changyi.fi.vo.AccountPair;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigDic implements Dictionary<String> {

    public static final String ENTERPRISE_EXTERNAL_SERVICE_TOGGLE = "ENTERPRISE_EXTERNAL_SERVICE_TOGGLE";
    public static final String ENTERPRISE_MATCH_RESULT_LENGTH = "ENTERPRISE_MATCH_RESULT_LENGTH";
    public static final String ENTERPRISE_EXTERNAL_SERVICE_IMPL = "ENTERPRISE_EXTERNAL_SERVICE_IMPL";
    public static final String SYNC_ENTERPRISE_WHEN_MATCH = "SYNC_ENTERPRISE_WHEN_MATCH";
    public static final String HTTP_PROXY_TOGGLE = "HTTP_PROXY_TOGGLE";
    public static final String HTTP_TIMEOUT = "HTTP_TIMEOUT";
    public static final String MERCHANT_VALID_DISTANCE = "MERCHANT_VALID_DISTANCE";
    public static final String MERCHANT_VALIDATION_TOGGLE = "MERCHANT_VALIDATION_TOGGLE";
    public static final String JOB_THREAD_POOL_SIZE = "JOB_THREAD_POOL_SIZE";
    public static final String QXB_ACCOUNT_LIST = "QXB_ACCOUNT_LIST";
    public static final String TYC_ACCOUNT_LIST = "TYC_ACCOUNT_LIST";
    public static final String QXB_ACCOUNT_USE_TIME = "QXB_ACCOUNT_USE_TIME";
    public static final String TYC_ACCOUNT_USE_TIME = "TYC_ACCOUNT_USE_TIME";
    public static final String LOG_LEVEL = "LOG_LEVEL";


    public static final String NAME = "config";

    private static final String FIELD_CODE = "code";
    private static final String FIELD_VALUE = "value";

    private List<AccountPair> qxbAccountList;

    private List<AccountPair> tycAccountList;

    public ConfigDic() {
        refresh();
    }

    private Map<String, String> cfgDic_ = new HashMap<String, String>();

    public String get(String key) {
        String value = cfgDic_.get(key);
        return value == null ? "" : value;
    }

    public boolean conatains(String key) {
        String value = cfgDic_.get(key);
        return StringUtils.isNotBlank(value);
    }

    public void add(String key, String value) {

    }

    public void remove(String key) {

    }

    public void removeAll() {

    }

    public List<String> listKeys() {
        return null;
    }

    public List<Map<String, String>> listAll() {
        return null;
    }

    public void refresh() {
        ConfigService configService = CtxProvider.getContext().getBean(ConfigService.class);
        List<Map> list = configService.getSysParameter();
        cfgDic_ = new HashMap<String, String>();
        for (Map map : list) {
            cfgDic_.put(map.get(FIELD_CODE).toString(), map.get(FIELD_VALUE).toString());
        }
        this.qxbAccountList = null;
        this.tycAccountList = null;
        getQXBAccountList();
        getTYCAccountList();
    }

    public List<AccountPair> getQXBAccountList() {
        if (this.qxbAccountList == null) {
            this.qxbAccountList = initAccountList(QXB_ACCOUNT_LIST);
        }
        return this.qxbAccountList;
    }

    public List<AccountPair> getTYCAccountList() {
        if (this.tycAccountList == null) {
            this.tycAccountList = initAccountList(TYC_ACCOUNT_LIST);
        }
        return this.tycAccountList;
    }

    public List<AccountPair> initAccountList(String key) {
        List<AccountPair> result = new ArrayList<AccountPair>();
        String accts = this.get(key);
        LogUtil.info(this.getClass(), "Init account list for: " + key);
        if (StringUtils.isNotBlank(accts)) {
            String[] acctPaires = accts.split("\\|");
            for (int i = 0; i < acctPaires.length; i++) {
                String[] pair = acctPaires[i].split("/");
                if (pair.length > 1) {
                    result.add(new AccountPair(pair[0], pair[1]));
                } else {
                    LogUtil.warn(this.getClass(), "Invalid account format: " + acctPaires[i]);
                }
            }
            LogUtil.info(this.getClass(), result.size() + " accounts are available for: " + key);
        } else {
            LogUtil.warn(this.getClass(), "No available account for: " + key);
        }
        return result;
    }

}
