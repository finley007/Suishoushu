package com.changyi.fi.component.merchant.service;

import com.changyi.fi.component.merchant.request.DoRecordRequest;
import com.changyi.fi.component.merchant.request.MerchantValidateRequest;
import com.changyi.fi.core.CommonUtil;
import com.changyi.fi.core.LogUtil;
import com.changyi.fi.core.annotation.Validate;
import com.changyi.fi.core.config.ConfigDic;
import com.changyi.fi.core.config.ConfigManager;
import com.changyi.fi.dao.MerchantDao;
import com.changyi.fi.exception.MerchantNotFoundException;
import com.changyi.fi.exception.OutOfBoundsException;
import com.changyi.fi.model.MerchantInvoicePO;
import com.changyi.fi.model.MerchantPO;
import com.changyi.fi.vo.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service("merchantService")
public class MerchantServiceImpl implements MerchantService {

    private MerchantDao merchantDao;

    @Autowired(required = true)
    public void setInvoiceDao(MerchantDao merchantDao) {
        this.merchantDao = merchantDao;
    }

    @Validate
    public void validate(MerchantValidateRequest req, String openId) throws Exception {
        LogUtil.info(this.getClass(), "Execute validate service for: " + openId);
        MerchantPO merchantPO = this.merchantDao.getMerchantById(req.getId());
        if (merchantPO == null) {
            throw new MerchantNotFoundException("Merchant: " + req.getId() + " not found");
        }
        isValidate(merchantPO, req.getPosition());
    }


    private Boolean isValidate(MerchantPO merchantPO, Position position) throws Exception {
        if (ConfigManager.getBooleanParameter(ConfigDic.MERCHANT_VALIDATION_TOGGLE, true)) {
            LogUtil.info(this.getClass(), "Merchant validation is on");
            Double validDistance = ConfigManager.getDoubleParameter(ConfigDic.MERCHANT_VALID_DISTANCE, 50d);
            Position merchantPosition = new Position(merchantPO.getLongitude().doubleValue(), merchantPO.getLetitude().doubleValue());
            LogUtil.debug(this.getClass(), "Merchant position: " + merchantPosition.toJson());
            LogUtil.debug(this.getClass(), "Current position: " + position.toJson());
            LogUtil.debug(this.getClass(), "Valid distance: " + validDistance);
            if (validDistance < CommonUtil.getDistance(merchantPosition, position)) {
                throw new OutOfBoundsException("Current QR code is out of bounds");
            }
        } else {
            LogUtil.info(this.getClass(), "Merchant validation is off");
        }
        return true;
    }

    @Validate
    public void doRecord(DoRecordRequest req, String openId) throws Exception {
        LogUtil.info(this.getClass(), "Execute doRecord service for: " + openId);
        MerchantInvoicePO po = new MerchantInvoicePO();
        po.setCreateTime(new Date());
        po.setInvoiceId(Integer.valueOf(req.getInvoiceId()));
        po.setMerchantId(Integer.valueOf(req.getMerchantId()));
        this.merchantDao.insertMerchantInvoice(po);
    }
}
