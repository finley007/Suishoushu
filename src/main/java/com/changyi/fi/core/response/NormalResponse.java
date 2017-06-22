package com.changyi.fi.core.response;

import com.changyi.fi.core.Payload;

/**
 * Created by finley on 6/22/17.
 */
public class NormalResponse {

    public String build() {
        String content = new Payload(this).from(this.getClass());
        if (!"{}".equals(content)) {
            return "{ \"returnCode\" : \"0\", \"content\" : " + content + " }";
        } else {
            return "{ \"returnCode\" : \"0\", \"content\" : \"success\" }";
        }
    }

}
