package com.changyi.fi.dao;

import com.changyi.fi.model.EnterpriseHistoryPO;
import com.changyi.fi.model.EnterpriseHistoryPOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EnterpriseHistoryPOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ENTERPRISE_MODIFY_HISTORY
     *
     * @mbggenerated
     */
    int insert(EnterpriseHistoryPO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ENTERPRISE_MODIFY_HISTORY
     *
     * @mbggenerated
     */
    int insertSelective(EnterpriseHistoryPO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ENTERPRISE_MODIFY_HISTORY
     *
     * @mbggenerated
     */
    List<EnterpriseHistoryPO> selectByExample(EnterpriseHistoryPOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ENTERPRISE_MODIFY_HISTORY
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") EnterpriseHistoryPO record, @Param("example") EnterpriseHistoryPOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ENTERPRISE_MODIFY_HISTORY
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") EnterpriseHistoryPO record, @Param("example") EnterpriseHistoryPOExample example);
}