package com.changyi.fi.core.job;

import com.changyi.fi.core.LogUtil;
import com.changyi.fi.core.config.ConfigDic;
import com.changyi.fi.core.config.ConfigManager;

public class JobManager {

    private static JobService jobService;

    public static void init() {
        LogUtil.info(JobManager.class, "Init job service by impl: ExecutorService");
        try {
            jobService = new ExecutorServiceImpl();
            int threadNum = ConfigManager.getIntegerParameter(ConfigDic.JOB_THREAD_POOL_SIZE, 1);
            jobService.init(new JobEnv(threadNum));
        } catch (Exception e) {
            LogUtil.error(JobManager.class, "Init job service error", e);
        }
    }

    public static void shutdown() {
        LogUtil.info(JobManager.class, "Shut down job service.");
        if (jobService != null) {
            jobService.shutdown();
        }
    }

    public static void addJob(Job job) {
        LogUtil.info(JobManager.class, "Add new job: " + job.getType().getValue());
        if (jobService != null) {
            jobService.addJob(job);
        } else {
            LogUtil.warn(JobManager.class, "Job service error, job will be ignored");
        }
    }
}
