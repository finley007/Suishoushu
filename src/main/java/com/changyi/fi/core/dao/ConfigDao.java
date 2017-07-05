package com.changyi.fi.core.dao;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * Created by finley on 2/8/17.
 */
public interface ConfigDao {

    @Select("SELECT * from SYS_EXCEPTION")
    @Result(javaType = Map.class)
    @Options(useCache = true)
    public List<Map> getExceptionCode();

    @Select("SELECT * FROM SYS_PARAMETER")
    @Result(javaType = Map.class)
    @Options(useCache = true)
    public List<Map> getSysParameter();

}
