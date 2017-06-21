package com.changyi.fi.core.response;

import com.changyi.fi.core.Payload;

/**
 * Created by finley on 2/18/17.
 */
public class ResponseWrapper {

    private Object res;

    public ResponseWrapper(Object res) {
        this.res = res;
    }

    public <T> String build(Class<T> clz) {
        if (res == null) {
            return "";
        }
        if (this.res instanceof ErrorResponse) {
            return new Payload(this.res).from(ErrorResponse.class);
        } else {
            String content = new Payload(this.res).from(clz);
            return "{ \"returnCode\" : \"0\", \"content\" : " + content + " }";
        }
    }

}
