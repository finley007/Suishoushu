package com.changyi.fi.exception;

import com.changyi.fi.core.exception.BusinessException;

/**
 * Created by finley on 2/14/17.
 */
public class AuthenticationFailedException extends BusinessException {

    public AuthenticationFailedException(String msg) {
        super(msg);
    }

}
