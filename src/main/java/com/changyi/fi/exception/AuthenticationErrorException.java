package com.changyi.fi.exception;

import com.changyi.fi.core.exception.SystemException;

/**
 * Created by finley on 2/14/17.
 */
public class AuthenticationErrorException extends SystemException {

    public AuthenticationErrorException(Throwable t, String msg) {
        super(t, msg);
    }

}
