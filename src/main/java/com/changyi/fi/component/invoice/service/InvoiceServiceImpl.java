package com.changyi.fi.component.invoice.service;

import com.changyi.fi.component.invoice.request.AddInvoiceRequest;
import com.changyi.fi.component.invoice.request.InvoicesRequest;
import com.changyi.fi.component.invoice.response.InvoicesResponse;
import org.springframework.stereotype.Service;

/**
 * Created by finley on 12/31/16.
 */
@Service("invoiceService")
public class InvoiceServiceImpl implements InvoiceService {

//    private Module2Dao module2Dao;

//    @Autowired(required = true)
//    public void setModule2Dao(Module2Dao module2Dao) {
//        this.module2Dao = module2Dao;
//    }

    public InvoicesResponse listInvoice(InvoicesRequest request) {
        return new InvoicesResponse();
    }

    public void addInvoice(AddInvoiceRequest request) {
    }



}
