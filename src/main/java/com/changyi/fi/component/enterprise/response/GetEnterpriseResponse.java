package com.changyi.fi.component.enterprise.response;

import com.changyi.fi.core.Payload;
import com.changyi.fi.core.response.NormalResponse;
import com.changyi.fi.model.EnterprisePO;

public class GetEnterpriseResponse extends NormalResponse {

    public GetEnterpriseResponse(EnterprisePO enterprise) {
        this.enterprise = enterprise;
    }

    public EnterprisePO getEnterprisePO() {
        return enterprise;
    }

    public void setEnterprisePO(EnterprisePO enterprise) {
        this.enterprise = enterprise;
    }

    private EnterprisePO enterprise;

    public String build() {
        if (this.enterprise == null) {
            return "{ \"returnCode\" : \"0\", \"content\" : \"\" }";
        }
        String content = new Payload(this.enterprise).from(this.enterprise.getClass());
        if (!"{}".equals(content)) {
            return "{ \"returnCode\" : \"0\", \"content\" : " + content + " }";
        } else {
            return "{ \"returnCode\" : \"0\", \"content\" : \"success\" }";
        }
    }

}
