package com.changyi.fi.component.merchant.response;

import com.changyi.fi.core.response.NormalResponse;

/**
 * Created by finley on 8/14/17.
 */
public class QRCodeResponse extends NormalResponse {

    public QRCodeResponse(String merchantId, String url) {
        this.merchantId = merchantId;
        this.url = url;
    }

    private String merchantId;

    private String url;

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
