package com.changyi.fi.model;

import java.util.Date;

public class ProdTypePO {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column inc_prod_type.id
     *
     * @mbggenerated
     */
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column inc_prod_type.pid
     *
     * @mbggenerated
     */
    private String pid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column inc_prod_type.name
     *
     * @mbggenerated
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column inc_prod_type.create_time
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column inc_prod_type.update_time
     *
     * @mbggenerated
     */
    private Date updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column inc_prod_type.remark
     *
     * @mbggenerated
     */
    private String remark;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column inc_prod_type.id
     *
     * @return the value of inc_prod_type.id
     *
     * @mbggenerated
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column inc_prod_type.id
     *
     * @param id the value for inc_prod_type.id
     *
     * @mbggenerated
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column inc_prod_type.pid
     *
     * @return the value of inc_prod_type.pid
     *
     * @mbggenerated
     */
    public String getPid() {
        return pid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column inc_prod_type.pid
     *
     * @param pid the value for inc_prod_type.pid
     *
     * @mbggenerated
     */
    public void setPid(String pid) {
        this.pid = pid == null ? null : pid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column inc_prod_type.name
     *
     * @return the value of inc_prod_type.name
     *
     * @mbggenerated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column inc_prod_type.name
     *
     * @param name the value for inc_prod_type.name
     *
     * @mbggenerated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column inc_prod_type.create_time
     *
     * @return the value of inc_prod_type.create_time
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column inc_prod_type.create_time
     *
     * @param createTime the value for inc_prod_type.create_time
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column inc_prod_type.update_time
     *
     * @return the value of inc_prod_type.update_time
     *
     * @mbggenerated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column inc_prod_type.update_time
     *
     * @param updateTime the value for inc_prod_type.update_time
     *
     * @mbggenerated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column inc_prod_type.remark
     *
     * @return the value of inc_prod_type.remark
     *
     * @mbggenerated
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column inc_prod_type.remark
     *
     * @param remark the value for inc_prod_type.remark
     *
     * @mbggenerated
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}