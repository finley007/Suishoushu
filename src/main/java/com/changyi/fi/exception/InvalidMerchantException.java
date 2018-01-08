package com.changyi.fi.exception;

import com.changyi.fi.core.exception.BusinessException;

public class InvalidMerchantException extends BusinessException {

    public InvalidMerchantException(String msg) {
        super(msg);
    }
}
