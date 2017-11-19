package com.changyi.fi.dao;

import com.changyi.fi.model.MerchantPO;
import com.changyi.fi.model.MerchantPOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MerchantPOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table inc_merchant
     *
     * @mbggenerated
     */
    int insert(MerchantPO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table inc_merchant
     *
     * @mbggenerated
     */
    int insertSelective(MerchantPO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table inc_merchant
     *
     * @mbggenerated
     */
    List<MerchantPO> selectByExample(MerchantPOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table inc_merchant
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") MerchantPO record, @Param("example") MerchantPOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table inc_merchant
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") MerchantPO record, @Param("example") MerchantPOExample example);
}