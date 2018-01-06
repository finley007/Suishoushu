package com.changyi.fi.component.merchant.service;

import com.changyi.fi.vo.Merchant;
import com.changyi.fi.component.merchant.request.MerchantValidateRequest;
import com.changyi.fi.vo.Channel;

import java.util.List;

public interface MerchantService {

    public boolean validate(MerchantValidateRequest req, String openId) throws Exception;

    public void recordVisit(MerchantValidateRequest req, String openId, boolean success) throws Exception;

    public String createQRCode(String merchantId) throws Exception;

    public List<String> createMerchantIds(int idNum) throws Exception;

    public List<String> createMerchants(int idNum, String channelId) throws Exception;

    public String updateChannel(Channel channel) throws Exception;

    public List<Channel> listChannel() throws Exception;

    public void merchantRegister(Merchant req) throws Exception;

}
