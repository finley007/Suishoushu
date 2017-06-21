package com.changyi.fi.core.exception;

import com.changyi.fi.core.response.ErrorResponse;
import com.changyi.fi.core.response.ResponseWrapper;
import com.changyi.fi.exception.SystemErrorException;

/**
 * Created by finley on 2/18/17.
 */
public class ExceptionHandler {

    public static String handle(Throwable t) {
        if (t instanceof BusinessException) {
            return new ResponseWrapper(new ErrorResponse((BusinessException)t)).build(ErrorResponse.class);
        } else if (t instanceof SystemException) {
            return new ResponseWrapper(new ErrorResponse(new SystemErrorException("System error, please contact the customer service"))).build(ErrorResponse.class);
        } else {
            return new ResponseWrapper(new ErrorResponse(new SystemErrorException("System error, please contact the customer service"))).build(ErrorResponse.class);
        }
    }
}
