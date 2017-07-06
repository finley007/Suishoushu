package com.changyi.fi.component.merchant.request;

import net.sf.oval.constraint.MatchPattern;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;

public class DoRecordRequest {

    @NotNull(message = "merchantId is required")
    @NotEmpty(message = "merchantId is required")
    @MatchPattern(pattern = "^0|[1-9][0-9]*$", message = "merchantId should be integer")
    String merchantId;

    @NotNull(message = "invoiceId is required")
    @NotEmpty(message = "invoiceId is required")
    @MatchPattern(pattern = "^0|[1-9][0-9]*$", message = "invoiceId should be integer")
    String invoiceId;

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }
}
