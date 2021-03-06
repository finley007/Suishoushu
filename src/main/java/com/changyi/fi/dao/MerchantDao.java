package com.changyi.fi.dao;

import com.changyi.fi.model.ChannelPO;
import com.changyi.fi.model.ChannelPOExample;
import com.changyi.fi.model.MerchantPO;
import com.changyi.fi.model.MerchantVisitPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MerchantDao {

    MerchantPO getMerchantById(@Param(("id")) String id);

    ChannelPO getChannelById(@Param(("id")) String id);

    List<MerchantPO> getMerchantByChannel(@Param(("channelId")) String channelId);

    int insertMerchant(MerchantPO record);

    int updateMerchantSelective(MerchantPO record);

    int insertMerchantVisit(MerchantVisitPO po);

    List<ChannelPO> selectChannelByExample(ChannelPOExample example);

    int insertChannel(ChannelPO record);

    int updateChannelByExampleSelective(@Param("record") ChannelPO record, @Param("example") ChannelPOExample example);
}