package com.changyi.fi.component.invoice.response;

import com.changyi.fi.core.response.NormalResponse;

import java.util.List;

public class CreateMerchantIDResponse extends NormalResponse {

    public CreateMerchantIDResponse(List<String> idList) {
        this.idList = idList;
    }

    public List<String> getIdList() {
        return idList;
    }

    public void setIdList(List<String> idList) {
        this.idList = idList;
    }

    List<String> idList;
}
