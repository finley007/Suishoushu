package com.changyi.fi.component.invoice.response;

import com.changyi.fi.core.Payload;
import com.changyi.fi.core.response.NormalResponse;
import com.changyi.fi.model.VInvoicePO;

/**
 * Created by finley on 6/21/17.
 */
public class GetInvoiceResponse extends NormalResponse {

    public GetInvoiceResponse(VInvoicePO invoice) {
        this.invoice = invoice;
    }

    public VInvoicePO getInvoice() {
        return invoice;
    }

    public void setInvoice(VInvoicePO invoice) {
        this.invoice = invoice;
    }

    private VInvoicePO invoice;

    public String build() {
        if (this.invoice == null) {
            return "{ \"returnCode\" : \"0\", \"content\" : \"\" }";
        }
        String content = new Payload(this.invoice).from(this.invoice.getClass());
        if (!"{}".equals(content)) {
            return "{ \"returnCode\" : \"0\", \"content\" : " + content + " }";
        } else {
            return "{ \"returnCode\" : \"0\", \"content\" : \"success\" }";
        }
    }


}
