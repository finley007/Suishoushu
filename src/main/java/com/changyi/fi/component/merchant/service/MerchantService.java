package com.changyi.fi.component.merchant.service;

import com.changyi.fi.component.merchant.request.MerchantValidateRequest;

import java.util.List;

public interface MerchantService {

    public boolean validate(MerchantValidateRequest req, String openId) throws Exception;

    public void recordVisit(MerchantValidateRequest req, String openId, boolean success) throws Exception;

    public String createQRCode(String merchantId) throws Exception;

    public List<String> createMerchantIds(int idNum) throws Exception;

}
