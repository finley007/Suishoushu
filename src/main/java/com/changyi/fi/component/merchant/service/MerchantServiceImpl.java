package com.changyi.fi.component.merchant.service;

import com.changyi.fi.exception.InvalidMerchantException;
import com.changyi.fi.vo.Merchant;
import com.changyi.fi.component.merchant.request.MerchantValidateRequest;
import com.changyi.fi.core.CommonUtil;
import com.changyi.fi.core.LogUtil;
import com.changyi.fi.core.annotation.Validate;
import com.changyi.fi.core.config.ConfigDic;
import com.changyi.fi.core.config.ConfigManager;
import com.changyi.fi.core.seq.ISeqCreator;
import com.changyi.fi.core.seq.SeqCreatorBuilder;
import com.changyi.fi.core.tool.Properties;
import com.changyi.fi.core.tool.QRCodeUtils;
import com.changyi.fi.dao.MerchantDao;
import com.changyi.fi.exception.ChannelNotFoundException;
import com.changyi.fi.exception.MerchantNotFoundException;
import com.changyi.fi.external.weixin.WeixinAPIService;
import com.changyi.fi.model.ChannelPO;
import com.changyi.fi.model.ChannelPOExample;
import com.changyi.fi.model.MerchantPO;
import com.changyi.fi.model.MerchantVisitPO;
import com.changyi.fi.util.FIConstants;
import com.changyi.fi.vo.Channel;
import com.changyi.fi.vo.Position;
import com.changyi.fi.exception.AuthenticationFailedException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("merchantService")
public class MerchantServiceImpl implements MerchantService {

    private static final int MERCHANR_ID_LENGTH = 11;
    private static final int CHANNEL_SEQ_LENGTH = 6;

    private static final String QRCODE_BASE_PATH = "qrcode.base.path";
    private static final String QRCODE_ROOT_URL = "qrcode.root.url";
    private static final String QRCODE_EXTEND = "qrcode.extend";

    private static final int SWITCH_DO_VALIDATION = 1;
    private static final short MERCHANT_VALICATION_RESULT_SUCCESS = 0;
    private static final short MERCHANT_VALICATION_RESULT_FAIL = 1;

    private ISeqCreator seqCreator = SeqCreatorBuilder.build(SeqCreatorBuilder.SEQ_CREATOR_RANDOWM);

    private MerchantDao merchantDao;

    @Resource
    private WeixinAPIService weixinAPIService;

    @Autowired(required = true)
    public void setInvoiceDao(MerchantDao merchantDao) {
        this.merchantDao = merchantDao;
    }

