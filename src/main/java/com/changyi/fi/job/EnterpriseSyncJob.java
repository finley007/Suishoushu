package com.changyi.fi.job;

import com.changyi.fi.core.CtxProvider;
import com.changyi.fi.core.LogUtil;
import com.changyi.fi.core.job.Job;
import com.changyi.fi.dao.InvoiceDao;
import com.changyi.fi.external.enterprise.ExternalEnterpriseAPIService;
import com.changyi.fi.util.FIConstants;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.ThreadContext;

import java.util.List;
import java.util.Map;

public class EnterpriseSyncJob extends Job {

    private final static String INVOICE_DAO = "invoiceDao";
    private final static String TIANYANCHA_DAO = "invoiceDao";

    private String key;

    private ExternalEnterpriseAPIService service;

    private InvoiceDao invoiceDao;

    public EnterpriseSyncJob(FIConstants.JobType type, String key) {
        super(type);
        this.key = key;
        //目前由于天眼查需要代理，用天眼查作为后台同步的接口
        service = (ExternalEnterpriseAPIService) CtxProvider.getContext().getBean(FIConstants.BEAN_TIANYANCHA_API_SERVICE);
        invoiceDao = (InvoiceDao) CtxProvider.getContext().getBean(INVOICE_DAO);
    }

    public void run() {
        //初始化后台日志
        ThreadContext.put(LogUtil.LOG_ROUTE_KEY, LogUtil.DAEMON_THREAD);
        LogUtil.info(this.getClass(), "Sync enterprise info");
        try {
            List<Map> enterpriseList = service.matchEnterprise(key);
            for (Map map : enterpriseList) {
                String name = map.get(FIConstants.FIELD_NAME).toString();
                String creditCode = map.get(FIConstants.FIELD_CREDIT_CODE).toString();
                if (StringUtils.isBlank(name)
                        || invoiceDao.countEnterpriseByName(name) > 0) {
                    continue;
                }
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
