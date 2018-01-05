package com.changyi.fi.component.merchant.response;

import com.changyi.fi.core.response.NormalResponse;

import java.util.List;
import java.util.Map;

public class QRCodesResponse extends NormalResponse {

    public static final String KEY_MERCHANT_ID = "merchantId";
    public static final String KEY_URL = "url";

    List<Map> qrcodes;

    public QRCodesResponse(List<Map> qrcodes) {
        this.qrcodes = qrcodes;
    }

    public List<Map> getQrcodes() {
        return qrcodes;
    }

    public void setQrcodes(List<Map> qrcodes) {
        this.qrcodes = qrcodes;
    }
}
