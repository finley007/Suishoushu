package com.changyi.fi.component.invoice.response;

import com.changyi.fi.core.Payload;
import com.changyi.fi.core.response.NormalResponse;
import com.changyi.fi.model.VInvoicePO;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

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

    protected String createContent() {
        return new Payload(this).setExclusion(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes f) {
                return f.getName().contains("createTime")
                        || f.getName().contains("openId")
                        || f.getName().contains("modifyTime");
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
        }).from(this.getClass());
    }

}
