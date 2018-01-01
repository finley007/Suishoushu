package com.changyi.fi.component.merchant.service;

import com.changyi.fi.component.merchant.request.MerchantValidateRequest;

public interface MerchantService {

    public boolean validate(MerchantValidateRequest req, String openId) throws Exception;

    public void recordVisit(MerchantValidateRequest req, String openId, boolean success) throws Exception;

    public String createQRCode(String merchantId) throws Exception;

}
