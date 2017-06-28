package com.changyi.fi.component.enterprise.service;

import com.changyi.fi.component.enterprise.response.GetEnterpriseResponse;
import com.changyi.fi.component.enterprise.response.MatchEnterpriseResponse;
import com.changyi.fi.core.LogUtil;
import com.changyi.fi.dao.InvoiceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by finley on 6/21/17.
 */
@Service("enterpriseService")
public class EnterpriseServiceImpl implements EnterpriseService {

    private InvoiceDao invoiceDao;

    @Autowired(required = true)
    public void setInvoiceDao(InvoiceDao invoiceDao) {
        this.invoiceDao = invoiceDao;
    }

    public MatchEnterpriseResponse matchEnterprise(String key) {
        LogUtil.info(this.getClass(), "Execute matchEnterprise service for key: " + key);
        return new MatchEnterpriseResponse(this.invoiceDao.matchEnterpriseList(key));
    }

    public GetEnterpriseResponse getEnterprise(String creditCode) {
        LogUtil.info(this.getClass(), "Execute getEnterprise service for creditCode: " + creditCode);
        return new GetEnterpriseResponse(this.invoiceDao.getEnterpriseById(creditCode));
    }
}
