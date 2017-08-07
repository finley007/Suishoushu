package com.changyi.fi.dao;

import com.changyi.fi.model.EnterpriseHistoryPO;
import com.changyi.fi.model.EnterprisePO;
import com.changyi.fi.model.InvoicePO;
import com.changyi.fi.model.VInvoicePO;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

public interface InvoiceDao {

    List<VInvoicePO> listInvoices(@Param("openId") String openId, @Param("status") String status);

    VInvoicePO getInvoiceById(@Param(("id")) String id);

    @Select("SELECT COUNT(*) FROM ENTERPRISE T WHERE T.CREDIT_CODE = #{creditCode}")
    @Result(javaType = Long.class)
    public Long countEnterpriseById(@Param("creditCode") String creditCode);

    public EnterprisePO getEnterpriseById(@Param("creditCode") String creditCode);

    @Delete("DELETE FROM INVOICE WHERE ID = #{id}")
    public void deleteInvoice(@Param(("id")) String id);

    @Update("UPDATE INVOICE T SET T.IS_DEFAULT = #{value} where T.OPEN_ID = #{openId} and T.ID <> #{id}")
    public void updateNotDefault(@Param(("openId")) String openId, @Param(("id")) String id, @Param(("value")) String value);

    int insert(InvoicePO record);

    int insertSelective(InvoicePO record);

    int updateSelective(InvoicePO record);

    int update(InvoicePO record);

    int insertEnterprise(EnterprisePO record);

    int insertEnterpriseSelective(EnterprisePO record);

    int updateEnterpriseSelective(EnterprisePO record);

    int updateEnterprise(EnterprisePO record);

    int insertEnterpriseHistory(EnterpriseHistoryPO record);

    int insertEnterpriseHistorySelective(EnterpriseHistoryPO record);

    @Select("SELECT CREDIT_CODE AS CREDITCODE, NAME FROM ENTERPRISE T WHERE T.NAME LIKE CONCAT('%',#{key},'%')")
    @Result(javaType = Map.class)
    List<Map> matchEnterpriseList(@Param("key") String key);
}