package com.changyi.fi.vo;

import com.changyi.fi.util.FIConstants;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;

public class Merchant {

    @NotNull(message = "id is required")
    @NotEmpty(message = "id is required")
    private String id;

    @NotNull(message = "channelId is required")
    @NotEmpty(message = "channelId is required")
    private String channelId;

    @NotNull(message = "name is required")
    @NotEmpty(message = "name is required")
    private String name;

    private short type = FIConstants.MERCHANT_DEFAULT_TYPE;

    @NotNull(message = "address is required")
    @NotEmpty(message = "address is required")
    private String address;

    private String email;

    private String phone1;

    private String phone2;

    private String zipCode;

    @NotNull(message = "longitude is required")
    private Double longitude;

    @NotNull(message = "latitude is required")
    private Double latitude;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public short getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
