package com.changyi.fi.dao;

import com.changyi.fi.model.EnterprisePO;
import com.changyi.fi.model.EnterprisePOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EnterprisePOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ENTERPRISE
     *
     * @mbggenerated
     */
    int insert(EnterprisePO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ENTERPRISE
     *
     * @mbggenerated
     */
    int insertSelective(EnterprisePO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ENTERPRISE
     *
     * @mbggenerated
     */
    List<EnterprisePO> selectByExampleWithBLOBs(EnterprisePOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ENTERPRISE
     *
     * @mbggenerated
     */
    List<EnterprisePO> selectByExample(EnterprisePOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ENTERPRISE
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") EnterprisePO record, @Param("example") EnterprisePOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ENTERPRISE
     *
     * @mbggenerated
     */
    int updateByExampleWithBLOBs(@Param("record") EnterprisePO record, @Param("example") EnterprisePOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ENTERPRISE
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") EnterprisePO record, @Param("example") EnterprisePOExample example);
}