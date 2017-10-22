package com.changyi.fi.core.response;

import com.changyi.fi.core.Payload;

/**
 * Created by finley on 6/22/17.
 */
public class NormalResponse implements Response {

    protected String createContent() {
        return new Payload(this).from(this.getClass());
    }

    public String build() {
        String content = this.createContent();
        if (!"{}".equals(content)) {
            return "{ \"returnCode\" : \"0\", \"content\" : " + content + " }";
        } else {
            return "{ \"returnCode\" : \"0\", \"content\" : \"success\" }";
        }
    }

}
