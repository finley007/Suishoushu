package com.changyi.fi.component.merchant.request;

import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;

public class QRCodesRequest {

    @NotNull(message = "channelId is required")
    @NotEmpty(message = "channelId is required")
    private String channelId;

    private int number = 1;

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
