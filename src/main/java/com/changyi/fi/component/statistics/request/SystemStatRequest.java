package com.changyi.fi.component.statistics.request;

import com.changyi.fi.util.FIConstants;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class SystemStatRequest {

    public SystemStatRequest() {
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

    public String getStrStartDate() {
        return FIConstants.sdf.format(this.startDate);
    }

    public String getStrEndDate() {
        return FIConstants.sdf.format(this.endDate);
    }
}
