package com.changyi.fi.component.merchant.service;

import com.changyi.fi.component.merchant.request.MerchantValidateRequest;

public interface MerchantService {

    public void validate(MerchantValidateRequest req, String openId) throws Exception;

    public void doRecord(String merchantId, String openId) throws Exception;

    public String createQRCode(String merchantId) throws Exception;

}
