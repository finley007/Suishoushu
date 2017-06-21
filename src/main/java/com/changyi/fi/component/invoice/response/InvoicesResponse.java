package com.changyi.fi.component.invoice.response;

/**
 * Created by finley on 1/2/17.
 */
public class InvoicesResponse {

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
