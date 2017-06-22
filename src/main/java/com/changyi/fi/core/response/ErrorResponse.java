package com.changyi.fi.core.response;

import com.changyi.fi.core.Payload;
import com.changyi.fi.core.exception.BaseException;

/**
 * Created by finley on 2/18/17.
 */
public class ErrorResponse implements Response {

    private String message;
    private String returnCode;

    public ErrorResponse(BaseException e) {
        this.message = e.getMessage();
        this.returnCode = e.getCode();
    }

    public String build() {
        return new Payload(this).from(ErrorResponse.class);
    }

}
