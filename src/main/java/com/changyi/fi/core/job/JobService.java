package com.changyi.fi.core.job;

public interface JobService {

    public void init(JobEnv env);

    public void addJob(Job job);

    public void shutdown();

}
