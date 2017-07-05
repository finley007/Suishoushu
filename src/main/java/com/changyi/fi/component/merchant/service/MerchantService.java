package com.changyi.fi.component.merchant.service;

import com.changyi.fi.component.merchant.request.MerchantValidateRequest;

public interface MerchantService {

    public void validate(MerchantValidateRequest req, String openId) throws Exception;

}
