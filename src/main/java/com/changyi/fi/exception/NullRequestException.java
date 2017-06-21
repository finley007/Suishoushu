package com.changyi.fi.exception;

import com.changyi.fi.core.exception.BusinessException;

/**
 * Created by finley on 2/8/17.
 */
public class NullRequestException extends BusinessException {

    public NullRequestException(String msg) {
        super(msg);
    }

}
