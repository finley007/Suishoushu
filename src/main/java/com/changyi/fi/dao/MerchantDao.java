package com.changyi.fi.dao;

import com.changyi.fi.model.MerchantInvoicePO;
import com.changyi.fi.model.MerchantPO;
import org.apache.ibatis.annotations.Param;

public interface MerchantDao {

    MerchantPO getMerchantById(@Param(("id")) String id);

    int insertMerchantInvoice(MerchantInvoicePO po);

    int insertMerchantInvoiceSelective(MerchantInvoicePO po);
}