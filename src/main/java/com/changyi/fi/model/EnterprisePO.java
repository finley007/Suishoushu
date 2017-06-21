package com.changyi.fi.model;

import java.math.BigDecimal;
import java.util.Date;

public class EnterprisePO {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ENTERPRISE.CREDIT_CODE
     *
     * @mbggenerated
     */
    private String CREDIT_CODE;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ENTERPRISE.NAME
     *
     * @mbggenerated
     */
    private String NAME;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ENTERPRISE.TYPE
     *
     * @mbggenerated
     */
    private String TYPE;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ENTERPRISE.LEGAL_PERSON
     *
     * @mbggenerated
     */
    private String LEGAL_PERSON;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ENTERPRISE.REG_CAPITAL
     *
     * @mbggenerated
     */
    private BigDecimal REG_CAPITAL;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ENTERPRISE.ESTABLISH_DATE
     *
     * @mbggenerated
     */
    private Date ESTABLISH_DATE;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ENTERPRISE.BIZ_PERIOD_START
     *
     * @mbggenerated
     */
    private Date BIZ_PERIOD_START;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ENTERPRISE.BIZ_PERIOD_END
     *
     * @mbggenerated
     */
    private Date BIZ_PERIOD_END;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ENTERPRISE.REG_AUTHORITY
     *
     * @mbggenerated
     */
    private String REG_AUTHORITY;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ENTERPRISE.ADDRESS
     *
     * @mbggenerated
     */
    private String ADDRESS;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ENTERPRISE.PHONE
     *
     * @mbggenerated
     */
    private String PHONE;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ENTERPRISE.BANK
     *
     * @mbggenerated
     */
    private String BANK;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ENTERPRISE.BANK_ACCOUNT
     *
     * @mbggenerated
     */
    private String BANK_ACCOUNT;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ENTERPRISE.CREATE_TIME
     *
     * @mbggenerated
     */
    private Date CREATE_TIME;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ENTERPRISE.CREATE_BY
     *
     * @mbggenerated
     */
    private String CREATE_BY;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ENTERPRISE.MODIFY_TIME
     *
     * @mbggenerated
     */
    private Date MODIFY_TIME;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ENTERPRISE.MODIFY_BY
     *
     * @mbggenerated
     */
    private String MODIFY_BY;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ENTERPRISE.MAIN_BIZ
     *
     * @mbggenerated
     */
    private byte[] MAIN_BIZ;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ENTERPRISE.CREDIT_CODE
     *
     * @return the value of ENTERPRISE.CREDIT_CODE
     *
     * @mbggenerated
     */
    public String getCREDIT_CODE() {
        return CREDIT_CODE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ENTERPRISE.CREDIT_CODE
     *
     * @param CREDIT_CODE the value for ENTERPRISE.CREDIT_CODE
     *
     * @mbggenerated
     */
    public void setCREDIT_CODE(String CREDIT_CODE) {
        this.CREDIT_CODE = CREDIT_CODE == null ? null : CREDIT_CODE.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ENTERPRISE.NAME
     *
     * @return the value of ENTERPRISE.NAME
     *
     * @mbggenerated
     */
    public String getNAME() {
        return NAME;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ENTERPRISE.NAME
     *
     * @param NAME the value for ENTERPRISE.NAME
     *
     * @mbggenerated
     */
    public void setNAME(String NAME) {
        this.NAME = NAME == null ? null : NAME.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ENTERPRISE.TYPE
     *
     * @return the value of ENTERPRISE.TYPE
     *
     * @mbggenerated
     */
    public String getTYPE() {
        return TYPE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ENTERPRISE.TYPE
     *
     * @param TYPE the value for ENTERPRISE.TYPE
     *
     * @mbggenerated
     */
    public void setTYPE(String TYPE) {
        this.TYPE = TYPE == null ? null : TYPE.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ENTERPRISE.LEGAL_PERSON
     *
     * @return the value of ENTERPRISE.LEGAL_PERSON
     *
     * @mbggenerated
     */
    public String getLEGAL_PERSON() {
        return LEGAL_PERSON;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ENTERPRISE.LEGAL_PERSON
     *
     * @param LEGAL_PERSON the value for ENTERPRISE.LEGAL_PERSON
     *
     * @mbggenerated
     */
    public void setLEGAL_PERSON(String LEGAL_PERSON) {
        this.LEGAL_PERSON = LEGAL_PERSON == null ? null : LEGAL_PERSON.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ENTERPRISE.REG_CAPITAL
     *
     * @return the value of ENTERPRISE.REG_CAPITAL
     *
     * @mbggenerated
     */
    public BigDecimal getREG_CAPITAL() {
        return REG_CAPITAL;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ENTERPRISE.REG_CAPITAL
     *
     * @param REG_CAPITAL the value for ENTERPRISE.REG_CAPITAL
     *
     * @mbggenerated
     */
    public void setREG_CAPITAL(BigDecimal REG_CAPITAL) {
        this.REG_CAPITAL = REG_CAPITAL;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ENTERPRISE.ESTABLISH_DATE
     *
     * @return the value of ENTERPRISE.ESTABLISH_DATE
     *
     * @mbggenerated
     */
    public Date getESTABLISH_DATE() {
        return ESTABLISH_DATE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ENTERPRISE.ESTABLISH_DATE
     *
     * @param ESTABLISH_DATE the value for ENTERPRISE.ESTABLISH_DATE
     *
     * @mbggenerated
     */
    public void setESTABLISH_DATE(Date ESTABLISH_DATE) {
        this.ESTABLISH_DATE = ESTABLISH_DATE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ENTERPRISE.BIZ_PERIOD_START
     *
     * @return the value of ENTERPRISE.BIZ_PERIOD_START
     *
     * @mbggenerated
     */
    public Date getBIZ_PERIOD_START() {
        return BIZ_PERIOD_START;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ENTERPRISE.BIZ_PERIOD_START
     *
     * @param BIZ_PERIOD_START the value for ENTERPRISE.BIZ_PERIOD_START
     *
     * @mbggenerated
     */
    public void setBIZ_PERIOD_START(Date BIZ_PERIOD_START) {
        this.BIZ_PERIOD_START = BIZ_PERIOD_START;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ENTERPRISE.BIZ_PERIOD_END
     *
     * @return the value of ENTERPRISE.BIZ_PERIOD_END
     *
     * @mbggenerated
     */
    public Date getBIZ_PERIOD_END() {
        return BIZ_PERIOD_END;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ENTERPRISE.BIZ_PERIOD_END
     *
     * @param BIZ_PERIOD_END the value for ENTERPRISE.BIZ_PERIOD_END
     *
     * @mbggenerated
     */
    public void setBIZ_PERIOD_END(Date BIZ_PERIOD_END) {
        this.BIZ_PERIOD_END = BIZ_PERIOD_END;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ENTERPRISE.REG_AUTHORITY
     *
     * @return the value of ENTERPRISE.REG_AUTHORITY
     *
     * @mbggenerated
     */
    public String getREG_AUTHORITY() {
        return REG_AUTHORITY;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ENTERPRISE.REG_AUTHORITY
     *
     * @param REG_AUTHORITY the value for ENTERPRISE.REG_AUTHORITY
     *
     * @mbggenerated
     */
    public void setREG_AUTHORITY(String REG_AUTHORITY) {
        this.REG_AUTHORITY = REG_AUTHORITY == null ? null : REG_AUTHORITY.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ENTERPRISE.ADDRESS
     *
     * @return the value of ENTERPRISE.ADDRESS
     *
     * @mbggenerated
     */
    public String getADDRESS() {
        return ADDRESS;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ENTERPRISE.ADDRESS
     *
     * @param ADDRESS the value for ENTERPRISE.ADDRESS
     *
     * @mbggenerated
     */
    public void setADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS == null ? null : ADDRESS.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ENTERPRISE.PHONE
     *
     * @return the value of ENTERPRISE.PHONE
     *
     * @mbggenerated
     */
    public String getPHONE() {
        return PHONE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ENTERPRISE.PHONE
     *
     * @param PHONE the value for ENTERPRISE.PHONE
     *
     * @mbggenerated
     */
    public void setPHONE(String PHONE) {
        this.PHONE = PHONE == null ? null : PHONE.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ENTERPRISE.BANK
     *
     * @return the value of ENTERPRISE.BANK
     *
     * @mbggenerated
     */
    public String getBANK() {
        return BANK;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ENTERPRISE.BANK
     *
     * @param BANK the value for ENTERPRISE.BANK
     *
     * @mbggenerated
     */
    public void setBANK(String BANK) {
        this.BANK = BANK == null ? null : BANK.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ENTERPRISE.BANK_ACCOUNT
     *
     * @return the value of ENTERPRISE.BANK_ACCOUNT
     *
     * @mbggenerated
     */
    public String getBANK_ACCOUNT() {
        return BANK_ACCOUNT;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ENTERPRISE.BANK_ACCOUNT
     *
     * @param BANK_ACCOUNT the value for ENTERPRISE.BANK_ACCOUNT
     *
     * @mbggenerated
     */
    public void setBANK_ACCOUNT(String BANK_ACCOUNT) {
        this.BANK_ACCOUNT = BANK_ACCOUNT == null ? null : BANK_ACCOUNT.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ENTERPRISE.CREATE_TIME
     *
     * @return the value of ENTERPRISE.CREATE_TIME
     *
     * @mbggenerated
     */
    public Date getCREATE_TIME() {
        return CREATE_TIME;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ENTERPRISE.CREATE_TIME
     *
     * @param CREATE_TIME the value for ENTERPRISE.CREATE_TIME
     *
     * @mbggenerated
     */
    public void setCREATE_TIME(Date CREATE_TIME) {
        this.CREATE_TIME = CREATE_TIME;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ENTERPRISE.CREATE_BY
     *
     * @return the value of ENTERPRISE.CREATE_BY
     *
     * @mbggenerated
     */
    public String getCREATE_BY() {
        return CREATE_BY;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ENTERPRISE.CREATE_BY
     *
     * @param CREATE_BY the value for ENTERPRISE.CREATE_BY
     *
     * @mbggenerated
     */
    public void setCREATE_BY(String CREATE_BY) {
        this.CREATE_BY = CREATE_BY == null ? null : CREATE_BY.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ENTERPRISE.MODIFY_TIME
     *
     * @return the value of ENTERPRISE.MODIFY_TIME
     *
     * @mbggenerated
     */
    public Date getMODIFY_TIME() {
        return MODIFY_TIME;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ENTERPRISE.MODIFY_TIME
     *
     * @param MODIFY_TIME the value for ENTERPRISE.MODIFY_TIME
     *
     * @mbggenerated
     */
    public void setMODIFY_TIME(Date MODIFY_TIME) {
        this.MODIFY_TIME = MODIFY_TIME;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ENTERPRISE.MODIFY_BY
     *
     * @return the value of ENTERPRISE.MODIFY_BY
     *
     * @mbggenerated
     */
    public String getMODIFY_BY() {
        return MODIFY_BY;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ENTERPRISE.MODIFY_BY
     *
     * @param MODIFY_BY the value for ENTERPRISE.MODIFY_BY
     *
     * @mbggenerated
     */
    public void setMODIFY_BY(String MODIFY_BY) {
        this.MODIFY_BY = MODIFY_BY == null ? null : MODIFY_BY.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ENTERPRISE.MAIN_BIZ
     *
     * @return the value of ENTERPRISE.MAIN_BIZ
     *
     * @mbggenerated
     */
    public byte[] getMAIN_BIZ() {
        return MAIN_BIZ;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ENTERPRISE.MAIN_BIZ
     *
     * @param MAIN_BIZ the value for ENTERPRISE.MAIN_BIZ
     *
     * @mbggenerated
     */
    public void setMAIN_BIZ(byte[] MAIN_BIZ) {
        this.MAIN_BIZ = MAIN_BIZ;
    }
}