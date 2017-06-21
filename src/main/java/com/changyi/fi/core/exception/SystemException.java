package com.changyi.fi.core.exception;

/**
 * Created by finley on 2/8/17.
 */
public class SystemException extends BaseException {

    public SystemException(Throwable t, String msg) {
        super(msg);
        this.initCause(t);
    }

}
