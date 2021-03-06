package com.changyi.fi.component.enterprise.service;

import com.changyi.fi.component.enterprise.request.GetEnterpriseRequest;
import com.changyi.fi.component.enterprise.response.GetEnterpriseResponse;
import com.changyi.fi.component.enterprise.response.MatchEnterpriseResponse;
import com.changyi.fi.core.LogUtil;
import com.changyi.fi.core.annotation.Timer;
import com.changyi.fi.core.annotation.Validate;
import com.changyi.fi.core.config.ConfigDic;
import com.changyi.fi.core.config.ConfigManager;
import com.changyi.fi.core.job.JobManager;
import com.changyi.fi.core.maintain.MaintainManager;
import com.changyi.fi.dao.InvoiceDao;
import com.changyi.fi.exception.SystemErrorException;
import com.changyi.fi.external.enterprise.ExternalEnterpriseAPIService;
import com.changyi.fi.external.enterprise.manager.EnternalEnterpriseAPIManager;
import com.changyi.fi.job.EnterpriseSyncJob;
import com.changyi.fi.model.EnterprisePO;
import com.changyi.fi.util.FIConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by finley on 6/21/17.
 */
@Service("enterpriseService")
public class EnterpriseServiceImpl implements EnterpriseService {

    private static final String DB_FIELD_CREDITCODE = "CREDITCODE";
    private static final String DB_FIELD_NAME = "NAME";

    private static final String FIELD_CREDITCODE = "creditCode";
    private static final String FIELD_NAME = "name";
    private static final String FIELD_SOURCE = "source";

    private static final String SOURCE_INTERNAL = "internal";

    private InvoiceDao invoiceDao;

    @Autowired(required = true)
    public void setInvoiceDao(InvoiceDao invoiceDao) {
        this.invoiceDao = invoiceDao;
    }

    @Timer
    public MatchEnterpriseResponse matchEnterprise(String key) throws Exception {
        LogUtil.info(this.getClass(), "Execute matchEnterprise service for key: " + key);
        //从内部获取
        List<Map> internalList = queryEnterpriseInDB(key);
        //从外部获取企业信息开关
        List<Map> externalList = new ArrayList<Map>();
        if (ConfigManager.getBooleanParameter(ConfigDic.ENTERPRISE_EXTERNAL_SERVICE_TOGGLE, false)) {
            //从外部获取
            externalList = queryEnterpriseByExternalAPI(key);
        }
        //后台自动同步
        if (ConfigManager.getBooleanParameter(ConfigDic.SYNC_ENTERPRISE_WHEN_MATCH, false)) {
            JobManager.addJob(new EnterpriseSyncJob(key));
        }
        return new MatchEnterpriseResponse(combineResult(internalList, externalList, key));
    }

    private List<Map> queryEnterpriseInDB(String key) {
        List<Map> internalList = this.invoiceDao.matchEnterpriseList(key, ConfigManager.getIntegerParameter(ConfigDic.ENTERPRISE_MATCH_RESULT_LENGTH, 20));
        if (internalList.size() == 0) {
            LogUtil.info(this.getClass(), "No match enterprise for key: " + key + " in DB");
        }
        return internalList;
    }

    private List<Map> queryEnterpriseByExternalAPI(String key) throws Exception {
        List<Map> externalList = new ArrayList<Map>();
        ExternalEnterpriseAPIService enterpriseAPIService = EnternalEnterpriseAPIManager.getAPIImpl();
        if (enterpriseAPIService == null) {
            LogUtil.info(this.getClass(), "No external API available");
            return externalList;
        }
        try {
            LogUtil.info(this.getClass(), "Search key: " + key + " by using external API: " + enterpriseAPIService.getAPIKey());
            externalList = enterpriseAPIService.matchEnterprise(key);
        } catch (Exception e) {
            LogUtil.error(this.getClass(), "Error with external API: " + enterpriseAPIService.getAPIKey(), e);
//            EnternalEnterpriseAPIManager.disableAPI(enterpriseAPIService.getAPIKey());
            MaintainManager.criticalErrorNotify(FIConstants.NotifyMethod.Email, e);
        }
        if (externalList.size() == 0) {
            LogUtil.info(this.getClass(),"No match enterprise with key: " + key + " by using external API");
        }
        return externalList;
    }

    private List combineResult(List<Map> internal, List<Map> external, String key) {
        int count = ConfigManager.getIntegerParameter(ConfigDic.ENTERPRISE_MATCH_RESULT_LENGTH, 20);
        List<Map> result = new ArrayList<Map>();
        if (internal.size() > 0) {
            Set<String> set = new HashSet<String>();
            for (Map map : internal) {
                if (map.get(DB_FIELD_NAME) != null && map.get(DB_FIELD_CREDITCODE) != null) {
                    String name = map.get(DB_FIELD_NAME).toString().trim();
                    Map m = new HashMap();
                    m.put(FIELD_SOURCE, SOURCE_INTERNAL);
                    m.put(FIELD_NAME, name);
                    m.put(FIELD_CREDITCODE, map.get(DB_FIELD_CREDITCODE).toString());
                    result.add(m);
                    set.add(name);
                }
            }
            count -= result.size();
            for (Map map : external) {
                if (map.get(FIELD_NAME) != null && !set.contains(map.get(FIELD_NAME).toString().trim())
                        && map.get(FIELD_NAME).toString().contains(key) //有的接口不在名称中查询关键字，因此加这个过滤
                            && !"-".equals(map.get(FIELD_CREDITCODE).toString()) //启信宝有的统一社会编码不公开
                                && count > 0 ) {
                    result.add(map);
                    count --;
                }
            }
        } else {
            for (Map map : external) {
                if (count > 0) {
                    result.add(map);
                    count--;
                }
            }
        }
        return result;
    }

    @Validate
    @Timer
    public GetEnterpriseResponse getEnterprise(GetEnterpriseRequest req) throws Exception {
        LogUtil.info(this.getClass(), "Execute getEnterprise service");
        if (SOURCE_INTERNAL.equals(req.getSource())) {
            return new GetEnterpriseResponse(this.invoiceDao.getEnterpriseById(req.getCreditCode()));
        } else {
            LogUtil.info(this.getClass(), "Get enterprise info from api: " + req.getSource());
            ExternalEnterpriseAPIService service = EnternalEnterpriseAPIManager.getAPIImpl(req.getSource());
            EnterprisePO po = service.getEnterpriseByCode(req.getCreditCode());
            return new GetEnterpriseResponse(po);
        }
    }

    @Timer
    public MatchEnterpriseResponse heartbeat(String key, String api) throws Exception {
        LogUtil.info(this.getClass(), "Execute heartbeat service for key: " + key + " and API: " + api);
        ExternalEnterpriseAPIService apiImpl = EnternalEnterpriseAPIManager.getAPIImpl(api);
        if (apiImpl == null) {
            throw new SystemErrorException("Unknown API: " + api);
        }
        return new MatchEnterpriseResponse(apiImpl.matchEnterprise(key));
    }
}
