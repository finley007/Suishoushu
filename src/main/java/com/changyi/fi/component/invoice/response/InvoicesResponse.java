package com.changyi.fi.component.invoice.response;

import com.changyi.fi.core.response.NormalResponse;
import com.changyi.fi.model.VInvoicePO;

import java.util.List;

/**
 * Created by finley on 1/2/17.
 */
public class InvoicesResponse extends NormalResponse {

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    private int count = 0;

    public InvoicesResponse(List<VInvoicePO> invoiceList) {
        this.invoiceList = invoiceList;
        this.count = invoiceList.size();
    }

    public List<VInvoicePO> getInvoiceList() {
        return invoiceList;
    }

    public void setInvoiceList(List<VInvoicePO> invoiceList) {
        this.invoiceList = invoiceList;
    }

    private List<VInvoicePO> invoiceList;

}
