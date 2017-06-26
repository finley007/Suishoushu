package com.changyi.fi.dao;

import com.changyi.fi.model.EnterpriseHistoryPO;
import com.changyi.fi.model.EnterprisePO;
import com.changyi.fi.model.InvoicePO;
import com.changyi.fi.model.VInvoicePO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface InvoiceDao {

    @Select("select * from v_invoice t where t.open_id = #{openId} order by t.is_default, t.modify_time")
    @Result(javaType = VInvoicePO.class)
    public List<VInvoicePO> listInvoices(@Param("openId") String openId);

    @Select("select * from v_invoice t where t.id = #{id}")
    @Result(javaType = VInvoicePO.class)
    public VInvoicePO getInvoiceById(@Param(("id")) String id);

    @Select("select count(*) from enterprise t where t.credit_code = #{creditCode}")
    @Result(javaType = Long.class)
    public Long countEnterpriseById(@Param("creditCode") String creditCode);

    @Select("select * from enterprise t where t.credit_code = #{creditCode}")
    @Result(javaType = EnterprisePO.class)
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
}