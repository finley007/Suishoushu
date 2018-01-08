package com.changyi.fi.component.merchant.response;

import com.changyi.fi.core.response.NormalResponse;

public class UpdateChannelResponse extends NormalResponse {

    public UpdateChannelResponse(String channelId, String url) {
        this.channelId = channelId;
        this.url = url;
    }

    private String channelId;

    private String url;

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
