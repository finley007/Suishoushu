package com.changyi.fi.core.model;

import java.util.Date;

public class SysAccessPO {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SYS_ACCESS.ID
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SYS_ACCESS.IP
     *
     * @mbggenerated
     */
    private String ip;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SYS_ACCESS.PORT
     *
     * @mbggenerated
     */
    private String port;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SYS_ACCESS.URL
     *
     * @mbggenerated
     */
    private String url;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SYS_ACCESS.ACCESS_TIME
     *
     * @mbggenerated
     */
    private Date accessTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SYS_ACCESS.ID
     *
     * @return the value of SYS_ACCESS.ID
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SYS_ACCESS.ID
     *
     * @param id the value for SYS_ACCESS.ID
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SYS_ACCESS.IP
     *
     * @return the value of SYS_ACCESS.IP
     *
     * @mbggenerated
     */
    public String getIp() {
        return ip;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SYS_ACCESS.IP
     *
     * @param ip the value for SYS_ACCESS.IP
     *
     * @mbggenerated
     */
    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SYS_ACCESS.PORT
     *
     * @return the value of SYS_ACCESS.PORT
     *
     * @mbggenerated
     */
    public String getPort() {
        return port;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SYS_ACCESS.PORT
     *
     * @param port the value for SYS_ACCESS.PORT
     *
     * @mbggenerated
     */
    public void setPort(String port) {
        this.port = port == null ? null : port.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SYS_ACCESS.URL
     *
     * @return the value of SYS_ACCESS.URL
     *
     * @mbggenerated
     */
    public String getUrl() {
        return url;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SYS_ACCESS.URL
     *
     * @param url the value for SYS_ACCESS.URL
     *
     * @mbggenerated
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SYS_ACCESS.ACCESS_TIME
     *
     * @return the value of SYS_ACCESS.ACCESS_TIME
     *
     * @mbggenerated
     */
    public Date getAccessTime() {
        return accessTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SYS_ACCESS.ACCESS_TIME
     *
     * @param accessTime the value for SYS_ACCESS.ACCESS_TIME
     *
     * @mbggenerated
     */
    public void setAccessTime(Date accessTime) {
        this.accessTime = accessTime;
    }
}