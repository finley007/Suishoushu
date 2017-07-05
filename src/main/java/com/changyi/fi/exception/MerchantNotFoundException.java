package com.changyi.fi.exception;

import com.changyi.fi.core.exception.BusinessException;

public class MerchantNotFoundException extends BusinessException {

    public MerchantNotFoundException(String msg) {
        super(msg);
    }

}
