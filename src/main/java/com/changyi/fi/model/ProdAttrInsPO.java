package com.changyi.fi.model;

import java.util.Date;

public class ProdAttrInsPO {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column inc_prod_attr_ins.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column inc_prod_attr_ins.attr_id
     *
     * @mbggenerated
     */
    private String attrId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column inc_prod_attr_ins.prod_id
     *
     * @mbggenerated
     */
    private String prodId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column inc_prod_attr_ins.value
     *
     * @mbggenerated
     */
    private String value;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column inc_prod_attr_ins.create_time
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column inc_prod_attr_ins.id
     *
     * @return the value of inc_prod_attr_ins.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column inc_prod_attr_ins.id
     *
     * @param id the value for inc_prod_attr_ins.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column inc_prod_attr_ins.attr_id
     *
     * @return the value of inc_prod_attr_ins.attr_id
     *
     * @mbggenerated
     */
    public String getAttrId() {
        return attrId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column inc_prod_attr_ins.attr_id
     *
     * @param attrId the value for inc_prod_attr_ins.attr_id
     *
     * @mbggenerated
     */
    public void setAttrId(String attrId) {
        this.attrId = attrId == null ? null : attrId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column inc_prod_attr_ins.prod_id
     *
     * @return the value of inc_prod_attr_ins.prod_id
     *
     * @mbggenerated
     */
    public String getProdId() {
        return prodId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column inc_prod_attr_ins.prod_id
     *
     * @param prodId the value for inc_prod_attr_ins.prod_id
     *
     * @mbggenerated
     */
    public void setProdId(String prodId) {
        this.prodId = prodId == null ? null : prodId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column inc_prod_attr_ins.value
     *
     * @return the value of inc_prod_attr_ins.value
     *
     * @mbggenerated
     */
    public String getValue() {
        return value;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column inc_prod_attr_ins.value
     *
     * @param value the value for inc_prod_attr_ins.value
     *
     * @mbggenerated
     */
    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column inc_prod_attr_ins.create_time
     *
     * @return the value of inc_prod_attr_ins.create_time
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column inc_prod_attr_ins.create_time
     *
     * @param createTime the value for inc_prod_attr_ins.create_time
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}