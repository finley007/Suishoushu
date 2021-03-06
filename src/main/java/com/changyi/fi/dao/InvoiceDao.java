package com.changyi.fi.dao;

import com.changyi.fi.model.*;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

public interface InvoiceDao {

    List<VInvoicePO> listInvoices(@Param("openId") String openId, @Param("status") String status);

    VInvoicePO getInvoiceById(@Param(("id")) String id);

    @Select("SELECT COUNT(*) FROM inc_enterprise T WHERE T.credit_code = #{creditCode}")
    @Result(javaType = Long.class)
    public Long countEnterpriseById(@Param("creditCode") String creditCode);

    @Select("SELECT COUNT(*) FROM inc_enterprise T WHERE T.name = #{name}")
    @Result(javaType = Long.class)
    public Long countEnterpriseByName(@Param("name") String name);

    @Select("SELECT COUNT(*) FROM inc_invoice t WHERE t.open_id = #{openId} and t.status = 0")
    @Result(javaType = Long.class)
    public Long countInvoiceByCustomer(@Param("openId") String openId);

    @Select("SELECT id FROM inc_invoice t WHERE t.open_id = #{openId} and t.credit_code = #{creditCode} and t.status = 0")
    @Result(javaType = String.class)
    public String getInvoiceByEnterpriceId(@Param("openId") String openId, @Param("creditCode") String creditCode);

    public EnterprisePO getEnterpriseById(@Param("creditCode") String creditCode);

    @Delete("DELETE FROM inc_invoice WHERE id = #{id}")
    public void deleteInvoice(@Param(("id")) String id);

    @Update("UPDATE inc_invoice T SET T.is_default = #{value} where T.open_id = #{openId} and T.id <> #{id}")
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

    @Select("SELECT credit_code AS CREDITCODE, name AS NAME FROM inc_enterprise T WHERE T.name LIKE CONCAT('%',#{key},'%') LIMIT 0, #{count}")
    @Result(javaType = Map.class)
    List<Map> matchEnterpriseList(@Param("key") String key, @Param("count") int count);

    @Select("SELECT id FROM inc_invoice T WHERE T.open_id = #{openId} and T.status = 0")
    @Result(javaType = Integer.class)
    List<Integer> getInvoiceIdByCustomer(@Param("openId") String openId);

    int insertCustomerQRCode(CustomerQRCodePO record);
}