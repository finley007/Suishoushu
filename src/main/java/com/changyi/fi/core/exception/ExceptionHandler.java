package com.changyi.fi.core.exception;

import com.changyi.fi.core.response.ErrorResponse;
import com.changyi.fi.exception.SystemErrorException;

/**
 * Created by finley on 2/18/17.
 */
public class ExceptionHandler {

    public static String handle(Throwable t) {
        ErrorResponse response;
        if (t instanceof BusinessException) {
            response = new ErrorResponse((BusinessException)t);
        } else if (t instanceof SystemException) {
            response = new ErrorResponse(new SystemErrorException("System error, please contact the customer service"));
        } else {
            response = new ErrorResponse(new SystemErrorException("System error, please contact the customer service"));
        }
        return response.build();
    }
}
