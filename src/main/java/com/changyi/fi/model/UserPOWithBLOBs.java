package com.changyi.fi.model;

public class UserPOWithBLOBs extends UserPO {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.ssl_cipher
     *
     * @mbggenerated
     */
    private byte[] ssl_cipher;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.x509_issuer
     *
     * @mbggenerated
     */
    private byte[] x509_issuer;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.x509_subject
     *
     * @mbggenerated
     */
    private byte[] x509_subject;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.authentication_string
     *
     * @mbggenerated
     */
    private String authentication_string;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.ssl_cipher
     *
     * @return the value of user.ssl_cipher
     *
     * @mbggenerated
     */
    public byte[] getSsl_cipher() {
        return ssl_cipher;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.ssl_cipher
     *
     * @param ssl_cipher the value for user.ssl_cipher
     *
     * @mbggenerated
     */
    public void setSsl_cipher(byte[] ssl_cipher) {
        this.ssl_cipher = ssl_cipher;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.x509_issuer
     *
     * @return the value of user.x509_issuer
     *
     * @mbggenerated
     */
    public byte[] getX509_issuer() {
        return x509_issuer;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.x509_issuer
     *
     * @param x509_issuer the value for user.x509_issuer
     *
     * @mbggenerated
     */
    public void setX509_issuer(byte[] x509_issuer) {
        this.x509_issuer = x509_issuer;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.x509_subject
     *
     * @return the value of user.x509_subject
     *
     * @mbggenerated
     */
    public byte[] getX509_subject() {
        return x509_subject;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.x509_subject
     *
     * @param x509_subject the value for user.x509_subject
     *
     * @mbggenerated
     */
    public void setX509_subject(byte[] x509_subject) {
        this.x509_subject = x509_subject;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.authentication_string
     *
     * @return the value of user.authentication_string
     *
     * @mbggenerated
     */
    public String getAuthentication_string() {
        return authentication_string;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.authentication_string
     *
     * @param authentication_string the value for user.authentication_string
     *
     * @mbggenerated
     */
    public void setAuthentication_string(String authentication_string) {
        this.authentication_string = authentication_string == null ? null : authentication_string.trim();
    }
}