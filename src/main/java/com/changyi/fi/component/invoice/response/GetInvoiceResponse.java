package com.changyi.fi.component.invoice.response;

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


}
