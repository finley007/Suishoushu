package com.changyi.fi.component.invoice.service;

import com.changyi.fi.component.invoice.request.PutInvoiceRequest;
import com.changyi.fi.component.invoice.response.GetInvoiceResponse;
import com.changyi.fi.component.invoice.response.InvoicesResponse;
import com.changyi.fi.core.LogUtil;
import com.changyi.fi.core.annotation.Validate;
import com.changyi.fi.dao.InvoiceDao;
import com.changyi.fi.model.VInvoicePO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.rmi.runtime.Log;

import java.util.List;

/**
 * Created by finley on 12/31/16.
 */
@Service("invoiceService")
public class InvoiceServiceImpl implements InvoiceService {

    private InvoiceDao invoiceDao;

    @Autowired(required = true)
    public void setInvoiceDao(InvoiceDao invoiceDao) {
        this.invoiceDao = invoiceDao;
    }

    public InvoicesResponse listInvoice(String openId) throws Exception {
        LogUtil.info(this.getClass(), "Execute listInvoice service for: " + openId);
        List<VInvoicePO> result = this.invoiceDao.listInvoices(openId);
        return new InvoicesResponse(result);
    }

    @Validate(validator = "com.changyi.fi.component.invoice.validate.PutInvoiceValidator")
    public void updateInvoice(String openId, PutInvoiceRequest request) throws Exception {
        LogUtil.info(this.getClass(), "Execute updateInvoice service for: " + openId);
        if (StringUtils.isEmpty(request.getId())) {
            LogUtil.info(this.getClass(), "Create a new invoice");

        } else {
            LogUtil.info(this.getClass(), "Update exited invoice for id: " + request.getId());
        }
    }

    public void deleteInvoice(String openId, String invoiceId) throws Exception {

    }

    public GetInvoiceResponse getInvoice(String id) throws Exception {
        LogUtil.info(this.getClass(), "Execute getInvoice service for: " + id);
        VInvoicePO invoice = this.invoiceDao.getInvoiceById(id);
        return new GetInvoiceResponse(invoice);
    }


}
