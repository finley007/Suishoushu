package com.changyi.fi.component.invoice.response;

import com.changyi.fi.core.Payload;
import com.changyi.fi.core.response.NormalResponse;

/**
 * Created by finley on 8/3/17.
 */
public class UpdateInvoiceResponse extends NormalResponse {

    public UpdateInvoiceResponse(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;

    public String build() {
        String content = new Payload(this).from(this.getClass());
        if (!"{}".equals(content)) {
            return "{ \"returnCode\" : \"0\", \"content\" : " + content + " }";
        } else {
            return "{ \"returnCode\" : \"0\", \"content\" : \"success\" }";
        }
    }
}
