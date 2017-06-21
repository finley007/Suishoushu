package com.changyi.fi.exception;

import com.changyi.fi.core.exception.BusinessException;

/**
 * Created by finley on 2/18/17.
 */
public class InvalidAccountException extends BusinessException {

    public InvalidAccountException(String msg) {
        super(msg);
    }

}
