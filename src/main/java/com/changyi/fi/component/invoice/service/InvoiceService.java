package com.changyi.fi.component.invoice.service;

import com.changyi.fi.component.invoice.request.PutInvoiceRequest;
import com.changyi.fi.component.invoice.response.GetInvoiceResponse;
import com.changyi.fi.component.invoice.response.InvoicesResponse;

/**
 * Created by finley on 12/31/16.
 */
public interface InvoiceService {

    public InvoicesResponse listInvoice(String openId) throws Exception;

    public String updateInvoice(PutInvoiceRequest request, String openId) throws Exception;

    public void deleteInvoice(String openId, String invoiceId) throws Exception;

    public GetInvoiceResponse getInvoice(String openId, String id) throws Exception;

    public String createCRCode(String openId, String invoiceId) throws Exception;

}

