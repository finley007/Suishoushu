package com.changyi.fi.component.statistics.request;

import com.changyi.fi.util.FIConstants;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class CustomerStatRequest {

    public CustomerStatRequest() {
        initEndDate();
    }

    public void initEndDate() {
        if (this.startDate == null) {
            this.startDate = new Date();
        }
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(this.startDate);
        calendar.add(calendar.DATE,1);
        this.endDate = calendar.getTime();
    }

    private Date startDate = new Date();

    private Date endDate ;

    private int customerTopN = 10;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getCustomerTopN() {
        return customerTopN;
    }

    public void setCustomerTopN(int customerTopN) {
        this.customerTopN = customerTopN;
    }

    public String getStrStartDate() {
        return FIConstants.sdf.format(this.startDate);
    }

    public String getStrEndDate() {
        return FIConstants.sdf.format(this.endDate);
    }
}
