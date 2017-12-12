package com.changyi.fi.job;

import com.changyi.fi.core.LogUtil;
import com.changyi.fi.core.job.Job;
import org.apache.logging.log4j.ThreadContext;

public class DBOperatorJob extends Job {

    private final static String DB_OPERATOR = "DB_OPERATOR";

    private IDBOperator operator;

    public String getType() {
        return DB_OPERATOR;
    }

    public DBOperatorJob(IDBOperator operator) {
        this.operator = operator;
    }

    public void run() {
        LogUtil.intSquence();
        ThreadContext.put(LogUtil.LOG_ROUTE_KEY, LogUtil.DAEMON_THREAD);
        if (operator != null) {
            LogUtil.info(this.getClass(), "Execute DB operator: " + this.operator.getClass());
            try {
                this.operator.execute();
            } catch (Exception e) {
                LogUtil.error(this.getClass(), "AExecute DB operator error: ", e);
            }
        }
    }

    public static interface IDBOperator {
        public void execute();
    }
}
