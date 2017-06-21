package com.changyi.fi.exception;

import com.changyi.fi.core.exception.BusinessException;

/**
 * Created by finley on 2/18/17.
 */
public class SystemErrorException extends BusinessException {

    public SystemErrorException(String msg) {
        super(msg);
    }

}
