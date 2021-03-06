package com.changyi.fi.component.invoice.service;

import com.changyi.fi.component.invoice.request.PutInvoiceRequest;
import com.changyi.fi.component.invoice.response.GetInvoiceResponse;
import com.changyi.fi.component.invoice.response.InvoicesResponse;
import com.changyi.fi.core.CtxProvider;
import com.changyi.fi.core.LogUtil;
import com.changyi.fi.core.annotation.Validate;
import com.changyi.fi.core.encrypt.EncryptManager;
import com.changyi.fi.core.job.JobManager;
import com.changyi.fi.core.tool.QRCodeUtils;
import com.changyi.fi.dao.InvoiceDao;
import com.changyi.fi.exception.AuthenticationFailedException;
import com.changyi.fi.exception.InvoiceNotFoundException;
import com.changyi.fi.job.DBOperatorJob;
import com.changyi.fi.model.*;
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

    private static final String SEPARATOR = "\t";
    private static final String QRCODE_INVOICE_URL = "qrcode.invoice.url";

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
        long count = invoiceDao.countInvoiceByCustomer(openId);
        if (FIConstants.InvoiceType.Person.getValue().equals(request.getType())) {
            InvoicePO po = new InvoicePO();
            po.setOpenId(openId);
            po.setType(Short.valueOf(request.getType()));
            if (count == 0) {
                po.setIsDefault(Short.valueOf(FIConstants.IsDefault.True.getValue()));
            } else {
                po.setIsDefault(Short.valueOf(request.getIsDefault()));
            }
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
            if (count == 0) {
                ipo.setIsDefault(Short.valueOf(FIConstants.IsDefault.True.getValue()));
            } else {
                ipo.setIsDefault(Short.valueOf(request.getIsDefault()));
            }
            ipo.setCreditCode(request.getCreditCode());
            ipo.setStatus(FIConstants.InvoiceStatus.Normal.getValue());
            ipo.setCreateTime(new Date());
            ipo.setModifyTime(new Date());
            invoiceDao.insert(ipo);
            return ipo.getId().toString();
        }
    }

    private void updateExistedInvoice(PutInvoiceRequest request, String openId) throws Exception {
        long count = invoiceDao.countInvoiceByCustomer(openId);
        if (FIConstants.InvoiceType.Person.getValue().equals(request.getType())) {
            InvoicePO po = new InvoicePO();
            po.setId(Integer.valueOf(request.getId()));
            po.setType(Short.valueOf(request.getType()));
            if (count == 1) {
                po.setIsDefault(Short.valueOf(FIConstants.IsDefault.True.getValue()));
            } else {
                po.setIsDefault(Short.valueOf(request.getIsDefault()));
            }
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
            if (count == 1) {
                po.setIsDefault(Short.valueOf(FIConstants.IsDefault.True.getValue()));
            } else {
                po.setIsDefault(Short.valueOf(request.getIsDefault()));
            }
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
            List<Integer> invoices = invoiceDao.getInvoiceIdByCustomer(openId);
            //只剩下一个抬头，设为默认
            if (invoices != null && invoices.size() == 1) {
                invoicePO = new InvoicePO();
                invoicePO.setId(invoices.get(0));
                invoicePO.setIsDefault(FIConstants.IsDefault.True.getShortValue());
                invoicePO.setModifyTime(new Date());
                this.invoiceDao.updateSelective(invoicePO);
            }
        } else {
            LogUtil.info(this.getClass(), "The invoice doesn't exist");
        }
    }

    public GetInvoiceResponse getInvoice(String openId, String id) throws Exception {
        LogUtil.info(this.getClass(), "Execute getInvoice service for: " + id);
        VInvoicePO invoice = this.invoiceDao.getInvoiceById(id);
        if (invoice != null) {
            if (!invoice.getOpenId().equals(openId)) {
                List<VInvoicePO> invoiceList = this.invoiceDao.listInvoices(openId,
                        FIConstants.InvoiceStatus.Normal.getValue().toString());
                boolean hasDefault = false;
                for (VInvoicePO invoicePO : invoiceList) {
                    if (invoicePO.getType() == invoice.getType()
                            && invoice.getType() > 0
                                && invoicePO.getCreditCode().equals(invoice.getCreditCode())) {
                        return new GetInvoiceResponse(invoicePO);
                    } else if (invoicePO.getType() == invoice.getType()
                            && invoice.getType() == FIConstants.InvoiceType.Person.getShortValue()
                            && invoicePO.getUserName().equals(invoice.getUserName())) {
                        return new GetInvoiceResponse(invoicePO);
                    }
                    if (invoicePO.getIsDefault() == FIConstants.IsDefault.True.getShortValue()) {
                        hasDefault = true;
                    }
                }
                return new GetInvoiceResponse(this.syncInvoice(openId, invoice, invoiceList, hasDefault));
            }
            return new GetInvoiceResponse(invoice);
        } else {
            LogUtil.info(this.getClass(), "The invoice doesn't exist");
            return new GetInvoiceResponse(null);
        }
    }

    //小程序分享同步发票
    private VInvoicePO syncInvoice(String openId, VInvoicePO invoice, List<VInvoicePO> invoiceList, boolean hasDefault) {
        LogUtil.info(this.getClass(), "Sync invoice info: " + invoiceList.size() + "|" + hasDefault + "|" + invoice.getIsDefault());
        VInvoicePO newPo = new VInvoicePO();
        newPo.setOpenId(openId);
        newPo.setType(invoice.getType());
        //如果当前用户还没有发票抬头，则自动设置为默认
        if (invoiceList.size() == 0) {
            newPo.setIsDefault(FIConstants.IsDefault.True.getShortValue());
        } else if (hasDefault) { //如果当前用户已经有默认发票抬头，则不设置默认
            newPo.setIsDefault(FIConstants.IsDefault.False.getShortValue());
        } else {
            newPo.setIsDefault(invoice.getIsDefault());
        }
        newPo.setCreditCode(invoice.getCreditCode());
        newPo.setStatus(invoice.getStatus());
        newPo.setEmail(invoice.getEmail());
        newPo.setPhone(invoice.getPhone());
        newPo.setUserName(invoice.getUserName());
        newPo.setCorpName(invoice.getCorpName());
        newPo.setAddress(invoice.getAddress());
        newPo.setBank(invoice.getBank());
        newPo.setBankAcct(invoice.getBankAcct());
        return newPo;
    }

    public File createCRCode(String invoiceId) throws Exception {
        LogUtil.info(this.getClass(), "Execute createCRCode service for: " + invoiceId);
        VInvoicePO invoice = this.invoiceDao.getInvoiceById(invoiceId);
        if (invoice == null) {
            throw new InvoiceNotFoundException("The invoice: " + invoiceId + " does not existed");
        }
        recordCustomerQRCode(invoice);
        return this.getQRCodeImg(invoice);
    }

    private void recordCustomerQRCode(VInvoicePO invoice) {
        final CustomerQRCodePO po = new CustomerQRCodePO();
        po.setOpenId(invoice.getOpenId());
        po.setType(invoice.getType());
        if (invoice.isEnterpriseInvoice()) {
            po.setCreditCode(invoice.getCreditCode());
        }
        final InvoiceDao dao = (InvoiceDao)CtxProvider.getContext().getBean("invoiceDao");
        JobManager.addJob(new DBOperatorJob(new DBOperatorJob.IDBOperator() {
            public void execute() {
                dao.insertCustomerQRCode(po);
            }
        }));
    }

    private File getQRCodeImg(VInvoicePO invoice) throws Exception {
        String content = createCRCodeContent(invoice);
        String fileName = this.createQRCodeImgName(content);
        LogUtil.debug(this.getClass(), "Obtain QRCode image: " + fileName);
        LogUtil.debug(this.getClass(), "QRcode content: " + content);
        File file = QRCodeUtils.getQRCode(fileName);
        if ( file != null && file.exists()) {
            LogUtil.debug(this.getClass(), "Return exist file: " + fileName);
            return file;
        } else {
            LogUtil.debug(this.getClass(), "Create new file: " + fileName);
            file = QRCodeUtils.createQRCode(content, fileName);
            QRCodeUtils.modifyPermission(file);
            return file;
        }
    }

    private String createQRCodeImgName(String content) throws Exception {
        return EncryptManager.getEncryptor(FIConstants.EncryptorAlgorithm.MD5).sign(content);
    }

    private String createCRCodeContent(VInvoicePO invoice) {
        StringBuffer result = new StringBuffer();
        if (FIConstants.InvoiceType.Person.getShortValue() == invoice.getType()) {
            result.append(invoice.getUserName().trim());
            result.append(SEPARATOR);
            result.append(SEPARATOR);
            if (StringUtils.isNotBlank(invoice.getPhone())) {
                result.append(invoice.getPhone());
            }
        } else if (FIConstants.InvoiceType.EnterpriseNormal.getShortValue() == invoice.getType()
                || FIConstants.InvoiceType.EnterpriseSpecial.getShortValue() == invoice.getType()){
            result.append(invoice.getCorpName().trim());
            result.append(SEPARATOR);
            result.append(invoice.getCreditCode().trim());
            result.append(SEPARATOR);
            if (StringUtils.isNotBlank(invoice.getAddress())) {
                result.append(invoice.getAddress() + " ");
            }
            if (StringUtils.isNotBlank(invoice.getPhone())) {
                result.append(invoice.getPhone());
            }
            if (StringUtils.isNotBlank(invoice.getBank())
                    && StringUtils.isNotBlank(invoice.getBankAcct())) {
                result.append(SEPARATOR);
                result.append(invoice.getBank() + " ");
                result.append(invoice.getBankAcct());
            }
        }
        return solveForInput(result.toString());
    }

    private String solveForInput(String src) {
        return src.replaceAll("([a-zA-Z])","$1\n");
    }

}
