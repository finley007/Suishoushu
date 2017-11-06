package com.changyi.fi.job;

import com.changyi.fi.core.CtxProvider;
import com.changyi.fi.core.LogUtil;
import com.changyi.fi.core.job.Job;
import com.changyi.fi.dao.InvoiceDao;
import com.changyi.fi.external.enterprise.ExternalEnterpriseAPIService;
import com.changyi.fi.util.FIConstants;
import org.apache.logging.log4j.ThreadContext;

import java.util.List;
import java.util.Map;

public class EnterpriseSyncJob extends Job {

    private final static String INVOICE_DAO = "invoiceDao";

    private List<Map> enterpriseList;

    private String bean;

    private ExternalEnterpriseAPIService service;

    private InvoiceDao invoiceDao;

    public EnterpriseSyncJob(FIConstants.JobType type, List<Map> enterpriseList, String bean) {
        super(type);
        this.enterpriseList = enterpriseList;
        service = (ExternalEnterpriseAPIService) CtxProvider.getContext().getBean(bean);
        invoiceDao = (InvoiceDao) CtxProvider.getContext().getBean(INVOICE_DAO);
    }

    public void run() {
        //初始化后台日志
        ThreadContext.put(LogUtil.LOG_ROUTE_KEY, LogUtil.DAEMON_THREAD);
        LogUtil.info(this.getClass(), "Sync enterprise info");
        for (Map map : enterpriseList) {
            String creditCode = map.get(FIConstants.FIELD_CREDIT_CODE).toString();
            if (invoiceDao.getEnterpriseById(creditCode) != null) {
                continue;
            }
            try {
                service.getEnterpriseByCode(creditCode);
            } catch (Exception e) {
                LogUtil.error(this.getClass(), "Sync enterprise: " + creditCode + " error", e);
            }
        }
        ThreadContext.remove(LogUtil.LOG_ROUTE_KEY);
    }
}
