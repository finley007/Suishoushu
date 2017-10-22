package com.changyi.fi.exception;

import com.changyi.fi.core.exception.BusinessException;

public class InvoiceNotFoundException extends BusinessException {

    public InvoiceNotFoundException(String msg) {
        super(msg);
    }

}
