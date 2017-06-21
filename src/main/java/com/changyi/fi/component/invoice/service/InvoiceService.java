package com.changyi.fi.component.invoice.service;

import com.changyi.fi.component.invoice.request.AddInvoiceRequest;
import com.changyi.fi.component.invoice.request.InvoicesRequest;
import com.changyi.fi.component.invoice.response.InvoicesResponse;

/**
 * Created by finley on 12/31/16.
 */
public interface InvoiceService {

    public InvoicesResponse listInvoice(InvoicesRequest request);

    public void addInvoice(AddInvoiceRequest request);

}

