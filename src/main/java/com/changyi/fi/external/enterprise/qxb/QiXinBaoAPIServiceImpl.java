package com.changyi.fi.external.enterprise.qxb;

import com.changyi.fi.external.enterprise.ExternalEnterpriseAPIAbstractImpl;
import com.changyi.fi.external.enterprise.ExternalEnterpriseAPIService;
import com.changyi.fi.model.EnterprisePO;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.Map;

public class QiXinBaoAPIServiceImpl extends ExternalEnterpriseAPIAbstractImpl implements ExternalEnterpriseAPIService {


    public List<Map> matchEnterprise(String key) throws Exception {
        return null;
    }

    public EnterprisePO getEnterpriseByCode(String code) throws Exception {
        return null;
    }

    protected String login() throws Exception {
        return null;
    }

    protected int getTokenExpiredTime() {
        return 0;
    }

    public String getKey(String path) {
        String result = "";
        String key = "z|z|q|P|z|B|8|j|s|0|l|c|F|K|v|t|E|G|A|8|";
        String[] keys = key.split("\\|");
        if (StringUtils.isNotBlank(path)) {
            path = path.toLowerCase();
            path = path + path;
            for (int i = 0; i < path.length(); ++i) {
                int a = path.charAt(i) % keys.length;
                result += keys[a];
            }
        }
        return result;
    }

}
