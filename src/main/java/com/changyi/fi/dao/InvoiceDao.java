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

    @Select("select count(*) from enterprise t where t.credit_code = #{creditCode}")
    @Result(javaType = Long.class)
    public Long countEnterpriseById(@Param("creditCode") String creditCode);

    public EnterprisePO getEnterpriseById(@Param("creditCode") String creditCode);

    @Delete("delete from invoice where id = #{id}")
    public void deleteInvoice(@Param(("id")) String id);

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

    @Select("select credit_code, name from enterprise t where t.name like concat('%',#{key},'%')")
    @Result(javaType = Map.class)
    List<Map> matchEnterpriseList(@Param("key") String key);
}