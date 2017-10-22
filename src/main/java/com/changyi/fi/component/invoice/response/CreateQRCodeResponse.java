package com.changyi.fi.component.invoice.response;

import com.changyi.fi.core.response.NormalResponse;

public class CreateQRCodeResponse extends NormalResponse {

    private String url;

    public CreateQRCodeResponse(String url) {
        this.url = url;
    }

    public String getUrl() {

        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
