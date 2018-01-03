package com.changyi.fi.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface StatisticsDao {

    @Select("select count(*) from (select t1.nick_name, count(*) from inc_customer_login t, inc_customer t1 where t1.open_id = t.open_id group by t1.nick_name) v")
    @Result(javaType = Long.class)
    public Long countTotalCustomer();

    @Select("select count(*) from (select t1.nick_name, count(*) from inc_customer_login t, inc_customer t1 where t1.open_id = t.open_id and t.login_time < #{limitDate} group by t1.nick_name) v")
    @Result(javaType = Long.class)
    public Long countInactiveCustomer(@Param("limitDate") String limitDate);

    @Select("select count(*) from inc_customer_login where login_time > #{startDate} and login_time < #{endDate}")
    @Result(javaType = Long.class)
    public Long countCustomerLogin(@Param("startDate") String startDate, @Param("endDate") String endDate);

    @Select("select count(*) from inc_customer where create_time > #{startDate} and create_time < #{endDate}")
    @Result(javaType = Long.class)
    public Long countCustomerRegister(@Param("startDate") String startDate, @Param("endDate") String endDate);

    @Select("select count(*) as count, t1.nick_name from inc_customer_login t, inc_customer t1 where t.open_id = t1.open_id group by t1.nick_name order by count desc limit #{topN}")
    @Result(javaType = Map.class)
    public List<Map> getCustomerStat(@Param("topN") Integer topN);

    @Select("select count(*), count as login_count from (select count(*) as count, t1.nick_name from inc_customer_login t, inc_customer t1 where t.open_id = t1.open_id group by t1.nick_name order by count) v group by v.count")
    @Result(javaType = Map.class)
    public List<Map> getCustomerDistribution();

    @Select("select t.name, v.count from (select count(*) as count, credit_code from inc_customer_qrcode group by credit_code) v, inc_enterprise t where t.credit_code = v.credit_code order by count desc limit #{topN}")
    @Result(javaType = Map.class)
    public List<Map> getEnterpriseStat(@Param("topN") Integer topN);

    @Select("select count(*), count as open_count from (select t.name, v.count from (select count(*) as count, credit_code from inc_customer_qrcode group by credit_code) v, inc_enterprise t where t.credit_code = v.credit_code) v1 group by v1.count")
    @Result(javaType = Map.class)
    public List<Map> getEnterpriseDistribution();

    @Select("select count(*), domain from sys_outbound where call_time > #{startDate} and call_time < #{endDate} group by domain")
    @Result(javaType = Map.class)
    public List<Map> getExternalAPIStat(@Param("startDate") String startDate, @Param("endDate") String endDate);

    @Select("select count(*) from inc_enterprise t where t.create_time > #{startDate} and create_time < #{endDate}")
    @Result(javaType = Long.class)
    public Long getEnterpiseSycNum(@Param("startDate") String startDate, @Param("endDate") String endDate);

    @Select("select count(*) as count, merchant_id as merchant, result from inc_merchant_visit where create_time > #{startDate} and create_time < #{endDate} group by merchant_id, result")
    @Result(javaType = Map.class)
    public List<Map> getMerchantStat(@Param("startDate") String startDate, @Param("endDate") String endDate);
}
