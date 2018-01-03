package com.changyi.fi.component.statistics.response;

import com.changyi.fi.core.response.NormalResponse;

import java.util.List;
import java.util.Map;

public class MerchantStatResponse extends NormalResponse {

    private List<Map> merchantStat;

    public List<Map> getMerchantStat() {
        return merchantStat;
    }

    public void setMerchantStat(List<Map> merchantStat) {
        this.merchantStat = merchantStat;
    }
}
