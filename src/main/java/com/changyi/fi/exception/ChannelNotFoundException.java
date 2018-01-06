package com.changyi.fi.exception;

import com.changyi.fi.core.exception.BusinessException;

public class ChannelNotFoundException extends BusinessException {

    public ChannelNotFoundException(String msg) {
        super(msg);
    }
}
