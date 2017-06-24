package com.changyi.fi.dao;

import com.changyi.fi.model.VInvoicePO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface InvoiceDao {

    @Select("select * from v_invoice t where t.open_id = #{openId} order by t.is_default, t.modify_time}")
    @Result(javaType = VInvoicePO.class)
    public List<VInvoicePO> listInvoices(@Param("openId") String openId);

    @Select("select * from v_invoice t where t.id = #{id}")
    @Result(javaType = VInvoicePO.class)
    public VInvoicePO getInvoiceById(@Param(("id")) String id);
}