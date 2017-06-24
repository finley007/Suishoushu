package com.changyi.fi.component.invoice.validate;

import com.changyi.fi.component.invoice.request.PutInvoiceRequest;
import com.changyi.fi.core.LogUtil;
import com.changyi.fi.core.exception.BusinessException;
import com.changyi.fi.core.validate.Validator;
import com.changyi.fi.exception.InvalidRequestException;
import com.changyi.fi.util.FIConstants;
import org.apache.commons.lang.StringUtils;

public class PutInvoiceValidator implements Validator<PutInvoiceRequest> {

    public void validate(PutInvoiceRequest request) throws BusinessException {
        LogUtil.info(this.getClass(), "Do validation for PutInvoiceRequest");
        if (FIConstants.InvoiceType.Person.getValue().equals(request.getType())
                && StringUtils.isEmpty(request.getUserName())) {
            throw new InvalidRequestException("Field userName is required");
        }
        if (FIConstants.InvoiceType.Enterprise.getValue().equals(request.getType())) {
            if (StringUtils.isEmpty(request.getCreditCode())) {
                throw new InvalidRequestException("Field creditCode is required");
            } else if (StringUtils.isEmpty(request.getAddress())) {
                throw new InvalidRequestException("Field address is required");
            } else if (StringUtils.isEmpty(request.getCorpName())) {
                throw new InvalidRequestException("Field corpName is required");
            } else if (StringUtils.isEmpty(request.getBank())) {
                throw new InvalidRequestException("Field bank is required");
            } else if (StringUtils.isEmpty(request.getBankAcct())) {
                throw new InvalidRequestException("Field bankAcct is required");
            } else if (StringUtils.isEmpty(request.getPhone())) {
                throw new InvalidRequestException("Field phone is required");
            }
        }
    }
}
