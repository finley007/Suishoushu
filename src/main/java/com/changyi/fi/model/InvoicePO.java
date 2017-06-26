package com.changyi.fi.model;

import java.util.Date;

public class InvoicePO {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column INVOICE.ID
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column INVOICE.OPEN_ID
     *
     * @mbggenerated
     */
    private String openId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column INVOICE.TYPE
     *
     * @mbggenerated
     */
    private Short type;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column INVOICE.USER_NAME
     *
     * @mbggenerated
     */
    private String userName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column INVOICE.CREDIT_CODE
     *
     * @mbggenerated
     */
    private String creditCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column INVOICE.CREATE_TIME
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column INVOICE.MODIFY_TIME
     *
     * @mbggenerated
     */
    private Date modifyTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column INVOICE.IS_DEFAULT
     *
     * @mbggenerated
     */
    private Short isDefault;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column INVOICE.ID
     *
     * @return the value of INVOICE.ID
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column INVOICE.ID
     *
     * @param id the value for INVOICE.ID
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column INVOICE.OPEN_ID
     *
     * @return the value of INVOICE.OPEN_ID
     *
     * @mbggenerated
     */
    public String getOpenId() {
        return openId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column INVOICE.OPEN_ID
     *
     * @param openId the value for INVOICE.OPEN_ID
     *
     * @mbggenerated
     */
    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column INVOICE.TYPE
     *
     * @return the value of INVOICE.TYPE
     *
     * @mbggenerated
     */
    public Short getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column INVOICE.TYPE
     *
     * @param type the value for INVOICE.TYPE
     *
     * @mbggenerated
     */
    public void setType(Short type) {
        this.type = type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column INVOICE.USER_NAME
     *
     * @return the value of INVOICE.USER_NAME
     *
     * @mbggenerated
     */
    public String getUserName() {
        return userName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column INVOICE.USER_NAME
     *
     * @param userName the value for INVOICE.USER_NAME
     *
     * @mbggenerated
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column INVOICE.CREDIT_CODE
     *
     * @return the value of INVOICE.CREDIT_CODE
     *
     * @mbggenerated
     */
    public String getCreditCode() {
        return creditCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column INVOICE.CREDIT_CODE
     *
     * @param creditCode the value for INVOICE.CREDIT_CODE
     *
     * @mbggenerated
     */
    public void setCreditCode(String creditCode) {
        this.creditCode = creditCode == null ? null : creditCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column INVOICE.CREATE_TIME
     *
     * @return the value of INVOICE.CREATE_TIME
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column INVOICE.CREATE_TIME
     *
     * @param createTime the value for INVOICE.CREATE_TIME
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column INVOICE.MODIFY_TIME
     *
     * @return the value of INVOICE.MODIFY_TIME
     *
     * @mbggenerated
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column INVOICE.MODIFY_TIME
     *
     * @param modifyTime the value for INVOICE.MODIFY_TIME
     *
     * @mbggenerated
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column INVOICE.IS_DEFAULT
     *
     * @return the value of INVOICE.IS_DEFAULT
     *
     * @mbggenerated
     */
    public Short getIsDefault() {
        return isDefault;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column INVOICE.IS_DEFAULT
     *
     * @param isDefault the value for INVOICE.IS_DEFAULT
     *
     * @mbggenerated
     */
    public void setIsDefault(Short isDefault) {
        this.isDefault = isDefault;
    }
}