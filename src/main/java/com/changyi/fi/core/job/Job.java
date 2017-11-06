package com.changyi.fi.core.job;

import com.changyi.fi.util.FIConstants;

public abstract class Job implements Runnable {

    private FIConstants.JobType type;

    public Job(FIConstants.JobType type) {
        this.type = type;
    }

    public FIConstants.JobType getType() {
        return type;
    }

    public void setType(FIConstants.JobType type) {
        this.type = type;
    }
}