    public MerchantPO getMerchantById(String merchantId) throws Exception {
        return this.merchantDao.getMerchantById(merchantId);
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
        //状态检查
        if (merchantPO.getStatus().equals(FIConstants.MerchantStatus.Nonactivated.getValue())) {
            throw new InvalidMerchantException("The current merchant: " +  merchantPO.getId() + " is not activated");
        }
        if (merchantPO.getStatus().equals(FIConstants.MerchantStatus.Invalid.getValue())) {
            throw new InvalidMerchantException("The current merchant: " + merchantPO.getId() + " is invalid");
        }
        //过期时间检查
        if (merchantPO.getExpireTime() != null && merchantPO.getExpireTime().compareTo(new Date()) < 0) {
            throw new InvalidMerchantException("The current merchant: " + merchantPO.getId() + " is over expired date: " + FIConstants.sdf.format(merchantPO.getExpireTime()));
        }
        if (ConfigManager.getBooleanParameter(ConfigDic.MERCHANT_VALIDATION_TOGGLE, true)) {
            LogUtil.info(this.getClass(), "Merchant validation is on");
            if (merchantPO.getDoValidate() == SWITCH_DO_VALIDATION) {
                Double validDistance = ConfigManager.getDoubleParameter(ConfigDic.MERCHANT_VALID_DISTANCE, 50d);
                Position merchantPosition = new Position(merchantPO.getLongitude().doubleValue(), merchantPO.getLetitude().doubleValue());
                LogUtil.debug(this.getClass(), "Merchant position: " + merchantPosition.toJson());
                LogUtil.debug(this.getClass(), "Current position: " + position.toJson());
                LogUtil.debug(this.getClass(), "Valid distance: " + validDistance);
                double actualDistance = CommonUtil.getDistance(merchantPosition, position);
                LogUtil.debug(this.getClass(), "Actual distance: " + actualDistance);
                if (validDistance < actualDistance) {
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
            po.setResult(MERCHANT_VALICATION_RESULT_FAIL);
        }
        this.merchantDao.insertMerchantVisit(po);
    }

    public List<String> createMerchantIds(int idNum) throws Exception {
        LogUtil.info(this.getClass(),"Create merchant id for number: " + idNum);
        List<String> idList = new ArrayList<String>();
        for (int i = 0; i < idNum; i++) {
            String id = SeqCreatorBuilder.build(SeqCreatorBuilder.SEQ_CREATOR_RANDOWM).createSeq(MERCHANR_ID_LENGTH);
            LogUtil.debug(this.getClass(), "Create merchant id: " + id);
            idList.add(id);
        }
        return idList;
    }

    public List<String> createMerchants(int idNum, String channelId) throws Exception {
        verifyChannel(channelId);
        List<String> ids = this.createMerchantIds(idNum);
        LogUtil.debug(this.getClass(), "Create merchant for the channel: " + channelId);
        for (int i = 0; i < ids.size(); i++) {
            MerchantPO po = new MerchantPO();
            po.setId(ids.get(i));
            po.setChannelId(channelId);
            po.setCreateBy(FIConstants.SYSTEM);
            po.setCreateTime(new Date());
            po.setModifyBy(FIConstants.SYSTEM);
            po.setModifyTime(new Date());
            po.setExpireTime(FIConstants.sdf.parse(FIConstants.DEFAULT_MERCHANT_EXPIRED_TIME));
            po.setLetitude(FIConstants.INIT_POSITION);
            po.setLongitude(FIConstants.INIT_POSITION);
            po.setName(FIConstants.FIELD_NAME);
            po.setAddress(FIConstants.FIELD_ADDRESS);
            po.setType(FIConstants.MERCHANT_TYPE_DEFAULT);
            //畅移信息创建的商户直接激活
            if (FIConstants.CHANGYI_CHANNEL_ID.equals(channelId)) {
                po.setStatus(FIConstants.MerchantStatus.Activated.getValue());
            } else {
                po.setStatus(FIConstants.MerchantStatus.Nonactivated.getValue());
            }
            po.setDoValidate(FIConstants.DoMerchantValidation.False.getShortValue());
            merchantDao.insertMerchant(po);
        }
        return ids;
    }

    public void verifyChannel(String channelId) throws ChannelNotFoundException {
        ChannelPOExample query = new ChannelPOExample();
        query.createCriteria().andIdEqualTo(channelId);
        List<ChannelPO> channels = merchantDao.selectChannelByExample(query);
        if (channels == null || channels.size() == 0) {
            throw new ChannelNotFoundException("Channel not found: " + channelId);
        }
    }

    public String createChannelQRCode(String channelId) throws Exception {
        ChannelPO channelPO = merchantDao.getChannelById(channelId);
        if (channelPO == null) {
            throw new ChannelNotFoundException("Channel： " + channelId + " not found");
        }
        return createChannelMerchant(channelPO);
    }

    @Validate
    public Channel updateChannel(Channel channel) throws Exception {
        LogUtil.info(this.getClass(), "Update channel info");
        ChannelPO channelPO = null;
        ChannelPOExample query = new ChannelPOExample();
        if (StringUtils.isNotBlank(channel.getId())) {
            query.createCriteria().andIdEqualTo(channel.getId());
            List<ChannelPO> poList = merchantDao.selectChannelByExample(query);
            if (poList != null && poList.size() > 0) {
                channelPO = poList.get(0);
            }
        }
        boolean isNew = false;
        if (channelPO != null) {
            LogUtil.info(this.getClass(), "The channel: " + channel.getId() + " is existed");
        } else {
            isNew = true;
            channelPO = new ChannelPO();
            channelPO.setId(createChannelId());
        }
        channelPO.setAddress(channel.getAddress());
        channelPO.setCity(channel.getCity());
        channelPO.setEmail(channel.getEmail());
        channelPO.setName(channel.getName());
        channelPO.setPhone(channel.getPhone());
        channelPO.setProv(channel.getProvince());
        channelPO.setQq(channel.getQq());
        channelPO.setWechat(channel.getWechat());
        channelPO.setRemark(channel.getRemark());
        channelPO.setUpdateTime(new Date());
        if (!isNew) {
            merchantDao.updateChannelByExampleSelective(channelPO, query);
        } else {
            channelPO.setCreateTime(new Date());
            channelPO.setStatus(FIConstants.ChannelStatus.Normal.getValue());
            channelPO.setStatusTime(new Date());
            channelPO.setLevel(FIConstants.CHANNEL_LEVEL_1);
            channelPO.setRank(FIConstants.CHANNEL_RANK_1);
            merchantDao.insertChannel(channelPO);

            //新建渠道自动建立渠道自身商户，并生成试用二维码
            channel.setUrl(createChannelMerchant(channelPO));
            channel.setId(channelPO.getId());
        }
        return channel;
    }

    public String createChannelMerchant(ChannelPO channelPO) throws Exception {
        MerchantPO merchantPO = new MerchantPO();
        merchantPO.setId(channelPO.getId());
        merchantPO.setChannelId(channelPO.getId());
        merchantPO.setName(channelPO.getName());
        merchantPO.setAddress(channelPO.getAddress());
        merchantPO.setPhone1(channelPO.getPhone());
        merchantPO.setEmail(channelPO.getEmail());
        merchantPO.setType(FIConstants.MERCHANT_TYPE_CHANNEL);
        merchantPO.setLongitude(FIConstants.INIT_POSITION);
        merchantPO.setLetitude(FIConstants.INIT_POSITION);
        merchantPO.setStatus(FIConstants.MerchantStatus.Activated.getValue());
        merchantPO.setDoValidate(FIConstants.DoMerchantValidation.False.getShortValue());
        merchantPO.setCreateBy(FIConstants.SYSTEM);
        merchantPO.setCreateTime(new Date());
        merchantPO.setModifyBy(FIConstants.SYSTEM);
        merchantPO.setModifyTime(new Date());
        merchantDao.insertMerchant(merchantPO);

        return this.createQRCode(merchantPO.getId());
    }

    private synchronized String createChannelId() {
        return seqCreator.createSeq(CHANNEL_SEQ_LENGTH);
    }

    public List<Channel> listChannel() throws Exception {
        LogUtil.info(this.getClass(), "List all the channels");
        List<Channel> result = new ArrayList<Channel>();
        List<ChannelPO> poList = merchantDao.selectChannelByExample(new ChannelPOExample());
        if (poList != null && poList.size() > 0) {
            for (ChannelPO po : poList) {
                Channel channel = new Channel(po);
                channel.setUrl(this.createQRCodeDownloadPath(channel.getId()).getUrl());
                result.add(channel);
            }
        }
        return result;
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

    @Validate
    public void merchantRegister(Merchant req) throws Exception {
        MerchantPO merchantPO = merchantDao.getMerchantById(req.getId());
        if (merchantPO == null) {
            throw new MerchantNotFoundException("Merchant: " + req.getId() + " does not existed");
        }
        if (!merchantPO.getChannelId().equals(req.getChannelId())) {
            throw new AuthenticationFailedException("Current channel: " + req.getChannelId() + " is not merchant's channel: " + merchantPO.getChannelId());
        }
        merchantPO.setStatus(FIConstants.MerchantStatus.Activated.getValue());
        merchantPO.setType(req.getType());
        merchantPO.setAddress(req.getAddress());
        merchantPO.setName(req.getName());
        merchantPO.setEmail(req.getEmail());
        merchantPO.setPhone1(req.getPhone1());
        merchantPO.setPhone2(req.getPhone2());
        merchantPO.setZipCode(req.getZipCode());
        merchantPO.setLongitude(new BigDecimal(req.getLongitude()));
        merchantPO.setLetitude(new BigDecimal(req.getLatitude()));
        merchantPO.setModifyBy(FIConstants.SYSTEM);
        merchantPO.setModifyTime(new Date());
        merchantPO.setDoValidate(FIConstants.DoMerchantValidation.True.getShortValue());
        merchantDao.updateMerchantSelective(merchantPO);
    }

    public List<MerchantPO> getMerchantsByChannel(String channelId) throws Exception {
        List<MerchantPO> result = merchantDao.getMerchantByChannel(channelId);
        for (MerchantPO po : result) {
            po.setQrcode(this.createQRCodeDownloadPath(po.getId()).getUrl());
        }
        return result;
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
