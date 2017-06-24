package com.changyi.fi.core.validate;


import com.changyi.fi.core.exception.BusinessException;
import com.changyi.fi.core.request.Request;

public interface Validator<T extends Request> {

    public void validate(T request) throws BusinessException;

}
