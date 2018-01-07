package com.changyi.fi.component.merchant.response;

import com.changyi.fi.core.response.NormalResponse;
import com.changyi.fi.vo.Channel;

import java.util.List;

public class ChannelListResponse extends NormalResponse {

    public ChannelListResponse(List<Channel> channelList) {
        this.channelList = channelList;
    }

    List<Channel> channelList;

    public List<Channel> getChannelList() {
        return channelList;
    }

    public void setChannelList(List<Channel> channelList) {
        this.channelList = channelList;
    }
}
