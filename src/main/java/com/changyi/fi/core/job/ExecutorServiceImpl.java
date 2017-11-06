package com.changyi.fi.core.job;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorServiceImpl implements JobService {

    private ExecutorService service;

    public void init(JobEnv env) {
        this.service = Executors.newFixedThreadPool(env.getThreadNum());
    }

    public void addJob(Job job) {
        this.service.execute(job);
    }

    public void shutdown() {
        if (this.service != null) {
            this.service.shutdown();
        }
    }
}
