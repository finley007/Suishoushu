package com.changyi.fi.component.merchant.service;

import com.changyi.fi.component.merchant.request.MerchantValidateRequest;
import com.changyi.fi.core.CommonUtil;
import com.changyi.fi.core.LogUtil;
import com.changyi.fi.core.annotation.Validate;
import com.changyi.fi.core.config.ConfigDic;
import com.changyi.fi.core.config.ConfigManager;
import com.changyi.fi.core.tool.Properties;
import com.changyi.fi.core.tool.QRCodeUtils;
import com.changyi.fi.dao.MerchantDao;
import com.changyi.fi.exception.MerchantNotFoundException;
import com.changyi.fi.external.weixin.WeixinAPIService;
import com.changyi.fi.model.MerchantPO;
import com.changyi.fi.model.MerchantVisitPO;
import com.changyi.fi.vo.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.math.BigDecimal;
import java.util.Date;

@Service("merchantService")
public class MerchantServiceImpl implements MerchantService {

    private static final String QRCODE_BASE_PATH = "qrcode.base.path";
    private static final String QRCODE_ROOT_URL = "qrcode.root.url";
    private static final String QRCODE_EXTEND = "qrcode.extend";

    private static final int SWITCH_DO_VALIDATION = 1;
    private static final short MERCHANT_VALICATION_RESULT_SUCCESS = 0;
    private static final short MERCHANT_VALICATION_RESULT_FAIL = 1;

    private MerchantDao merchantDao;

    @Resource
    private WeixinAPIService weixinAPIService;

    @Autowired(required = true)
    public void setInvoiceDao(MerchantDao merchantDao) {
        this.merchantDao = merchantDao;
    }

    @Validate
    public boolean validate(MerchantValidateRequest req, String openId) throws Exception {
        LogUtil.info(this.getClass(), "Execute validate service for: " + openId);
        MerchantPO merchantPO = this.merchantDao.getMerchantById(req.getId());
        if (merchantPO == null) {
            throw new MerchantNotFoundException("Merchant: " + req.getId() + " not found");
        }
        return isValidate(merchantPO, req.getPosition());
    }


    private Boolean isValidate(MerchantPO merchantPO, Position position) throws Exception {
        if (ConfigManager.getBooleanParameter(ConfigDic.MERCHANT_VALIDATION_TOGGLE, true)) {
            LogUtil.info(this.getClass(), "Merchant validation is on");
            if (merchantPO.getDoValidate() == SWITCH_DO_VALIDATION) {
                Double validDistance = ConfigManager.getDoubleParameter(ConfigDic.MERCHANT_VALID_DISTANCE, 50d);
                Position merchantPosition = new Position(merchantPO.getLongitude().doubleValue(), merchantPO.getLetitude().doubleValue());
                LogUtil.debug(this.getClass(), "Merchant position: " + merchantPosition.toJson());
                LogUtil.debug(this.getClass(), "Current position: " + position.toJson());
                LogUtil.debug(this.getClass(), "Valid distance: " + validDistance);
                if (validDistance < CommonUtil.getDistance(merchantPosition, position)) {
                    return false;
                }
            } else {
                LogUtil.info(this.getClass(), "The current merchant validation switch is off");
            }
        } else {
            LogUtil.info(this.getClass(), "Merchant validation switch is off");
        }
        return true;
    }

    @Validate
    public void recordVisit(MerchantValidateRequest req, String openId, boolean success) throws Exception {
        LogUtil.info(this.getClass(), "Execute doRecord service for: " + openId + " and merchant: " + req.getId());
        MerchantVisitPO po = new MerchantVisitPO();
        po.setCreateTime(new Date());
        po.setOpenId(openId);
        po.setMerchantId(req.getId());
        po.setLetitude(BigDecimal.valueOf(req.getPosition().getLatitude()));
        po.setLongitude(BigDecimal.valueOf(req.getPosition().getLongitude()));
        if (success) {
            po.setResult(MERCHANT_VALICATION_RESULT_SUCCESS);
        } else {
            po.setResult(MERCHANT_VALICATION_RESULT_SUCCESS);
        }
        this.merchantDao.insertMerchantVisit(po);
    }

    public String createQRCode(String merchantId) throws Exception {
        LogUtil.info(this.getClass(), "Execute createQRCode service for: " + merchantId);
        MerchantPO merchantPO = this.merchantDao.getMerchantById(merchantId);
        if (merchantPO == null) {
            throw new MerchantNotFoundException("Merchant: " + merchantId + " not found");
        }
        QRCodeURI codeURI = createQRCodeFile(merchantId);
        return codeURI.getUrl();
    }

    private QRCodeURI createQRCodeFile(String merchantId) throws Exception {
        QRCodeURI codeURI = createQRCodeDownloadPath(merchantId);
        File file = new File(codeURI.getPath());
        if (!file.exists()) {
            file.delete();
        }
        weixinAPIService.createMerchantQRCode(merchantId, codeURI.getPath());
        QRCodeUtils.modifyPermission(file.getParentFile());
        return codeURI;
    }

    private QRCodeURI createQRCodeDownloadPath(String merchantId) {
        String extend = Properties.get(QRCODE_EXTEND);
        String path = Properties.get(QRCODE_BASE_PATH) + "/" + merchantId + "/qrcode." + extend;
        String url = Properties.get(QRCODE_ROOT_URL) + "/" + merchantId + "/qrcode." + extend;
        return new QRCodeURI(path, url);
    }

    private static class QRCodeURI {

        public QRCodeURI(String path, String url) {
            this.path = path;
            this.url = url;
        }

        private String path;

        private String url;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
