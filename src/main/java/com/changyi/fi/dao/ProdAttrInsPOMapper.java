package com.changyi.fi.dao;

import com.changyi.fi.model.ProdAttrInsPO;
import com.changyi.fi.model.ProdAttrInsPOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProdAttrInsPOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table inc_prod_attr_ins
     *
     * @mbggenerated
     */
    int deleteByExample(ProdAttrInsPOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table inc_prod_attr_ins
     *
     * @mbggenerated
     */
    int insert(ProdAttrInsPO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table inc_prod_attr_ins
     *
     * @mbggenerated
     */
    int insertSelective(ProdAttrInsPO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table inc_prod_attr_ins
     *
     * @mbggenerated
     */
    List<ProdAttrInsPO> selectByExample(ProdAttrInsPOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table inc_prod_attr_ins
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") ProdAttrInsPO record, @Param("example") ProdAttrInsPOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table inc_prod_attr_ins
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") ProdAttrInsPO record, @Param("example") ProdAttrInsPOExample example);
}