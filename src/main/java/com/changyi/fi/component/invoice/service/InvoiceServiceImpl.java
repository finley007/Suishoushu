package com.changyi.fi.component.invoice.service;

import com.changyi.fi.component.invoice.request.PutInvoiceRequest;
import com.changyi.fi.component.invoice.response.GetInvoiceResponse;
import com.changyi.fi.component.invoice.response.InvoicesResponse;
import com.changyi.fi.core.LogUtil;
import com.changyi.fi.core.annotation.Validate;
import com.changyi.fi.core.tool.QRCodeUtils;
import com.changyi.fi.dao.InvoiceDao;
import com.changyi.fi.exception.AuthenticationFailedException;
import com.changyi.fi.exception.InvoiceNotFoundException;
import com.changyi.fi.exception.PermissionDeniedException;
import com.changyi.fi.model.EnterpriseHistoryPO;
import com.changyi.fi.model.EnterprisePO;
import com.changyi.fi.model.InvoicePO;
import com.changyi.fi.model.VInvoicePO;
import com.changyi.fi.util.FIConstants;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
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
        String status = FIConstants.InvoiceStatus.Normal.getValue() + "";
        return new InvoicesResponse(this.invoiceDao.listInvoices(openId, status));
    }

    @Validate(validator = "com.changyi.fi.component.invoice.validate.PutInvoiceValidator")
    public String updateInvoice(PutInvoiceRequest request, String openId) throws Exception {
        LogUtil.info(this.getClass(), "Execute updateInvoice service for: " + openId);
        String id = getRelatedInvoiceId(openId, request);
        if (StringUtils.isBlank(id)) {
            LogUtil.info(this.getClass(), "Create a new invoice");
            id = createNewInvoice(request, openId);
        } else {
            request.setId(id);
            LogUtil.info(this.getClass(), "Update exited invoice for id: " + request.getId());
            updateExistedInvoice(request, openId);
            id = request.getId();
        }
        //设置当前发票为默认发票则更新其它发票为为非默认
        if (FIConstants.IsDefault.True.getValue().equals(request.getIsDefault())) {
            this.invoiceDao.updateNotDefault(openId, id, FIConstants.IsDefault.False.getValue());
        }
        return id;
    }

    private String getRelatedInvoiceId(String openId, PutInvoiceRequest request) {
        String invoiceId = "";
        if (!StringUtils.isEmpty(request.getId())) {
            VInvoicePO po = invoiceDao.getInvoiceById(request.getId());
            if (po != null) {
                invoiceId = request.getId();
                LogUtil.info(this.getClass(), "Invoice: " + request.getId() + " is existed");
            }
        } else {
            boolean isEnterprise = !FIConstants.InvoiceType.Person.getValue().equals(request.getType());
            if (isEnterprise) {
                invoiceId = invoiceDao.getInvoiceByEnterpriceId(openId, request.getCreditCode());
                if (StringUtils.isNotBlank(invoiceId)) {
                    LogUtil.info(this.getClass(), "Enterprise: " + request.getCreditCode() + " has already been added for current user and invoice id: " + invoiceId);
                }
            }
        }
        return invoiceId;
    }

    private String createNewInvoice(PutInvoiceRequest request, String openId) throws Exception {
        if (FIConstants.InvoiceType.Person.getValue().equals(request.getType())) {
            InvoicePO po = new InvoicePO();
            po.setOpenId(openId);
            po.setType(Short.valueOf(request.getType()));
            po.setIsDefault(Short.valueOf(request.getIsDefault()));
            po.setStatus(FIConstants.InvoiceStatus.Normal.getValue());
            po.setUserName(request.getName());
            po.setPhone(request.getPhone());
            po.setEmail(request.getEmail());
            po.setCreateTime(new Date());
            po.setModifyTime(new Date());
            invoiceDao.insert(po);
            return po.getId().toString();
        } else {
            updateEnterpriseInfo(request, openId);

            InvoicePO ipo = new InvoicePO();
            ipo.setOpenId(openId);
            ipo.setType(Short.valueOf(request.getType()));
            ipo.setIsDefault(Short.valueOf(request.getIsDefault()));
            ipo.setCreditCode(request.getCreditCode());
            ipo.setStatus(FIConstants.InvoiceStatus.Normal.getValue());
            ipo.setCreateTime(new Date());
            ipo.setModifyTime(new Date());
            invoiceDao.insert(ipo);
            return ipo.getId().toString();
        }
    }

    private void updateExistedInvoice(PutInvoiceRequest request, String openId) throws Exception {
        if (FIConstants.InvoiceType.Person.getValue().equals(request.getType())) {
            InvoicePO po = new InvoicePO();
            po.setId(Integer.valueOf(request.getId()));
            po.setType(Short.valueOf(request.getType()));
            po.setIsDefault(Short.valueOf(request.getIsDefault()));
            po.setUserName(request.getName());
            po.setPhone(request.getPhone());
            po.setEmail(request.getEmail());
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
        String creditCode = request.getCreditCode();
        if ((curEpo.getAddress() == null && StringUtils.isNotBlank(request.getAddress())) ||
                (curEpo.getAddress() != null && !curEpo.getAddress().equals(request.getAddress()))) {
            result.add(createChangeItem(creditCode, openId, FIConstants.EnterpriseField.Address.getName(), curEpo.getAddress(), request.getAddress()));
        }
        if ((curEpo.getPhone() == null && StringUtils.isNotBlank(request.getPhone())) ||
                (curEpo.getPhone() != null && !curEpo.getPhone().equals(request.getPhone()))) {
            result.add(createChangeItem(creditCode, openId, FIConstants.EnterpriseField.Phone.getName(), curEpo.getPhone(), request.getPhone()));
        }
        if ((curEpo.getBank() == null && StringUtils.isNotBlank(request.getBank())) ||
                (curEpo.getBank() != null && !curEpo.getBank().equals(request.getBank()))) {
            result.add(createChangeItem(creditCode, openId, FIConstants.EnterpriseField.Bank.getName(), curEpo.getBank(), request.getBank()));
        }
        if ((curEpo.getBankAcct() == null && StringUtils.isNotBlank(request.getBankAcct())) ||
                (curEpo.getBankAcct() != null && !curEpo.getBankAcct().equals(request.getBankAcct()))) {
            result.add(createChangeItem(creditCode, openId, FIConstants.EnterpriseField.BankAcct.getName(), curEpo.getBankAcct(), request.getBankAcct()));
        }
        if (!curEpo.getName().equals(request.getName())) {
            result.add(createChangeItem(creditCode, openId, FIConstants.EnterpriseField.Name.getName(), curEpo.getName(), request.getName()));
        }
        return result;
    }

    private EnterpriseHistoryPO createChangeItem(String creditCode, String openId, String fieldName, String oldValue, String newValue) {
        EnterpriseHistoryPO historyPO = new EnterpriseHistoryPO();
        historyPO.setCreditCode(creditCode);
        historyPO.setField(fieldName);
        historyPO.setModifyTime(new Date());
        historyPO.setModifyBy(openId);
        historyPO.setOldValue(oldValue);
        historyPO.setNewValue(newValue);
        return historyPO;
    }


    public void deleteInvoice(String openId, String id) throws Exception {
        LogUtil.info(this.getClass(), "Execute deleteInvoice service for id: " + id);
        VInvoicePO invoice = this.invoiceDao.getInvoiceById(id);
        if (invoice != null) {
            if (!invoice.getOpenId().equals(openId)) {
                throw new AuthenticationFailedException("The current customer is not the invoice owner!");
            }
            InvoicePO invoicePO = new InvoicePO();
            invoicePO.setId(invoice.getId());
            invoicePO.setStatus(FIConstants.InvoiceStatus.Invalid.getValue());
            invoicePO.setModifyTime(new Date());
            this.invoiceDao.updateSelective(invoicePO);
        } else {
            LogUtil.info(this.getClass(), "The invoice doesn't exist");
        }
    }

    public GetInvoiceResponse getInvoice(String openId, String id) throws Exception {
        LogUtil.info(this.getClass(), "Execute getInvoice service for: " + id);
        VInvoicePO invoice = this.invoiceDao.getInvoiceById(id);
        if (invoice != null) {
            if (!invoice.getOpenId().equals(openId)) {
                throw new AuthenticationFailedException("The current customer is not the invoice owner!");
            }
            return new GetInvoiceResponse(invoice);
        } else {
            LogUtil.info(this.getClass(), "The invoice doesn't exist");
            return new GetInvoiceResponse(null);
        }
    }

    public String createCRCode(String openId, String invoiceId) throws Exception {
        LogUtil.info(this.getClass(), "Execute createCRCode service for: " + openId);
        VInvoicePO invoice = this.invoiceDao.getInvoiceById(invoiceId);
        if (invoice == null) {
            throw new InvoiceNotFoundException("The invoice: " + invoiceId + " does not existed");
        }
        if (!openId.equals(invoice.getOpenId())) {
            throw new PermissionDeniedException("The invoice does not belong to current user");
        }
        File file = this.getQRCodeImg(invoice);
        return file.getAbsolutePath();
    }

    private File getQRCodeImg(VInvoicePO invoice) throws Exception {
        String fileName = this.createQRCodeImgName(invoice);
        return QRCodeUtils.createQRCode(createCRCodeContent(invoice), fileName);
    }

    private String createQRCodeImgName(VInvoicePO invoice) {
        return "test";
    }

    private String createCRCodeContent(VInvoicePO invoice) {
        StringBuffer result = new StringBuffer();
        if (FIConstants.InvoiceType.Person.getShortValue() == invoice.getType()) {
            result.append(invoice.getUserName());
        } else if (FIConstants.InvoiceType.EnterpriseNormal.getShortValue() == invoice.getType()){
            result.append(invoice.getCorpName());
            result.append("\r");
            result.append(invoice.getCreditCode());
        } else if (FIConstants.InvoiceType.EnterpriseSpecial.getShortValue() == invoice.getType()){
            result.append(invoice.getCorpName());
            result.append("\r");
            result.append(invoice.getCreditCode());
            result.append("\r");
            result.append(invoice.getAddress());
            result.append("\r");
            result.append(invoice.getBankAcct());
        }
        return result.toString();
    }

}
