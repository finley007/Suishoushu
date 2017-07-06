package com.changyi.fi.exception;

import com.changyi.fi.core.exception.BusinessException;

public class InvalidJsonException extends BusinessException {

    public InvalidJsonException(String msg) { super(msg); };
}
