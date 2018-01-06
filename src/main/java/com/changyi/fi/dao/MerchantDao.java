package com.changyi.fi.dao;

import com.changyi.fi.model.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MerchantDao {

    MerchantPO getMerchantById(@Param(("id")) String id);

    int insertMerchantVisit(MerchantVisitPO po);

    List<ChannelPO> selectChannelByExample(ChannelPOExample example);

    int insertMerchant(MerchantPO record);

    int updateMerchantByExampleSelective(MerchantPO record, MerchantPOExample example);
}