package com.changyi.fi.core.job;

public class JobEnv {

    private int threadNum;

    public JobEnv(int threadNum) {
        this.threadNum = threadNum;
    }

    public int getThreadNum() {
        return threadNum;
    }

    public void setThreadNum(int threadNum) {
        this.threadNum = threadNum;
    }
}
