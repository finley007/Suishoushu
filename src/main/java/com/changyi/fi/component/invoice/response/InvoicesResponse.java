package com.changyi.fi.component.invoice.response;

import com.changyi.fi.core.response.NormalResponse;

/**
 * Created by finley on 1/2/17.
 */
public class InvoicesResponse extends NormalResponse {

    public InvoicesResponse InvoicesResponse() {
        return this;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    private String result = "ok";

}
