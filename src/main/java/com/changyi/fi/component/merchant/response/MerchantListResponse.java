package com.changyi.fi.component.merchant.response;

import com.changyi.fi.core.response.NormalResponse;
import com.changyi.fi.model.MerchantPO;

import java.util.List;

public class MerchantListResponse extends NormalResponse {

    public MerchantListResponse(List<MerchantPO> merchantList) {
        this.merchantList = merchantList;
    }

    private List<MerchantPO> merchantList;

    public List<MerchantPO> getMerchantList() {
        return merchantList;
    }

    public void setMerchantList(List<MerchantPO> merchantList) {
        this.merchantList = merchantList;
    }
}
