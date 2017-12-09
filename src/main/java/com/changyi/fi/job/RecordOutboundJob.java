package com.changyi.fi.job;

import com.changyi.fi.core.CtxProvider;
import com.changyi.fi.core.dao.SysDao;
import com.changyi.fi.core.job.Job;
import com.changyi.fi.core.model.SysOutboundPO;

public class RecordOutboundJob extends Job {

    private final static String SYS_DAO = "SysDao";
    private final static String RECORD_OUTBOUND = "RECORD_OUTBOUND";

    private SysDao sysDao;

    private SysOutboundPO po;

    public String getType() {
        return RECORD_OUTBOUND;
    }

    public RecordOutboundJob(SysOutboundPO po) {
        this.po = po;
        sysDao = (SysDao) CtxProvider.getContext().getBean(SYS_DAO);
    }

    public void run() {
        if (sysDao != null && po != null) {
            sysDao.insertSysOutbound(po);
        }
    }
}