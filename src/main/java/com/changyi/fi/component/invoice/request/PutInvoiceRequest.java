package com.changyi.fi.component.invoice.request;

import com.changyi.fi.core.request.Request;
import net.sf.oval.constraint.MatchPattern;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;

/**
 * Created by finley on 6/21/17.
 */
public class PutInvoiceRequest implements Request {

    private String id;

    @NotNull(message = "type is required")
    @NotEmpty(message = "type is required")
    @MatchPattern(pattern = "^0|1$", message = "invalid type value, should be 0 or 1")
    private String type;

    @NotNull(message = "name is required")
    @NotEmpty(message = "name is required")
    private String name;

    private String address;

    private String creditCode;

    private String phone;

    private String bank;

    private String bankAcct;

    private String email;

    @NotNull(message = "isDefault is required")
    @NotEmpty(message = "isDefault is required")
    @MatchPattern(pattern = "^0|1$", message = "invalid isDefault value, should be 0 or 1")
    private String isDefault;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String userName) {
        this.name = userName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreditCode() {
        return creditCode;
    }

    public void setCreditCode(String creditCode) {
        this.creditCode = creditCode;
    }

    public String getPhone() {
        return phone;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBankAcct() {
        return bankAcct;
    }

    public void setBankAcct(String bankAcct) {
        this.bankAcct = bankAcct;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }
}
