package com.changyi.fi.component.customer.service;

import com.changyi.fi.core.LogUtil;
import com.changyi.fi.core.annotation.Validate;
import com.changyi.fi.core.seq.ISeqCreator;
import com.changyi.fi.core.seq.SeqCreatorBuilder;
import com.changyi.fi.dao.CustomerDao;
import com.changyi.fi.model.ChannelPO;
import com.changyi.fi.model.ChannelPOExample;
import com.changyi.fi.model.CustomerLoginPO;
import com.changyi.fi.model.CustomerPO;
import com.changyi.fi.util.FIConstants;
import com.changyi.fi.vo.Channel;
import com.changyi.fi.vo.Customer;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("customerService")
public class CustomerServiceImpl implements CustomerService {

    private static final int CHANNEL_SEQ_LENGTH = 6;

    private CustomerDao customerDao;

    private ISeqCreator seqCreator = SeqCreatorBuilder.build(SeqCreatorBuilder.SEQ_CREATOR_RANDOWM);

    @Autowired(required = true)
    public void setCustomerDao(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Validate
    public void updateCustomer(Customer info, String openId, boolean isLogin) throws Exception {
        LogUtil.info(this.getClass(), "Update customer info in DB");
        CustomerPO po = new CustomerPO();
        po.setCity(info.getCity());
        po.setCountry(info.getCountry());
        po.setGender(Short.valueOf(info.getGendar()));
        po.setNickName(info.getNickName());
        po.setLastLoginTime(new Date());
        po.setProvince(info.getProvince());
        po.setOpenId(openId);
        if (customerDao.countCustomerById(openId) > 0) {
            customerDao.updateCustomerSelective(po);
        } else {
            po.setCreateTime(new Date());
            customerDao.insertCustomer(po);
        }

        if (isLogin) {
            CustomerLoginPO loginPO = new CustomerLoginPO();
            loginPO.setOpenId(openId);
            loginPO.setLoginTime(new Date());
            customerDao.insertCustomerLogin(loginPO);
        }
    }

    @Validate
    public String updateChannel(Channel channel) throws Exception {
        LogUtil.info(this.getClass(), "Update channel info");
        ChannelPO channelPO = null;
        ChannelPOExample query = new ChannelPOExample();
        if (StringUtils.isNotBlank(channel.getId())) {
            query.createCriteria().andIdEqualTo(channel.getId());
            List<ChannelPO> poList = customerDao.selectChannelByExample(query);
            if (poList != null && poList.size() > 0) {
                channelPO = poList.get(0);
            }
        }
        if (channelPO != null) {
            LogUtil.info(this.getClass(), "The channel: " + channel.getId() + " is existed");
        } else {
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
        if (channelPO != null) {
            customerDao.updateChannelByExampleSelective(channelPO, query);
        } else {
            channelPO.setCreateTime(new Date());
            channelPO.setStatus(FIConstants.ChannelStatus.Normal.getValue());
            channelPO.setStatusTime(new Date());
            channelPO.setLevel(FIConstants.CHANNEL_LEVEL_1);
            channelPO.setRank(FIConstants.CHANNEL_RANK_1);
            customerDao.insertChannel(channelPO);
        }
        return channel.getId();
    }

    private synchronized String createChannelId() {
        return seqCreator.createSeq(CHANNEL_SEQ_LENGTH);
    }

    public List<Channel> listChannel() throws Exception {
        LogUtil.info(this.getClass(), "List all the channels");
        List<Channel> result = new ArrayList<Channel>();
        List<ChannelPO> poList = customerDao.selectChannelByExample(new ChannelPOExample());
        if (poList != null && poList.size() > 0) {
            for (ChannelPO po : poList) {
                result.add(new Channel(po));
            }
        }
        return result;
    }
}
