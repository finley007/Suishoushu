package com.changyi.fi.component.invoice.service;

import com.changyi.fi.component.invoice.request.PutInvoiceRequest;
import com.changyi.fi.component.invoice.response.GetInvoiceResponse;
import com.changyi.fi.component.invoice.response.InvoicesResponse;
import com.changyi.fi.core.LogUtil;
import com.changyi.fi.core.annotation.Validate;
import com.changyi.fi.dao.InvoiceDao;
import com.changyi.fi.exception.AuthenticationFailedException;
import com.changyi.fi.model.EnterpriseHistoryPO;
import com.changyi.fi.model.EnterprisePO;
import com.changyi.fi.model.InvoicePO;
import com.changyi.fi.model.VInvoicePO;
import com.changyi.fi.util.FIConstants;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
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
    public void updateInvoice(PutInvoiceRequest request, String openId) throws Exception {
        LogUtil.info(this.getClass(), "Execute updateInvoice service for: " + openId);
        if (StringUtils.isEmpty(request.getId())) {
            LogUtil.info(this.getClass(), "Create a new invoice");
            createNewInvoice(request, openId);
        } else {
            LogUtil.info(this.getClass(), "Update exited invoice for id: " + request.getId());
            updateExistedInvoice(request, openId);
        }
    }

    private void createNewInvoice(PutInvoiceRequest request, String openId) throws Exception {
        if (FIConstants.InvoiceType.Person.getValue().equals(request.getType())) {
            InvoicePO po = new InvoicePO();
            po.setType(Short.valueOf(request.getType()));
            po.setIsDefault(Short.valueOf(request.getIsDefault()));
            po.setUserName(request.getName());
            po.setOpenId(openId);
            po.setCreateTime(new Date());
            po.setModifyTime(new Date());
            invoiceDao.insert(po);
        } else {
            updateEnterpriseInfo(request, openId);

            InvoicePO ipo = new InvoicePO();
            ipo.setType(Short.valueOf(request.getType()));
            ipo.setIsDefault(Short.valueOf(request.getIsDefault()));
            ipo.setCreditCode(request.getCreditCode());
            ipo.setOpenId(openId);
            ipo.setCreateTime(new Date());
            ipo.setModifyTime(new Date());
            invoiceDao.insert(ipo);
        }
    }

    private void updateExistedInvoice(PutInvoiceRequest request, String openId) throws Exception {
        if (FIConstants.InvoiceType.Person.getValue().equals(request.getType())) {
            InvoicePO po = new InvoicePO();
            po.setId(Integer.valueOf(request.getId()));
            po.setType(Short.valueOf(request.getType()));
            po.setIsDefault(Short.valueOf(request.getIsDefault()));
            po.setUserName(request.getName());
            po.setOpenId(openId);
            po.setModifyTime(new Date());
            invoiceDao.updateSelective(po);
        } else {
            updateEnterpriseInfo(request, openId);

            InvoicePO po = new InvoicePO();
            po.setId(Integer.valueOf(request.getId()));
            po.setType(Short.valueOf(request.getType()));
            po.setIsDefault(Short.valueOf(request.getIsDefault()));
            po.setCreditCode(request.getCreditCode());
            po.setOpenId(openId);
            po.setModifyTime(new Date());
            invoiceDao.updateSelective(po);
        }
    }

    private void updateEnterpriseInfo(PutInvoiceRequest request, String openId) {
        EnterprisePO curEpo = invoiceDao.getEnterpriseById(request.getCreditCode());
        if (curEpo != null) {
            List<EnterpriseHistoryPO> modifiedList = this.checkModification(curEpo, request, openId);
            if (modifiedList.size() > 0) {
                EnterprisePO po = new EnterprisePO();
                po.setCreditCode(request.getCreditCode());
                po.setName(request.getName());
                po.setAddress(request.getAddress());
                po.setBank(request.getBank());
                po.setBankAcct(request.getBankAcct());
                po.setPhone(request.getPhone());
                po.setModifyTime(new Date());
                po.setCreateBy(openId);
                po.setModifyBy(openId);
                invoiceDao.updateEnterpriseSelective(po);

                for (EnterpriseHistoryPO historyPO : modifiedList) {
                    invoiceDao.insertEnterpriseHistory(historyPO);
                }
            }
        } else {
            EnterprisePO po = new EnterprisePO();
            po.setCreditCode(request.getCreditCode());
            po.setName(request.getName());
            po.setAddress(request.getAddress());
            po.setBank(request.getBank());
            po.setBankAcct(request.getBankAcct());
            po.setPhone(request.getPhone());
            po.setCreateTime(new Date());
            po.setModifyTime(new Date());
            po.setCreateBy(openId);
            po.setModifyBy(openId);
            invoiceDao.insertEnterprise(po);
        }
    }

    private List<EnterpriseHistoryPO> checkModification(EnterprisePO curEpo, PutInvoiceRequest request, String openId) {
        List<EnterpriseHistoryPO> result = new ArrayList<EnterpriseHistoryPO>();
        if (!curEpo.getAddress().equals(request.getAddress())) {
            result.add(createChangeItem(curEpo, request, openId, FIConstants.EnterpriseField.Address.getName()));
        } else if (!curEpo.getPhone().equals(request.getPhone())) {
            result.add(createChangeItem(curEpo, request, openId, FIConstants.EnterpriseField.Phone.getName()));
        } else if (!curEpo.getBank().equals(request.getBank())) {
            result.add(createChangeItem(curEpo, request, openId, FIConstants.EnterpriseField.Bank.getName()));
        } else if (!curEpo.getBankAcct().equals(request.getBankAcct())) {
            result.add(createChangeItem(curEpo, request, openId, FIConstants.EnterpriseField.BankAcct.getName()));
        } else if (!curEpo.getName().equals(request.getName())) {
            result.add(createChangeItem(curEpo, request, openId, FIConstants.EnterpriseField.Name.getName()));
        }
        return result;
    }

    private EnterpriseHistoryPO createChangeItem(EnterprisePO curEpo, PutInvoiceRequest request, String openId, String fieldName) {
        EnterpriseHistoryPO historyPO = new EnterpriseHistoryPO();
        historyPO.setCreditCode(request.getCreditCode());
        historyPO.setField(fieldName);
        historyPO.setModifyTime(new Date());
        historyPO.setModifyBy(openId);
        historyPO.setOldValue(curEpo.getAddress());
        historyPO.setNewValue(request.getAddress());
        return historyPO;
    }


    public void deleteInvoice(String openId, String id) throws Exception {
        LogUtil.info(this.getClass(), "Execute deleteInvoice service for id: " + id);
        VInvoicePO invoice = this.invoiceDao.getInvoiceById(id);
        if (invoice != null) {
            if (!invoice.getOpenId().equals(openId)) {
                throw new AuthenticationFailedException("The current customer is not the invoice owner!");
            }
            this.invoiceDao.deleteInvoice(id);
        } else {
            LogUtil.info(this.getClass(), "The invoice doesn't exist");
        }
    }

    public GetInvoiceResponse getInvoice(String openId, String id) throws Exception {
        LogUtil.info(this.getClass(), "Execute getInvoice service for: " + id);
        VInvoicePO invoice = this.invoiceDao.getInvoiceById(id);
        if (!invoice.getOpenId().equals(openId)) {
            throw new AuthenticationFailedException("The current customer is not the invoice owner!");
        }
        return new GetInvoiceResponse(invoice);
    }


}
