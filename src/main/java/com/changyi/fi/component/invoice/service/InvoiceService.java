package com.changyi.fi.component.invoice.service;

import com.changyi.fi.component.invoice.request.PutInvoiceRequest;
import com.changyi.fi.component.invoice.response.GetInvoiceResponse;
import com.changyi.fi.component.invoice.response.InvoicesResponse;

/**
 * Created by finley on 12/31/16.
 */
public interface InvoiceService {

    public InvoicesResponse listInvoice(String openId);

    public void updateInvoice(PutInvoiceRequest request);

    public void deleteInvoice(String openId, String invoiceId);

    public GetInvoiceResponse getInvoice(String id);

}

