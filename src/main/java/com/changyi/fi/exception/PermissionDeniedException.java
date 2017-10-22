package com.changyi.fi.exception;

import com.changyi.fi.core.exception.BusinessException;

public class PermissionDeniedException extends BusinessException {

    public PermissionDeniedException(String msg) {
        super(msg);
    }

}
