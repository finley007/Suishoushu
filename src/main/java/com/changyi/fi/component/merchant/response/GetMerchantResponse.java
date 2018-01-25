package com.changyi.fi.component.merchant.response;

import com.changyi.fi.core.response.NormalResponse;
import com.changyi.fi.model.MerchantPO;

public class GetMerchantResponse extends NormalResponse {

    public GetMerchantResponse(MerchantPO merchant) {
        this.merchant = merchant;
    }

    private MerchantPO merchant;

    public MerchantPO getMerchant() {
        return merchant;
    }

    public void setMerchant(MerchantPO merchant) {
        this.merchant = merchant;
    }
}
