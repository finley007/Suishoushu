package com.changyi.fi.model;

import java.util.Date;

public class CustomerLoginPO {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column inc_customer_login.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column inc_customer_login.open_id
     *
     * @mbggenerated
     */
    private String openId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column inc_customer_login.login_time
     *
     * @mbggenerated
     */
    private Date loginTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column inc_customer_login.id
     *
     * @return the value of inc_customer_login.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column inc_customer_login.id
     *
     * @param id the value for inc_customer_login.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column inc_customer_login.open_id
     *
     * @return the value of inc_customer_login.open_id
     *
     * @mbggenerated
     */
    public String getOpenId() {
        return openId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column inc_customer_login.open_id
     *
     * @param openId the value for inc_customer_login.open_id
     *
     * @mbggenerated
     */
    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column inc_customer_login.login_time
     *
     * @return the value of inc_customer_login.login_time
     *
     * @mbggenerated
     */
    public Date getLoginTime() {
        return loginTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column inc_customer_login.login_time
     *
     * @param loginTime the value for inc_customer_login.login_time
     *
     * @mbggenerated
     */
    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }
}