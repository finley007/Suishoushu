package com.changyi.fi.dao;

import com.changyi.fi.model.MerchantPO;
import com.changyi.fi.model.MerchantVisitPO;
import org.apache.ibatis.annotations.Param;

public interface MerchantDao {

    MerchantPO getMerchantById(@Param(("id")) String id);

    int insertMerchantVisit(MerchantVisitPO po);
}