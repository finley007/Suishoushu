package com.changyi.fi.component.invoice.response;

import com.changyi.fi.core.response.NormalResponse;
import com.changyi.fi.model.VInvoicePO;

import java.util.List;

/**
 * Created by finley on 1/2/17.
 */
public class InvoicesResponse extends NormalResponse {

    public InvoicesResponse(List<VInvoicePO> invoiceList) {
        this.invoiceList = invoiceList;
    }

    public List<VInvoicePO> getInvoiceList() {
        return invoiceList;
    }

    public void setInvoiceList(List<VInvoicePO> invoiceList) {
        this.invoiceList = invoiceList;
    }

    private List<VInvoicePO> invoiceList;

}
