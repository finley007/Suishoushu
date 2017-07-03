package com.changyi.fi.core;

public abstract class BaseVO {

    public String toJson() {
        return new Payload(this).from(this.getClass());
    }

}
