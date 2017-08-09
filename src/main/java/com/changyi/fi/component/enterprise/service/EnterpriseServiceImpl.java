package com.changyi.fi.component.enterprise.service;

import com.changyi.fi.component.enterprise.request.GetEnterpriseRequest;
import com.changyi.fi.component.enterprise.response.GetEnterpriseResponse;
import com.changyi.fi.component.enterprise.response.MatchEnterpriseResponse;
import com.changyi.fi.core.LogUtil;
import com.changyi.fi.core.annotation.Validate;
import com.changyi.fi.core.config.ConfigManager;
import com.changyi.fi.dao.InvoiceDao;
import com.changyi.fi.external.enterprise.ExternalEnterpriseAPIService;
import com.changyi.fi.model.EnterprisePO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by finley on 6/21/17.
 */
@Service("enterpriseService")
public class EnterpriseServiceImpl implements EnterpriseService {

    private static final String ENTERPRISE_EXTERNAL_SERVICE_TOGGLE = "ENTERPRISE_EXTERNAL_SERVICE_TOGGLE";
    private static final String ENTERPRISE_MATCH_RESULT_LENGTH = "ENTERPRISE_MATCH_RESULT_LENGTH";
    private static final String ENTERPRISE_EXTERNAL_SERVICE_IMPL_CLZ = "ENTERPRISE_EXTERNAL_SERVICE_IMPL_CLZ";

    private static final String DB_FIELD_CREDITCODE = "CREDITCODE";
    private static final String DB_FIELD_NAME = "NAME";

    private static final String FIELD_CREDITCODE = "creditCode";
    private static final String FIELD_NAME = "name";
    private static final String FIELD_IS_EXTERNAL = "isExternal";


    private ExternalEnterpriseAPIService enterpriseAPIService;

    private InvoiceDao invoiceDao;

    private ExternalEnterpriseAPIService getEnterpriseAPIService() throws Exception {
        if (enterpriseAPIService == null) {
            enterpriseAPIService = (ExternalEnterpriseAPIService)Class.forName(ConfigManager.getParameter(ENTERPRISE_EXTERNAL_SERVICE_IMPL_CLZ)).newInstance();
        }
        return enterpriseAPIService;
    }


    @Autowired(required = true)
    public void setInvoiceDao(InvoiceDao invoiceDao) {
        this.invoiceDao = invoiceDao;
    }

    public MatchEnterpriseResponse matchEnterprise(String key) throws Exception {
        LogUtil.info(this.getClass(), "Execute matchEnterprise service for key: " + key);
        //从内部获取
        List<Map> internalList = this.invoiceDao.matchEnterpriseList(key, ConfigManager.getIntegerParameter(ENTERPRISE_MATCH_RESULT_LENGTH, 20));
        List<Map> externalList = new ArrayList<Map>();
        //从外部获取企业信息开关
        if (ConfigManager.getBooleanParameter(ENTERPRISE_EXTERNAL_SERVICE_TOGGLE, false)) {
            //从外部获取
            externalList = getEnterpriseAPIService().matchEnterprise(key);
        }
        return new MatchEnterpriseResponse(combineResult(internalList, externalList));
    }

    private List combineResult(List<Map> internal, List<Map> external) {
        int count = ConfigManager.getIntegerParameter(ENTERPRISE_MATCH_RESULT_LENGTH, 20);
        List<Map> result = new ArrayList<Map>();
        if (internal.size() > 0) {
            Set<String> set = new HashSet<String>();
            for (Map map : internal) {
                if (map.get(DB_FIELD_NAME) != null && map.get(DB_FIELD_CREDITCODE) != null) {
                    String name = map.get(DB_FIELD_NAME).toString();
                    Map m = new HashMap();
                    m.put(FIELD_IS_EXTERNAL, "false");
                    m.put(FIELD_NAME, name);
                    m.put(FIELD_CREDITCODE, map.get(DB_FIELD_CREDITCODE).toString());
                    result.add(m);
                    set.add(name);
                }
            }
            count -= result.size();
            for (Map map : external) {
                if (!set.contains(map.get(FIELD_NAME).toString()) && count > 0) {
                    map.put(FIELD_IS_EXTERNAL, "true");
                    result.add(map);
                    count --;
                }
            }
        } else {
            for (Map map : external) {
                if (count > 0) {
                    map.put(FIELD_IS_EXTERNAL, "true");
                    result.add(map);
                    count--;
                }
            }
        }
        return result;
    }

    @Validate
    public GetEnterpriseResponse getEnterprise(GetEnterpriseRequest req) throws Exception {
        LogUtil.info(this.getClass(), "Execute getEnterprise service");
        if (!Boolean.valueOf(req.getIsExternal())) {
            return new GetEnterpriseResponse(this.invoiceDao.getEnterpriseById(req.getCreditCode()));
        } else {
            EnterprisePO po = this.getEnterpriseAPIService().getEnterpriseByCode(req.getCreditCode());
            if (po != null) {
                this.invoiceDao.insertEnterprise(po);
            }
            return new GetEnterpriseResponse(po);
        }
    }
}
