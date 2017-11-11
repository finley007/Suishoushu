package com.changyi.fi.core.dao;

import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * Created by finley on 2/8/17.
 */
public interface ConfigDao {

    @Select("SELECT * from sys_exception")
    @Result(javaType = Map.class)
    @Options(useCache = true)
    public List<Map> getExceptionCode();

    @Select("SELECT * FROM sys_parameter")
    @Result(javaType = Map.class)
    @Options(useCache = true)
    public List<Map> getSysParameter();

    @Update("UPDATE sys_parameter T SET T.value = #{value} where T.code = #{code}")
    public void updateParameterByCode(@Param(("code")) String code, @Param(("value")) String value);

}
