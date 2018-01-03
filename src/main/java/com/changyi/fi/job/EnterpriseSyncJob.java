package com.changyi.fi.job;

import com.changyi.fi.core.CtxProvider;
import com.changyi.fi.core.LogUtil;
import com.changyi.fi.core.config.ConfigManager;
import com.changyi.fi.core.job.Job;
import com.changyi.fi.dao.InvoiceDao;
import com.changyi.fi.external.enterprise.ExternalEnterpriseAPIService;
import com.changyi.fi.util.FIConstants;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.ThreadContext;

import java.util.List;
import java.util.Map;

import static com.changyi.fi.core.config.ConfigDic.SYNC_ENTERPRISE_BEAN;

public class EnterpriseSyncJob extends Job {

    private final static String INVOICE_DAO = "invoiceDao";
    private final static String TIANYANCHA_DAO = "invoiceDao";

    private final static String ENTERPRISE_SYNC = "ENTERPRISE_SYNC";

    private String key;

    public String getType() {
        return ENTERPRISE_SYNC;
    }

    private ExternalEnterpriseAPIService service;

    private InvoiceDao invoiceDao;

    public EnterpriseSyncJob(String key) {
        this.key = key;
        //目前由于天眼查需要代理，用企查查作为后台同步的接口
        service = (ExternalEnterpriseAPIService) CtxProvider.getContext().getBean(ConfigManager.getParameter(SYNC_ENTERPRISE_BEAN));
        invoiceDao = (InvoiceDao) CtxProvider.getContext().getBean(INVOICE_DAO);
    }

    public void run() {
        //初始化后台日志
        LogUtil.intSquence();
        ThreadContext.put(LogUtil.LOG_ROUTE_KEY, LogUtil.DAEMON_THREAD);
        LogUtil.info(this.getClass(), "Sync enterprise info");
        try {
            List<Map> enterpriseList = service.matchEnterprise(key);
            for (Map map : enterpriseList) {
                String name = map.get(FIConstants.FIELD_NAME).toString();
                String creditCode = map.get(FIConstants.FIELD_CREDIT_CODE).toString();
//                if (StringUtils.isBlank(name)
//                        || invoiceDao.countEnterpriseByName(name) > 0) {
//                    continue;
//                }
                try {
                    service.getEnterpriseByCode(creditCode);
                } catch (Exception e) {
                    LogUtil.error(this.getClass(), "Sync enterprise: " + creditCode + " error", e);
                }
            }
        } catch (Exception e) {
            LogUtil.error(this.getClass(), "Sync enterprise: error", e);
        }
        ThreadContext.remove(LogUtil.LOG_ROUTE_KEY);
    }

}
