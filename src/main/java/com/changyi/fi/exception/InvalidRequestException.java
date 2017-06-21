package com.changyi.fi.exception;

import com.changyi.fi.core.exception.BusinessException;

/**
 * Created by finley on 2/8/17.
 */
public class InvalidRequestException extends BusinessException {

    public InvalidRequestException(String msg) {
        super(msg);
    }
}
