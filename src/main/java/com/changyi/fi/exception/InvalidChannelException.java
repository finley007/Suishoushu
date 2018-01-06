package com.changyi.fi.exception;

import com.changyi.fi.core.exception.BusinessException;

public class InvalidChannelException extends BusinessException {

    public InvalidChannelException(String msg) {
        super(msg);
    }
}
